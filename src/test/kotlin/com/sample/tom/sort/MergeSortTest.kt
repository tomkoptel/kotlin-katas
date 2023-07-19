package com.sample.tom.sort

import com.sample.tom.sort.MergeSort.merge
import com.sample.tom.sort.MergeSort.mergeSort
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class MergeSortTest {
    @Test
    fun testEmptyList() {
        val emptyList: List<Int> = emptyList()
        val sortedList = emptyList.mergeSort()
        sortedList shouldBe emptyList
    }

    @Test
    fun testSortedAscendingList() {
        val sortedList = listOf(1, 2, 3, 4, 5)
        val result = sortedList.mergeSort()
        result shouldBe sortedList
    }

    @Test
    fun testSortedDescendingList() {
        val sortedList = listOf(5, 4, 3, 2, 1)
        val expected = sortedList.sorted()
        val result = sortedList.mergeSort()
        result shouldBe expected
    }

    @Test
    fun testUnsortedList() {
        val unsortedList = listOf(38, 27, 43, 3, 9, 82, 10)
        val expected = unsortedList.sorted()
        val result = unsortedList.mergeSort()
        result shouldBe expected
    }

    @Test
    fun testListWithDuplicates() {
        val listWithDuplicates = listOf(5, 2, 7, 2, 8, 5, 1, 9, 7)
        val expected = listWithDuplicates.sorted()
        val result = listWithDuplicates.mergeSort()
        result shouldBe expected
    }

    @Test
    fun testListWithStrings() {
        val listOfStrings = listOf("apple", "orange", "banana", "grape", "kiwi")
        val expected = listOfStrings.sorted()
        val result = listOfStrings.mergeSort()
        result shouldBe expected
    }

    @Test
    fun `merge iterable`() {
        val list1 = listOf(1, 2, 3, 4, 5, 6, 7, 8)
        val list2 = listOf(1, 3, 4, 5, 5, 6, 7, 7)
        val result = merge(list1, list2)
        result shouldBe listOf(1, 1, 2, 3, 3, 4, 4, 5, 5, 5, 6, 6, 7, 7, 7, 8)
    }
}
