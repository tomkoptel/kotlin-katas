package com.sample.tom.ds.ringbuffer

@Suppress("UNCHECKED_CAST")
class RingBufferImpl<T : Any>(
    private val capacity: Int,
) : RingBuffer<T>, Iterable<T> {
    private val storage = arrayOf<Any>(capacity)
    private var readPosition = 0
    private var writePosition = 0
    private var available = 0

    override val size: Int = available

    override fun enqueue(item: T) {
        storage[writePosition] = item
        if (writePosition == capacity - 1) {
            writePosition = 0
        } else {
            writePosition++
            available++
        }
    }

    override fun dequeue(): T? {
        storage[readPosition]
        return null
    }

    override fun peekFirst(): T? = null
    override fun peekLast(): T? = null
    override fun peekAt(position: Int): T? = null
    override fun clear() = Unit

    override fun iterator(): Iterator<T> {
        TODO("Not yet implemented")
    }
}
