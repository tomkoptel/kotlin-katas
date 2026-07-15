package tom.koptel.leet

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.util.PriorityQueue

class `0973_KDistance` {
    @Test
    fun name() {
        val points = arrayOf(intArrayOf(1, 3), intArrayOf(-2, 2))
        Solution().kClosest(points, k = 1) shouldBe arrayOf(intArrayOf(-2, 2))
    }

    private class Solution {
        /**
         * Solution based on the priority queue
         */
        fun kClosest(points: Array<IntArray>, k: Int): Array<IntArray> {
            val maxHeap = PriorityQueue<IntArray>(
                compareByDescending { point ->
                    point[0] * point[0] + point[1] * point[1]
                }
            )
            points.forEach { point ->
                maxHeap.add(point)
                if (maxHeap.size > k) {
                    maxHeap.poll() // drop the farthest
                }
            }
            return maxHeap.toTypedArray()
        }
    }
}
