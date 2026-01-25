package tom.koptel.leet

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.math.abs

class `0110_BalancedBinaryTree` {
    /**
     * Test: Example 1 - Balanced tree
     * Tree structure:
     *       3
     *      / \
     *     9  20
     *        / \
     *       15  7
     * Expected: true
     */
    @Test
    fun `test example 1 - balanced tree`() {
        val root =
            TreeNode(3).apply {
                left = TreeNode(9)
                right =
                    TreeNode(20).apply {
                        left = TreeNode(15)
                        right = TreeNode(7)
                    }
            }

        val result = Solution().isBalanced(root)

        result shouldBe true
    }

    /**
     * Test: Example 2 - Unbalanced tree
     * Tree structure:
     *          1
     *         / \
     *        2   2
     *       / \
     *      3   3
     *     / \
     *    4   4
     * Expected: false
     */
    @Test
    fun `test example 2 - unbalanced tree with left skew`() {
        val root =
            TreeNode(1).apply {
                left =
                    TreeNode(2).apply {
                        left =
                            TreeNode(3).apply {
                                left = TreeNode(4)
                                right = TreeNode(4)
                            }
                        right = TreeNode(3)
                    }
                right = TreeNode(2)
            }

        val result = Solution().isBalanced(root)

        result shouldBe false
    }

    /**
     * Test: Example 3 - Empty tree
     * Expected: true
     */
    @Test
    fun `test example 3 - empty tree`() {
        val root = null

        val result = Solution().isBalanced(root)

        result shouldBe true
    }

    /**
     * Test: Single node tree
     * Tree structure:
     *     1
     * Expected: true
     */
    @Test
    fun `test single node tree`() {
        val root = TreeNode(1)

        val result = Solution().isBalanced(root)

        result shouldBe true
    }

    /**
     * Test: Perfectly balanced complete binary tree
     * Tree structure:
     *        1
     *       / \
     *      2   3
     *     / \ / \
     *    4  5 6  7
     * Expected: true
     */
    @Test
    fun `test perfectly balanced complete tree`() {
        val root =
            TreeNode(1).apply {
                left =
                    TreeNode(2).apply {
                        left = TreeNode(4)
                        right = TreeNode(5)
                    }
                right =
                    TreeNode(3).apply {
                        left = TreeNode(6)
                        right = TreeNode(7)
                    }
            }

        val result = Solution().isBalanced(root)

        result shouldBe true
    }

    /**
     * Test: Left-skewed tree (unbalanced)
     * Tree structure:
     *     1
     *    /
     *   2
     *  /
     * 3
     * Expected: false
     */
    @Test
    fun `test left-skewed tree`() {
        val root =
            TreeNode(1).apply {
                left =
                    TreeNode(2).apply {
                        left = TreeNode(3)
                    }
            }

        val result = Solution().isBalanced(root)

        result shouldBe false
    }

    /**
     * Test: Right-skewed tree (unbalanced)
     * Tree structure:
     * 1
     *  \
     *   2
     *    \
     *     3
     * Expected: false
     */
    @Test
    fun `test right-skewed tree`() {
        val root =
            TreeNode(1).apply {
                right =
                    TreeNode(2).apply {
                        right = TreeNode(3)
                    }
            }

        val result = Solution().isBalanced(root)

        result shouldBe false
    }

    /**
     * Test: Two-node tree (balanced)
     * Tree structure:
     *   1
     *  /
     * 2
     * Expected: true
     */
    @Test
    fun `test two-node tree balanced`() {
        val root =
            TreeNode(1).apply {
                left = TreeNode(2)
            }

        val result = Solution().isBalanced(root)

        result shouldBe true
    }

    /**
     * Test: Tree with height difference exactly 1 (balanced)
     * Tree structure:
     *       1
     *      / \
     *     2   3
     *    /
     *   4
     * Expected: true
     */
    @Test
    fun `test tree with height difference of 1`() {
        val root =
            TreeNode(1).apply {
                left =
                    TreeNode(2).apply {
                        left = TreeNode(4)
                    }
                right = TreeNode(3)
            }

        val result = Solution().isBalanced(root)

        result shouldBe true
    }

