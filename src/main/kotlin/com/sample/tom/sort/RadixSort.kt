package com.sample.tom.sort

import java.lang.Math.pow

object RadixSort {
    fun MutableList<Int>.radixSort(): MutableList<Int> {
        if (isEmpty()) return this

        val base = 10
        var digits = 1
        var done = false

        while (!done) {
            done = true

            val buckets = MutableList<MutableList<Int>>(10) { mutableListOf() }
            forEach { number ->
                val remainingPart = number / digits
                val digit = remainingPart % base
                buckets[digit] += number
                if (remainingPart > 0) {
                    done = false
                }
            }
            digits *= base

            clear()
            addAll(buckets.flatten())
        }

        return this
    }

    fun MutableList<Int>.lexicographicalSort(): MutableList<Int> {
        val stack = mutableListOf(this to 0)
        val result = mutableListOf<Int>()

        while (stack.isNotEmpty()) {
            val (currentList, position) = stack.removeAt(stack.size - 1)

            if (position >= currentList.maxDigits()) {
                result.addAll(currentList)
            } else {
                val buckets = MutableList(10) { mutableListOf<Int>() }
                val priorityBucket = arrayListOf<Int>()

                currentList.forEach { number ->
                    val digit = number.digit(position)
                    if (digit == null) {
                        priorityBucket.add(number)
                    } else {
                        buckets[digit].add(number)
                    }
                }

                // Add the buckets to the stack in reverse order
                for (i in 9 downTo 0) {
                    if (buckets[i].isNotEmpty()) {
                        stack.add(buckets[i] to position + 1)
                    }
                }

                // Add the priority bucket to the stack for further processing
                if (priorityBucket.isNotEmpty()) {
                    stack.add(priorityBucket to position + 1)
                }
            }
        }

        return result
    }

    private fun Int.digits(): Int {
        var count = 0
        var num = this
        while (num != 0) {
            count += 1
            num /= 10
        }
        return count
    }

    private fun Int.digit(atPosition: Int): Int? {
        val correctPosition = (atPosition + 1).toDouble()
        if (correctPosition > digits()) return null

        var num = this
        while (num / (pow(10.0, correctPosition).toInt()) != 0) {
            num /= 10
        }
        return num % 10
    }

    private fun List<Int>.maxDigits(): Int {
        return maxOrNull()?.digits() ?: 0
    }
}
