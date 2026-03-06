package tom.koptel.leet

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class `0217_ContainsDuplicate` {
    @Test
    fun `example 1 - has duplicate`() {
        Solution().containsDuplicate(intArrayOf(1, 2, 3, 1)) shouldBe true
    }

    @Test
    fun `example 2 - no duplicates`() {
        Solution().containsDuplicate(intArrayOf(1, 2, 3, 4)) shouldBe false
    }

    @Test
    fun `example 3 - multiple duplicates`() {
        Solution().containsDuplicate(intArrayOf(1, 1, 1, 3, 3, 4, 3, 2, 4, 2)) shouldBe true
    }

    @Test
    fun `single element`() {
        Solution().containsDuplicate(intArrayOf(5)) shouldBe false
    }

    @Test
    fun `two same elements`() {
        Solution().containsDuplicate(intArrayOf(1, 1)) shouldBe true
    }

    @Test
    fun `two different elements`() {
        Solution().containsDuplicate(intArrayOf(1, 2)) shouldBe false
    }

    @Test
    fun `duplicate at the end`() {
        Solution().containsDuplicate(intArrayOf(1, 2, 3, 4, 5, 1)) shouldBe true
    }

    @Test
    fun `negative numbers with duplicate`() {
        Solution().containsDuplicate(intArrayOf(-1, -2, -3, -1)) shouldBe true
    }

    @Test
    fun `negative numbers no duplicate`() {
        Solution().containsDuplicate(intArrayOf(-1, -2, -3)) shouldBe false
    }

    private class Solution {
        fun containsDuplicate(nums: IntArray): Boolean = TODO()
    }
}
