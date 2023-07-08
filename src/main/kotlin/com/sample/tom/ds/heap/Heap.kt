package com.sample.tom.ds.heap

/**
 * Is a perfect binary tree with all levels field except the last one.
 * The primary invariant:
 * * the parent is greater or equal for max heap
 * * the parent is less or equal for min heap
 */
interface Heap<T : Any> : Collection<T> {
    /**
     * The peek operation is a generalization of methods returning the min or the max depending on the implementation. ”
     */
    fun peek(): T?
}
