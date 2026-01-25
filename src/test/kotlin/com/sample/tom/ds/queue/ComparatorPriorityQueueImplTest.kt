package com.sample.tom.ds.queue

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class ComparatorPriorityQueueImplTest {
    @Test
    fun `min priority queue`() {
        val result = mutableListOf<String>()
        val stringLengthComparator =
            Comparator<String> { o1, o2 ->
                val length1 = o1?.length ?: -1
                val length2 = o2?.length ?: -1
                length1 - length2
            }
        val priorityQueue = ComparatorPriorityQueueImpl(stringLengthComparator)
        arrayListOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine").forEach {
            priorityQueue.enqueue(it)
        }
        while (!priorityQueue.isEmpty) {
            priorityQueue.dequeue()?.let {
                result += it
            }
        }
        result shouldBe listOf("three", "eight", "seven", "nine", "four", "five", "one", "two", "six")
    }
}
