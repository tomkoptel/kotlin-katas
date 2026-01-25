package tom.koptel.leet

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class `0704_BinarySearch` {
    @Test
    fun `example 1 - find target 9 in array`() {
        // Input: nums = [-1,0,3,5,9,12], target = 9
        // Output: 4
        // Explanation: 9 exists in nums and its index is 4
        val nums = intArrayOf(-1, 0, 3, 5, 9, 12)
        val target = 9
        val result = Solution().search(nums, target)
        result shouldBe 4
    }

    @Test
    fun `example 2 - target 2 does not exist in array`() {
        // Input: nums = [-1,0,3,5,9,12], target = 2
        // Output: -1
        // Explanation: 2 does not exist in nums so return -1
        val nums = intArrayOf(-1, 0, 3, 5, 9, 12)
        val target = 2
        val result = Solution().search(nums, target)
        result shouldBe -1
    }

    @Test
    fun `single element array - target found`() {
        // Input: nums = [5], target = 5
        // Output: 0
        // Explanation: Single element matches the target
        val nums = intArrayOf(5)
        val target = 5
        val result = Solution().search(nums, target)
        result shouldBe 0
    }

    @Test
    fun `single element array - target not found`() {
        // Input: nums = [5], target = 1
        // Output: -1
        // Explanation: Single element doesn't match the target
        val nums = intArrayOf(5)
        val target = 1
        val result = Solution().search(nums, target)
        result shouldBe -1
    }

    @Test
    fun `target at the beginning of array`() {
        // Input: nums = [1,2,3,4,5], target = 1
        // Output: 0
        // Explanation: Target is the first element
        val nums = intArrayOf(1, 2, 3, 4, 5)
        val target = 1
        val result = Solution().search(nums, target)
        result shouldBe 0
    }

    @Test
    fun `target at the end of array`() {
        // Input: nums = [1,2,3,4,5], target = 5
        // Output: 4
        // Explanation: Target is the last element
        val nums = intArrayOf(1, 2, 3, 4, 5)
        val target = 5
        val result = Solution().search(nums, target)
        result shouldBe 4
    }

    @Test
    fun `target in the middle of array`() {
        // Input: nums = [1,2,3,4,5,6,7], target = 4
        // Output: 3
        // Explanation: Target is in the middle of the array
        val nums = intArrayOf(1, 2, 3, 4, 5, 6, 7)
        val target = 4
        val result = Solution().search(nums, target)
        result shouldBe 3
    }

    @Test
    fun `target smaller than all elements`() {
        // Input: nums = [5,10,15,20], target = 1
        // Output: -1
        // Explanation: Target is smaller than the smallest element
        val nums = intArrayOf(5, 10, 15, 20)
        val target = 1
        val result = Solution().search(nums, target)
        result shouldBe -1
    }

    @Test
    fun `target larger than all elements`() {
        // Input: nums = [5,10,15,20], target = 25
        // Output: -1
        // Explanation: Target is larger than the largest element
        val nums = intArrayOf(5, 10, 15, 20)
        val target = 25
        val result = Solution().search(nums, target)
        result shouldBe -1
    }

    @Test
    fun `target between elements but not present`() {
        // Input: nums = [1,3,5,7,9], target = 6
        // Output: -1
        // Explanation: Target falls between existing elements but doesn't exist
        val nums = intArrayOf(1, 3, 5, 7, 9)
        val target = 6
        val result = Solution().search(nums, target)
        result shouldBe -1
    }

    @Test
    fun `array with negative numbers - target found`() {
        // Input: nums = [-10,-5,0,5,10], target = -5
        // Output: 1
        // Explanation: Negative number found in array
        val nums = intArrayOf(-10, -5, 0, 5, 10)
        val target = -5
        val result = Solution().search(nums, target)
        result shouldBe 1
    }

    @Test
    fun `array with negative numbers - target not found`() {
        // Input: nums = [-10,-5,0,5,10], target = -7
        // Output: -1
        // Explanation: Target negative number not in array
        val nums = intArrayOf(-10, -5, 0, 5, 10)
        val target = -7
        val result = Solution().search(nums, target)
        result shouldBe -1
    }

    @Test
    fun `all negative numbers array`() {
        // Input: nums = [-50,-40,-30,-20,-10], target = -30
        // Output: 2
        // Explanation: Find target in array of all negative numbers
        val nums = intArrayOf(-50, -40, -30, -20, -10)
        val target = -30
        val result = Solution().search(nums, target)
        result shouldBe 2
    }

    @Test
    fun `two element array - target is first element`() {
        // Input: nums = [1,3], target = 1
        // Output: 0
        // Explanation: Two elements, target is first
        val nums = intArrayOf(1, 3)
        val target = 1
        val result = Solution().search(nums, target)
        result shouldBe 0
    }

    @Test
    fun `two element array - target is second element`() {
        // Input: nums = [1,3], target = 3
        // Output: 1
        // Explanation: Two elements, target is second
        val nums = intArrayOf(1, 3)
        val target = 3
        val result = Solution().search(nums, target)
        result shouldBe 1
    }

    @Test
    fun `two element array - target not found`() {
        // Input: nums = [1,3], target = 2
        // Output: -1
        // Explanation: Two elements, target not present
        val nums = intArrayOf(1, 3)
        val target = 2
        val result = Solution().search(nums, target)
        result shouldBe -1
    }

    @Test
    fun `large array with evenly spaced elements`() {
        // Input: nums = [0,10,20,30,40,50,60,70,80,90,100], target = 70
        // Output: 7
        // Explanation: Testing with larger array
        val nums = intArrayOf(0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100)
        val target = 70
        val result = Solution().search(nums, target)
        result shouldBe 7
    }

    @Test
    fun `consecutive numbers - target found`() {
        // Input: nums = [1,2,3,4,5,6,7,8,9,10], target = 6
        // Output: 5
        // Explanation: Consecutive numbers, target at index 5
        val nums = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val target = 6
        val result = Solution().search(nums, target)
        result shouldBe 5
    }

    @Test
    fun `target is zero in array with negative and positive numbers`() {
        // Input: nums = [-5,-3,-1,0,2,4,6], target = 0
        // Output: 3
        // Explanation: Finding zero in mixed array
        val nums = intArrayOf(-5, -3, -1, 0, 2, 4, 6)
        val target = 0
        val result = Solution().search(nums, target)
        result shouldBe 3
    }

    @Test
    fun `array with maximum constraint size - target at start`() {
        // Input: nums = array of 10000 elements [0,1,2,...,9999], target = 0
        // Output: 0
        // Explanation: Testing with large array (max constraint is 10^4)
        val nums = IntArray(10000) { it }
        val target = 0
        val result = Solution().search(nums, target)
        result shouldBe 0
    }

    @Test
    fun `array with maximum constraint size - target at end`() {
        // Input: nums = array of 10000 elements [0,1,2,...,9999], target = 9999
        // Output: 9999
        // Explanation: Testing with large array, target at end
        val nums = IntArray(10000) { it }
        val target = 9999
        val result = Solution().search(nums, target)
        result shouldBe 9999
    }

    @Test
    fun `array with maximum constraint size - target in middle`() {
        // Input: nums = array of 10000 elements [0,1,2,...,9999], target = 5000
        // Output: 5000
        // Explanation: Testing with large array, target in middle
        val nums = IntArray(10000) { it }
        val target = 5000
        val result = Solution().search(nums, target)
        result shouldBe 5000
    }

    @Test
    fun `boundary values - minimum constraint`() {
        // Input: nums = [-10000, -5000, 0, 5000, 10000], target = -10000
        // Output: 0
        // Explanation: Testing with boundary value (constraint: -10^4 < nums[i])
        val nums = intArrayOf(-10000, -5000, 0, 5000, 10000)
        val target = -10000
        val result = Solution().search(nums, target)
        result shouldBe 0
    }

    class Solution {
        fun search(nums: IntArray, target: Int): Int {
            if (nums.isEmpty()) return -1
            var left = 0
            var right = nums.size - 1
            while (left <= right) {
                val mid = left + (right - left) / 2
                val num = nums[mid]
                if (nums[mid] == target) {
                    return mid
                } else if (num < target) {
                    left = mid + 1
                } else {
                    right = mid - 1
                }
            }
            return -1
        }
    }
}
