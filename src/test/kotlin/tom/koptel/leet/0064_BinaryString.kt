package tom.koptel.leet

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class `0064_BinaryString` {
    @Test
    fun `example 1 - 11 + 1 = 100`() {
        Solution().addBinary("11", "1") shouldBe "100"
    }

    @Test
    fun `example 2 - 1010 + 1011 = 10101`() {
        Solution().addBinary("1010", "1011") shouldBe "10101"
    }

    @Test
    fun `both zeros`() {
        Solution().addBinary("0", "0") shouldBe "0"
    }

    @Test
    fun `one zero and one non-zero`() {
        Solution().addBinary("0", "1") shouldBe "1"
    }

    @Test
    fun `single digits no carry`() {
        Solution().addBinary("1", "0") shouldBe "1"
    }

    @Test
    fun `single digits with carry`() {
        Solution().addBinary("1", "1") shouldBe "10"
    }

    @Test
    fun `different lengths`() {
        Solution().addBinary("1", "111") shouldBe "1000"
    }

    @Test
    fun `long carry propagation`() {
        Solution().addBinary("1111", "1") shouldBe "10000"
    }

    class Solution {
        fun addBinary(a: String, b: String): String {
            val stackA = ArrayDeque<Char>()
            val stackB = ArrayDeque<Char>()
            val result = ArrayDeque<Char>()
            for (i in a) {
                stackA.addLast(i)
            }
            for (i in b) {
                stackB.addLast(i)
            }

            var leftOver = '0'
            while(stackA.isNotEmpty() || stackB.isNotEmpty() || leftOver == '1') {
                val bitA = stackA.removeLastOrNull() ?: '0'
                val bitB = stackB.removeLastOrNull() ?: '0'

                val firstPass = binarySum(leftOver, bitA)
                leftOver = '0'
                if (firstPass.second == '1') leftOver = '1'

                val secondPass = binarySum(firstPass.first, bitB)
                if (secondPass.second == '1') leftOver = '1'

                result.add(secondPass.first)
            }

            val builder = StringBuilder()
            while (result.isNotEmpty()) {
                builder.append(result.removeLast())
            }
            return builder.toString()
        }

        private fun binarySum(bitA: Char, bitB: Char) : Pair<Char, Char> {
            return when {
                bitA == '1' && bitB == '1' -> '0' to '1'
                bitA == '0' && bitB == '1' -> '1' to '0'
                bitA == '1' && bitB == '0' -> '1' to '0'
                bitA == '0' && bitB == '0' -> '0' to '0'
                else -> throw UnsupportedOperationException("we are not working with binary string")
            }
        }
    }
}
