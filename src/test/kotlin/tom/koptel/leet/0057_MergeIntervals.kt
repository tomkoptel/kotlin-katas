package tom.koptel.leet

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class `0057_MergeIntervals` {
    @Test
    fun `new interval to be added`() {
        val result = Solution().insert(
            arrayOf(
                intArrayOf(1, 2),
                intArrayOf(3, 5),
                intArrayOf(6, 7),
                intArrayOf(8, 10),
                intArrayOf(15, 17),
                intArrayOf(20, 22),
            ), intArrayOf(11, 12)
        )

        result shouldBe arrayOf(
            intArrayOf(1, 2),
            intArrayOf(3, 5),
            intArrayOf(6, 7),
            intArrayOf(8, 10),
            intArrayOf(11, 12),
            intArrayOf(15, 17),
            intArrayOf(20, 22),
        )
    }

    @Test
    fun `lower bound overlaps with 1 element`() {
        val result = Solution().insert(
            arrayOf(
                intArrayOf(1, 2),
                intArrayOf(3, 5),
                intArrayOf(6, 7),
                intArrayOf(8, 10),
                intArrayOf(15, 17),
                intArrayOf(20, 22),
            ), intArrayOf(10, 14)
        )

        result shouldBe arrayOf(
            intArrayOf(1, 2),
            intArrayOf(3, 5),
            intArrayOf(6, 7),
            intArrayOf(8, 14),
            intArrayOf(15, 17),
            intArrayOf(20, 22),
        )
    }

    @Test
    fun `upper bound overlaps with 1 element`() {
        val result = Solution().insert(
            arrayOf(
                intArrayOf(1, 2),
                intArrayOf(3, 5),
                intArrayOf(6, 7),
                intArrayOf(8, 10),
                intArrayOf(15, 17),
                intArrayOf(20, 22),
            ), intArrayOf(15, 18)
        )

        result shouldBe arrayOf(
            intArrayOf(1, 2),
            intArrayOf(3, 5),
            intArrayOf(6, 7),
            intArrayOf(8, 10),
            intArrayOf(15, 18),
            intArrayOf(20, 22),
        )
    }

    @Test
    fun `overlaps with multiple elements`() {
        val result = Solution().insert(
            arrayOf(
                intArrayOf(1, 2),
                intArrayOf(3, 5),
                intArrayOf(6, 7),
                intArrayOf(8, 10),
                intArrayOf(15, 17),
                intArrayOf(20, 22),
            ), intArrayOf(6, 17)
        )

        result shouldBe arrayOf(
            intArrayOf(1, 2),
            intArrayOf(3, 5),
            intArrayOf(6, 17),
            intArrayOf(20, 22),
        )
    }

    @Test
    fun `new interval fully swallows multiple intervals across gaps`() {
        val result = Solution().insert(
            arrayOf(
                intArrayOf(1, 3),
                intArrayOf(5, 7),
                intArrayOf(9, 11),
                intArrayOf(13, 15),
            ), intArrayOf(4, 12)  // start in gap, end in gap, swallows [5,7] and [9,11]
        )

        result shouldBe arrayOf(
            intArrayOf(1, 3),
            intArrayOf(4, 12),
            intArrayOf(13, 15),
        )
    }

    private class Solution {
        fun insert(intervals: Array<IntArray>, newInterval: IntArray): Array<IntArray> {
            val lower = newInterval.first()
            val upper = newInterval.last()
            val lowerOutcome = find(intervals, lower)
            val upperOutcome = find(intervals, upper)

            return when (lowerOutcome) {
                is Outcome.Found -> when (upperOutcome) {
                    is Outcome.Found -> {
                        merge(intervals[lowerOutcome.index], newInterval).let { merge1 ->
                            merge(intervals[upperOutcome.index], merge1).let { merge2 ->
                                intervals.toMutableList().run {
                                    val leftPart = this.subList(0, lowerOutcome.index).toMutableList()
                                    leftPart.add(merge2)
                                    val rightPart = this.subList(upperOutcome.index + 1, intervals.size)
                                    leftPart.addAll(rightPart)
                                    leftPart.toTypedArray()
                                }
                            }
                        }
                    }
                    is Outcome.NotFound -> replaceAt(
                        intervals,
                        newInterval,
                        lowerOutcome.index
                    )
                }

                is Outcome.NotFound -> when (upperOutcome) {
                    is Outcome.Found -> replaceAt(
                        intervals,
                        newInterval,
                        upperOutcome.index
                    )
                    is Outcome.NotFound -> intervals.toMutableList().run {
                        add(upperOutcome.index + 1, newInterval)
                        toTypedArray()
                    }
                }
            }
        }

        private fun replaceAt(
            intervals: Array<IntArray>,
            newInterval: IntArray,
            at: Int,
        ): Array<IntArray> = merge(intervals[at], newInterval).let { merged ->
            intervals.toMutableList().run {
                this[at] = merged
                toTypedArray()
            }
        }

        private fun merge(intervalA: IntArray, intervalB: IntArray): IntArray {
            val lower = minOf(intervalA.first(), intervalB.first())
            val upper = maxOf(intervalA.last(), intervalB.last())
            return intArrayOf(lower, upper)
        }

        private fun find(intervals: Array<IntArray>, value: Int): Outcome {
            var left = 0
            var right = intervals.size - 1

            while (left <= right) {
                val mid = left + (right - left) / 2
                val interval = intervals[mid]
                val lower = interval.first()
                val upper = interval.last()

                when {
                    value in lower..upper -> return Outcome.Found(mid)
                    value < lower -> right = mid - 1
                    else -> left = mid + 1
                }
            }

            return Outcome.NotFound(minOf(left, right))
        }

        sealed class Outcome {
            data class Found(val index: Int) : Outcome()
            data class NotFound(val index: Int) : Outcome()
        }
    }
}
