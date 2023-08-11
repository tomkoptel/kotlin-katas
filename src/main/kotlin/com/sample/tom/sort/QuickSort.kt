package com.sample.tom.sort

import com.sample.tom.sort.QuickSort.lomutoQuickSort
import java.util.Collections
import java.util.LinkedList

object QuickSort {
    fun <T : Comparable<T>> List<T>.naiveQuickSort(): List<T> {
        val stack = LinkedList<List<T>>().also { it.add(this) }
        val result = LinkedList<T>()
        while (stack.isNotEmpty()) {
            val list = stack.removeLast()
            if (list.size > 1) {
                val pivot = list[list.size / 2]
                val less = list.filter { it < pivot }
                val more = list.filter { it > pivot }
                stack.add(more)
                stack.add(listOf(pivot))
                stack.add(less)
            } else {
                list.firstOrNull()?.let { result.add(it) }
            }
            println(stack)
        }
        return result
    }

    fun <T : Comparable<T>> List<T>.lomutoQuickSort(): List<T> {
        if (size < 2) return this
        val stack = LinkedList<Pair<Int, Int>>()
        stack.push(0 to size - 1)
        while (stack.isNotEmpty()) {
            val (low, high) = stack.pop()
            if (low < high) {
                val pivot = this[high]
                var i = low
                for (j in low until high) {
                    if (this[j] <= pivot) {
                        Collections.swap(this, i, j)
                        i += 1
                    }
                }
                Collections.swap(this, i, high)
                stack.push(i + 1 to high)
                stack.push(low to i - 1)
            }
        }
        return this
    }

    fun <T : Comparable<T>> List<T>.hoareQuickSort(): List<T> {
        if (size < 2) return this
        val stack = LinkedList<Pair<Int, Int>>()
        stack.push(0 to size - 1)
        while (stack.isNotEmpty()) {
            val (low, high) = stack.pop()
            if (low < high) {
                val pivot = this[low]
                var i = low - 1
                var j = high + 1
                while (true) {
                    do {
                        j -= 1
                    } while (this[j] > pivot)
                    do {
                        i += 1
                    } while (this[i] < pivot)
                    if (i < j) {
                        Collections.swap(this, i, j)
                    } else {
                        break
                    }
                }
                stack.push(j + 1 to high)
                stack.push(low to j)
            }
        }
        return this
    }
}
