package tom.koptel.leet

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class `0066_PlusOne` {
    @Test
    fun `example 1 - simple increment without carry`() {
        val solution = Solution()
        val input = intArrayOf(1, 2, 3)
        val expected = intArrayOf(1, 2, 4)
        solution.plusOne(input) shouldBe expected
    }

    @Test
    fun `example 2 - increment with last digit not 9`() {
        val solution = Solution()
        val input = intArrayOf(4, 3, 2, 1)
        val expected = intArrayOf(4, 3, 2, 2)
        solution.plusOne(input) shouldBe expected
    }

    @Test
    fun `example 3 - single digit 9 requires new leading digit`() {
        val solution = Solution()
        val input = intArrayOf(9)
        val expected = intArrayOf(1, 0)
        solution.plusOne(input) shouldBe expected
    }

    @Test
    fun `all nines - requires new leading digit`() {
        val solution = Solution()
        val input = intArrayOf(9, 9, 9)
        val expected = intArrayOf(1, 0, 0, 0)
        solution.plusOne(input) shouldBe expected
    }

    @Test
    fun `single digit less than 9`() {
        val solution = Solution()
        val input = intArrayOf(5)
        val expected = intArrayOf(6)
        solution.plusOne(input) shouldBe expected
    }

    @Test
    fun `carry propagates through middle digits`() {
        val solution = Solution()
        val input = intArrayOf(1, 9, 9)
        val expected = intArrayOf(2, 0, 0)
        solution.plusOne(input) shouldBe expected
    }

    @Test
    fun `partial carry propagation`() {
        val solution = Solution()
        val input = intArrayOf(1, 2, 9)
        val expected = intArrayOf(1, 3, 0)
        solution.plusOne(input) shouldBe expected
    }

    @Test
    fun `single digit zero`() {
        val solution = Solution()
        val input = intArrayOf(0)
        val expected = intArrayOf(1)
        solution.plusOne(input) shouldBe expected
    }

    @Test
    fun `large number without carry at end`() {
        val solution = Solution()
        val input = intArrayOf(9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
        val expected = intArrayOf(9, 8, 7, 6, 5, 4, 3, 2, 1, 1)
        solution.plusOne(input) shouldBe expected
    }

    @Test
    fun `two digit number ending in 9`() {
        val solution = Solution()
        val input = intArrayOf(1, 9)
        val expected = intArrayOf(2, 0)
        solution.plusOne(input) shouldBe expected
    }

    @Test
    fun `alternating 9s and other digits`() {
        val solution = Solution()
        val input = intArrayOf(8, 9, 9, 9)
        val expected = intArrayOf(9, 0, 0, 0)
        solution.plusOne(input) shouldBe expected
    }

    class Solution {
        fun plusOne(digits: IntArray): IntArray {
            for (i in digits.lastIndex downTo 0) {
                if (digits[i] < 9) {
                    digits[i]++
                    return digits
                } else {
                    digits[i] = 0
                }
            }
            return intArrayOf(1) + digits
        }
    }
}
