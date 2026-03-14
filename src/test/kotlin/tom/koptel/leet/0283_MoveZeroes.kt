package tom.koptel.leet

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class `0283_MoveZeroes` {
    @Test
    fun `example 1`() {
        val nums = intArrayOf(0, 1, 0, 3, 12)
        Solution().moveZeroes(nums)
        nums shouldBe intArrayOf(1, 3, 12, 0, 0)
    }

    @Test
    fun `example 2 - single zero`() {
        val nums = intArrayOf(0)
        Solution().moveZeroes(nums)
        nums shouldBe intArrayOf(0)
    }

    @Test
    fun `no zeroes`() {
        val nums = intArrayOf(1, 2, 3)
        Solution().moveZeroes(nums)
        nums shouldBe intArrayOf(1, 2, 3)
    }

    @Test
    fun `all zeroes`() {
        val nums = intArrayOf(0, 0, 0)
        Solution().moveZeroes(nums)
        nums shouldBe intArrayOf(0, 0, 0)
    }

    @Test
    fun `zero at the end`() {
        val nums = intArrayOf(1, 2, 0)
        Solution().moveZeroes(nums)
        nums shouldBe intArrayOf(1, 2, 0)
    }

    @Test
    fun `zero at the start`() {
        val nums = intArrayOf(0, 1, 2)
        Solution().moveZeroes(nums)
        nums shouldBe intArrayOf(1, 2, 0)
    }

    @Test
    fun `single non-zero`() {
        val nums = intArrayOf(5)
        Solution().moveZeroes(nums)
        nums shouldBe intArrayOf(5)
    }

    @Test
    fun `alternating zeroes`() {
        val nums = intArrayOf(0, 1, 0, 2, 0, 3)
        Solution().moveZeroes(nums)
        nums shouldBe intArrayOf(1, 2, 3, 0, 0, 0)
    }

    @Test
    fun `negative numbers with zeroes`() {
        val nums = intArrayOf(0, -1, 0, -3, 12)
        Solution().moveZeroes(nums)
        nums shouldBe intArrayOf(-1, -3, 12, 0, 0)
    }

    private class Solution {
        fun moveZeroes(nums: IntArray): Unit = TODO()
    }
}
