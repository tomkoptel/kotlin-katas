package tom.koptel.leet

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.util.PriorityQueue

class `0973_KDistance` {
    private fun Array<IntArray>.asPointSet(): Set<List<Int>> = map { it.toList() }.toSet()

    @Test
    fun `k equals 1 returns single closest`() {
        val points = arrayOf(intArrayOf(1, 3), intArrayOf(-2, 2))
        Solution().kClosest(points, k = 1).asPointSet() shouldBe
            setOf(listOf(-2, 2))
    }

    @Test
    fun `k equals array size returns all points`() {
        val points = arrayOf(intArrayOf(1, 3), intArrayOf(-2, 2), intArrayOf(5, 5))
        Solution().kClosest(points, k = 3).asPointSet() shouldBe
            setOf(listOf(1, 3), listOf(-2, 2), listOf(5, 5))
    }

    @Test
    fun `single element array`() {
        val points = arrayOf(intArrayOf(3, 4))
        Solution().kClosest(points, k = 1).asPointSet() shouldBe
            setOf(listOf(3, 4))
    }

    @Test
    fun `k equals 2 picks two closest ignoring order`() {
        // distances: [0,1]=1, [1,0]=1, [3,4]=25, [5,5]=50
        val points = arrayOf(
            intArrayOf(3, 4),
            intArrayOf(0, 1),
            intArrayOf(5, 5),
            intArrayOf(1, 0)
        )
        Solution().kClosest(points, k = 2).asPointSet() shouldBe
            setOf(listOf(0, 1), listOf(1, 0))
    }

    @Test
    fun `negative coordinates handled by squaring`() {
        // distances: [-1,-1]=2, [-5,-5]=50, [0,-2]=4, [3,0]=9
        val points = arrayOf(
            intArrayOf(-5, -5),
            intArrayOf(-1, -1),
            intArrayOf(3, 0),
            intArrayOf(0, -2)
        )
        Solution().kClosest(points, k = 2).asPointSet() shouldBe
            setOf(listOf(-1, -1), listOf(0, -2))
    }

    @Test
    fun `already sorted input does not break partition`() {
        // ascending by distance: 1, 4, 9, 16
        val points = arrayOf(
            intArrayOf(1, 0),
            intArrayOf(2, 0),
            intArrayOf(3, 0),
            intArrayOf(4, 0)
        )
        Solution().kClosest(points, k = 2).asPointSet() shouldBe
            setOf(listOf(1, 0), listOf(2, 0))
    }

    @Test
    fun `reverse sorted input does not break partition`() {
        // descending by distance: 16, 9, 4, 1
        val points = arrayOf(
            intArrayOf(4, 0),
            intArrayOf(3, 0),
            intArrayOf(2, 0),
            intArrayOf(1, 0)
        )
        Solution().kClosest(points, k = 2).asPointSet() shouldBe
            setOf(listOf(1, 0), listOf(2, 0))
    }

    @Test
    fun `point at origin is always closest`() {
        val points = arrayOf(
            intArrayOf(5, 5),
            intArrayOf(0, 0),
            intArrayOf(1, 1)
        )
        Solution().kClosest(points, k = 1).asPointSet() shouldBe
            setOf(listOf(0, 0))
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

        fun kClosestLomuto(points: Array<IntArray>, k: Int): Array<IntArray> {
            fun IntArray.distance() = this[0] * this[0] + this[1] * this[1]

            fun <T : Any> Array<T>.swap(from: Int, to: Int) {
                val tmp = this[from]
                this[from] = this[to]
                this[to] = tmp
            }

            /**
             * Lomuto scheme
             * [ closer zone | farther zone | not-yet-examined | pivot ]
             */
            fun partition(points: Array<IntArray>, low: Int, high: Int): Int {
                val pivot = (low..high).random()
                val pivotDistance = points[pivot].distance()
                points.swap(pivot, high)
                var i = low
                for (j in low until high) {
                    val point = points[j]
                    val distance = point.distance()
                    if (distance < pivotDistance) {
                        points.swap(i, j)
                        i++
                    }
                }
                points.swap(high, i)
                return i
            }

            var low = 0
            var high = points.lastIndex
            while (low <= high) {
                val p = partition(points, low, high)
                when {
                    p == k - 1 -> return points.take(k).toTypedArray()
                    (p > k - 1) -> high = p - 1 // search left
                    (p < k - 1) -> low = p + 1 // search right
                }
            }
            return points.take(k).toTypedArray()
        }
    }
}
