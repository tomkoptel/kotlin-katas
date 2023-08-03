package com.sample.tom.sort

import java.util.Collections

object HeapSort {
    fun <T> List<T>.heapSort(comparator: Comparator<T>): List<T> {
        val heap = this.toMutableList().apply { heapify(comparator) }
        for (index in heap.indices.reversed()) {
            Collections.swap(heap, 0, index)
            heap.siftDown(0, index, comparator)
        }
        return heap.toList()
    }

    private fun <T> MutableList<T>.heapify(comparator: Comparator<T>) {
        if (isEmpty()) return
        for (i in (size / 2) downTo  0) {
            siftDown(i, size, comparator)
        }
    }

    private fun <T> MutableList<T>.siftDown(index: Int, upTo: Int, comparator: Comparator<T>) {
        var parent = index
        while (true) {
            val left = leftIndex(parent)
            val right = rightIndex(parent)
            var candidate = parent

            if (left < upTo && comparator.compare(this[candidate], this[left]) > 0) {
                candidate = left
            }
            if (right < upTo && comparator.compare(this[candidate], this[right]) > 0) {
                candidate = right
            }
            if (parent == candidate) {
                return
            }
            Collections.swap(this, parent, candidate)
            parent = candidate
        }
    }

    private fun parentIndex(index: Int) = (index - 1) / 2

    private fun leftIndex(index: Int) = (2 * index) + 1

    private fun rightIndex(index: Int) = (2 * index) + 2
}
