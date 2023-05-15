package com.sample.tom.ds.ringbuffer

fun <T : Any> ringBuffer(capacity: Int): RingBuffer<T> = RingBufferImpl(capacity)

interface RingBuffer<T : Any> : Iterable<T> {
    val size: Int
    fun enqueue(item: T)
    fun dequeue(): T?
    fun peekFirst(): T?
    fun peekLast(): T?
    fun peekAt(position: Int): T?
    fun clear()
}