    /**
     * Test: Tree with height difference of 2 (unbalanced)
     * Tree structure:
     *       1
     *      / \
     *     2   3
     *    /
     *   4
     *  /
     * 5
     * Expected: false
     */
    @Test
    fun `test tree with height difference of 2`() {
        val root =
            TreeNode(1).apply {
                left =
                    TreeNode(2).apply {
                        left =
                            TreeNode(4).apply {
                                left = TreeNode(5)
                            }
                    }
                right = TreeNode(3)
            }

        val result = Solution().isBalanced(root)

        result shouldBe false
    }

    /**
     * Test: Balanced tree with negative values
     * Tree structure:
     *       -1
     *       / \
     *     -2  -3
     *     /     \
     *   -4      -5
     * Expected: true
     */
    @Test
    fun `test balanced tree with negative values`() {
        val root =
            TreeNode(-1).apply {
                left =
                    TreeNode(-2).apply {
                        left = TreeNode(-4)
                    }
                right =
                    TreeNode(-3).apply {
                        right = TreeNode(-5)
                    }
            }

        val result = Solution().isBalanced(root)

        result shouldBe true
    }

    /**
     * Test: Subtree is unbalanced even if root looks balanced
     * Tree structure:
     *         1
     *        / \
     *       2   3
     *      /
     *     4
     *    /
     *   5
     * Expected: false
     */
    @Test
    fun `test unbalanced subtree`() {
        val root =
            TreeNode(1).apply {
                left =
                    TreeNode(2).apply {
                        left =
                            TreeNode(4).apply {
                                left = TreeNode(5)
                            }
                    }
                right = TreeNode(3)
            }

        val result = Solution().isBalanced(root)

        result shouldBe false
    }

    /**
     * Test: Large balanced tree
     * Tree structure:
     *           1
     *         /   \
     *        2     3
     *       / \   / \
     *      4   5 6   7
     *     /
     *    8
     * Expected: true
     */
    @Test
    fun `test large balanced tree`() {
        val root =
            TreeNode(1).apply {
                left =
                    TreeNode(2).apply {
                        left =
                            TreeNode(4).apply {
                                left = TreeNode(8)
                            }
                        right = TreeNode(5)
                    }
                right =
                    TreeNode(3).apply {
                        left = TreeNode(6)
                        right = TreeNode(7)
                    }
            }

        val result = Solution().isBalanced(root)

        result shouldBe true
    }

    /**
     * Test: Tricky balanced tree (failing case from leetcode)
     * Tree structure:
     *        1
     *       / \
     *      2   3
     *     / \ /
     *    4  5 6
     *   /
     *  8
     *
     * Input: [1,2,3,4,5,6,null,8]
     *
     * Heights:
     * - Left subtree (2): height = 2
     * - Right subtree (3): height = 1
     * - Difference at root: |2 - 1| = 1 ✓ balanced
     *
     * This tree IS balanced because:
     * - Node 8: |height(8) - height(null)| = |0 - (-1)| = 1 ✓
     * - Node 4: |height(4.left) - height(4.right)| = |1 - 0| = 1 ✓
     * - Node 2: |height(2.left) - height(2.right)| = |2 - 1| = 1 ✓
     * - Node 3: |height(3.left) - height(3.right)| = |1 - 0| = 1 ✓
     * - Node 1: |height(1.left) - height(1.right)| = |3 - 2| = 1 ✓
     *
     * Expected: true
     */
    @Test
    fun `test tricky balanced tree - leetcode failing case`() {
        val root =
            TreeNode(1).apply {
                left =
                    TreeNode(2).apply {
                        left =
                            TreeNode(4).apply {
                                left = TreeNode(8)
                            }
                        right = TreeNode(5)
                    }
                right =
                    TreeNode(3).apply {
                        left = TreeNode(6)
                    }
            }

        val result = Solution().isBalanced(root)

        result shouldBe true
    }

    // ==================== ITERATIVE APPROACH TESTS ====================

    /**
     * Test iterative approach with balanced tree
     */
    @Test
    fun `test iterative - example 1 balanced tree`() {
        val root =
            TreeNode(3).apply {
                left = TreeNode(9)
                right =
                    TreeNode(20).apply {
                        left = TreeNode(15)
                        right = TreeNode(7)
                    }
            }

        val result = Solution().isBalancedIterative(root)

        result shouldBe true
    }

