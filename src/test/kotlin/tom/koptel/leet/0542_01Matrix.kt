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
            intArrayOf(0, 0, 0),
        )
        Solution().updateMatrix(input) shouldBe expected
    }

    private class Solution {
        fun updateMatrix(mat: Array<IntArray>): Array<IntArray> {
            val seed = ArrayDeque<Cell>()
            val distance: Array<IntArray> = Array(mat.size) {
                IntArray(mat[0].size) { -1 }
            }
            for (row in (0 until mat.size)) {
                for (cell in (0 until mat[row].size)) {
                    if (mat[row][cell] == 0) {
                        distance[row][cell] = 0
                        seed.add(Cell(row = row, cell = cell, mat = mat))
                    }
                }
            }

            while (seed.isNotEmpty()) {
                val seedCell = seed.removeFirst()
                seedCell.down()?.let { (row, cell) ->
                    seedCell.populateDistance(row = row, cell = cell, distance = distance)?.let(seed::add)
                }
                seedCell.up()?.let { (row, cell) ->
                    seedCell.populateDistance(row = row, cell = cell, distance = distance)?.let(seed::add)
                }
                seedCell.left()?.let { (row, cell) ->
                    seedCell.populateDistance(row = row, cell = cell, distance = distance)?.let(seed::add)
                }
                seedCell.right()?.let { (row, cell) ->
                    seedCell.populateDistance(row = row, cell = cell, distance = distance)?.let(seed::add)
                }
            }

            return distance
        }

        private class Cell(
            val row: Int,
            val cell: Int,
            private val mat: Array<IntArray>,
        ) {
            fun populateDistance(row: Int, cell: Int, distance: Array<IntArray>): Cell? {
                val el = distance[row][cell]
                return if (el == -1) {
                    distance[row][cell] = distance[this.row][this.cell] + 1
                    Cell(row = row, cell = cell, mat = mat)
                } else {
                    null
                }
            }

            fun down(): Pair<Int, Int>? {
                if (row + 1 < rowNum) return (row + 1) to cell
                return null
            }

            fun up(): Pair<Int, Int>? {
                if (row - 1 >= 0) return (row - 1) to cell
                return null
            }

            fun left(): Pair<Int, Int>? {
                if (cell - 1 >= 0) return row to (cell - 1)
                return null
            }

            fun right(): Pair<Int, Int>? {
                if (cell + 1 < cellNum) return row to (cell + 1)
                return null
            }

            private val rowNum: Int get() = mat.size
            private val cellNum: Int get() = mat[0].size
        }
    }
}
