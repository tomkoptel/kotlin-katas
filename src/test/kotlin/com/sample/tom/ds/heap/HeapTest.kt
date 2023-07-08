package com.sample.tom.ds.heap

import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class HeapTest {
    private val maxHeap = heapOf(listOf(1, 12, 3, 4, 1, 6, 8, 7))
    private val minHeap = listOf(1, 12, 3, 4, 1, 6, 8, 7).heapify { o1, o2 -> o2.compareTo(o1) }

    @Test
    fun `in max heap removes from the largest to lowest`() {
        val result = mutableListOf<Int>()
        while (!maxHeap.isEmpty) {
            maxHeap.remove()?.let {
                result += it
            }
        }
        result shouldBe listOf(12, 8, 7, 6, 4, 3, 1, 1)
    }

    @Test
    fun `remove from max heap`() {
        maxHeap.remove(12) shouldBe 12
        maxHeap.remove(12).shouldBeNull()
    }

    @Test
    fun `in min heap removes from the lowest to largest`() {
        val result = mutableListOf<Int>()
        while (!minHeap.isEmpty) {
            minHeap.remove()?.let {
                result += it
            }
        }
        result shouldBe listOf(1, 1, 3, 4, 6, 7, 8, 12)
    }

    @Test
    fun `remove from min heap`() {
        minHeap.remove(1) shouldBe 1
        minHeap.remove(1) shouldBe 1
        minHeap.remove(1).shouldBeNull()
    }

    @Test
    fun `the smallest n-th element`() {
        val integers = arrayListOf(3, 10, 18, 5, 21, 100)
        integers.theSmallestNth(n = 3) shouldBe 10
        integers.theSmallestNth(n = 3) shouldBe 10
    }
}
