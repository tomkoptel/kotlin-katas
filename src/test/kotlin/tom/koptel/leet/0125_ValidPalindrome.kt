package tom.koptel.leet

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class `0125_ValidPalindrome` {
    @Test
    fun `classic palindrome phrase with mixed case and punctuation`() {
        // Input: s = "A man, a plan, a canal: Panama"
        // Output: true
        // Explanation: "amanaplanacanalpanama" is a palindrome.
        val s = "A man, a plan, a canal: Panama"
        val result = Solution().isPalindrome(s)
        result shouldBe true
    }

    @Test
    fun `non-palindrome phrase`() {
        // Input: s = "race a car"
        // Output: false
        // Explanation: "raceacar" is not a palindrome.
        val s = "race a car"
        val result = Solution().isPalindrome(s)
        result shouldBe false
    }

    @Test
    fun `single space becomes empty string and is palindrome`() {
        // Input: s = " "
        // Output: true
        // Explanation: s is an empty string "" after removing non-alphanumeric characters.
        // Since an empty string reads the same forward and backward, it is a palindrome.
        val s = " "
        val result = Solution().isPalindrome(s)
        result shouldBe true
    }

    @Test
    fun `empty string is palindrome`() {
        // Input: s = ""
        // Output: true
        // Explanation: Empty string is considered a palindrome
        val s = ""
        val result = Solution().isPalindrome(s)
        result shouldBe true
    }

    @Test
    fun `single alphanumeric character is palindrome`() {
        // Input: s = "a"
        // Output: true
        val s = "a"
        val result = Solution().isPalindrome(s)
        result shouldBe true
    }

    @Test
    fun `single character uppercase is palindrome`() {
        // Input: s = "A"
        // Output: true
        val s = "A"
        val result = Solution().isPalindrome(s)
        result shouldBe true
    }

    @Test
    fun `two same characters is palindrome`() {
        // Input: s = "aa"
        // Output: true
        val s = "aa"
        val result = Solution().isPalindrome(s)
        result shouldBe true
    }

    @Test
    fun `two different characters is not palindrome`() {
        // Input: s = "ab"
        // Output: false
        val s = "ab"
        val result = Solution().isPalindrome(s)
        result shouldBe false
    }

    @Test
    fun `palindrome with only special characters becomes empty and is palindrome`() {
        // Input: s = ".,;:!?"
        // Output: true
        // Explanation: After removing all non-alphanumeric, becomes empty string
        val s = ".,;:!?"
        val result = Solution().isPalindrome(s)
        result shouldBe true
    }

    @Test
    fun `simple palindrome word`() {
        // Input: s = "racecar"
        // Output: true
        val s = "racecar"
        val result = Solution().isPalindrome(s)
        result shouldBe true
    }

    @Test
    fun `palindrome with mixed case`() {
        // Input: s = "RaceCar"
        // Output: true
        val s = "RaceCar"
        val result = Solution().isPalindrome(s)
        result shouldBe true
    }

    @Test
    fun `palindrome with numbers`() {
        // Input: s = "A1B2B1A"
        // Output: true
        // Explanation: "a1b2b1a" is a palindrome
        val s = "A1B2B1A"
        val result = Solution().isPalindrome(s)
        result shouldBe true
    }

    @Test
    fun `non-palindrome with numbers`() {
        // Input: s = "A1B2C3"
        // Output: false
        // Explanation: "a1b2c3" is not a palindrome
        val s = "A1B2C3"
        val result = Solution().isPalindrome(s)
        result shouldBe false
    }

    @Test
    fun `numbers only palindrome`() {
        // Input: s = "12321"
        // Output: true
        val s = "12321"
        val result = Solution().isPalindrome(s)
        result shouldBe true
    }

    @Test
    fun `numbers only non-palindrome`() {
        // Input: s = "12345"
        // Output: false
        val s = "12345"
        val result = Solution().isPalindrome(s)
        result shouldBe false
    }

    @Test
    fun `palindrome with special characters in between`() {
        // Input: s = "A@b!B*a"
        // Output: true
        // Explanation: After removing special chars: "abba" is a palindrome
        val s = "A@b!B*a"
        val result = Solution().isPalindrome(s)
        result shouldBe true
    }

    @Test
    fun `non-palindrome with special characters`() {
        // Input: s = "A@b!c*a"
        // Output: false
        // Explanation: After removing special chars: "abca" is not a palindrome
        val s = "A@b!c*a"
        val result = Solution().isPalindrome(s)
        result shouldBe false
    }

    @Test
    fun `long palindrome sentence`() {
        // Input: s = "Was it a car or a cat I saw?"
        // Output: true
        // Explanation: "wasitacaroracatisaw" is a palindrome
        val s = "Was it a car or a cat I saw?"
        val result = Solution().isPalindrome(s)
        result shouldBe true
    }

    @Test
    fun `palindrome with tabs and newlines`() {
        // Input: s = "a\tb\na"
        // Output: true
        // Explanation: After removing non-alphanumeric: "aba" is a palindrome
        val s = "a\tb\na"
        val result = Solution().isPalindrome(s)
        result shouldBe true
    }

    @Test
    fun `mixed alphanumeric palindrome with symbols`() {
        // Input: s = "0P0"
        // Output: true
        // Explanation: "0p0" is a palindrome
        val s = "0P0"
        val result = Solution().isPalindrome(s)
        result shouldBe true
    }

    @Test
    fun `single digit is palindrome`() {
        // Input: s = "0"
        // Output: true
        val s = "0"
        val result = Solution().isPalindrome(s)
        result shouldBe true
    }

    @Test
    fun `palindrome with multiple spaces`() {
        // Input: s = "a   b   a"
        // Output: true
        // Explanation: After removing spaces: "aba" is a palindrome
        val s = "a   b   a"
        val result = Solution().isPalindrome(s)
        result shouldBe true
    }

    @Test
    fun `almost palindrome but not quite`() {
        // Input: s = "abcba1"
        // Output: false
        // Explanation: "abcba1" is not a palindrome
        val s = "abcba1"
        val result = Solution().isPalindrome(s)
        result shouldBe false
    }

    @Test
    fun `odd length palindrome`() {
        // Input: s = "abcba"
        // Output: true
        val s = "abcba"
        val result = Solution().isPalindrome(s)
        result shouldBe true
    }

    @Test
    fun `even length palindrome`() {
        // Input: s = "abba"
        // Output: true
        val s = "abba"
        val result = Solution().isPalindrome(s)
        result shouldBe true
    }

    @Test
    fun `case sensitivity test - uppercase and lowercase mix`() {
        // Input: s = "AaBbAa"
        // Output: true
        // Explanation: "aabbaa" is a palindrome
        val s = "AaBbAa"
        val result = Solution().isPalindrome(s)
        result shouldBe true
    }

    @Test
    fun `underscores and hyphens are non-alphanumeric`() {
        // Input: s = "a_b-a"
        // Output: true
        // Explanation: After removing _ and -: "aba" is a palindrome
        val s = "a_b-a"
        val result = Solution().isPalindrome(s)
        result shouldBe true
    }

    @Test
    fun `complex mix with all printable ASCII`() {
        // Input: s = "a!@#$%^&*()b!@#$%^&*()a"
        // Output: true
        // Explanation: After removing special chars: "aba" is a palindrome
        val s = "a!@#$%^&*()b!@#$%^&*()a"
        val result = Solution().isPalindrome(s)
        result shouldBe true
    }

    class Solution {
        fun isPalindrome(s: String): Boolean {
            val cleaned = StringBuilder()
            val reversed = StringBuilder()
            val stack = ArrayDeque<Char>()
            for (c in s) {
                if (c.isLetterOrDigit()) {
                    val lower = c.lowercaseChar()
                    cleaned.append(lower)
                    stack.addLast(lower)
                }
            }
            while (stack.isNotEmpty()) {
                reversed.append(stack.removeLast())
            }
            return reversed.toString() == cleaned.toString()
        }

        fun isPalindromeFaster(s: String): Boolean {
            var left = 0
            var right = s.length - 1

            while (left < right) {
                // Skip non-alphanumeric from left
                while (left < right && !s[left].isLetterOrDigit()) {
                    left++
                }
                // Skip non-alphanumeric from right
                while (left < right && !s[right].isLetterOrDigit()) {
                    right--
                }

                if (s[left].lowercaseChar() != s[right].lowercaseChar()) {
                    return false
                }

                left++
                right--
            }

            return true
        }
    }
}
