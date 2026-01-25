package tom.koptel.leet

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.util.LinkedList
import java.util.Queue

class `0733_FloodFill` {
    @Test
    fun `example 1 - flood fill from center of 3x3 grid`() {
        // Input: image = [[1,1,1],[1,1,0],[1,0,1]], sr = 1, sc = 1, color = 2
        // Output: [[2,2,2],[2,2,0],[2,0,1]]
        // Explanation: From center (1,1), all connected pixels with color 1 become 2
        val image =
            arrayOf(
                intArrayOf(1, 1, 1),
                intArrayOf(1, 1, 0),
                intArrayOf(1, 0, 1)
            )
        val result = Solution().floodFill(image, sr = 1, sc = 1, color = 2)
        result shouldBe
            arrayOf(
                intArrayOf(2, 2, 2),
                intArrayOf(2, 2, 0),
                intArrayOf(2, 0, 1)
            )
    }

    @Test
    fun `example 2 - starting pixel already has target color`() {
        // Input: image = [[0,0,0],[0,0,0]], sr = 0, sc = 0, color = 0
        // Output: [[0,0,0],[0,0,0]]
        // Explanation: Starting pixel is already color 0, so no changes needed
        val image =
            arrayOf(
                intArrayOf(0, 0, 0),
                intArrayOf(0, 0, 0)
            )
        val result = Solution().floodFill(image, sr = 0, sc = 0, color = 0)
        result shouldBe
            arrayOf(
                intArrayOf(0, 0, 0),
                intArrayOf(0, 0, 0)
            )
    }

    @Test
    fun `single pixel image`() {
        // Input: image = [[1]], sr = 0, sc = 0, color = 2
        // Output: [[2]]
        // Explanation: Single pixel changes from 1 to 2
        val image = arrayOf(intArrayOf(1))
        val result = Solution().floodFill(image, sr = 0, sc = 0, color = 2)
        result shouldBe arrayOf(intArrayOf(2))
    }

    @Test
    fun `single pixel - already target color`() {
        // Input: image = [[5]], sr = 0, sc = 0, color = 5
        // Output: [[5]]
        // Explanation: Single pixel already has the target color
        val image = arrayOf(intArrayOf(5))
        val result = Solution().floodFill(image, sr = 0, sc = 0, color = 5)
        result shouldBe arrayOf(intArrayOf(5))
    }

    @Test
    fun `flood fill from top-left corner with ring pattern`() {
        // Input: image = [[1,1,1],[1,0,1],[1,1,1]], sr = 0, sc = 0, color = 3
        // Output: [[3,3,3],[3,0,3],[3,3,3]]
        // Explanation: All 1s form a ring around the center 0, so all are connected
        val image =
            arrayOf(
                intArrayOf(1, 1, 1),
                intArrayOf(1, 0, 1),
                intArrayOf(1, 1, 1)
            )
        val result = Solution().floodFill(image, sr = 0, sc = 0, color = 3)
        result shouldBe
            arrayOf(
                intArrayOf(3, 3, 3),
                intArrayOf(3, 0, 3),
                intArrayOf(3, 3, 3)
            )
    }

    @Test
    fun `flood fill from top-left corner with barrier`() {
        // Input: image = [[1,1,0],[1,0,1],[0,1,1]], sr = 0, sc = 0, color = 3
        // Output: [[3,3,0],[3,0,1],[0,1,1]]
        // Explanation: Barrier at (0,2) and (1,1) prevents reaching bottom-right region
        val image =
            arrayOf(
                intArrayOf(1, 1, 0),
                intArrayOf(1, 0, 1),
                intArrayOf(0, 1, 1)
            )
        val result = Solution().floodFill(image, sr = 0, sc = 0, color = 3)
        result shouldBe
            arrayOf(
                intArrayOf(3, 3, 0),
                intArrayOf(3, 0, 1),
                intArrayOf(0, 1, 1)
            )
    }

