package tom.koptel.leet

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import org.junit.jupiter.api.Test

class `0383_RansomNote` {
    @Test
    fun `example 1 - single char not in magazine`() {
        // Input: ransomNote = "a", magazine = "b"
        // Output: false
        Solution().canConstruct("a", "b").shouldBeFalse()
    }

    @Test
    fun `example 2 - not enough occurrences in magazine`() {
        // Input: ransomNote = "aa", magazine = "ab"
        // Output: false
        Solution().canConstruct("aa", "ab").shouldBeFalse()
    }

    @Test
    fun `example 3 - can construct with extra letters`() {
        // Input: ransomNote = "aa", magazine = "aab"
        // Output: true
        Solution().canConstruct("aa", "aab").shouldBeTrue()
    }

    @Test
    fun `empty ransom note - always constructible`() {
        Solution().canConstruct("", "abc").shouldBeTrue()
    }

    @Test
    fun `empty magazine with non-empty ransom note`() {
        Solution().canConstruct("a", "").shouldBeFalse()
    }

    @Test
    fun `both empty strings`() {
        Solution().canConstruct("", "").shouldBeTrue()
    }

    @Test
    fun `exact match - same characters`() {
        Solution().canConstruct("abc", "abc").shouldBeTrue()
    }

    @Test
    fun `exact match - different order`() {
        Solution().canConstruct("abc", "cba").shouldBeTrue()
    }

    @Test
    fun `ransom note longer than magazine`() {
        Solution().canConstruct("aaaa", "aa").shouldBeFalse()
    }

    @Test
    fun `single character match`() {
        Solution().canConstruct("a", "a").shouldBeTrue()
    }

    @Test
    fun `repeated characters - sufficient in magazine`() {
        Solution().canConstruct(ransomNote = "aaa", magazine = "aaab").shouldBeTrue()
    }

    @Test
    fun `repeated characters - insufficient in magazine`() {
        Solution().canConstruct("aaaa", "aaa").shouldBeFalse()
    }

    @Test
    fun `mixed characters - can construct`() {
        Solution().canConstruct("hello", "hloelxyz").shouldBeTrue()
    }

    @Test
    fun `mixed characters - missing one`() {
        Solution().canConstruct("hello", "hloexyz").shouldBeFalse()
    }

    class Solution {
        fun canConstruct(ransomNote: String, magazine: String): Boolean {
            val vocab = mutableMapOf<Char, Int>()
            magazine.forEach { char ->
                vocab[char] = (vocab[char] ?: 0) + 1
            }
            ransomNote.forEach { char ->
                val counter = vocab[char] ?: return false
                if (counter == 0) return false
                vocab[char] = counter - 1
            }
            return true
        }
    }
}
