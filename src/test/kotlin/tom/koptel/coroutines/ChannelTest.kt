package tom.koptel.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withTimeoutOrNull
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ChannelTest {
    @Test
    fun `channel can be closed`() = runTest {
        val channel = Channel<Int>(Channel.RENDEZVOUS)
        launch {
            for (x in 1..5) {
                channel.send(x)
            }
            channel.close()
        }
        for (el in channel) {
            println("x=$el")
        }
        println("Done")
    }

    @Test
    fun `produce and consumeEach`() = runTest {
        fun CoroutineScope.produceNums(): ReceiveChannel<Int> = produce {
            var i = 0
            while (isActive) {
                send(i++)
            }
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        fun CoroutineScope.produceSquares(nums: ReceiveChannel<Int>): ReceiveChannel<Int> = produce {
            for (x in nums) {
                send(x * x)
            }
        }

        val squares = produceSquares(produceNums())
        repeat(5) {
            println("square=${squares.receive()}")
        }
        println("Done")
        coroutineContext.cancelChildren()
    }

    @Test
    fun `prime numbers`() = runTest {
        fun CoroutineScope.numbersFrom(start: Int) = produce<Int> {
            var x = start
            while (true) send(x++) // infinite stream of integers from start
        }

        fun CoroutineScope.filter(numbers: ReceiveChannel<Int>, prime: Int) = produce<Int> {
            for (n in numbers) if (n % prime != 0) send(n)
        }

        var cur = numbersFrom(2)
        repeat(5) {
            val prime = cur.receive()
            println("$prime")
            cur = filter(cur, prime)
        }
        coroutineContext.cancelChildren()
    }


    @Test
    fun `fan out`() = runTest {
        fun CoroutineScope.produceNumbers(): ReceiveChannel<Int> = produce {
            var i = 0;
            while (isActive) {
                send(i++)
                delay(95)
            }
        }

        fun CoroutineScope.consumeNumbers(id: Int, channel: ReceiveChannel<Int>) {
            launch { channel.consumeEach { println("channel=$id received=$it") } }
        }

        val producer = produceNumbers()
        repeat(5) {
            consumeNumbers(id = it, producer)
        }
        delay(950)
        producer.cancel()
    }

    @Test
    fun `fan in`() = runTest {
        suspend fun sendString(s: String, millis: Long, channel: Channel<String>) {
            while (true) {
                delay(millis)
                channel.send(s)
            }
        }

        val channel = Channel<String>()
        launch {
            sendString(s = "foo", millis = 200, channel)
        }
        launch {
            sendString(s = "bar", millis = 300, channel)
        }
        repeat(6) {
            println(channel.receive())
        }
        coroutineContext.cancelChildren()
    }

    /**
     *  The problem is that ticker() uses real delay internally — it launches its coroutine with a
     *   fixed dispatcher (Dispatchers.Unconfined), not the test dispatcher. So runTest's virtual time
     *    has no control over it.
     *
     *   When runTest advances virtual time, the ticker's delay(200) still waits for real time. But
     *   runTest completes everything in virtual time instantly, so withTimeoutOrNull(120) expires in
     *   virtual time before the ticker has actually produced an element → you get null.
     *
     *   With runBlocking, everything runs in real time — the ticker's 200ms delay and
     *   withTimeoutOrNull(120) both use real clocks, so the timing works as expected.
     *
     *   This is why ticker is marked @ObsoleteCoroutinesApi — it doesn't integrate well with
     *   structured concurrency or test dispatchers. For testable periodic work, use Flow with
     *   tickerFlow or a simple loop:
     *
     *   fun tickerFlow(delayMillis: Long) = flow {
     *       while (true) {
     *           emit(Unit)
     *           delay(delayMillis)  // uses the coroutine's dispatcher, works with runTest
     *       }
     *   }
     */
    @OptIn(ObsoleteCoroutinesApi::class)
    @Test
    fun `ticker channels`() = runTest {
        val tickerChannel = ticker(delayMillis = 200, initialDelayMillis = 0) // create a ticker channel
        var nextElement = withTimeoutOrNull(1) { tickerChannel.receive() }
        println("Initial element is available immediately: $nextElement") // no initial delay

        nextElement = withTimeoutOrNull(100) { tickerChannel.receive() } // all subsequent elements have 200ms delay
        println("Next element is not ready in 100 ms: $nextElement")

        nextElement = withTimeoutOrNull(120) { tickerChannel.receive() }
        println("Next element is ready in 200 ms: $nextElement")

        // Emulate large consumption delays
        println("Consumer pauses for 300ms")
        delay(300)
        // Next element is available immediately
        nextElement = withTimeoutOrNull(1) { tickerChannel.receive() }
        println("Next element is available immediately after large consumer delay: $nextElement")
        // Note that the pause between `receive` calls is taken into account and next element arrives faster
        nextElement = withTimeoutOrNull(120) { tickerChannel.receive() }
        println("Next element is ready in 100ms after consumer pause in 300ms: $nextElement")

        tickerChannel.cancel() // indicate that no more elements are needed
    }
}
