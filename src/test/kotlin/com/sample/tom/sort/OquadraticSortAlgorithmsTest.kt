package com.sample.tom.sort

import com.sample.tom.sort.OquadraticSortAlgorithms.bubbleSort
import com.sample.tom.sort.OquadraticSortAlgorithms.insertionSort
import com.sample.tom.sort.OquadraticSortAlgorithms.rightAlign
import com.sample.tom.sort.OquadraticSortAlgorithms.selectionSort
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class OquadraticSortAlgorithmsTest {
    @Test
    fun bubbleSort() {
        val list = arrayListOf(9, 4, 10, 3)
        list.bubbleSort()
        list shouldBe listOf(3, 4, 9, 10)
    }

    @Test
    fun selectionSort() {
        val list = arrayListOf(9, 4, 10, 3)
        list.selectionSort()
        list shouldBe listOf(3, 4, 9, 10)
    }

    @Test
    fun insertionSort() {
        val list = arrayListOf(9, 4, 10, 3)
        list.insertionSort()
        list shouldBe listOf(3, 4, 9, 10)
    }

    @Test
    fun rightAlign() {
        val list = mutableListOf(1, 2, 3, 3, 3, 4, 5)
        // 1, 2, 3, 3, 3, 4, 5
        // 1, 2, 3, 3, 4, 5, 3
        // 1, 2, 3, 4, 5, 3, 3
        // 1, 2, 4, 5, 3, 3, 3
        // 1, 2, 4, 5, 3, 3, 3
        // 1, 2, 4, 5, 3, 3, 3
        list.rightAlign(3)
        list shouldBe listOf(1, 2, 4, 5, 3, 3, 3)
    }
}
