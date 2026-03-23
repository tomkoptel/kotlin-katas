package tom.koptel.coroutines

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ClosedSendChannelException
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.UUID

/**
 * # Worker Pool — fan-out pattern with bounded concurrency
 *
 * The goal: process a list of tasks with exactly N concurrent workers, no more.
 *
 * ## Architecture
 *
 * ```
 * Producer (execute block)          Channel (buffered queue)         Workers (N coroutines)
 * ┌─────────────────────┐          ┌──────────────────────┐         ┌──────────────────┐
 * │ enqueue(task1)  ────┼─send()──>│ [task1, task2, ...]  │──recv──>│ Worker-0: task()  │
 * │ enqueue(task2)  ────┼─send()──>│                      │──recv──>│ Worker-1: task()  │
 * │ enqueue(task3)  ────┼─send()──>│                      │──recv──>│ Worker-2: task()  │
 * │ ...                 │          │                      │         │ ...               │
 * └────────┬────────────┘          └──────────────────────┘         └──────────────────┘
 *          │ .use { } closes                                          ▲
 *          │ channel on exit                                          │ on failure:
 *          ▼                                                          │ retry re-enqueues
 *    channel.close()                                                  │ back into channel
 * ```
 *
 * ## How concurrency is bounded
 *
 * Each worker is a single coroutine running `for (task in channel) { task() }`.
 * A worker cannot pick up the next task until `task()` completes (including if it uses
 * `withContext` internally — that suspends the worker until it returns).
 * Since there are exactly N workers, at most N tasks execute concurrently.
 * The channel is the coordination mechanism — each element can only be received by one worker.
 *
 * ## Lifecycle
 *
 * 1. `coroutineScope` launches N worker coroutines, each looping over the channel.
 * 2. The `execute` block runs, calling `enqueue()` which sends tasks into the channel.
 * 3. When `execute` completes, `WorkerPoolScope.use {}` calls `close()` → `channel.close()`.
 * 4. Workers see the closed channel — `for (task in channel)` ends naturally.
 * 5. All worker coroutines complete → `coroutineScope` returns.
 *
 * ## Error handling
 *
 * - `task.action()` exceptions are caught per-worker — one failing task does not kill the pool.
 * - `CancellationException` is re-thrown to respect structured concurrency cancellation.
 * - Failed tasks are retried up to `maxRetries` (default 3) by re-enqueuing a new Task with
 *   an incremented retry counter.
 * - If the channel is already closed when a retry is enqueued, `ClosedSendChannelException`
 *   is caught and logged — the retry is silently dropped.
 *
 * ## Why `coroutineScope` and not `supervisorScope`
 *
 * All task-level errors are caught inside the try/catch — they never propagate to the scope.
 * `coroutineScope` is used so that a structural failure (e.g., channel machinery bug) cancels
 * all workers and fails fast, rather than silently degrading with fewer workers.
 *
 * ## Caveat: retries and channel lifetime
 *
 * Retries are re-enqueued into the same channel. If the `execute` block finishes and closes
 * the channel before retries are processed, they are dropped. Callers that rely on retries
 * should keep the channel open long enough (e.g., add `delay()` after enqueuing).
 */
class ChannelWorkerPoolTest {
    @Test
    fun `all tasks are processed`() = runTest {
        val completed = mutableListOf<Int>()
        val mutex = Mutex()
        workerPool(channelCapacity = 10) {
            (0..20).forEach { i ->
                enqueue {
                    delay(100)
                    mutex.withLock { completed.add(i) }
                }
            }
        }
        assertEquals((0..20).toSet(), completed.toSet())
    }

    @Test
    fun `concurrency is bounded by workerCount`() = runTest {
        var maxConcurrent = 0
        var currentConcurrent = 0
        val mutex = Mutex()
        val workerCount = 3

        workerPool(channelCapacity = 10, workerCount = workerCount) {
            (0..20).forEach { i ->
                enqueue {
                    mutex.withLock {
                        currentConcurrent++
                        if (currentConcurrent > maxConcurrent) {
                            maxConcurrent = currentConcurrent
                        }
                    }
                    delay(100)
                    mutex.withLock { currentConcurrent-- }
                }
            }
        }
        assertTrue(maxConcurrent <= workerCount, "Max concurrent $maxConcurrent exceeded workerCount $workerCount")
    }

    @Test
    fun `failing task is retried up to maxRetries`() = runTest {
        var attempts = 0
        val mutex = Mutex()

        workerPool(channelCapacity = 10, workerCount = 2) {
            enqueue {
                mutex.withLock { attempts++ }
                throw RuntimeException("Boom")
            }
            // Keep channel open long enough for retries to be re-enqueued
            delay(1000)
        }
        // 1 initial + 3 retries = 4
        assertEquals(4, attempts)
    }

    @Test
    fun `failing task does not prevent other tasks from completing`() = runTest {
        val completed = mutableListOf<Int>()
        val mutex = Mutex()

        workerPool(channelCapacity = 10, workerCount = 2) {
            enqueue { throw RuntimeException("Boom") }
            (1..5).forEach { i ->
                enqueue {
                    delay(50)
                    mutex.withLock { completed.add(i) }
                }
            }
        }
        assertEquals((1..5).toSet(), completed.toSet())
    }

