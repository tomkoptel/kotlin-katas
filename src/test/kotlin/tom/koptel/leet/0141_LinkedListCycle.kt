package tom.koptel.leet

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class `0141_LinkedListCycle` {
    // ==================== NO CYCLE TESTS ====================

    @Test
    fun `empty list has no cycle`() {
        val head: ListNode? = null
        Solution().hasCycle(head) shouldBe false
    }

    @Test
    fun `single node without cycle`() {
        // [1] -> null
        val head = ListNode(1)
        Solution().hasCycle(head) shouldBe false
    }

    @Test
    fun `two nodes without cycle`() {
        // [1] -> [2] -> null
        val head =
            ListNode(1).apply {
                next = ListNode(2)
            }
        Solution().hasCycle(head) shouldBe false
    }

    @Test
    fun `three nodes without cycle`() {
        // [1] -> [2] -> [3] -> null
        val head =
            ListNode(1).apply {
                next =
                    ListNode(2).apply {
                        next = ListNode(3)
                    }
            }
        Solution().hasCycle(head) shouldBe false
    }

    @Test
    fun `long list without cycle`() {
        // [1] -> [2] -> [3] -> [4] -> [5] -> null
        val head = ListNode(1)
        var current = head
        for (i in 2..5) {
            current.next = ListNode(i)
            current = current.next!!
        }
        Solution().hasCycle(head) shouldBe false
    }

    // ==================== CYCLE TESTS ====================

    @Test
    fun `single node with cycle to itself`() {
        // [1] -> [1] (points to itself)
        val head = ListNode(1)
        head.next = head
        Solution().hasCycle(head) shouldBe true
    }

    @Test
    fun `two nodes with cycle - tail connects to head`() {
        // [1] -> [2] -> [1] (cycle)
        // pos = 0
        val head = ListNode(1)
        val second = ListNode(2)
        head.next = second
        second.next = head
        Solution().hasCycle(head) shouldBe true
    }

    @Test
    fun `two nodes with cycle - tail connects to tail`() {
        // [1] -> [2] -> [2] (second node points to itself)
        val head = ListNode(1)
        val second = ListNode(2)
        head.next = second
        second.next = second
        Solution().hasCycle(head) shouldBe true
    }

    @Test
    fun `example 1 - cycle at position 1`() {
        // [3] -> [2] -> [0] -> [-4] -> [2] (cycle back to index 1)
        // pos = 1
        val node3 = ListNode(3)
        val node2 = ListNode(2)
        val node0 = ListNode(0)
        val nodeNeg4 = ListNode(-4)

        node3.next = node2
        node2.next = node0
        node0.next = nodeNeg4
        nodeNeg4.next = node2 // cycle back to node2

        Solution().hasCycle(node3) shouldBe true
    }

    @Test
    fun `example 2 - cycle at position 0`() {
        // [1] -> [2] -> [1] (cycle back to head)
        // pos = 0
        val node1 = ListNode(1)
        val node2 = ListNode(2)

        node1.next = node2
        node2.next = node1 // cycle back to head

        Solution().hasCycle(node1) shouldBe true
    }

    @Test
    fun `cycle at the last node pointing to middle`() {
        // [1] -> [2] -> [3] -> [4] -> [5] -> [3] (cycle)
        // pos = 2
        val node1 = ListNode(1)
        val node2 = ListNode(2)
        val node3 = ListNode(3)
        val node4 = ListNode(4)
        val node5 = ListNode(5)

        node1.next = node2
        node2.next = node3
        node3.next = node4
        node4.next = node5
        node5.next = node3 // cycle back to node3

        Solution().hasCycle(node1) shouldBe true
    }

    @Test
    fun `cycle at the last node pointing to head`() {
        // [1] -> [2] -> [3] -> [4] -> [1] (cycle)
        // pos = 0
        val node1 = ListNode(1)
        val node2 = ListNode(2)
        val node3 = ListNode(3)
        val node4 = ListNode(4)

        node1.next = node2
        node2.next = node3
        node3.next = node4
        node4.next = node1 // cycle back to head

        Solution().hasCycle(node1) shouldBe true
    }

    @Test
    fun `cycle at the last node pointing to second-to-last`() {
        // [1] -> [2] -> [3] -> [4] -> [3] (small cycle at end)
        // pos = 2
        val node1 = ListNode(1)
        val node2 = ListNode(2)
        val node3 = ListNode(3)
        val node4 = ListNode(4)

        node1.next = node2
        node2.next = node3
        node3.next = node4
        node4.next = node3 // cycle between 3 and 4

        Solution().hasCycle(node1) shouldBe true
    }

    @Test
    fun `long list with cycle at beginning`() {
        // [1] -> [2] -> [3] -> ... -> [10] -> [1]
        val nodes = (1..10).map { ListNode(it) }
        for (i in 0 until nodes.size - 1) {
            nodes[i].next = nodes[i + 1]
        }
        nodes.last().next = nodes.first() // cycle to head

        Solution().hasCycle(nodes.first()) shouldBe true
    }

    @Test
    fun `long list with cycle in middle`() {
        // [1] -> [2] -> [3] -> [4] -> [5] -> [6] -> [7] -> [8] -> [9] -> [10] -> [5]
        val nodes = (1..10).map { ListNode(it) }
        for (i in 0 until nodes.size - 1) {
            nodes[i].next = nodes[i + 1]
        }
        nodes.last().next = nodes[4] // cycle to node with value 5 (index 4)

        Solution().hasCycle(nodes.first()) shouldBe true
    }

    // ==================== EDGE CASES ====================

    @Test
    fun `all nodes have same value without cycle`() {
        // [1] -> [1] -> [1] -> null
        val head =
            ListNode(1).apply {
                next =
                    ListNode(1).apply {
                        next = ListNode(1)
                    }
            }
        Solution().hasCycle(head) shouldBe false
    }

    @Test
    fun `all nodes have same value with cycle`() {
        // [1] -> [1] -> [1] -> [1] (first node)
        val node1 = ListNode(1)
        val node2 = ListNode(1)
        val node3 = ListNode(1)

        node1.next = node2
        node2.next = node3
        node3.next = node1

        Solution().hasCycle(node1) shouldBe true
    }

    @Test
    fun `negative values without cycle`() {
        // [-1] -> [-2] -> [-3] -> null
        val head =
            ListNode(-1).apply {
                next =
                    ListNode(-2).apply {
                        next = ListNode(-3)
                    }
            }
        Solution().hasCycle(head) shouldBe false
    }

    @Test
    fun `negative values with cycle`() {
        // [-1] -> [-2] -> [-3] -> [-1]
        val node1 = ListNode(-1)
        val node2 = ListNode(-2)
        val node3 = ListNode(-3)

        node1.next = node2
        node2.next = node3
        node3.next = node1

        Solution().hasCycle(node1) shouldBe true
    }

    @Test
    fun `mixed positive and negative values with cycle`() {
        // [3] -> [-2] -> [0] -> [4] -> [-2] (cycle to second node)
        val node3 = ListNode(3)
        val nodeNeg2 = ListNode(-2)
        val node0 = ListNode(0)
        val node4 = ListNode(4)

        node3.next = nodeNeg2
        nodeNeg2.next = node0
        node0.next = node4
        node4.next = nodeNeg2

        Solution().hasCycle(node3) shouldBe true
    }

    // ==================== HELPER & CLASSES ====================

    class Solution {
        fun hasCycle(head: ListNode?): Boolean {
            if (head == null) return false
            val map = mutableMapOf<ListNode, Int>()
            map[head] = 0
            var temp = head
            while (temp != null) {
                val increment = map[temp] ?: 0
                if (increment > 1) return true
                map[temp] = increment + 1
                temp = temp.next
            }
            return false
        }

        fun hasCycle2Pointer(head: ListNode?): Boolean {
            if (head == null) return false
            var slow = head
            var fast = head
            while (fast != null && fast.next != null) {
                fast = fast.next?.next
                slow = slow?.next
                if (fast == slow) {
                    return true
                }
            }
            return false
        }
    }

    class ListNode(var `val`: Int) {
        var next: ListNode? = null

        override fun toString(): String = "ListNode(`val`=$`val`)"
    }
}
