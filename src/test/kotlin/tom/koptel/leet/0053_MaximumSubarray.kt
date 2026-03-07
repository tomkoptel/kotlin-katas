package tom.koptel.leet

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class `0053_MaximumSubarray` {
    @Test
    fun `example 1 - mixed values`() {
        Solution().maxSubArray(intArrayOf(-2, 1, -3, 4, -1, 2, 1, -5, 4)) shouldBe 6
    }

    @Test
    fun `example 2 - single element`() {
        Solution().maxSubArray(intArrayOf(1)) shouldBe 1
    }

    @Test
    fun `example 3 - all positive`() {
        Solution().maxSubArray(intArrayOf(5, 4, -1, 7, 8)) shouldBe 23
    }

    @Test
    fun `all negative`() {
        Solution().maxSubArray(intArrayOf(-3, -2, -5, -1, -4)) shouldBe -1
    }

    @Test
    fun `single negative element`() {
        Solution().maxSubArray(intArrayOf(-1)) shouldBe -1
    }

    @Test
    fun `all same positive values`() {
        Solution().maxSubArray(intArrayOf(3, 3, 3)) shouldBe 9
    }

    @Test
    fun `negative then positive`() {
        Solution().maxSubArray(intArrayOf(-5, 10)) shouldBe 10
    }

    @Test
    fun `positive then large negative then positive`() {
        Solution().maxSubArray(intArrayOf(2, -1, 3)) shouldBe 4
    }

    @Test
    fun `best subarray in the middle`() {
        Solution().maxSubArray(intArrayOf(-1, -2, 5, 6, -3, -4)) shouldBe 11
    }

    @Test
    fun `leet code example 1`() {
        Solution().maxSubArray(intArrayOf(31, -41, 59, 26, -53, 58, 97, -93, -23, 84)) shouldBe 187
    }

    private class Solution {
        fun maxSubArray(nums: IntArray): Int {
            val table: Array<IntArray> = Array(size = nums.size) {
                IntArray(nums.size) { 0 }
            }

            return maxOf(
                computeMax(nums, table) { row -> (nums.lastIndex - row) downTo row },
                computeMax(nums, table) { row -> (nums.lastIndex - row) downTo 0 },
                computeMax(nums, table) { row -> nums.lastIndex downTo row },
            )
        }

        private fun computeMax(nums: IntArray, table: Array<IntArray>, compute: (row: Int) -> IntProgression): Int {
            var max = nums[0]

            for (row in 0 until nums.size) {
                val dp = table[row]
                val progression = compute(row)

                for (column in progression) {
                    val nextEl = dp.getOrNull(column + 1) ?: 0
                    val el = nums[column]

                    val sum = nextEl + el
                    dp[column] = sum
                    max = maxOf(max, sum)
                }
            }

            return max
        }
    }
}
