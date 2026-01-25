package tom.koptel.leet

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class `0278_FirstBadVersion` {
    @Test
    fun `example 1 - n=5 bad=4`() {
        // Input: n = 5, bad = 4
        // Output: 4
        // Explanation: call isBadVersion(3) -> false, call isBadVersion(5) -> true, call isBadVersion(4) -> true
        val solution = Solution(firstBad = 4)
        solution.firstBadVersion(5) shouldBe 4
    }

    @Test
    fun `example - n=2 bad=2`() {
        val solution = Solution(firstBad = 2)
        solution.firstBadVersion(2) shouldBe 2
    }

    @Test
    fun `example 2 - n=1 bad=1`() {
        // Input: n = 1, bad = 1
        // Output: 1
        // Explanation: Only one version which is bad
        val solution = Solution(firstBad = 1)
        solution.firstBadVersion(1) shouldBe 1
    }

    @Test
    fun `first version is bad`() {
        // Input: n = 10, bad = 1
        // Output: 1
        // Explanation: First version is already bad
        val solution = Solution(firstBad = 1)
        solution.firstBadVersion(10) shouldBe 1
    }

    @Test
    fun `last version is bad`() {
        // Input: n = 10, bad = 10
        // Output: 10
        // Explanation: Only the last version is bad
        val solution = Solution(firstBad = 10)
        solution.firstBadVersion(10) shouldBe 10
    }

    @Test
    fun `bad version in the middle`() {
        // Input: n = 100, bad = 50
        // Output: 50
        // Explanation: Bad version is exactly in the middle
        val solution = Solution(firstBad = 50)
        solution.firstBadVersion(100) shouldBe 50
    }

    @Test
    fun `two versions - first is bad`() {
        // Input: n = 2, bad = 1
        // Output: 1
        val solution = Solution(firstBad = 1)
        solution.firstBadVersion(2) shouldBe 1
    }

    @Test
    fun `two versions - second is bad`() {
        // Input: n = 2, bad = 2
        // Output: 2
        val solution = Solution(firstBad = 2)
        solution.firstBadVersion(2) shouldBe 2
    }

    @Test
    fun `bad version near beginning`() {
        // Input: n = 1000, bad = 3
        // Output: 3
        // Explanation: Bad version is near the start
        val solution = Solution(firstBad = 3)
        solution.firstBadVersion(1000) shouldBe 3
    }

    @Test
    fun `bad version near end`() {
        // Input: n = 1000, bad = 998
        // Output: 998
        // Explanation: Bad version is near the end
        val solution = Solution(firstBad = 998)
        solution.firstBadVersion(1000) shouldBe 998
    }

    @Test
    fun `large n value - handles integer overflow correctly`() {
        // Input: n = Int.MAX_VALUE (2^31 - 1), bad = Int.MAX_VALUE
        // Output: Int.MAX_VALUE
        // Explanation: Tests that mid calculation doesn't overflow
        val solution = Solution(firstBad = Int.MAX_VALUE)
        solution.firstBadVersion(Int.MAX_VALUE) shouldBe Int.MAX_VALUE
    }

    @Test
    fun `large n value - bad version at 1`() {
        // Input: n = Int.MAX_VALUE, bad = 1
        // Output: 1
        // Explanation: Large n but first version is bad
        val solution = Solution(firstBad = 1)
        solution.firstBadVersion(Int.MAX_VALUE) shouldBe 1
    }

    @Test
    fun `minimizes API calls - odd number of versions`() {
        // Input: n = 7, bad = 4
        // Output: 4
        // Binary search should find this efficiently
        val solution = Solution(firstBad = 4)
        solution.firstBadVersion(7) shouldBe 4
    }

    @Test
    fun `minimizes API calls - even number of versions`() {
        // Input: n = 8, bad = 5
        // Output: 5
        // Binary search should find this efficiently
        val solution = Solution(firstBad = 5)
        solution.firstBadVersion(8) shouldBe 5
    }

    class Solution(private val firstBad: Int) : VersionControl() {
        /**
         * LLM solution
         *         override fun firstBadVersion(n: Int): Int {
         *             var left = 1
         *             var right = n
         *             while (left < right) {
         *                 val mid = left + (right - left) / 2
         *                 if (isBadVersion(mid)) {
         *                     right = mid
         *                 } else {
         *                     left = mid + 1
         *                 }
         *             }
         *             return left
         *         }
         *
         */
        override fun firstBadVersion(n: Int): Int {
            var left = 1
            var right = n
            while (true) {
                val version = right - (right - left) / 2
                if (isBadVersion(version)) {
                    if (right == version) {
                        return if (isBadVersion(left)) left else version
                    }
                    right = version
                } else {
                    if (left == version) {
                        return left + 1
                    }
                    left = version
                }
            }
        }

        override fun isBadVersion(version: Int): Boolean = version >= firstBad
    }

    abstract class VersionControl {
        abstract fun firstBadVersion(n: Int): Int

        abstract fun isBadVersion(version: Int): Boolean
    }
}
