package com.sample.tom.sort

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.util.Collections

class QuickSortWithClaudeTest {
    @Test
    fun name() {
        val alreadySorted = listOf(1, 2, 3, 4, 5)
        val reverseSorted = listOf(5, 4, 3, 2, 1)
        val duplicates = listOf(3, 1, 3, 2, 3)
        val singleElement = listOf(7)

        quickSort(alreadySorted) shouldBe listOf(1, 2, 3, 4, 5)
        quickSort(reverseSorted) shouldBe listOf(1, 2, 3, 4, 5)
        quickSort(duplicates) shouldBe listOf(1, 2, 3, 3, 3)
        quickSort(singleElement) shouldBe listOf(7)
        quickSort(emptyList()).shouldBeEmpty()
    }

    fun quickSort(input: List<Int>): List<Int> {
        if (input.isEmpty() || input.size == 1) return input
        val mutableInput = input.toMutableList()
        val stack = ArrayDeque<Data>()
        stack.add(Data.prepare(input = mutableInput, range = (0 until input.size)))

        while (stack.isNotEmpty()) {
            val data = stack.removeLast()
            val wherePivotLanded = sortInPlace(mutableInput, data)
            data.rangeToLeftOfPivot(mutableInput, wherePivotLanded)?.let { data ->
                stack.add(data)
            }
            data.rangeToRightOfPivot(mutableInput, wherePivotLanded)?.let { data ->
                stack.add(data)
            }
        }

        return mutableInput
    }

    class Data private constructor(
        val range: IntRange,
    ) {
        companion object {
            fun prepare(input: MutableList<Int>, range: IntRange): Data {
                val pivotIndex = pickApivot(input, range)
                Collections.swap(input, pivotIndex, range.endInclusive)
                return Data(range)
            }

            private fun pickApivot(input: List<Int>, range: IntRange): Int {
                val firstIndex = range.start
                val lastIndex = range.endInclusive
                val midIndex = (firstIndex + lastIndex).ushr(1) // should be safe to avoid overflow
                val mid = input[midIndex]
                val first = input[firstIndex]
                val last = input[lastIndex]

                return when {
                    first <= mid && mid <= last -> midIndex
                    mid <= first && first <= last -> firstIndex
                    else -> lastIndex
                }
            }
        }

        fun pivotOf(input: List<Int>): Int = input[pivotIndex]

        val pivotIndex: Int get() = range.endInclusive

        val subRange: IntRange = IntRange(start = range.start, endInclusive = range.endInclusive - 1)

        fun rangeToRightOfPivot(input: MutableList<Int>, wherePivotLanded: Int): Data? {
            val start = wherePivotLanded + 1
            val end = range.endInclusive

            return if (end - start >= 1) {
                prepare(input = input, range = IntRange(start, end))
            } else {
                null
            }
        }

        fun rangeToLeftOfPivot(input: MutableList<Int>, wherePivotLanded: Int): Data? {
            val start = range.start
            val end = wherePivotLanded - 1
            return if (end - start >= 1) {
                prepare(input = input, range = IntRange(start, end))
            } else {
                null
            }
        }
    }

    fun sortInPlace(input: MutableList<Int>, data: Data): Int {
        val pivot = data.pivotOf(input)
        var wall = data.range.start
        for (i in data.subRange) {
            val el = input[i]
            if (el <= pivot) {
                Collections.swap(input, i, wall)
                wall++
            }
        }
        Collections.swap(input,data.pivotIndex, wall)
        return wall
    }
}
