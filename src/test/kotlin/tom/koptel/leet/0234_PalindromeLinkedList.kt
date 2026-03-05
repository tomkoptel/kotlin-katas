package tom.koptel.leet

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class `0234_PalindromeLinkedList` {
    @Test
    fun `example 1 - not a palindrome`() {
        // [1] -> [2]
        val head = listOf(1, 2).toLinkedList()
        Solution().isPalindrome(head) shouldBe false
    }

    @Test
    fun `example 2 - palindrome`() {
        // [1] -> [2] -> [2] -> [1]
        val head = listOf(1, 2, 2, 1).toLinkedList()
        Solution().isPalindrome(head) shouldBe true
    }

    @Test
    fun `single node`() {
        Solution().isPalindrome(ListNode(1)) shouldBe true
    }

    @Test
    fun `null input`() {
        Solution().isPalindrome(null) shouldBe true
    }

    @Test
    fun `odd length palindrome`() {
        // [1] -> [2] -> [3] -> [2] -> [1]
        val head = listOf(1, 2, 3, 2, 1).toLinkedList()
        Solution().isPalindrome(head) shouldBe true
    }

    @Test
    fun `odd length not palindrome`() {
        // [1] -> [2] -> [3] -> [4] -> [5]
        val head = listOf(1, 2, 3, 4, 5).toLinkedList()
        Solution().isPalindrome(head) shouldBe false
    }

    @Test
    fun `even length not palindrome`() {
        // [1] -> [2] -> [3] -> [4]
        val head = listOf(1, 2, 3, 4).toLinkedList()
        Solution().isPalindrome(head) shouldBe false
    }

    @Test
    fun `all same values`() {
        // [7] -> [7] -> [7] -> [7]
        val head = listOf(7, 7, 7, 7).toLinkedList()
        Solution().isPalindrome(head) shouldBe true
    }

    @Test
    fun `two same nodes`() {
        // [1] -> [1]
        val head = listOf(1, 1).toLinkedList()
        Solution().isPalindrome(head) shouldBe true
    }

    @Test
    fun `three nodes palindrome`() {
        // [1] -> [2] -> [1]
        val head = listOf(1, 2, 1).toLinkedList()
        Solution().isPalindrome(head) shouldBe true
    }

    @Test
    fun `differs only at edges`() {
        // [1] -> [2] -> [3] -> [2] -> [9]
        val head = listOf(1, 2, 3, 2, 9).toLinkedList()
        Solution().isPalindrome(head) shouldBe false
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

    private class Solution {
        fun isPalindrome(head: ListNode?): Boolean {
            if (head == null) return true
            if (head.next == null) return true
            val middle = findMiddle(head)
            val reversed = reverse(middle)
            var slow1: ListNode? = head
            var slow2: ListNode? = reversed
            while (slow2 != null) {
                if (slow1?.`val` != slow2.`val`) return false
                slow1 = slow1.next
                slow2 = slow2.next
            }
            return true
        }

        private fun reverse(head: ListNode): ListNode {
            var current = head // 1 -> 2 ...
            var next: ListNode? = current.next // 2 -> 3 ...
            current.next = null // 1
            while (next != null) {
                val tmp = next.next // 3 -> ...
                next.next = current // 2 -> 1 -> null
                current = next // 2 -> 1 -> null
                next = tmp // 3 -> ...
            }
            return current
        }

        private fun findMiddle(head: ListNode): ListNode {
            var slow = head
            var fast: ListNode? = head
            while (fast != null && fast.next != null) {
                slow = slow.next ?: break
                fast = fast.next?.next
            }
            return slow
        }
    }

    private class ListNode(var `val`: Int) {
        var next: ListNode? = null

        override fun toString(): String = "ListNode(`val`=$`val`)"
    }
}
