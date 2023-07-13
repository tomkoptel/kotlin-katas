package com.sample.tom.ds.queue

import com.sample.tom.ds.heap.Heap
import com.sample.tom.ds.heap.heapOf

class ComparablePriorityQueueImpl<T : Comparable<T>> : AbstractPriorityQueue<T>() {
    override val heap: Heap<T> = heapOf(emptyList())
}
