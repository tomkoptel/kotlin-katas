package tom.koptel.leet

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.util.LinkedList
import java.util.Queue

class `0070_ClimbingStairs` {

    @Test
    fun `example 1 - n=2 has 2 ways`() {
        // Input: n = 2
        // Output: 2
        // Explanation: 1+1 or 2
        Solution().climbStairs(2) shouldBe 2
    }

    @Test
    fun `example 2 - n=3 has 3 ways`() {
        // Input: n = 3
        // Output: 3
        // Explanation: 1+1+1, 1+2, 2+1
        Solution().climbStairs(3) shouldBe 3
    }

    @Test
    fun `n=1 has 1 way`() {
        // Only one way: single step
        Solution().climbStairs(1) shouldBe 1
    }

    @Test
    fun `n=4 has 5 ways`() {
        // 1+1+1+1, 1+1+2, 1+2+1, 2+1+1, 2+2
        Solution().climbStairs(4) shouldBe 5
    }

    @Test
    fun `n=5 has 8 ways`() {
        // Fibonacci pattern: f(5) = f(4) + f(3) = 5 + 3 = 8
        Solution().climbStairs(5) shouldBe 8
    }

    @Test
    fun `n=6 has 13 ways`() {
        // f(6) = f(5) + f(4) = 8 + 5 = 13
        Solution().climbStairs(6) shouldBe 13
    }

    @Test
    fun `n=10 has 89 ways`() {
        // Fibonacci sequence: 1,2,3,5,8,13,21,34,55,89
        Solution().climbStairs(10) shouldBe 89
    }

    @Test
    fun `n=20 has 10946 ways`() {
        // Larger input to test efficiency
        Solution().climbStairs(20) shouldBe 10946
    }

    @Test
    fun `n=45 constraint boundary`() {
        // Maximum constraint: 1 <= n <= 45
        Solution().climbStairs(45) shouldBe 1836311903
    }

    class Solution {
        fun climbStairs(n: Int): Int {
            if (n <= 2) return n
            var previous_2_steps = 1
            var previous_1_step = 2
            for (step in 3..n) {
                val total = previous_2_steps + previous_1_step
                previous_2_steps = previous_1_step
                previous_1_step = total
            }
            return previous_1_step
        }

        fun climbStairs2(n: Int): Int {
            val queue: Queue<Int> = LinkedList()
            queue.add(0)
            var paths = 0
            while (queue.isNotEmpty()) {
                val step = queue.poll()
                if (step == n) paths++

                val step_1 = step + 1
                val step_2 = step + 2
                if (step_1 <= n) {
                    queue.add(step_1)
                }
                if (step_2 <= n) {
                    queue.add(step_2)
                }
            }
            return paths
        }

        /**
         * backward from last step to 0
         */
        fun climbStairs3(n: Int): Int {
            if (n == 1) return 1
            if (n == 2) return 2
            val ways = Array(n) { 0 }
            ways[n - 1] = 1
            var step = n - 2
            while (step >= 0) {
                var counter = 0
                if (step + 1 <= n - 1) {
                    counter += ways[step + 1]
                }
                if (step + 2 <= n - 1) {
                    counter += ways[step + 2]
                }
                ways[step] = counter
                step--
            }
            return ways[0] + ways[1]
        }
    }
}
