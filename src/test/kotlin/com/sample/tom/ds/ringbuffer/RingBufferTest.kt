package com.sample.tom.ds.ringbuffer

import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class RingBufferTest {
    @Test
    fun testAddingElements() {
        val capacity = 3
        val ringBuffer = ringBuffer<Int>(capacity)

        ringBuffer.enqueue(1)
        ringBuffer.enqueue(2)
        ringBuffer.enqueue(3)

        ringBuffer.size shouldBe capacity

        // Adding more elements than the capacity
        ringBuffer.enqueue(4)
        ringBuffer.enqueue(5)
        ringBuffer.enqueue(6)

        ringBuffer.size shouldBe capacity
    }

    @Test
    fun testRemovingElements() {
        val ringBuffer = ringBuffer<String>(3)

        ringBuffer.enqueue("Apple")
        ringBuffer.enqueue("Banana")
        ringBuffer.enqueue("Cherry")

        ringBuffer.dequeue() shouldBe "Apple"
        ringBuffer.size shouldBe 2

        // Removing multiple elements
        ringBuffer.dequeue()
        ringBuffer.dequeue()

        // Dequeueing from an empty RingBuffer should return null
        ringBuffer.dequeue().shouldBeNull()
    }

    @Test
    fun testCheckingElements() {
        val ringBuffer = ringBuffer<Char>(4)

        ringBuffer.enqueue('A')
        ringBuffer.enqueue('B')
        ringBuffer.enqueue('C')

        ringBuffer.peekFirst() shouldBe 'A'

        ringBuffer.peekLast() shouldBe 'C'

        ringBuffer.peekAt(1) shouldBe 'B'
    }

    @Test
    fun testClearingRingBuffer() {
        val ringBuffer = ringBuffer<Double>(4)

        ringBuffer.enqueue(1.1)
        ringBuffer.enqueue(2.2)
        ringBuffer.enqueue(3.3)
        ringBuffer.enqueue(4.4)

        ringBuffer.clear()
        ringBuffer.size shouldBe 0
    }

    @Test
    fun testIteration() {
        val ringBuffer = ringBuffer<Int>(4)

        ringBuffer.enqueue(1)
        ringBuffer.enqueue(2)
        ringBuffer.enqueue(3)
        ringBuffer.enqueue(4)

        val elements = mutableListOf<Int>()
        for (element in ringBuffer) {
            elements.add(element)
        }

        // Iteration should return elements in the correct order
        elements shouldBe listOf(1, 2, 3, 4)

        ringBuffer.dequeue()
        ringBuffer.dequeue()

        val remainingElements = mutableListOf<Int>()
        for (element in ringBuffer) {
            remainingElements.add(element)
        }

        // Iteration should only return remaining elements
        remainingElements shouldBe listOf(3, 4)
    }
}
