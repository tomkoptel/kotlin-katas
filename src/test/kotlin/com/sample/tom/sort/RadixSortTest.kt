package com.sample.tom.sort

import com.sample.tom.sort.RadixSort.lexicographicalSort
import com.sample.tom.sort.RadixSort.radixSort
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class RadixSortTest {
    @Test
    fun testEmptyList() {
         mutableListOf<Int>().radixSort().shouldBeEmpty()
    }

    @Test
    fun testSortedAscendingList() {
        mutableListOf(1, 2, 3, 4, 5).radixSort() shouldBe mutableListOf(1, 2, 3, 4, 5)
    }

    @Test
    fun testSortedDescendingList() {
        mutableListOf(5, 4, 3, 2, 1).radixSort() shouldBe mutableListOf(1, 2, 3, 4, 5)
    }

    @Test
    fun testUnsortedList() {
        mutableListOf(38, 27, 43, 3, 9, 82, 10).radixSort() shouldBe mutableListOf(3, 9, 10, 27, 38, 43, 82)
    }

    @Test
    fun testListWithDuplicates() {
        mutableListOf(5, 2, 7, 2, 8, 5, 1, 9, 7).radixSort() shouldBe mutableListOf(1, 2, 2, 5, 5, 7, 7, 8, 9)
    }

    @Test
    fun lexicographicalSort() {
        val list = mutableListOf(448, 3168, 6217, 7117, 1256, 3887, 3900, 3444, 4976, 6891, 4682)
        list.lexicographicalSort() shouldBe mutableListOf(1256, 3168, 3444, 3887, 3900, 448, 4682, 4976, 6217, 6891, 7117)
    }
}
