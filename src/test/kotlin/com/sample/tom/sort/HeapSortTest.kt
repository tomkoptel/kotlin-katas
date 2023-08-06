package com.sample.tom.sort

import com.sample.tom.sort.HeapSort.heapSort
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class HeapSortTest {
    @Test
    fun `ascending heap sort`() {
        listOf(6, 12, 2, 26, 8, 18, 21, 9, 5).heapSort(Comparator.reverseOrder())
            .shouldBe(listOf(2, 5, 6, 8, 9, 12, 18, 21, 26))
    }
}