    /**
     * Test iterative approach with unbalanced tree
     */
    @Test
    fun `test iterative - example 2 unbalanced tree`() {
        val root =
            TreeNode(1).apply {
                left =
                    TreeNode(2).apply {
                        left =
                            TreeNode(3).apply {
                                left = TreeNode(4)
                                right = TreeNode(4)
                            }
                        right = TreeNode(3)
                    }
                right = TreeNode(2)
            }

        val result = Solution().isBalancedIterative(root)

        result shouldBe false
    }

    /**
     * Test iterative approach with empty tree
     */
    @Test
    fun `test iterative - empty tree`() {
        val root = null

        val result = Solution().isBalancedIterative(root)

        result shouldBe true
    }

    /**
     * Test iterative approach with the tricky balanced tree
     */
    @Test
    fun `test iterative - tricky balanced tree from leetcode`() {
        val root =
            TreeNode(1).apply {
                left =
                    TreeNode(2).apply {
                        left =
                            TreeNode(4).apply {
                                left = TreeNode(8)
                            }
                        right = TreeNode(5)
                    }
                right =
                    TreeNode(3).apply {
                        left = TreeNode(6)
                    }
            }

        val result = Solution().isBalancedIterative(root)

        result shouldBe true
    }

    /**
     * Test iterative approach with left-skewed tree
     */
    @Test
    fun `test iterative - left-skewed unbalanced tree`() {
        val root =
            TreeNode(1).apply {
                left =
                    TreeNode(2).apply {
                        left = TreeNode(3)
                    }
            }

        val result = Solution().isBalancedIterative(root)

        result shouldBe false
    }

    /**
     * Test iterative approach with right-skewed tree
     */
    @Test
    fun `test iterative - right-skewed unbalanced tree`() {
        val root =
            TreeNode(1).apply {
                right =
                    TreeNode(2).apply {
                        right = TreeNode(3)
                    }
            }

        val result = Solution().isBalancedIterative(root)

        result shouldBe false
    }

    /**
     * Test: Verify both recursive and iterative produce same results
     */
    @Test
    fun `test both approaches produce same result - balanced`() {
        val root =
            TreeNode(1).apply {
                left =
                    TreeNode(2).apply {
                        left = TreeNode(4)
                        right = TreeNode(5)
                    }
                right =
                    TreeNode(3).apply {
                        left = TreeNode(6)
                        right = TreeNode(7)
                    }
            }

        val recursiveResult = Solution().isBalanced(root)
        val iterativeResult = Solution().isBalancedIterative(root)

        recursiveResult shouldBe iterativeResult
        recursiveResult shouldBe true
    }

    /**
     * Test: Verify both recursive and iterative produce same results
     */
    @Test
    fun `test both approaches produce same result - unbalanced`() {
        val root =
            TreeNode(1).apply {
                left =
                    TreeNode(2).apply {
                        left =
                            TreeNode(4).apply {
                                left = TreeNode(5)
                            }
                    }
                right = TreeNode(3)
            }

        val recursiveResult = Solution().isBalanced(root)
        val iterativeResult = Solution().isBalancedIterative(root)

        recursiveResult shouldBe iterativeResult
        recursiveResult shouldBe false
    }

    /**
     * Example:
     * var ti = TreeNode(5)
     * var v = ti.`val`
     * Definition for a binary tree node.
     */
    class TreeNode(var `val`: Int = 0) {
        var left: TreeNode? = null
        var right: TreeNode? = null

        /**
         * Pretty print the tree structure for debugging
         * Example output:
         *     4
         *    / \
         *   2   7
         *  / \ / \
         * 1  3 6  9
         */
        override fun toString(): String {
            val lines = mutableListOf<String>()
            buildTreeString(this, "", "", lines)
            return lines.joinToString("\n")
        }

