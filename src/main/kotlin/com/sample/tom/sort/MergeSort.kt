package com.sample.tom.sort

import java.util.*

object MergeSort {
    fun <T : Comparable<T>> List<T>.mergeSort(): List<T> {
        val stack = Stack<List<T>>()
        stack.pushHalves(this)

        var merged = listOf<T>()
        while (stack.isNotEmpty()) {
            val list = stack.pop()
            if (list.size > 1) {
                stack.pushHalves(list) // first split
            } else {
                // then merge pieces
                merged = merge(left = merged, right = list)
            }
        }

        return merged
    }

    private fun <T : Comparable<T>> merge(left: List<T>, right: List<T>): List<T> {
        var leftIndex = 0
        var rightIndex = 0
        val result = mutableListOf<T>()

        while (leftIndex < left.size && rightIndex < right.size) {
            val leftElement = left[leftIndex]
            val rightElement = right[rightIndex]

            if (leftElement < rightElement) {
                result += leftElement
                leftIndex += 1
            } else if (rightElement < leftElement) {
                result += rightElement
                rightIndex += 1
            } else {
                result += leftElement
                leftIndex += 1
                result += rightElement
                rightIndex += 1
            }
        }

        if (leftIndex < left.size) {
            result.addAll(left.subList(leftIndex, left.size))
        }
        if (rightIndex < right.size) {
            result.addAll(right.subList(rightIndex, right.size))
        }

        return result
    }

    private fun <T : Comparable<T>> Stack<List<T>>.pushHalves(list: List<T>): Stack<List<T>> {
        val left = list.subList(0, list.size / 2)
        val right = list.subList(list.size / 2, list.size)
        add(left)
        add(right)
        return this
    }
}