    @Test
    fun `task using withContext still respects concurrency bound`() = runTest {
        var maxConcurrent = 0
        var currentConcurrent = 0
        val mutex = Mutex()
        val workerCount = 2

        workerPool(channelCapacity = 10, workerCount = workerCount) {
            (0..10).forEach { _ ->
                enqueue {
                    withContext(Dispatchers.Unconfined) {
                        mutex.withLock {
                            currentConcurrent++
                            if (currentConcurrent > maxConcurrent) {
                                maxConcurrent = currentConcurrent
                            }
                        }
                        delay(100)
                        mutex.withLock { currentConcurrent-- }
                    }
                }
            }
        }
        assertTrue(maxConcurrent <= workerCount, "Max concurrent $maxConcurrent exceeded workerCount $workerCount")
    }

    @Test
    fun `empty pool completes immediately`() = runTest {
        workerPool(channelCapacity = 10, workerCount = 3) {
            // enqueue nothing
        }
    }

    /**
     * Creates a worker pool that processes tasks with bounded concurrency.
     *
     * @param channelCapacity buffer size for the task queue. Controls backpressure —
     *   when the buffer is full, `enqueue()` suspends until a worker picks up a task.
     * @param workerCount number of concurrent worker coroutines. This is the hard upper
     *   bound on parallelism — no more than this many tasks execute at once.
     * @param execute the producer block that enqueues tasks via [WorkerPoolScope.enqueue].
     *   When this block returns, the channel is closed and workers drain remaining tasks.
     */
    private suspend fun workerPool(
        channelCapacity: Int,
        workerCount: Int = Runtime.getRuntime().availableProcessors(),
        execute: suspend WorkerPoolScope.() -> Unit,
    ) {
        val channel = Channel<Task>(capacity = channelCapacity)
        val workerPoolScope = WorkerPoolScope(channel)

        // coroutineScope ensures we wait for all workers to finish before returning.
        // If any worker fails structurally (not a task failure), all workers are cancelled.
        coroutineScope {
            // Fan-out: launch N workers that compete for tasks from the shared channel.
            // Channel guarantees each task is delivered to exactly one worker.
            repeat(workerCount) { worker ->
                launch {
                    // Iterates until channel is closed AND empty.
                    for (task in channel) {
                        try {
                            println("worker=$worker picks=$task")
                            task.action()
                        } catch (ex: CancellationException) {
                            // Never swallow cancellation — propagate to respect
                            // structured concurrency (e.g., scope cancelled from outside).
                            throw ex
                        } catch (ex: Exception) {
                            // Task-level failure: log and attempt retry.
                            // The worker stays alive and picks up the next task.
                            println("Stumbled on exception ${ex.message} re-enqueue the task")
                            task.retry { newTask ->
                                try {
                                    workerPoolScope.enqueue(newTask)
                                } catch (ex: ClosedSendChannelException) {
                                    // Channel closed between failure and retry —
                                    // the execute block already finished. Drop the retry.
                                    println("Can not schedule new work channel closed ${ex.message}")
                                }
                            }
                        }
                    }
                }
            }

            // Producer: runs the caller's block then closes the channel via AutoCloseable.
            // .use {} guarantees channel.close() even if execute throws.
            // Closing signals workers that no more tasks will arrive —
            // they finish their current task, drain any buffered tasks, then exit.
            workerPoolScope.use {
                it.execute()
            }
        }
    }

    /**
     * Scoped interface exposed to the producer block.
     * Provides [enqueue] to submit tasks and implements [AutoCloseable]
     * to close the underlying channel when the producer finishes (via `.use {}`).
     */
    private class WorkerPoolScope(
        private val channel: Channel<Task>,
    ) : AutoCloseable {
        /** Enqueue a [Task] (used internally by retry logic). */
        suspend fun enqueue(task: Task) {
            channel.send(task)
        }

        /** Enqueue a suspend lambda as a new task. */
        suspend fun enqueue(task: suspend () -> Unit) {
            channel.send(Task(action = task))
        }

        /** Closes the channel, signaling workers that no more tasks will arrive. */
        override fun close() {
            channel.close()
        }
    }

    /**
     * Represents a unit of work in the pool.
     *
     * @param id stable identifier preserved across retries, useful for logging/tracking.
     * @param retries current retry count. Starts at 0, incremented on each retry.
     * @param maxRetries upper bound on retries. After this, the task is dropped.
     * @param action the suspend function that performs the actual work.
     */
    private class Task(
        val id: String = UUID.randomUUID().toString(),
        val retries: Int = 0,
        val maxRetries: Int = 3,
        val action: suspend () -> Unit,
    ) {
        /**
         * If retries remain, creates a copy with incremented retry count and passes it
         * to [block] (which re-enqueues it). Otherwise logs and drops the task.
         * Inline so the lambda (which calls suspend enqueue) doesn't allocate a wrapper.
         */
        inline fun retry(block: (Task) -> Unit) {
            if (retries < maxRetries) {
                block(Task(id = id, action = action, retries = retries + 1))
            } else {
                println("Can't retry reached threshold of retries=$retries")
            }
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Task

            if (retries != other.retries) return false
            if (maxRetries != other.maxRetries) return false
            if (id != other.id) return false

            return true
        }

        override fun hashCode(): Int {
            var result = retries
            result = 31 * result + maxRetries
            result = 31 * result + id.hashCode()
            return result
        }

        override fun toString(): String {
            return "Task(id='$id', retries=$retries)"
        }
    }
}