    @Test
    fun `flood fill from bottom-right corner`() {
        // Input: image = [[1,0,1],[0,1,1],[1,1,1]], sr = 2, sc = 2, color = 4
        // Output: [[1,0,4],[0,4,4],[4,4,4]]
        // Explanation: From bottom-right, all connected 1s (including top-right and bottom-left) change to 4
        val image =
            arrayOf(
                intArrayOf(1, 0, 1),
                intArrayOf(0, 1, 1),
                intArrayOf(1, 1, 1)
            )
        val result = Solution().floodFill(image, sr = 2, sc = 2, color = 4)
        result shouldBe
            arrayOf(
                intArrayOf(1, 0, 4),
                intArrayOf(0, 4, 4),
                intArrayOf(4, 4, 4)
            )
    }

    @Test
    fun `entire image same color - all pixels change`() {
        // Input: image = [[1,1],[1,1]], sr = 0, sc = 0, color = 5
        // Output: [[5,5],[5,5]]
        // Explanation: All pixels are connected with same color, all change
        val image =
            arrayOf(
                intArrayOf(1, 1),
                intArrayOf(1, 1)
            )
        val result = Solution().floodFill(image, sr = 0, sc = 0, color = 5)
        result shouldBe
            arrayOf(
                intArrayOf(5, 5),
                intArrayOf(5, 5)
            )
    }

    @Test
    fun `no adjacent pixels with same color`() {
        // Input: image = [[1,2,3],[2,1,2],[3,2,1]], sr = 1, sc = 1, color = 9
        // Output: [[1,2,3],[2,9,2],[3,2,1]]
        // Explanation: Only the starting pixel changes, no adjacent pixels match
        val image =
            arrayOf(
                intArrayOf(1, 2, 3),
                intArrayOf(2, 1, 2),
                intArrayOf(3, 2, 1)
            )
        val result = Solution().floodFill(image, sr = 1, sc = 1, color = 9)
        result shouldBe
            arrayOf(
                intArrayOf(1, 2, 3),
                intArrayOf(2, 9, 2),
                intArrayOf(3, 2, 1)
            )
    }

    @Test
    fun `vertical line pattern`() {
        // Input: image = [[0,1,0],[0,1,0],[0,1,0]], sr = 0, sc = 1, color = 7
        // Output: [[0,7,0],[0,7,0],[0,7,0]]
        // Explanation: Vertical line of 1s all change to 7
        val image =
            arrayOf(
                intArrayOf(0, 1, 0),
                intArrayOf(0, 1, 0),
                intArrayOf(0, 1, 0)
            )
        val result = Solution().floodFill(image, sr = 0, sc = 1, color = 7)
        result shouldBe
            arrayOf(
                intArrayOf(0, 7, 0),
                intArrayOf(0, 7, 0),
                intArrayOf(0, 7, 0)
            )
    }

    @Test
    fun `horizontal line pattern`() {
        // Input: image = [[0,0,0],[1,1,1],[0,0,0]], sr = 1, sc = 2, color = 8
        // Output: [[0,0,0],[8,8,8],[0,0,0]]
        // Explanation: Horizontal line of 1s all change to 8
        val image =
            arrayOf(
                intArrayOf(0, 0, 0),
                intArrayOf(1, 1, 1),
                intArrayOf(0, 0, 0)
            )
        val result = Solution().floodFill(image, sr = 1, sc = 2, color = 8)
        result shouldBe
            arrayOf(
                intArrayOf(0, 0, 0),
                intArrayOf(8, 8, 8),
                intArrayOf(0, 0, 0)
            )
    }

    @Test
    fun `diagonal not connected - only direct neighbors change`() {
        // Input: image = [[1,0,0],[0,1,0],[0,0,1]], sr = 0, sc = 0, color = 3
        // Output: [[3,0,0],[0,1,0],[0,0,1]]
        // Explanation: Diagonals don't count as adjacent, only the starting pixel changes
        val image =
            arrayOf(
                intArrayOf(1, 0, 0),
                intArrayOf(0, 1, 0),
                intArrayOf(0, 0, 1)
            )
        val result = Solution().floodFill(image, sr = 0, sc = 0, color = 3)
        result shouldBe
            arrayOf(
                intArrayOf(3, 0, 0),
                intArrayOf(0, 1, 0),
                intArrayOf(0, 0, 1)
            )
    }

