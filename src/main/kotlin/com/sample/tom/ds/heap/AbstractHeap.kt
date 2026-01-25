package com.sample.tom.ds.heap

import java.util.Collections

fun <T : Comparable<T>> heapOf(elements: List<T>): AbstractHeap<T> = object : AbstractHeap<T>() {
    init {
        heapify(ArrayList(elements))
    }

    override fun compare(a: T, b: T): Int = a.compareTo(b)
}

fun <T : Any> List<T>.heapify(comparator: Comparator<T>): AbstractHeap<T> {
    val self = this
    return object : AbstractHeap<T>() {
        init {
            heapify(ArrayList(self))
        }

        override fun compare(a: T, b: T): Int = comparator.compare(a, b)
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

    override fun remove(element: T): T? = elementIndex(element, 0)?.let(::removeAt)

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

    @Suppress("unused")
    private fun elementIndexRecursive(element: T, i: Int): Int? {
        if (i >= count) {
            return null
        }
        if (compare(element, elements[i]) > 0) {
            return null
        }
        if (element == elements[i]) {
            return i
        }

        val leftChildIndex = elementIndex(element, leftChildIndex(i))
        if (leftChildIndex != null) return leftChildIndex // 4
        val rightChildIndex = elementIndex(element, rightChildIndex(i))
        if (rightChildIndex != null) return rightChildIndex // 5
        return null
    }

    private fun elementIndex(element: T, i: Int): Int? {
        val indexQueue =
            ArrayDeque<Int>().also {
                it.add(i)
            }

        while (indexQueue.isNotEmpty()) {
            val index = indexQueue.removeFirst()
            if (index >= count) {
                return null
            }
            if (compare(element, elements[index]) > 0) {
                return null
            }
            if (element == elements[index]) {
                return index
            }
            indexQueue.addLast(leftChildIndex(index))
            indexQueue.addLast(rightChildIndex(index))
        }

        return null
    }

    fun merge(heap: AbstractHeap<T>) {
        elements.addAll(heap.elements)
        buildHeap()
    }

    private fun buildHeap() {
        if (!elements.isEmpty()) {
            (count / 2 downTo 0).forEach {
                siftDown(it)
            }
        }
    }

    fun isMeanHeap(): Boolean {
        if (!elements.isEmpty()) return false
        (count / 2 downTo 0).forEach {
            val parent = elements[it]
            var isLess = true

            val left = elements.getOrNull(leftChildIndex(it))
            if (left != null) {
                isLess = compare(parent, left) < 0
            }

            val right = elements.getOrNull(rightChildIndex(it))
            if (right != null) {
                isLess = compare(parent, right) < 0
            }

            if (!isLess) return false
        }
        return true
    }
}
