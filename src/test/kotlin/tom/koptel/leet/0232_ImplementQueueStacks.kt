package tom.koptel.leet

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class `0232_ImplementQueueStacks` {
    /**
     * The lazy approach achieves amortized O(1) for all operations because each element is moved at most once from input to output.
     */
    class MyQueueAmortized {
        private val input = ArrayDeque<Int>() // for pushing
        private val output = ArrayDeque<Int>() // for popping

        fun push(x: Int) {
            input.addLast(x) // O(1)
        }

        fun pop(): Int {
            transferIfNeeded()
            return output.removeLast()
        }

        fun peek(): Int {
            transferIfNeeded()
            return output.last()
        }

        fun empty(): Boolean = input.isEmpty() && output.isEmpty()

        private fun transferIfNeeded() {
            if (output.isEmpty()) {
                while (input.isNotEmpty()) {
                    output.addLast(input.removeLast())
                }
            }
        }
    }

    /**
     * Originally solved this way.
     */
    class MyQueue {
        private val head = ArrayDeque<Int>()
        private val tail = ArrayDeque<Int>()

        fun push(x: Int) {
            if (tail.isEmpty()) {
                tail.addLast(x)
                return
            }
            while (tail.isNotEmpty()) {
                val el = tail.removeLast()
                head.addLast(el)
            }
            head.addLast(x)
            while (head.isNotEmpty()) {
                val el = head.removeLast()
                tail.addLast(el)
            }
        }

        fun pop(): Int = tail.removeLast()

        fun peek(): Int = tail.last()

        fun empty(): Boolean = tail.isEmpty()
    }

    @Test
    fun `example 1 - basic operations`() {
        val myQueue = MyQueue()
        myQueue.push(1) // queue is: [1]
        myQueue.push(2) // queue is: [1, 2]
        myQueue.peek() shouldBe 1 // return 1
        myQueue.pop() shouldBe 1 // return 1, queue is [2]
        myQueue.empty().shouldBeFalse() // return false
    }

    @Test
    fun `new queue should be empty`() {
        val myQueue = MyQueue()
        myQueue.empty().shouldBeTrue()
    }

    @Test
    fun `queue with one element should not be empty`() {
        val myQueue = MyQueue()
        myQueue.push(5)
        myQueue.empty().shouldBeFalse()
    }

    @Test
    fun `peek should not remove element`() {
        val myQueue = MyQueue()
        myQueue.push(3)
        myQueue.peek() shouldBe 3
        myQueue.peek() shouldBe 3 // peek again should return same value
        myQueue.empty().shouldBeFalse()
    }

    @Test
    fun `pop should remove element`() {
        val myQueue = MyQueue()
        myQueue.push(7)
        myQueue.pop() shouldBe 7
        myQueue.empty().shouldBeTrue()
    }

    @Test
    fun `FIFO order - multiple elements`() {
        val myQueue = MyQueue()
        myQueue.push(1)
        myQueue.push(2)
        myQueue.push(3)
        myQueue.pop() shouldBe 1
        myQueue.pop() shouldBe 2
        myQueue.pop() shouldBe 3
        myQueue.empty().shouldBeTrue()
    }

    @Test
    fun `interleaved push and pop operations`() {
        val myQueue = MyQueue()
        myQueue.push(1)
        myQueue.push(2)
        myQueue.pop() shouldBe 1 // [2]
        myQueue.push(3) // [2, 3]
        myQueue.pop() shouldBe 2 // [3]
        myQueue.push(4) // [3, 4]
        myQueue.peek() shouldBe 3
        myQueue.pop() shouldBe 3 // [4]
        myQueue.pop() shouldBe 4 // []
        myQueue.empty().shouldBeTrue()
    }

    @Test
    fun `push all values 1 to 9`() {
        val myQueue = MyQueue()
        for (i in 1..9) {
            myQueue.push(i)
        }
        for (i in 1..9) {
            myQueue.pop() shouldBe i
        }
        myQueue.empty().shouldBeTrue()
    }
}