    @Test
    fun `L-shaped pattern`() {
        // Input: image = [[1,1,0],[1,0,0],[1,1,1]], sr = 0, sc = 0, color = 6
        // Output: [[6,6,0],[6,0,0],[6,6,6]]
        // Explanation: L-shaped region of 1s all change to 6
        val image =
            arrayOf(
                intArrayOf(1, 1, 0),
                intArrayOf(1, 0, 0),
                intArrayOf(1, 1, 1)
            )
        val result = Solution().floodFill(image, sr = 0, sc = 0, color = 6)
        result shouldBe
            arrayOf(
                intArrayOf(6, 6, 0),
                intArrayOf(6, 0, 0),
                intArrayOf(6, 6, 6)
            )
    }

    @Test
    fun `donut pattern - inner hole preserved`() {
        // Input: image = [[1,1,1,1],[1,0,0,1],[1,0,0,1],[1,1,1,1]], sr = 0, sc = 0, color = 5
        // Output: [[5,5,5,5],[5,0,0,5],[5,0,0,5],[5,5,5,5]]
        // Explanation: Outer ring changes, inner hole of 0s preserved
        val image =
            arrayOf(
                intArrayOf(1, 1, 1, 1),
                intArrayOf(1, 0, 0, 1),
                intArrayOf(1, 0, 0, 1),
                intArrayOf(1, 1, 1, 1)
            )
        val result = Solution().floodFill(image, sr = 0, sc = 0, color = 5)
        result shouldBe
            arrayOf(
                intArrayOf(5, 5, 5, 5),
                intArrayOf(5, 0, 0, 5),
                intArrayOf(5, 0, 0, 5),
                intArrayOf(5, 5, 5, 5)
            )
    }

    @Test
    fun `checkerboard pattern - only one color changes`() {
        // Input: image = [[1,0,1],[0,1,0],[1,0,1]], sr = 1, sc = 1, color = 9
        // Output: [[1,0,1],[0,9,0],[1,0,1]]
        // Explanation: In checkerboard, center pixel has no adjacent same-colored pixels
        val image =
            arrayOf(
                intArrayOf(1, 0, 1),
                intArrayOf(0, 1, 0),
                intArrayOf(1, 0, 1)
            )
        val result = Solution().floodFill(image, sr = 1, sc = 1, color = 9)
        result shouldBe
            arrayOf(
                intArrayOf(1, 0, 1),
                intArrayOf(0, 9, 0),
                intArrayOf(1, 0, 1)
            )
    }

    @Test
    fun `large rectangular region`() {
        // Input: image = [[2,2,2,2],[2,2,2,2],[2,2,2,2]], sr = 1, sc = 2, color = 3
        // Output: [[3,3,3,3],[3,3,3,3],[3,3,3,3]]
        // Explanation: All pixels are connected with same color
        val image =
            arrayOf(
                intArrayOf(2, 2, 2, 2),
                intArrayOf(2, 2, 2, 2),
                intArrayOf(2, 2, 2, 2)
            )
        val result = Solution().floodFill(image, sr = 1, sc = 2, color = 3)
        result shouldBe
            arrayOf(
                intArrayOf(3, 3, 3, 3),
                intArrayOf(3, 3, 3, 3),
                intArrayOf(3, 3, 3, 3)
            )
    }

    @Test
    fun `complex maze-like pattern with separate regions`() {
        // Input: image = [[1,1,0,1],[1,0,0,1],[1,1,1,0]], sr = 0, sc = 0, color = 4
        // Output: [[4,4,0,1],[4,0,0,1],[4,4,4,0]]
        // Explanation: Left region of 1s changes, right column of 1s separated by 0s
        val image =
            arrayOf(
                intArrayOf(1, 1, 0, 1),
                intArrayOf(1, 0, 0, 1),
                intArrayOf(1, 1, 1, 0)
            )
        val result = Solution().floodFill(image, sr = 0, sc = 0, color = 4)
        result shouldBe
            arrayOf(
                intArrayOf(4, 4, 0, 1),
                intArrayOf(4, 0, 0, 1),
                intArrayOf(4, 4, 4, 0)
            )
    }

