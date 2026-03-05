package tom.koptel.leet

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class `0876_MiddleLinkedList` {
    @Test
    fun `example 1 - odd length list`() {
        // [1] -> [2] -> [3] -> [4] -> [5]
        // middle = 3
        val head = listOf(1, 2, 3, 4, 5).toLinkedList()
        Solution().middleNode(head)?.`val` shouldBe 3
    }

    @Test
    fun `example 2 - even length list returns second middle`() {
        // [1] -> [2] -> [3] -> [4] -> [5] -> [6]
        // two middles: 3 and 4, return second = 4
        val head = listOf(1, 2, 3, 4, 5, 6).toLinkedList()
        Solution().middleNode(head)?.`val` shouldBe 4
    }

    @Test
    fun `single node`() {
        val head = ListNode(1)
        Solution().middleNode(head)?.`val` shouldBe 1
    }

    @Test
    fun `two nodes returns second`() {
        // [1] -> [2]
        // two middles: return second = 2
        val head = listOf(1, 2).toLinkedList()
        Solution().middleNode(head)?.`val` shouldBe 2
    }

    @Test
    fun `three nodes`() {
        // [1] -> [2] -> [3]
        val head = listOf(1, 2, 3).toLinkedList()
        Solution().middleNode(head)?.`val` shouldBe 2
    }

    @Test
    fun `four nodes returns second middle`() {
        // [1] -> [2] -> [3] -> [4]
        // two middles: 2 and 3, return second = 3
        val head = listOf(1, 2, 3, 4).toLinkedList()
        Solution().middleNode(head)?.`val` shouldBe 3
    }

    @Test
    fun `null input`() {
        Solution().middleNode(null) shouldBe null
    }

    private fun List<Int>.toLinkedList(): ListNode? {
        if (isEmpty()) return null
        val head = ListNode(first())
        var current = head
        for (i in 1 until size) {
            val node = ListNode(this[i])
            current.next = node
            current = node
        }
        return head
    }

    class Solution {
        fun middleNode(head: ListNode?): ListNode? {
            if (head == null) return null
            if (head.next == null) return head
            var slow = head
            var fast = head
            while (fast != null && fast.next != null) {
                slow = slow?.next
                fast = fast.next?.next
            }
            return slow
        }
    }

    class ListNode(var `val`: Int) {
        var next: ListNode? = null

        override fun toString(): String = "ListNode(`val`=$`val`)"
    }
}
