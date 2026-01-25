package tom.koptel.leet

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class `0121_BestTimeBuySellStock` {
    @Test
    fun `buy low and sell high for maximum profit`() {
        // Input: prices = [7,1,5,3,6,4]
        // Output: 5
        // Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5
        val prices = intArrayOf(7, 1, 5, 3, 6, 4)
        val result = Solution().maxProfit(prices)
        result shouldBe 5
    }

    @Test
    fun `buy at price 2 and sell at price 7 for profit of 5`() {
        val prices = intArrayOf(2, 7, 1, 4)
        val result = Solution().maxProfit(prices)
        result shouldBe 5
    }

    @Test
    fun `no profit when prices only decrease`() {
        // Input: prices = [7,6,4,3,1]
        // Output: 0
        // Explanation: No transactions are done and the max profit = 0
        val prices = intArrayOf(7, 6, 4, 3, 1)
        val result = Solution().maxProfit(prices)
        result shouldBe 0
    }

    @Test
    fun `single day - no transaction possible`() {
        // Input: prices = [5]
        // Output: 0
        // Explanation: Need at least 2 days to buy and sell
        val prices = intArrayOf(5)
        val result = Solution().maxProfit(prices)
        result shouldBe 0
    }

    @Test
    fun `two days with profit`() {
        // Input: prices = [1,5]
        // Output: 4
        // Explanation: Buy on day 1 (price = 1) and sell on day 2 (price = 5), profit = 5-1 = 4
        val prices = intArrayOf(1, 5)
        val result = Solution().maxProfit(prices)
        result shouldBe 4
    }

    @Test
    fun `two days with no profit`() {
        // Input: prices = [5,1]
        // Output: 0
        // Explanation: Cannot sell before buying
        val prices = intArrayOf(5, 1)
        val result = Solution().maxProfit(prices)
        result shouldBe 0
    }

    @Test
    fun `buy at minimum and sell at maximum`() {
        // Input: prices = [3,2,6,5,0,3]
        // Output: 4
        // Explanation: Buy on day 2 (price = 2) and sell on day 3 (price = 6), profit = 6-2 = 4
        val prices = intArrayOf(3, 2, 6, 5, 0, 3)
        val result = Solution().maxProfit(prices)
        result shouldBe 4
    }

    @Test
    fun `prices are all the same`() {
        // Input: prices = [5,5,5,5,5]
        // Output: 0
        // Explanation: No profit possible when all prices are equal
        val prices = intArrayOf(5, 5, 5, 5, 5)
        val result = Solution().maxProfit(prices)
        result shouldBe 0
    }

    @Test
    fun `profit at the very end`() {
        // Input: prices = [2,4,1,7]
        // Output: 6
        // Explanation: Buy on day 3 (price = 1) and sell on day 4 (price = 7), profit = 7-1 = 6
        val prices = intArrayOf(2, 4, 1, 7)
        val result = Solution().maxProfit(prices)
        result shouldBe 6
    }

    @Test
    fun `continuous increase`() {
        // Input: prices = [1,2,3,4,5]
        // Output: 4
        // Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit = 5-1 = 4
        val prices = intArrayOf(1, 2, 3, 4, 5)
        val result = Solution().maxProfit(prices)
        result shouldBe 4
    }

    @Test
    fun `maximum value constraints`() {
        // Input: prices = [10000,1,10000]
        // Output: 9999
        // Explanation: Buy on day 2 (price = 1) and sell on day 3 (price = 10000), profit = 9999
        val prices = intArrayOf(10000, 1, 10000)
        val result = Solution().maxProfit(prices)
        result shouldBe 9999
    }

    @Test
    fun `profit in the middle with noise`() {
        // Input: prices = [3,2,6,5,0,3]
        // Output: 4
        // Explanation: Buy on day 2 (price = 2) and sell on day 3 (price = 6), profit = 6-2 = 4
        val prices = intArrayOf(3, 2, 6, 5, 0, 3)
        val result = Solution().maxProfit(prices)
        result shouldBe 4
    }

    @Test
    fun `complex case with multiple peaks`() {
        // Input: prices = [6,1,3,2,4,7]
        // Output: 6
        // Explanation: Buy on day 2 (price = 1) and sell on day 6 (price = 7), profit = 7-1 = 6
        val prices = intArrayOf(6, 1, 3, 2, 4, 7)
        val result = Solution().maxProfit(prices)
        result shouldBe 6
    }

    class Solution {
        fun maxProfit(prices: IntArray): Int {
            if (prices.size < 2) return 0

            var buy = 0
            var sell = 1
            var maxProfit = 0

            while (sell < prices.size) {
                if (prices[sell] > prices[buy]) {
                    val profit = prices[sell] - prices[buy]
                    maxProfit = maxOf(maxProfit, profit)
                } else {
                    buy = sell
                }
                sell++
            }

            return maxProfit
        }
    }
}
