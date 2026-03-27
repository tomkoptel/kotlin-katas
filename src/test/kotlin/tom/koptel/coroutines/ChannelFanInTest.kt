package tom.koptel.coroutines

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import java.io.IOException

class ChannelFanInTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `1 consumer 300 producers`() = runTest {
        val expectedNumOfMessages = 300
        val channel = Channel<Result<Int>>()
        launch {
            for (msg in channel) {
                msg.fold(onSuccess = {
                    println("consumer consuming $it")
                }, onFailure = {
                    println("consumer consumed failure ${it.message}")
                })
            }
        }
        coroutineScope {
            repeat(times = expectedNumOfMessages) { index ->
                launch {
                    delay(100)
                    repeat(5) {
                        val outcome = runSuspendCatching {
                            val value = index * it
                            println("producer-$index produces=${index * it}")
                            if (value % 2 == 0) {
                                throw IOException("Boom")
                            } else {
                                value
                            }
                        }
                        channel.send(outcome)
                    }
                }
            }
        }
        channel.close()
        advanceUntilIdle()
    }

    private suspend inline fun <T> runSuspendCatching(block: suspend () -> T): Result<T> = try {
        Result.success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(e)
    }
}
