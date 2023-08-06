package com.sample.tom.sort

import java.util.LinkedList

object QuickSort {
    fun <T: Comparable<T>> List<T>.naiveQuickSort(): List<T> {
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
}
