package com.sample.tom.ds.heap

import java.util.*
import kotlin.collections.ArrayList

fun <T : Comparable<T>> heapOf(elements: List<T>): Heap<T> {
    return object : AbstractHeap<T>() {
        init {
            heapify(ArrayList(elements))
        }

        override fun compare(a: T, b: T): Int {
            return a.compareTo(b)
        }
    }
}

fun <T : Any> List<T>.heapify(comparator: Comparator<T>): Heap<T> {
    val self = this
    return object : AbstractHeap<T>() {
        init {
            heapify(ArrayList(self))
        }

        override fun compare(a: T, b: T): Int {
            return comparator.compare(a, b)
        }
    }
}

abstract class AbstractHeap<T : Any> : Heap<T> {
    var elements = ArrayList<T>()

    override val count: Int
        get() = elements.size

    override fun peek(): T? = elements.firstOrNull()

    override fun insert(element: T) {
        elements += element
        siftUp(count - 1)
    }

    override fun remove(element: T): T? {
        return elementIndex(element, 0)?.let(::removeAt)
    }

    private fun removeAt(index: Int): T? {
        if (index >= count) return null
        return if (index == count - 1) {
            elements.removeAt(count - 1)
        } else {
            Collections.swap(elements, index, count - 1)
            val item = elements.removeAt(count - 1)
            siftDown(index)
            siftUp(index)
            item
        }
    }

    override fun remove(): T? {
        if (isEmpty) return null
        Collections.swap(elements, 0, count - 1)
        val removed = elements.removeAt(count - 1)
        siftDown(0)
        return removed
    }

    private fun leftChildIndex(index: Int) = (2 * index) + 1

    private fun rightChildIndex(index: Int) = (2 * index) + 2

    private fun parentIndex(index: Int) = (index - 1) / 2

    abstract fun compare(a: T, b: T): Int

    private fun siftUp(index: Int) {
        var child = index
        var parent = parentIndex(child)
        while (child > 0 && compare(elements[child], elements[parent]) > 0) {
            Collections.swap(elements, child, parent)
            child = parent
            parent = parentIndex(child)
        }
    }

    private fun siftDown(index: Int) {
        var parent = index
        while (true) {
            val left = leftChildIndex(parent)
            val right = rightChildIndex(parent)
            var candidate = parent

            if (left < count && compare(elements[left], elements[candidate]) > 0) {
                candidate = left
            }
            if (right < count && compare(elements[right], elements[candidate]) > 0) {
                candidate = right
            }
            if (candidate == parent) {
                return
            }
            Collections.swap(elements, parent, candidate)
            parent = candidate
        }
    }

    protected fun heapify(values: ArrayList<T>) {
        elements = values
        if (!elements.isEmpty()) {
            (count / 2 downTo 0).forEach {
                siftDown(it)
            }
        }
    }

    private fun elementIndex(element: T, i: Int): Int? {
        if (i >= count) {
            return null // 1
        }
        if (compare(element, elements[i]) > 0) {
            return null // 2
        }
        if (element == elements[i]) {
            return i // 3
        }

        val leftChildIndex = elementIndex(element, leftChildIndex(i))
        if (leftChildIndex != null) return leftChildIndex // 4
        val rightChildIndex = elementIndex(element, rightChildIndex(i))
        if (rightChildIndex != null) return rightChildIndex // 5
        return null // 6
    }
}
