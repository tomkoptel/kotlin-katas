package tom.koptel.leet

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class `0687_LongestUnivaluePath` {
    @Test
    fun `example 1 - path through root with same values`() {
        // Tree:       5
        //            / \
        //           4   5
        //          / \   \
        //         1   1   5
        val root = TreeNode(5).apply {
            left = TreeNode(4).apply {
                left = TreeNode(1)
                right = TreeNode(1)
            }
            right = TreeNode(5).apply {
                right = TreeNode(5)
            }
        }
        Solution().longestUnivaluePath(root) shouldBe 2
    }

    @Test
    fun `example 2 - longer path on left side`() {
        // Tree:       1
        //            / \
        //           4   5
        //          / \   \
        //         4   4   5
        val root = TreeNode(1).apply {
            left = TreeNode(4).apply {
                left = TreeNode(4)
                right = TreeNode(4)
            }
            right = TreeNode(5).apply {
                right = TreeNode(5)
            }
        }
        Solution().longestUnivaluePath(root) shouldBe 2
    }

    @Test
    fun `null root`() {
        Solution().longestUnivaluePath(null) shouldBe 0
    }

    @Test
    fun `single node`() {
        Solution().longestUnivaluePath(TreeNode(1)) shouldBe 0
    }

    @Test
    fun `all same values`() {
        // Tree:       1
        //            / \
        //           1   1
        //          / \
        //         1   1
        val root = TreeNode(1).apply {
            left = TreeNode(1).apply {
                left = TreeNode(1)
                right = TreeNode(1)
            }
            right = TreeNode(1)
        }
        Solution().longestUnivaluePath(root) shouldBe 3
    }

    @Test
    fun `all different values`() {
        // Tree:       1
        //            / \
        //           2   3
        val root = TreeNode(1).apply {
            left = TreeNode(2)
            right = TreeNode(3)
        }
        Solution().longestUnivaluePath(root) shouldBe 0
    }

    @Test
    fun `longest path does not pass through root`() {
        // Tree:       1
        //            /
        //           2
        //          / \
        //         2   2
        //        /     \
        //       2       2
        val root = TreeNode(1).apply {
            left = TreeNode(2).apply {
                left = TreeNode(2).apply {
                    left = TreeNode(2)
                }
                right = TreeNode(2).apply {
                    right = TreeNode(2)
                }
            }
        }
        Solution().longestUnivaluePath(root) shouldBe 4
    }

    @Test
    fun `left-skewed same values`() {
        // Tree: 3 - 3 - 3 - 3 (all left children)
        val root = TreeNode(3).apply {
            left = TreeNode(3).apply {
                left = TreeNode(3).apply {
                    left = TreeNode(3)
                }
            }
        }
        Solution().longestUnivaluePath(root) shouldBe 3
    }

    private class TreeNode(var `val`: Int) {
        var left: TreeNode? = null
        var right: TreeNode? = null

        override fun toString(): String {
            return "TreeNode(`val`=$`val`)"
        }
    }

    private class Solution {
        fun longestUnivaluePath(root: TreeNode?): Int {
            if (root == null) return 0
            val stack = ArrayDeque<TreeNode>().also { it.add(root) }
            val nodeHeights = mutableMapOf<TreeNode, Int>()
            var maxDiameter = 0

            var lastVisited: TreeNode? = null
            while (stack.isNotEmpty()) {
                val node = stack.last()
                val parentValue = node.`val`

                val leftNode = node.left
                val leftNodeValue = node.left?.`val` ?: 0

                val rightNode = node.right
                val rightNodeValue = node.right?.`val` ?: 0

                val childNode = rightNode ?: leftNode

                if ((childNode == null) || (lastVisited == childNode)) {
                    val leftHeight = (leftNode?.let { nodeHeights[it] ?: 0 } ?: 0).let {
                        if (parentValue == leftNodeValue) it else 0
                    }
                    val rightHeight = (rightNode?.let { nodeHeights[it] ?: 0 } ?: 0).let {
                        if (parentValue == rightNodeValue) it else 0
                    }
                    nodeHeights[node] = maxOf(leftHeight, rightHeight) + 1

                    val diameter = (leftHeight + rightHeight)
                    maxDiameter = maxOf(maxDiameter, diameter)
                    lastVisited = stack.removeLast()
                } else {
                    rightNode?.let { stack.addLast(it) }
                    leftNode?.let { stack.addLast(it) }
                }
            }
            return maxDiameter
        }
    }
}

