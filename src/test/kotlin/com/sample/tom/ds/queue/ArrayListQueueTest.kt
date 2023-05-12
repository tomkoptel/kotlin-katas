package com.sample.tom.ds.queue

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class ArrayListQueueTest {
    @Test
    fun `should support enqueue method`() {
        val queue = arrayListQueueOf(listOf("1", "2", "3", "4"))
        "${queue.also { it.enqueue("5") }}" shouldBe "1 > 2 > 3 > 4 > 5"
        "${queue.also { it.enqueue("6") }}" shouldBe "1 > 2 > 3 > 4 > 5 > 6"
    }

    @Test
    fun `should support dequeue method`() {
        val queue = arrayListQueueOf(listOf("1", "2", "3", "4"))
        queue.dequeue() shouldBe "1"
        queue.dequeue() shouldBe "2"
        queue.dequeue() shouldBe "3"
        queue.dequeue() shouldBe "4"
        queue.dequeue() shouldBe null
    }

    @Test
    fun `peek should not modify the queue`() {
        val queue = arrayListQueueOf(listOf("1", "2", "3", "4"))
        queue.peek() shouldBe "1"
        queue.peek() shouldBe "1"
    }
}