        private fun buildTreeString(node: TreeNode?, prefix: String, childrenPrefix: String, lines: MutableList<String>) {
            if (node == null) return

            lines.add(prefix + node.`val`)

            val children =
                listOfNotNull(
                    node.left?.let { "L" to it },
                    node.right?.let { "R" to it }
                )

            children.forEachIndexed { index, (side, child) ->
                val isLast = index == children.lastIndex
                val connector = if (side == "L") "├─L: " else "└─R: "
                val newPrefix = childrenPrefix + connector
                val newChildrenPrefix = childrenPrefix + if (isLast) "    " else "│   "
                buildTreeString(child, newPrefix, newChildrenPrefix, lines)
            }
        }

        /**
         * Compare two trees for structural and value equality
         */
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is TreeNode) return false
            if (this.`val` != other.`val`) return false
            if (this.left != other.left) return false
            if (this.right != other.right) return false
            return true
        }

        /**
         * Generate hash code based on tree structure and values
         */
        override fun hashCode(): Int {
            var result = `val`
            result = 31 * result + (left?.hashCode() ?: 0)
            result = 31 * result + (right?.hashCode() ?: 0)
            return result
        }
    }

    class Solution {
        fun isBalanced(root: TreeNode?): Boolean = checkHeight(root) != -1

        /**
         * Returns the height of the subtree if balanced, -1 if unbalanced
         */
        private fun checkHeight(node: TreeNode?): Int {
            // Base case: empty tree has height 0 and is balanced
            if (node == null) return 0

            // Check left subtree
            val leftHeight = checkHeight(node.left)
            if (leftHeight == -1) return -1 // Left subtree is unbalanced

            // Check right subtree
            val rightHeight = checkHeight(node.right)
            if (rightHeight == -1) return -1 // Right subtree is unbalanced

            // Check current node's balance
            if (abs(leftHeight - rightHeight) > 1) {
                return -1 // Current node is unbalanced
            }

            // Return height of this subtree
            return maxOf(leftHeight, rightHeight) + 1
        }

        fun isBalancedIterative(root: TreeNode?): Boolean {
            if (root == null) return true

            // Map to store heights of processed nodes
            val heights = mutableMapOf<TreeNode, Int>()

            // Stack for post-order traversal
            val stack = mutableListOf<TreeNode>()

            // current: tracks which node we're EXPLORING (going down the tree)
            var current: TreeNode? = root

            // lastVisited: tracks which node we just PROCESSED (finished calculating)
            // Used to determine if right child has been processed yet
            var lastVisited: TreeNode? = null

            while (stack.isNotEmpty() || current != null) {
                // PHASE 1: EXPLORE - Go down to the leftmost node
                // Push all nodes along the left path onto the stack
                while (current != null) {
                    stack.add(current)
                    current = current.left // Move down left (EXPLORING)
                }
                // When current becomes null, we've reached the bottom

                // PHASE 2: DECIDE - Should we process this node or explore right?
                val peekNode = stack.last()

                // DECISION: Has the right child been processed?
                // Compare lastVisited with peekNode.right to find out
                if (peekNode.right != null && lastVisited != peekNode.right) {
                    // RIGHT CHILD EXISTS and HASN'T BEEN PROCESSED YET
                    // We need to explore the right subtree first (post-order: left→right→parent)
                    current = peekNode.right // Start EXPLORING the right subtree
                } else {
                    // PHASE 3: PROCESS - Both children done (or don't exist)
                    // Either:
                    // 1. No right child exists (peekNode.right == null), OR
                    // 2. Right child was just processed (lastVisited == peekNode.right)
                    // Safe to process this node now!

                    stack.removeAt(stack.lastIndex) // Remove from stack

                    // Get heights of left and right children from our map
                    val leftHeight =
                        if (peekNode.left != null) {
                            heights[peekNode.left] ?: return false
                        } else {
                            0 // No left child = height 0
                        }

                    val rightHeight =
                        if (peekNode.right != null) {
                            heights[peekNode.right] ?: return false
                        } else {
                            0 // No right child = height 0
                        }

                    // Check if current node is balanced
                    if (abs(leftHeight - rightHeight) > 1) {
                        return false // Unbalanced! Short-circuit
                    }

                    // Store height for this node (so parent can use it)
                    heights[peekNode] = maxOf(leftHeight, rightHeight) + 1

                    // Mark this node as PROCESSED
                    lastVisited = peekNode
                    // Note: current stays null, so next iteration peeks at stack top
                }
            }

            return true // All nodes checked, tree is balanced
        }
    }
}
