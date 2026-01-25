package com.sample.tom.ds.queue

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class StackQueueTest {
    @Test
    fun `should support enqueue method`() {
        val queue = stackQueueOf(listOf("1", "2", "3", "4"))
        "$queue" shouldBe
            """
            StackQueue(left=----top----
            -----------
            , right=----top----
            4
            3
            2
            1
            -----------
            )
            """.trimIndent()
        queue.dequeue()
        "$queue" shouldBe
            """
            StackQueue(left=----top----
            2
            3
            4
            -----------
            , right=----top----
            -----------
            )
            """.trimIndent()
    }

    @Test
    fun `should support dequeue method`() {
        val queue = stackQueueOf(listOf("1", "2", "3", "4"))
        queue.dequeue() shouldBe "1"
        queue.dequeue() shouldBe "2"
        queue.dequeue() shouldBe "3"
        queue.dequeue() shouldBe "4"
        queue.dequeue() shouldBe null
    }

    @Test
    fun `peek should not modify the queue`() {
        val queue = stackQueueOf(listOf("1", "2", "3", "4"))
        queue.peek() shouldBe "1"
        queue.peek() shouldBe "1"
    }
}
