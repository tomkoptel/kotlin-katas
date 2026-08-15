package tom.koptel.leet

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class `0542_01Matrix` {

    private data class Case(val input: Array<IntArray>, val expected: Array<IntArray>)

    private val cases = listOf(
        // example 1: single 1 surrounded by 0s
        Case(
            arrayOf(
                intArrayOf(0, 0, 0),
                intArrayOf(0, 1, 0),
                intArrayOf(0, 0, 0)
            ),
            arrayOf(
                intArrayOf(0, 0, 0),
                intArrayOf(0, 1, 0),
                intArrayOf(0, 0, 0)
            )
        ),
        // example 2: bottom row all 1s
        Case(
            arrayOf(
                intArrayOf(0, 0, 0),
                intArrayOf(0, 1, 0),
                intArrayOf(1, 1, 1)
            ),
            arrayOf(
                intArrayOf(0, 0, 0),
                intArrayOf(0, 1, 0),
                intArrayOf(1, 2, 1)
            )
        ),
        // single row, one 0 at the end
        Case(
            arrayOf(intArrayOf(1, 1, 1, 0)),
            arrayOf(intArrayOf(3, 2, 1, 0))
        ),
        // single column, one 0 at the top
        Case(
            arrayOf(intArrayOf(0), intArrayOf(1), intArrayOf(1)),
            arrayOf(intArrayOf(0), intArrayOf(1), intArrayOf(2))
        ),
        // 1x1 with the only cell being 0
        Case(
            arrayOf(intArrayOf(0)),
            arrayOf(intArrayOf(0))
        ),
        // all zeros
        Case(
            arrayOf(
                intArrayOf(0, 0),
                intArrayOf(0, 0)
            ),
            arrayOf(
                intArrayOf(0, 0),
                intArrayOf(0, 0)
            )
        ),
        // single 0 in a corner, distances grow diagonally
        Case(
            arrayOf(
                intArrayOf(0, 1, 1),
                intArrayOf(1, 1, 1),
                intArrayOf(1, 1, 1)
            ),
            arrayOf(
                intArrayOf(0, 1, 2),
                intArrayOf(1, 2, 3),
                intArrayOf(2, 3, 4)
            )
        )
    )

    @Test
    fun `bfs solves all cases`() {
        for (case in cases) {
            Solution().updateMatrix(deepCopy(case.input)) shouldBe case.expected
        }
    }

    @Test
    fun `dp solves all cases`() {
        for (case in cases) {
            Solution().dpUpdateMatrix(deepCopy(case.input)) shouldBe case.expected
        }
    }

    private fun deepCopy(mat: Array<IntArray>): Array<IntArray> = Array(mat.size) { r -> mat[r].copyOf() }

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