    @Test
    fun `1x5 horizontal strip`() {
        // Input: image = [[3,3,3,3,3]], sr = 0, sc = 2, color = 7
        // Output: [[7,7,7,7,7]]
        // Explanation: Single row, all pixels connected
        val image = arrayOf(intArrayOf(3, 3, 3, 3, 3))
        val result = Solution().floodFill(image, sr = 0, sc = 2, color = 7)
        result shouldBe arrayOf(intArrayOf(7, 7, 7, 7, 7))
    }

    @Test
    fun `5x1 vertical strip`() {
        // Input: image = [[4],[4],[4],[4],[4]], sr = 3, sc = 0, color = 9
        // Output: [[9],[9],[9],[9],[9]]
        // Explanation: Single column, all pixels connected
        val image =
            arrayOf(
                intArrayOf(4),
                intArrayOf(4),
                intArrayOf(4),
                intArrayOf(4),
                intArrayOf(4)
            )
        val result = Solution().floodFill(image, sr = 3, sc = 0, color = 9)
        result shouldBe
            arrayOf(
                intArrayOf(9),
                intArrayOf(9),
                intArrayOf(9),
                intArrayOf(9),
                intArrayOf(9)
            )
    }

    @Test
    fun `two separate regions - only one changes`() {
        // Input: image = [[1,1,0,2,2],[1,1,0,2,2]], sr = 0, sc = 0, color = 5
        // Output: [[5,5,0,2,2],[5,5,0,2,2]]
        // Explanation: Only the left region of 1s changes, right region of 2s unchanged
        val image =
            arrayOf(
                intArrayOf(1, 1, 0, 2, 2),
                intArrayOf(1, 1, 0, 2, 2)
            )
        val result = Solution().floodFill(image, sr = 0, sc = 0, color = 5)
        result shouldBe
            arrayOf(
                intArrayOf(5, 5, 0, 2, 2),
                intArrayOf(5, 5, 0, 2, 2)
            )
    }

    @Test
    fun `maximum pixel value constraint`() {
        // Input: image = [[65535,65535],[65535,65535]], sr = 1, sc = 1, color = 65535
        // Output: [[65535,65535],[65535,65535]]
        // Explanation: Testing with max pixel value (2^16 - 1), already target color
        val image =
            arrayOf(
                intArrayOf(65535, 65535),
                intArrayOf(65535, 65535)
            )
        val result = Solution().floodFill(image, sr = 1, sc = 1, color = 65535)
        result shouldBe
            arrayOf(
                intArrayOf(65535, 65535),
                intArrayOf(65535, 65535)
            )
    }

    @Test
    fun `starting from edge - top edge`() {
        // Input: image = [[5,5,5],[0,0,0],[5,5,5]], sr = 0, sc = 1, color = 8
        // Output: [[8,8,8],[0,0,0],[5,5,5]]
        // Explanation: Starting from top edge, only top row changes
        val image =
            arrayOf(
                intArrayOf(5, 5, 5),
                intArrayOf(0, 0, 0),
                intArrayOf(5, 5, 5)
            )
        val result = Solution().floodFill(image, sr = 0, sc = 1, color = 8)
        result shouldBe
            arrayOf(
                intArrayOf(8, 8, 8),
                intArrayOf(0, 0, 0),
                intArrayOf(5, 5, 5)
            )
    }

