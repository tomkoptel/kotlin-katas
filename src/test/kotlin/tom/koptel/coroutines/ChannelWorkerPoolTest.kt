package tom.koptel.coroutines

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ClosedSendChannelException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.Test
import java.util.UUID

class ChannelWorkerPoolTest {
    /**
     * The goal: process a list of tasks with exactly N concurrent workers, no more.
     */
    @Test
    fun impl() = runTest {
        workerPool(channelCapacity = 10) {
            (0..20).forEach { i ->
                enqueue {
                    println("Started task=$i")
                    delay(100)
                    println("Completed task=$i")
                }
            }
        }
    }

    private suspend fun workerPool(
        channelCapacity: Int,
        workerCount: Int = Runtime.getRuntime().availableProcessors(),
        execute: suspend WorkerPoolScope.() -> Unit,
    ) {
        val channel = Channel<Task>(capacity = channelCapacity)
        val workerPoolScope = WorkerPoolScope(channel)

        supervisorScope {
            repeat(workerCount) { worker ->
                launch {
                    for (task in channel) {
                        try {
                            println("worker=$worker picks=$task")
                            task.action()
                        } catch (ex: CancellationException) {
                            throw ex
                        } catch (ex: Exception) {
                            println("Stumbled on exception ${ex.message} re-enqueue the task")
                            task.retry { newTask ->
                                try {
                                    workerPoolScope.enqueue(newTask)
                                } catch (ex: ClosedSendChannelException) {
                                    println("Can not schedule new work channel closed ${ex.message}")
                                }
                            }
                        }
                    }
                }
            }

            workerPoolScope.use {
                it.execute()
            }
        }
    }

    private class WorkerPoolScope(
        private val channel: Channel<Task>,
    ) : AutoCloseable {
        suspend fun enqueue(task: Task) {
            channel.send(task)
        }

        suspend fun enqueue(task: suspend () -> Unit) {
            channel.send(Task(action = task))
        }

        override fun close() {
            channel.close()
        }
    }

    private class Task(
        val id: String = UUID.randomUUID().toString(),
        val retries: Int = 0,
        val maxRetries: Int = 3,
        val action: suspend () -> Unit,
    ) {
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
