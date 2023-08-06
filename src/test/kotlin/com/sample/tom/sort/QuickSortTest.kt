package com.sample.tom.sort

import com.sample.tom.sort.QuickSort.naiveQuickSort
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class QuickSortTest {
    @Test
    fun naiveQuickSor() {
        val unsorted = listOf(10, 9, 8, 7, 6, 3)
        unsorted.naiveQuickSort() shouldBe listOf(3, 6, 7, 8, 9, 10)
    }
}
