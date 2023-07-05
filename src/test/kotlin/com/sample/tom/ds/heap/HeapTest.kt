package com.sample.tom.ds.heap

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class HeapTest {
    @Test
    fun `in max heap removes from the largest to lowest`() {
        val array = arrayListOf(1, 12, 3, 4, 1, 6, 8, 7) // 1
        val priorityQueue = heapOf(array)
        val result = mutableListOf<Int>()
        while (!priorityQueue.isEmpty) {
            priorityQueue.remove()?.let {
                result += it
            }
        }
        result shouldBe listOf(12, 8, 7, 6, 4, 3, 1, 1)
    }

    @Test
    fun `in min heap removes from the lowest to largest`() {
        val array = arrayListOf(1, 12, 3, 4, 1, 6, 8, 7) // 1
        val inverseComparator = Comparator<Int> { o1, o2 ->  // 2
            o2.compareTo(o1)
        }
        val result = mutableListOf<Int>()
        val minHeap = array.heapify(inverseComparator)
        while (!minHeap.isEmpty) {
            minHeap.remove()?.let {
                result += it
            }
        }
        result shouldBe listOf(1, 1, 3, 4, 6, 7, 8, 12)
    }
}
