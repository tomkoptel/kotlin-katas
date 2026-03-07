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

    private class Solution {
        fun maxSubArray(nums: IntArray): Int = TODO()
    }
}
