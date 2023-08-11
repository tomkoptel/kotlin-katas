package com.sample.tom.sort

import com.sample.tom.sort.QuickSort.hoareQuickSort
import com.sample.tom.sort.QuickSort.lomutoQuickSort
import com.sample.tom.sort.QuickSort.naiveQuickSort
import com.sample.tom.sort.QuickSort.quicksortHoare
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class QuickSortTest {
    @Test
    fun naiveQuickSor() {
        val unsorted = listOf(10, 9, 8, 7, 6, 3)
        unsorted.naiveQuickSort() shouldBe listOf(3, 6, 7, 8, 9, 10)
    }

    @Test
    fun lomutoQuickSort1() {
        val unsorted = listOf(0, 7, 8, 4, 3)
        unsorted.lomutoQuickSort() shouldBe listOf(0, 3, 4, 7, 8)
    }

    @Test
    fun lomutoQuickSort2() {
        val unsorted = listOf(12, 0, 3, 9, 2, 21, 18, 27, 1, 5, 8, -1, 8)
        unsorted.lomutoQuickSort() shouldBe listOf(-1, 0, 1, 2, 3, 5, 8, 8, 9, 12, 18, 21, 27)
    }

    @Test
    fun hoareQuickSort() {
        val unsorted = listOf(12, 0, 3, 9, 2, 21, 18, 27, 1, 5, 8, -1, 8)
        unsorted.hoareQuickSort() shouldBe listOf(-1, 0, 1, 2, 3, 5, 8, 8, 9, 12, 18, 21, 27)
    }
}
