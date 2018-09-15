package com.sample.tom

import java.util.*

// Challenge https://www.hackerrank.com/challenges/2d-array/problem

fun main(args: Array<String>) {
    val scan = Scanner(System.`in`)
    val arrays = Array(6) { it -> Array(6) { 0 } }
    for (i in 0 until 6) {
        arrays[i] = scan.nextLine().split(" ").map { it.trim().toInt() }.toTypedArray()
    }
    val result = hourGlassSum(arrays)
    println(result)
}

fun hourGlassSum(table: Array<Array<Int>>): Int {
    return (0 until table.size - 2).map { sumSquareByPosition(it, table) }.flatten().maxBy { it }!!
}

fun sumSquareByPosition(rowIndex: Int, table: Array<Array<Int>>): List<Int> {
    return (0 until table[rowIndex].size - 2).map { colIndex ->
        val top = table[rowIndex][colIndex] + table[rowIndex][colIndex + 1] + table[rowIndex][colIndex + 2]
        val middle = table[rowIndex + 1][colIndex + 1]
        val bottom = table[rowIndex + 2][colIndex] + table[rowIndex + 2][colIndex + 1] + table[rowIndex + 2][colIndex + 2]

        top + middle + bottom
    }
}