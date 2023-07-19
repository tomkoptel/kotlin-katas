package com.sample.tom.sort

import java.util.*

object MergeSort {
    fun <T : Comparable<T>> List<T>.mergeSort(): List<T> {
        if (this.size <= 1) return this

        val queue = LinkedList<List<T>>()
        forEach { queue.add(listOf(it)) }

        while (queue.size > 1) {
            val left = queue.remove()
            val right = queue.remove()
            val result = merge(left, right)
            queue.add(result)
        }

        return queue.remove()
    }

    private fun <T : Comparable<T>> merge(left: List<T>, right: List<T>): List<T> {
        val result = mutableListOf<T>()
        var leftIndex = 0
        var rightIndex = 0

        while (leftIndex < left.size && rightIndex < right.size) {
            val leftElement = left[leftIndex]
            val rightElement = right[rightIndex]

            if (leftElement <= rightElement) {
                result.add(leftElement)
                leftIndex++
            } else {
                result.add(rightElement)
                rightIndex++
            }
        }

        result.addAll(left.subList(leftIndex, left.size))
        result.addAll(right.subList(rightIndex, right.size))
        return result
    }

    /**
     * Write a function that takes two sorted iterables and merges them into a single iterable.
     */
    fun <T : Comparable<T>> merge(
        first: Iterable<T>,
        second: Iterable<T>
    ): Iterable<T> {
        TODO()
    }

}
