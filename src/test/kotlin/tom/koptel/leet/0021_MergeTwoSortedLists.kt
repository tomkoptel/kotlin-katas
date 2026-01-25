package tom.koptel.leet

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class `0021_MergeTwoSortedLists` {
    @Test
    fun `merge two lists with interleaved values`() {
        // Input: list1 = [1,2,4], list2 = [1,3,4]
        // Output: [1,1,2,3,4,4]
        val list1 = createLinkedList(1, 2, 4)
        val list2 = createLinkedList(1, 3, 4)
        val result = Solution().mergeTwoLists(list1, list2)
        result.toList() shouldBe listOf(1, 1, 2, 3, 4, 4)
    }

    @Test
    fun `merge two empty lists`() {
        // Input: list1 = [], list2 = []
        // Output: []
        val result = Solution().mergeTwoLists(null, null)
        result.toList() shouldBe emptyList()
    }

    @Test
    fun `merge empty list with non-empty list`() {
        // Input: list1 = [], list2 = [0]
        // Output: [0]
        val list2 = createLinkedList(0)
        val result = Solution().mergeTwoLists(null, list2)
        result.toList() shouldBe listOf(0)
    }

    @Test
    fun `merge non-empty list with empty list`() {
        // Input: list1 = [5], list2 = []
        // Output: [5]
        val list1 = createLinkedList(5)
        val result = Solution().mergeTwoLists(list1, null)
        result.toList() shouldBe listOf(5)
    }

    @Test
    fun `merge lists where first list has all smaller values`() {
        // Input: list1 = [1,2,3], list2 = [4,5,6]
        // Output: [1,2,3,4,5,6]
        val list1 = createLinkedList(1, 2, 3)
        val list2 = createLinkedList(4, 5, 6)
        val result = Solution().mergeTwoLists(list1, list2)
        result.toList() shouldBe listOf(1, 2, 3, 4, 5, 6)
    }

    @Test
    fun `merge lists where second list has all smaller values`() {
        // Input: list1 = [7,8,9], list2 = [1,2,3]
        // Output: [1,2,3,7,8,9]
        val list1 = createLinkedList(7, 8, 9)
        val list2 = createLinkedList(1, 2, 3)
        val result = Solution().mergeTwoLists(list1, list2)
        result.toList() shouldBe listOf(1, 2, 3, 7, 8, 9)
    }

    @Test
    fun `merge lists with different lengths`() {
        // Input: list1 = [1,3,5,7,9], list2 = [2,4]
        // Output: [1,2,3,4,5,7,9]
        val list1 = createLinkedList(1, 3, 5, 7, 9)
        val list2 = createLinkedList(2, 4)
        val result = Solution().mergeTwoLists(list1, list2)
        result.toList() shouldBe listOf(1, 2, 3, 4, 5, 7, 9)
    }

    @Test
    fun `merge single node lists`() {
        // Input: list1 = [1], list2 = [2]
        // Output: [1,2]
        val list1 = createLinkedList(1)
        val list2 = createLinkedList(2)
        val result = Solution().mergeTwoLists(list1, list2)
        result.toList() shouldBe listOf(1, 2)
    }

    @Test
    fun `merge lists with duplicate values`() {
        // Input: list1 = [1,1,1], list2 = [1,1,1]
        // Output: [1,1,1,1,1,1]
        val list1 = createLinkedList(1, 1, 1)
        val list2 = createLinkedList(1, 1, 1)
        val result = Solution().mergeTwoLists(list1, list2)
        result.toList() shouldBe listOf(1, 1, 1, 1, 1, 1)
    }

    // Helper functions
    private fun createLinkedList(vararg values: Int): ListNode? {
        if (values.isEmpty()) return null
        val head = ListNode(values[0])
        var current = head
        for (i in 1 until values.size) {
            current.next = ListNode(values[i])
            current = current.next!!
        }
        return head
    }

    private fun ListNode?.toList(): List<Int> {
        val result = mutableListOf<Int>()
        var current = this
        while (current != null) {
            result.add(current.`val`)
            current = current.next
        }
        return result
    }

    class Solution {
        fun mergeTwoLists(list1: ListNode?, list2: ListNode?): ListNode? {
            val node = ListNode(0)
            var current: ListNode? = node
            var l1 = list1
            var l2 = list2
            while (l1 != null && l2 != null) {
                if (l1.`val` <= l2.`val`) {
                    current?.next = l1
                    l1 = l1.next
                } else {
                    current?.next = l2
                    l2 = l2.next
                }
                current = current?.next
            }
            current?.next = l1 ?: l2
            return node.next
        }
    }

    class ListNode(var `val`: Int) {
        var next: ListNode? = null

        override fun toString(): String {
            val values = mutableListOf<Int>()
            var current: ListNode? = this
            while (current != null) {
                values.add(current.`val`)
                current = current.next
            }
            return values.toString()
        }
    }
}
