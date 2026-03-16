package tom.koptel.leet

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class `0189_RotateArray` {
    @Test
    fun `example 1 - rotate by 3`() {
        val nums = intArrayOf(1, 2, 3, 4, 5, 6, 7)
        Solution().rotate(nums, 3)
        nums shouldBe intArrayOf(5, 6, 7, 1, 2, 3, 4)
    }

    @Test
    fun `example 2 - rotate by 2`() {
        val nums = intArrayOf(-1, -100, 3, 99)
        Solution().rotate(nums, 2)
        nums shouldBe intArrayOf(3, 99, -1, -100)
    }

    @Test
    fun `rotate by 0 - no change`() {
        val nums = intArrayOf(1, 2, 3)
        Solution().rotate(nums, 0)
        nums shouldBe intArrayOf(1, 2, 3)
    }

    @Test
    fun `rotate by array length - no change`() {
        val nums = intArrayOf(1, 2, 3)
        Solution().rotate(nums, 3)
        nums shouldBe intArrayOf(1, 2, 3)
    }

    @Test
    fun `rotate by more than array length`() {
        val nums = intArrayOf(1, 2, 3)
        Solution().rotate(nums, 5)
        nums shouldBe intArrayOf(2, 3, 1)
    }

    @Test
    fun `single element`() {
        val nums = intArrayOf(1)
        Solution().rotate(nums, 4)
        nums shouldBe intArrayOf(1)
    }

    @Test
    fun `rotate by 1`() {
        val nums = intArrayOf(1, 2, 3, 4)
        Solution().rotate(nums, 1)
        nums shouldBe intArrayOf(4, 1, 2, 3)
    }

    @Test
    fun `two elements rotate by 1`() {
        val nums = intArrayOf(1, 2)
        Solution().rotate(nums, 1)
        nums shouldBe intArrayOf(2, 1)
    }

    private class Solution {
        fun rotate(nums: IntArray, k: Int) {
            val k = k % nums.size
            nums.reverse(0 until nums.size)
            nums.reverse(0 until k)
            nums.reverse(k until nums.size)
        }

        private fun IntArray.reverse(range: IntRange) {
            var left = range.first
            var right = range.last
            while (left < right) {
                swap(left, right)
                left++
                right--
            }
        }

        private fun IntArray.swap(from: Int, to: Int) {
            val tmp = this[from]
            this[from] = this[to]
            this[to] = tmp
        }
    }
}
