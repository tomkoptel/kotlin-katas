package com.sample.tom.sort

import java.util.LinkedList

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
    fun <T : Comparable<T>> merge(first: Iterable<T>, second: Iterable<T>): Iterable<T> {
        val firstIterator = first.iterator()
        val secondIterator = second.iterator()
        val result = mutableListOf<T>()

        if (!firstIterator.hasNext()) return second
        if (!secondIterator.hasNext()) return first

        var firstValue: T? = firstIterator.nextOrNull()
        var secondValue: T? = secondIterator.nextOrNull()

        while (firstValue != null && secondValue != null) {
            if (firstValue <= secondValue) {
                result += firstValue
                firstValue = firstIterator.nextOrNull()
            } else {
                result += secondValue
                secondValue = secondIterator.nextOrNull()
            }
        }

        while (firstValue != null) {
            result += firstValue
            firstValue = firstIterator.nextOrNull()
        }
        while (secondValue != null) {
            result += secondValue
            secondValue = secondIterator.nextOrNull()
        }

        return result
    }

    private fun <T> Iterator<T>.nextOrNull(): T? = if (this.hasNext()) this.next() else null
}
