package tom.koptel.leet

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import org.junit.jupiter.api.Test

class `0020_ValidParentheses` {
    @Test
    fun `valid parentheses`() {
        Solution().isValid("()").shouldBeTrue()
    }

    @Test
    fun `valid nested parentheses`() {
        Solution().isValid("([{}])").shouldBeTrue()
    }

    @Test
    fun `valid mixed brackets`() {
        Solution().isValid("{([])}}").shouldBeFalse()
    }

    @Test
    fun `invalid mismatched brackets`() {
        Solution().isValid("([)]").shouldBeFalse()
    }

    @Test
    fun `invalid opening bracket without closing`() {
        Solution().isValid("([]").shouldBeFalse()
    }

    @Test
    fun `invalid closing bracket without opening`() {
        Solution().isValid("())").shouldBeFalse()
    }

    @Test
    fun `empty string is valid`() {
        Solution().isValid("").shouldBeTrue()
    }

    @Test
    fun `single closing bracket is invalid`() {
        Solution().isValid(")").shouldBeFalse()
    }

    @Test
    fun name() {
        val stack = ArrayDeque<Int>()
        stack.add(1)
        stack.add(2)
        stack.add(3)
        println(stack.removeLast())
        println(stack.removeLast())
        println(stack.removeLast())
    }

    class Solution {
        fun isValid(s: String): Boolean {
            if (s.isEmpty()) return true
            val stack = ArrayDeque<Char>()
            val dict =
                mapOf(
                    ')' to '(',
                    ']' to '[',
                    '}' to '{'
                )

            var i = 0
            for (char in s) {
                println()
                println("iteration start: $i char: $char stack: $stack")
                val openner = dict[char]
                if (openner != null) {
                    if (stack.removeLastOrNull() != openner) return false
                } else {
                    stack.add(char)
                }
                println("iteration end: $i char: $char stack: $stack openner=$openner")
                i++
            }
            return stack.isEmpty()
        }
    }
}
