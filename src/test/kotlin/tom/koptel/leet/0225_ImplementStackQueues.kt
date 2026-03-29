package tom.koptel.leet

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.util.Deque
import java.util.LinkedList

class `0225_ImplementStackQueues` {

    class MyStack {
        private val queue: Deque<Int> = LinkedList<Int>()

        fun push(x: Int) {
            println("pushing new element=$x")
            println("step 1. $queue")
            queue.add(x)
            println("step 2. $queue")
            repeat(queue.size - 1) {
                val el = queue.remove()
                println("step 3. $el")
                queue.add(el)
                println("step 4. $queue")
            }
            println("step 5. $queue")
            println()
        }

        fun pop(): Int = queue.remove()

        fun top(): Int = queue.element()

        fun empty(): Boolean = queue.isEmpty()

        override fun toString(): String = "$queue"
    }

    @Test
    fun `example 1 - basic operations`() {
        val myStack = MyStack()
        myStack.push(1)
        myStack.push(2)
        myStack.top() shouldBe 2
        myStack.pop() shouldBe 2
        myStack.empty().shouldBeFalse()
    }

    @Test
    fun `new stack should be empty`() {
        val myStack = MyStack()
        myStack.empty().shouldBeTrue()
    }

    @Test
    fun `stack with one element should not be empty`() {
        val myStack = MyStack()
        myStack.push(5)
        myStack.empty().shouldBeFalse()
    }

    @Test
    fun `top should not remove element`() {
        val myStack = MyStack()
        myStack.push(3)
        myStack.top() shouldBe 3
        myStack.top() shouldBe 3
        myStack.empty().shouldBeFalse()
    }

    @Test
    fun `pop should remove element`() {
        val myStack = MyStack()
        myStack.push(7)
        myStack.pop() shouldBe 7
        myStack.empty().shouldBeTrue()
    }

    @Test
    fun `LIFO order - multiple elements`() {
        val myStack = MyStack()
        myStack.push(1)
        myStack.push(2)
        myStack.push(3)
        myStack.push(4)
        myStack.push(5)
        myStack.pop() shouldBe 5
        myStack.pop() shouldBe 4
        myStack.pop() shouldBe 3
        myStack.pop() shouldBe 2
        myStack.pop() shouldBe 1
        myStack.empty().shouldBeTrue()
    }

    @Test
    fun `interleaved push and pop operations`() {
        val myStack = MyStack()
        myStack.push(1)
        myStack.push(2)
        myStack.pop() shouldBe 2
        myStack.push(3)
        myStack.pop() shouldBe 3
        myStack.push(4)
        myStack.top() shouldBe 4
        myStack.pop() shouldBe 4
        myStack.pop() shouldBe 1
        myStack.empty().shouldBeTrue()
    }

    @Test
    fun `push all values 1 to 9`() {
        val myStack = MyStack()
        for (i in 1..9) {
            myStack.push(i)
        }
        for (i in 9 downTo 1) {
            myStack.pop() shouldBe i
        }
        myStack.empty().shouldBeTrue()
    }
}
