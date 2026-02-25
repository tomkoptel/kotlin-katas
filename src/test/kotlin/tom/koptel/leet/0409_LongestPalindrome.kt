package tom.koptel.leet

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class `0409_LongestPalindrome` {

    @Test
    fun `example 1 - abccccdd`() {
        Solution().longestPalindrome("abccccdd") shouldBe 7
    }

    @Test
    fun `example 2 - single character`() {
        Solution().longestPalindrome("a") shouldBe 1
    }

    @Test
    fun `single pair`() {
        Solution().longestPalindrome("bb") shouldBe 2
    }

    @Test
    fun `all even counts`() {
        Solution().longestPalindrome("aabbccdd") shouldBe 8
    }

    @Test
    fun `all odd counts uses pairs plus one center`() {
        Solution().longestPalindrome("aaabbbccc") shouldBe 7
    }

    @Test
    fun `all unique characters`() {
        Solution().longestPalindrome("abcdefg") shouldBe 1
    }

    @Test
    fun `case sensitivity - Aa is not a pair`() {
        Solution().longestPalindrome("Aa") shouldBe 1
    }

    @Test
    fun `case sensitivity - mixed case pairs`() {
        Solution().longestPalindrome("AaAaBbBb") shouldBe 8
    }

    @Test
    fun `entire string is already a palindrome`() {
        Solution().longestPalindrome("racecar") shouldBe 7
    }

    @Test
    fun `odd count contributes pairs plus center`() {
        Solution().longestPalindrome("aaaaa") shouldBe 5
    }

    @Test
    fun `many odds - only one center slot`() {
        Solution().longestPalindrome("abcde") shouldBe 1
    }

    @Test
    fun `mix of even and odd counts`() {
        Solution().longestPalindrome("aabbccd") shouldBe 7
    }

    class Solution {
        fun longestPalindrome(s: String): Int {
            val dict = mutableMapOf<Char, Int>()
            s.forEach { ch ->
                dict[ch] = (dict[ch] ?: 0) + 1
            }

            var longest = 0
            dict.forEach { (_, count) ->
                if (longest == 0) {
                    longest += count
                } else {
                    if (longest % 2 == 0) {
                        longest += count
                    } else {
                        if (count % 2 == 0) {
                            longest += count
                        } else {
                            longest += (count - 1)
                        }
                    }
                }
            }

            return longest
        }

        fun longestPalindromeClaude(s: String): Int {
            val dict = mutableMapOf<Char, Int>()
            // val dict = s.groupingBy { it }.eachCount()
            s.forEach { ch ->
                dict[ch] = (dict[ch] ?: 0) + 1
            }

            var longest = 0
            var hasEven = false
            dict.forEach { (_, count) ->
                longest += count / 2 * 2 // count in all pairs
                if (count % 2 == 1) hasEven = true
            }

            return if (hasEven) longest + 1 else longest
        }
    }
}
