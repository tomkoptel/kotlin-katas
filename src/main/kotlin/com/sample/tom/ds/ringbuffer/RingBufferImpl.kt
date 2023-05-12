package com.sample.tom.ds.ringbuffer

@Suppress("UNCHECKED_CAST")
class RingBufferImpl<T : Any>(
    private val capacity: Int,
) : RingBuffer<T>, Iterable<T> {
    private val storage = Array<Any?>(size = capacity, init = { null })
    private var readPosition = 0
    private var writePosition = 0

    override val size: Int
        get() {
            val diff = writePosition - readPosition
            return if (diff < 0) {
                TODO()
            } else if (writePosition == readPosition) {
                return capacity
            } else {
                return diff
            }
        }

    override fun enqueue(item: T) {
        // Adding more elements than the capacity
        if (writePosition == capacity) {
            writePosition = 0
        }
        storage[writePosition] = item
        writePosition++
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
