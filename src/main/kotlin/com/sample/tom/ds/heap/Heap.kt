package com.sample.tom.ds.heap

interface Heap<T: Any> : Collection<T> {
    fun peek(): T?
}
