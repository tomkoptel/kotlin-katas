package com.sample.tom.sort

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
        mutableListOf(5, 2, 7, 2, 8, 5, 1, 9, 7).radixSort() shouldBe mutableListOf(1, 2, 2, 5, 5, 8, 7, 9)
    }
}