    @Test
    fun `starting from edge - left edge`() {
        // Input: image = [[3,0,3],[3,0,3],[3,0,3]], sr = 1, sc = 0, color = 2
        // Output: [[2,0,3],[2,0,3],[2,0,3]]
        // Explanation: Starting from left edge, left column changes
        val image =
            arrayOf(
                intArrayOf(3, 0, 3),
                intArrayOf(3, 0, 3),
                intArrayOf(3, 0, 3)
            )
        val result = Solution().floodFill(image, sr = 1, sc = 0, color = 2)
        result shouldBe
            arrayOf(
                intArrayOf(2, 0, 3),
                intArrayOf(2, 0, 3),
                intArrayOf(2, 0, 3)
            )
    }

    @Test
    fun `zigzag pattern`() {
        // Input: image = [[1,1,0],[0,1,1],[1,1,0]], sr = 0, sc = 0, color = 6
        // Output: [[6,6,0],[0,6,6],[6,6,0]]
        // Explanation: All 1s are connected through the zigzag path via (1,1)
        val image =
            arrayOf(
                intArrayOf(1, 1, 0),
                intArrayOf(0, 1, 1),
                intArrayOf(1, 1, 0)
            )
        val result = Solution().floodFill(image, sr = 0, sc = 0, color = 6)
        result shouldBe
            arrayOf(
                intArrayOf(6, 6, 0),
                intArrayOf(0, 6, 6),
                intArrayOf(6, 6, 0)
            )
    }

    class Solution {
        // Approach 1: Improved BFS (your current approach with optimizations)
        fun floodFill(image: Array<IntArray>, sr: Int, sc: Int, color: Int): Array<IntArray> {
            val originalColor = image[sr][sc]

            // Early return: if already the target color, no work needed
            if (originalColor == color) return image

            val rows = image.size
            val cols = image[0].size
            val queue: Queue<Pair<Int, Int>> = LinkedList()

            // Direction vectors for cleaner neighbor checking (up, left, down, right)
            val directions = arrayOf(-1 to 0, 0 to -1, 1 to 0, 0 to 1)

            image[sr][sc] = color
            queue.add(sr to sc)

            while (queue.isNotEmpty()) {
                val (row, col) = queue.poll()

                for ((dr, dc) in directions) {
                    val newRow = row + dr
                    val newCol = col + dc

                    if (newRow in 0 until rows &&
                        newCol in 0 until cols &&
                        image[newRow][newCol] == originalColor
                    ) {
                        image[newRow][newCol] = color
                        queue.add(newRow to newCol)
                    }
                }
            }

            return image
        }

        // Approach 2: DFS (Recursive) - more memory efficient for deep fills
        fun floodFillDFS(image: Array<IntArray>, sr: Int, sc: Int, color: Int): Array<IntArray> {
            val originalColor = image[sr][sc]
            if (originalColor == color) return image

            fun dfs(row: Int, col: Int) {
                // Base case: out of bounds or not matching original color
                if (row !in image.indices ||
                    col !in image[0].indices ||
                    image[row][col] != originalColor
                ) {
                    return
                }

                image[row][col] = color

                // Recursively fill all 4 directions
                dfs(row - 1, col)
                dfs(row + 1, col)
                dfs(row, col - 1)
                dfs(row, col + 1)
            }

            dfs(sr, sc)
            return image
        }

        // Approach 3: DFS with explicit stack (avoids recursion stack overflow)
        fun floodFillStack(image: Array<IntArray>, sr: Int, sc: Int, color: Int): Array<IntArray> {
            val originalColor = image[sr][sc]
            if (originalColor == color) return image

            val rows = image.size
            val cols = image[0].size
            val stack = ArrayDeque<Pair<Int, Int>>()

            stack.addLast(sr to sc)
            image[sr][sc] = color

            while (stack.isNotEmpty()) {
                val (row, col) = stack.removeLast()

                // Check all 4 neighbors
                listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1).forEach { (dr, dc) ->
                    val newRow = row + dr
                    val newCol = col + dc

                    if (newRow in 0 until rows &&
                        newCol in 0 until cols &&
                        image[newRow][newCol] == originalColor
                    ) {
                        image[newRow][newCol] = color
                        stack.addLast(newRow to newCol)
                    }
                }
            }

            return image
        }
    }
}
