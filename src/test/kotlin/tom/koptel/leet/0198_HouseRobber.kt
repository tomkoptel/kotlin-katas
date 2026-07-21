package tom.koptel.leet

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class `0198_HouseRobber` {
    @Test
    fun name() {
        val nums = intArrayOf(1, 2, 3, 1)
        Solution().rob(nums) shouldBe 4
    }

    private class Solution {
        fun rob(nums: IntArray): Int {
            val best = IntArray(nums.size) { 0 }
            best[0] = nums[0]
            best[1] = maxOf(nums[0], nums[1])
            for (i in 2 until nums.size) {
                val robIt = nums[i] + best[i - 2]
                val skipIt = best[i - 1]
                best[i] = maxOf(robIt, skipIt)
            }
            return best.last()
        }
    }
}
