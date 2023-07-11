package com.sample.tom.ds.queue

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class ComparablePriorityQueueImplTest {
    @Test
    fun `comparable priority queue`() {
        val priorityQueue = ComparablePriorityQueueImpl<Int>()
        arrayListOf(1, 12, 3, 4, 1, 6, 8, 7).forEach {
            priorityQueue.enqueue(it)
        }
        val result = mutableListOf<Int>()
        while (!priorityQueue.isEmpty) {
            priorityQueue.dequeue()?.let {
                result += it
            }
        }

        result shouldBe listOf(12, 8, 7, 6, 4, 3, 1, 1)
    }
}
