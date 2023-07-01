package com.sample.tom.ds.binary

import java.util.*

object CollectionExt {
    fun <T : Comparable<T>> List<T>.binarySearch(el: T): Int? {
        if (isEmpty()) return null
        var start = 0
        var end = size - 1
        while (start <= end) {
            val middle = (start + end) / 2
            val middleEl = this[middle]
            if (middleEl == el) {
                return middle
            } else if (middleEl < el) {
                start = middle + 1
            } else {
                end = middle - 1
            }
        }
        return null
    }

    fun <T : Comparable<T>> List<T>.findIndices(el: T): IntRange? {
        val stack = Stack<Pair<Int, Int>>()
        val indices = mutableListOf<Int>()
        val n = size - 1
        stack.push((0 to n / 2))
        stack.push((n / 2 to n))

        while (stack.isNotEmpty()) {
            val (start, end) = stack.pop()
            val middle = (start + end) / 2
            if (el == getOrNull(middle)) {
                indices += middle
            }
            val leftEnd = middle - 1
            if (start <= leftEnd) {
                stack.push((start to leftEnd))
            }
            val rightStart = middle + 1
            if (rightStart <= end) {
                stack.push((rightStart to end))
            }
        }
        indices.sort()
        if (indices.isEmpty()) return null
        return indices.first()..indices.last()
    }
}
