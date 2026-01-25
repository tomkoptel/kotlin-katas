package tom.koptel.leet

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class `0242_ValidAnagram` {
    @Test
    fun `example 1 - anagram and nagaram are anagrams`() {
        // Input: s = "anagram", t = "nagaram"
        // Output: true
        // Explanation: Both strings contain the same characters with the same frequency
        val s = "anagram"
        val t = "nagaram"
        val result = Solution().isAnagram(s, t)
        result shouldBe true
    }

    @Test
    fun `example 2 - rat and car are not anagrams`() {
        // Input: s = "rat", t = "car"
        // Output: false
        // Explanation: Different characters - 't' in first string, 'c' in second
        val s = "rat"
        val t = "car"
        val result = Solution().isAnagram(s, t)
        result shouldBe false
    }

    @Test
    fun `empty strings are anagrams`() {
        // Input: s = "", t = ""
        // Output: true
        // Explanation: Empty strings contain the same (no) characters
        val s = ""
        val t = ""
        val result = Solution().isAnagram(s, t)
        result shouldBe true
    }

    @Test
    fun `single character strings that match are anagrams`() {
        // Input: s = "a", t = "a"
        // Output: true
        // Explanation: Single matching character
        val s = "a"
        val t = "a"
        val result = Solution().isAnagram(s, t)
        result shouldBe true
    }

    @Test
    fun `single character strings that differ are not anagrams`() {
        // Input: s = "a", t = "b"
        // Output: false
        // Explanation: Different characters
        val s = "a"
        val t = "b"
        val result = Solution().isAnagram(s, t)
        result shouldBe false
    }

    @Test
    fun `different lengths are not anagrams`() {
        // Input: s = "abc", t = "ab"
        // Output: false
        // Explanation: Different lengths cannot be anagrams
        val s = "abc"
        val t = "ab"
        val result = Solution().isAnagram(s, t)
        result shouldBe false
    }

    @Test
    fun `same string is an anagram of itself`() {
        // Input: s = "listen", t = "listen"
        // Output: true
        // Explanation: A string is always an anagram of itself
        val s = "listen"
        val t = "listen"
        val result = Solution().isAnagram(s, t)
        result shouldBe true
    }

    @Test
    fun `strings with repeated characters - valid anagram`() {
        // Input: s = "aabbcc", t = "abcabc"
        // Output: true
        // Explanation: Same characters with same frequencies
        val s = "aabbcc"
        val t = "abcabc"
        val result = Solution().isAnagram(s, t)
        result shouldBe true
    }

    @Test
    fun `strings with repeated characters - invalid anagram due to frequency mismatch`() {
        // Input: s = "aaab", t = "aabb"
        // Output: false
        // Explanation: Different character frequencies ('a' appears 3 times vs 2 times)
        val s = "aaab"
        val t = "aabb"
        val result = Solution().isAnagram(s, t)
        result shouldBe false
    }

    @Test
    fun `classic anagram pair - listen and silent`() {
        // Input: s = "listen", t = "silent"
        // Output: true
        // Explanation: Classic anagram example
        val s = "listen"
        val t = "silent"
        val result = Solution().isAnagram(s, t)
        result shouldBe true
    }

    @Test
    fun `all same character - valid anagram`() {
        // Input: s = "aaaa", t = "aaaa"
        // Output: true
        // Explanation: All characters are the same with same frequency
        val s = "aaaa"
        val t = "aaaa"
        val result = Solution().isAnagram(s, t)
        result shouldBe true
    }

    @Test
    fun `all same character but different lengths - invalid anagram`() {
        // Input: s = "aaaa", t = "aaa"
        // Output: false
        // Explanation: Different lengths
        val s = "aaaa"
        val t = "aaa"
        val result = Solution().isAnagram(s, t)
        result shouldBe false
    }

    @Test
    fun `similar but not anagrams - extra character in one string`() {
        // Input: s = "a", t = "ab"
        // Output: false
        // Explanation: Second string has extra character
        val s = "a"
        val t = "ab"
        val result = Solution().isAnagram(s, t)
        result shouldBe false
    }

    @Test
    fun `long strings that are anagrams`() {
        // Input: s = "thequickbrownfoxjumpsoverthelazydog", t = "godyzalehtrevospmujxofnworbkciuqeht"
        // Output: true
        // Explanation: Testing with longer strings
        val s = "thequickbrownfoxjumpsoverthelazydog"
        val t = "godyzalehtrevospmujxofnworbkciuqeht"
        val result = Solution().isAnagram(s, t)
        result shouldBe true
    }

    // Follow-up: Unicode characters test cases
    @Test
    fun `unicode characters - valid anagram with emoji`() {
        // Input: s = "😀😃😄", t = "😄😃😀"
        // Output: true
        // Explanation: Testing Unicode support with emojis
        val s = "😀😃😄"
        val t = "😄😃😀"
        val result = Solution().isAnagram(s, t)
        result shouldBe true
    }

    @Test
    fun `unicode characters - valid anagram with accented characters`() {
        // Input: s = "café", t = "éfac"
        // Output: true
        // Explanation: Testing Unicode support with accented characters
        val s = "café"
        val t = "éfac"
        val result = Solution().isAnagram(s, t)
        result shouldBe true
    }

    @Test
    fun `unicode characters - invalid anagram with different unicode chars`() {
        // Input: s = "😀😃", t = "😄😁"
        // Output: false
        // Explanation: Different Unicode characters
        val s = "😀😃"
        val t = "😄😁"
        val result = Solution().isAnagram(s, t)
        result shouldBe false
    }

    @Test
    fun `ggii eek`() {
        val s = "ggii"
        val t = "eekk"
        val result = Solution().isAnagram(s, t)
        result shouldBe false
    }

    private class Solution {
        fun isAnagram(s: String, t: String): Boolean {
            if (s.length != t.length) return false
            val index1 = mutableMapOf<Char, Int>()
            val index2 = mutableMapOf<Char, Int>()
            s.forEach { ch ->
                val counter = index1.getOrElse(ch) { 0 }
                index1[ch] = counter + 1
            }
            t.forEach { ch ->
                val counter = index2.getOrElse(ch) { 0 }
                index2[ch] = counter + 1
            }
            return index1 == index2
        }

        fun isAnagramEnOnly(s: String, t: String): Boolean {
            if (s.length != t.length) return false
            val alphabet = intArrayOf(25)
            s.forEach { ch ->
                alphabet[ch - 'a']++
            }
            t.forEach { ch ->
                alphabet[ch - 'a']--
            }
            return alphabet.all { it == 0 }
        }
    }
}
