package tom.koptel.leet

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class `0542_01Matrix` {

    @Test
    fun first() {
        val input = arrayOf(
            intArrayOf(0, 0, 0),
            intArrayOf(0, 1, 0),
            intArrayOf(0, 0, 0)
        )
        val expected = arrayOf(
            intArrayOf(0, 0, 0),
            intArrayOf(0, 1, 0),
            intArrayOf(0, 0, 0)
        )
        Solution().updateMatrix(input) shouldBe expected
    }

    private class Solution {
        fun updateMatrix(mat: Array<IntArray>): Array<IntArray> {
            val seed = ArrayDeque<Pair<Int, Int>>()
            val rows = mat.size
            val cols = mat[0].size
            val directions = listOf((-1 to 0), (1 to 0), (0 to -1), (0 to 1))
            val distance: Array<IntArray> = Array(rows) { r ->
                IntArray(cols) { c ->
                    val el = mat[r][c]
                    if (el == 0) seed.add((r to c))
                    if (el == 0) 0 else -1
                }
            }

            while (seed.isNotEmpty()) {
                val (row, col) = seed.removeFirst()
                for ((dr, dc) in directions) {
                    val nr = row + dr
                    val nc = col + dc
                    if (
                        (nr in 0 until rows) &&
                        (nc in 0 until cols) &&
                        distance[nr][nc] == -1
                    ) {
                        distance[nr][nc] = distance[row][col] + 1
                        seed.add((nr to nc))
                    }
                }
            }

            return distance
        }

        fun dpUpdateMatrix(mat: Array<IntArray>): Array<IntArray> {
            val rows = mat.size
            val columns = mat[0].size
            val distance = Array(rows) { r ->
                IntArray(columns) { c ->
                    if (mat[r][c] == 0) 0 else rows + columns
                }
            }

            for (r in 0 until rows) {
                for (c in 0 until columns) {
                    if (r > 0) {
                        distance[r][c] = minOf(
                            distance[r - 1][c] + 1,
                            distance[r][c]
                        )
                    }
                    if (c > 0) {
                        distance[r][c] = minOf(
                            distance[r][c - 1] + 1,
                            distance[r][c]
                        )
                    }
                }
            }

            for (r in rows - 1 downTo 0) {
                for (c in columns - 1 downTo 0) {
                    if (r < rows - 1) {
                        distance[r][c] = minOf(
                            distance[r + 1][c] + 1,
                            distance[r][c]
                        )
                    }
                    if (c < columns - 1) {
                        distance[r][c] = minOf(
                            distance[r][c + 1] + 1,
                            distance[r][c]
                        )
                    }
                }
            }

            return distance
        }
    }
}
