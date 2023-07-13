package com.sample.tom.sort

import com.sample.tom.sort.OquadraticSortAlgorithms.bubbleSort
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class OquadraticSortAlgorithmsTest {
    @Test
    fun sort() {
        val list = arrayListOf(9, 4, 10, 3)
        list.bubbleSort()
        list shouldBe listOf(3, 4, 9, 10)
    }
}
