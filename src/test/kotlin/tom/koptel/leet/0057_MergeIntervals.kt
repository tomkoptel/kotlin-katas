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
        /**
         * Linear scan handles **what to do with everything**.
         */
        fun insert(intervals: Array<IntArray>, newInterval: IntArray): Array<IntArray> {
            val result = mutableListOf<IntArray>()
            var inserted = false
            for (interval in intervals) {
                if (interval.last() < newInterval.first()) {
                    result.add(interval)
                } else if (interval.first() > newInterval.last()) {
                    if (!inserted) {
                        inserted = true
                        result.add(newInterval)
                    }
                    result.add(interval)
                } else {
                    newInterval[0] = minOf(interval.first(), newInterval.first())
                    newInterval[1] = maxOf(interval.last(), newInterval.last())
                }
            }
            if (!inserted) result.add(newInterval)
            return result.toTypedArray()
        }

        /**
         * Mine first attempt.
         * Classic over-engineering pattern — and it happens for a specific reason.
         *
         * ---
         *
         * ### You saw a sorted array and reached for binary search
         *
         * That instinct is not wrong in general. Sorted input + searching = binary search is a deeply ingrained and usually correct heuristic. It's the right tool for "find me index of value X."
         *
         * But this problem isn't really a *search* problem. It's a *partition* problem. You're not looking for a specific value — you're **categorizing every interval** into one of three zones. Binary search finds a point; a linear scan builds a picture. Those are different needs.
         *
         * ---
         *
         * ### Then the sealed class followed naturally
         *
         * Once you committed to binary search, you needed to handle what happens when a value falls *between* intervals — hence `Outcome.Found` and `Outcome.NotFound`. That's a reasonable design for a binary search result. But now you had two outcomes × two bounds = four combinations to enumerate, and the logic diverged in each branch.
         *
         * The complexity didn't come from bad design — it came from solving a **harder version of the problem** than was actually asked.
         *
         * ---
         *
         * ### The underlying pattern
         *
         * > Binary search finds **where something is**.
         * > Linear scan handles **what to do with everything**.
         *
         * When the answer requires processing all intervals anyway, linear scan dominates. Binary search only wins when you can *skip* work — and here you can't, because every interval needs to land somewhere in the result.
         *
         * ---
         *
         * Your instinct was sharp, just applied one level too deep. The sorted property *is* useful here — but it's already exploited by the fact that the three zones appear in order, never interleaved.
         */
        fun insertWithBug(intervals: Array<IntArray>, newInterval: IntArray): Array<IntArray> {
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
