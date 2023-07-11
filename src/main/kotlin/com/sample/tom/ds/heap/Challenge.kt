package com.sample.tom.ds.heap

fun <T : Comparable<T>> List<T>.theSmallestNth(n: Int): T? {
    val heap = heapOf(emptyList<T>())

    forEach {
        val maxEl = heap.peek()
        if (heap.count < n) {
            heap.insert(it)
        } else if (maxEl != null && maxEl > it) {
            heap.remove()
            heap.insert(it)
        }
    }

    return heap.peek()
}
