package tom.koptel.coroutines

import com.sample.tom.ds.list.LinkedList
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.onTimeout
import kotlinx.coroutines.selects.select
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withTimeout
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class, ObsoleteCoroutinesApi::class)
class ChannelTickerSelectTest {
    @Test
    fun `ticker + launch + select`() = runTest {
        val ticker = ticker(delayMillis = 150)
        val channel = Channel<String>()

        launch(Dispatchers.Unconfined) {
            repeat(5) {
                delay(100)
                channel.send("${it}")
            }
            channel.close()
        }

        select {
            channel.onReceive { value ->
                println("received value=$value")
            }
            ticker.onReceive { value ->
                println("ticker value=$value")
            }
        }

        coroutineContext.cancelChildren()
    }

    @Test
    fun `implementing raceN`() = runTest {
        suspend fun <R : Any?> raceN(racers: List<suspend () -> R>): R = coroutineScope {
            val deferreds = racers.map { async { it() } }
            val result = select {
                deferreds.forEach { deffer -> deffer.onAwait { it } }
            }
            // dedicated scope + select + cancel losers.
            coroutineContext.cancelChildren()
            result
        }

        val result = raceN(
            listOf(
                {
                    try {
                        delay(100)
                        "from DB"
                    } catch (ex: CancellationException) {
                        println("DB read cancelled")
                        throw ex
                    }
                },
                {
                    try {
                        delay(200)
                        "from Net"
                    } catch (ex: CancellationException) {
                        println("Network request cancelled")
                        throw ex
                    }
                },
                {
                    delay(50)
                    "from Cache"
                }
            ))

        println("result=$result")

        advanceTimeBy(50)
    }

    @Test
    fun `multiplexing 3 channels + select`() = runTest {
        coroutineScope {
            val channel1 = Channel<String>()
            launch { repeat(times = 5) { delay(100); channel1.send("channel1 - $it") }; channel1.close() }
            val channel2 = Channel<String>()
            launch { repeat(times = 5) { delay(50); channel2.send("channel2 - $it") }; channel2.close() }
            val channel3 = Channel<String>()
            launch { repeat(times = 5) { delay(20); channel3.send("channel3 - $it") }; channel3.close() }

            val channels = mutableListOf(channel1, channel2, channel3)

            launch {
                while (channels.isNotEmpty()) {
                    coroutineContext.ensureActive()
                    select {
                        channels.forEach { channel ->
                            channel.onReceiveCatching { result ->
                                if (result.isSuccess) {
                                    result.getOrNull()?.let { println(it) }
                                } else {
                                    channels.remove(channel)
                                }
                            }
                        }
                    }
                }
            }
        }
        advanceUntilIdle()
    }

    @Test
    fun `onTimeout clause inside select`() = runTest {
        coroutineScope {
            val api = async {
                try {
                    delay(501)
                    "api result"
                } catch (ex: CancellationException) {
                    println("Api call cancelled")
                    throw ex
                }
            }
            val result = select {
                api.onAwait { it }
                onTimeout(timeMillis = 500) {
                    "cache result"
                }
            }
            coroutineContext.cancelChildren()
            println(result)
        }
        advanceUntilIdle()
    }

    /**
     * "We're building a price comparison service. The user searches for a product, and we query 5 different vendor APIs concurrently. Requirements:
     *
     * Each vendor API has a 2-second SLA — if a vendor doesn't respond within 2 seconds, skip it
     * As soon as we have results from at least 3 vendors (or all have either responded or timed out), return the aggregated results to the user
     * If a vendor API throws an exception, it shouldn't affect the other vendors
     * We want to log which vendors were slow or failed
     *
     * Design the coroutine architecture for this. Walk me through the channel topology, the patterns you'd use, and then implement it."
     */
    @Test
    fun `price comparison`() = runTest {
        suspend fun producer(it: Int): String {
            val delay = it * 300L
            println("starting vendor=$it with delay=$delay")
            delay(delay)
            return "product${it}"
        }

        val results = Channel<String>()
        val mutex = Mutex()
        val aggregatedResults = LinkedList<String>()

        val producer = launch {
            coroutineScope {
                repeat(5) {
                    launch {
                        try {
                            val result = withTimeout(timeMillis = 500L) { producer(it) }
                            println("Producer=$it sends message $result")
                            results.send(result)
                        } catch (_: TimeoutCancellationException) {
                            println("Producer=$it timed out")
                        } catch (ex: CancellationException) {
                            println("Producer=$it cancelled")
                            throw ex
                        } catch (ex: Exception) {
                            println("Producer=$it failed: ${ex.message}")
                        }
                    }
                }
            }
            results.close()
        }

        launch {
            while (aggregatedResults.size < 3) {
                val catched = results.receiveCatching()
                when (val result = catched.getOrNull()) {
                    null -> {
                        results.close()
                        producer.cancel()
                        break
                    }

                    else -> {
                        mutex.withLock {
                            aggregatedResults.add(result)
                        }
                    }
                }
            }
            producer.cancel()
        }
        advanceUntilIdle()

        println("all results $aggregatedResults")
    }
}
