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
        /**
         * Kadane's algorithm
         * Brute force thinking: Check every possible subarray (every start + end pair). That's O(n²). Too slow.
         *
         *   Kadane's insight: You don't need to track where subarrays start. You only need to track the best subarray ending at the current position.
         *
         *   Why? Every subarray ends somewhere. If you know the best subarray ending at each position, the overall answer is just the maximum among all of them.
         *
         *   The decision at each element:
         *
         *   You're standing at element nums[i]. The best subarray ending here is either:
         *   - Extend: the best subarray ending at i-1, plus nums[i]
         *   - Start fresh: just nums[i] alone
         *
         *   When does starting fresh win? When the previous best sum is negative — because negative + anything makes it worse.
         *
         *   Walk through [-2, 1, -3, 4, -1, 2, 1, -5, 4]:
         *
         *   i=0: currentSum = -2              max = -2
         *   i=1: -2+1=-1 vs 1 → fresh    1   max = 1
         *   i=2: 1+(-3)=-2 vs -3 → extend -2  max = 1
         *   i=3: -2+4=2 vs 4 → fresh     4   max = 4
         *   i=4: 4+(-1)=3 vs -1 → extend  3   max = 4
         *   i=5: 3+2=5 vs 2 → extend      5   max = 5
         *   i=6: 5+1=6 vs 1 → extend      6   max = 6
         *   i=7: 6+(-5)=1 vs -5 → extend  1   max = 6
         *   i=8: 1+4=5 vs 4 → extend      5   max = 6
         *
         *   Answer: 6 (subarray [4, -1, 2, 1])
         *
         *   That's it: One pass, one decision per element, two variables. O(n) time, O(1) space.
         */
        fun maxSubArray(nums: IntArray): Int {
            var currentSum = nums[0]
            var max = currentSum
            for (i in 1 until nums.size) {
                val num = nums[i]
                if (currentSum + num > num) {
                    currentSum += num
                } else {
                    currentSum = num
                }
                max = maxOf(max, currentSum)
            }
            return max
        }

        fun maxSubArrayDivideAndConquer(nums: IntArray): Int {
            fun crossMaxLeft(progression: IntProgression): Int {
                var sum = nums[progression.last]
                var best = sum
                for (i in progression.last - 1 downTo progression.first) {
                    sum += nums[i]
                    best = maxOf(best, sum)
                }
                return best
            }

            fun crossMaxRight(progression: IntProgression): Int {
                var sum = nums[progression.first]
                var best = sum
                for (i in (progression.first + 1)..(progression.last)) {
                    sum += nums[i]
                    best = maxOf(best, sum)
                }
                return best
            }

            val maxSubArray = DeepRecursiveFunction<IntProgression, Int> { progression ->
                if (progression.first == progression.last) {
                    nums[progression.first]
                } else {
                    val left = progression.first
                    val right = progression.last
                    val mid = (left + right).ushr(1)

                    val leftProgression = left..mid
                    val leftSum = callRecursive(leftProgression)

                    val rightProgression = mid + 1..right
                    val rightSum = callRecursive(rightProgression)

                    val crossSum = crossMaxLeft(leftProgression) + crossMaxRight(rightProgression)

                    maxOf(leftSum, rightSum, crossSum)
                }
            }
            return maxSubArray(0 until nums.size)
        }

        /**
         * O(n^2) - time
         * O(n^2) - complexity
         */
        fun maxSubArrayBruteForce(nums: IntArray): Int {
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
