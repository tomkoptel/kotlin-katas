package com.sample.tom.ds.ringbuffer

class RingBufferImpl<T : Any>(
    private val capacity: Int,
) : RingBuffer<T>, Iterable<T> {
    private val buffer: Array<Any?> = arrayOfNulls<Any?>(capacity)

    // Head - remove from the head (read index)
    private var head = 0

    // Tail - add to the tail (write index)
    private var tail = 0

    // How many items are currently in the queue
    private var _size = 0

    override val size: Int get() = _size

    override fun enqueue(item: T) {
        if (size == capacity) {
            buffer[tail] = item
            tail = (tail + 1) % capacity
            head = tail
        } else {
            buffer[tail] = item
            tail = (tail + 1) % capacity
            _size++
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun dequeue(): T? {
        if (_size == 0) return null
        val element = buffer[head] as T
        buffer[head] = null
        head = (head + 1) % capacity
        _size--
        return element
    }

    @Suppress("UNCHECKED_CAST")
    override fun peekFirst(): T? {
        return buffer[head] as? T
    }

    @Suppress("UNCHECKED_CAST")
    override fun peekLast(): T? {
        val lastPosition = (tail - 1 + capacity) % capacity
        return buffer[lastPosition] as? T
    }

    @Suppress("UNCHECKED_CAST")
    override fun peekAt(position: Int): T? {
        val index = (head + position) % capacity
        return buffer[index] as? T
    }

    override fun clear() {
        buffer.fill(null)
        tail = 0
        head = 0
        _size = 0
    }

    override fun iterator(): Iterator<T> {
        return object : Iterator<T> {
            private var currentIndex = head
            private var remaining = size

            override fun hasNext(): Boolean {
                return remaining > 0
            }

            @Suppress("UNCHECKED_CAST")
            override fun next(): T {
                if (!hasNext()) {
                    throw NoSuchElementException("No more elements in the RingBuffer.")
                }
                val element = buffer[currentIndex] as T
                currentIndex = (currentIndex + 1) % capacity
                remaining--
                return element
            }
        }
    }
}
