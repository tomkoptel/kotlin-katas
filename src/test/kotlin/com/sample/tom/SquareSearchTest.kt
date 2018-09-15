package com.tom.sample;

import com.sample.tom.hourGlassSum
import com.sample.tom.sumSquareByPosition
import junit.framework.Assert.assertTrue
import org.junit.Test

class SquareSearchTest {
    @Test
    fun should_find_sum_for_square_in_position() {
        val row1: Array<Int> = arrayOf(0, 1, 2)
        val row2: Array<Int> = arrayOf(3, 4, 5)
        val row3: Array<Int> = arrayOf(6, 7, 8)
        val table: Array<Array<Int>> = arrayOf(row1, row2, row3)

        val result = sumSquareByPosition(0, table)
        assertTrue(result.size == 1)
        assertTrue(result[0] == 28)
    }

    @Test
    fun should_find_sum_for_square_in_position2() {
        val row1: Array<Int> = arrayOf(0, 1, 2, 0, 1, 2)
        val row2: Array<Int> = arrayOf(3, 4, 5, 3, 4, 5)
        val row3: Array<Int> = arrayOf(6, 7, 8, 6, 7, 8)
        val table: Array<Array<Int>> = arrayOf(row1, row2, row3)

        val result = sumSquareByPosition(0, table)
        assertTrue(result.size == 4)
        assertTrue(result.containsAll(listOf(28, 29, 27, 28)))
    }

    @Test
    fun should_find_the_biggest_square() {
        val row1: Array<Int> = arrayOf(0, 1, 2, 0, 1, 2)
        val row2: Array<Int> = arrayOf(3, 4, 5, 3, 4, 5)
        val row3: Array<Int> = arrayOf(6, 7, 8, 6, 7, 8)
        val table: Array<Array<Int>> = arrayOf(row1, row2, row3)

        val result = hourGlassSum(table)
        assertTrue(result == 29)
    }
}