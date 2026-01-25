package com.sample.tom.ds.queue

import com.sample.tom.ds.heap.heapify

class ComparatorPriorityQueueImpl<T : Any>(private val comparator: Comparator<T>) : AbstractPriorityQueue<T>() {
    override val heap = emptyList<T>().heapify(comparator)
}
