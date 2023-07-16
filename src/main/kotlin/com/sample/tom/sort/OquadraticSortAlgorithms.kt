package com.sample.tom.sort

import java.util.*

object OquadraticSortAlgorithms {
    fun <T : Comparable<T>> MutableList<T>.bubbleSort() {
        if (size < 2) return

        for (end in lastIndex downTo 1) {
            var swapped = false

            println(this)

            for (current in 0 until end) {
                val next = current + 1
                val currentEl = this[current]
                val nextEl = this[next]
                if (currentEl > nextEl) {
                    Collections.swap(this, current, next)
                    swapped = true
                }
                println("end=$end current=$current next=$next currentEl=$currentEl nextEl=$nextEl")
            }

            if (!swapped) return
        }
    }

    fun <T : Comparable<T>> MutableList<T>.selectionSort() {
        if (size < 2) return

        for (current in 0 until lastIndex) {
            var lowest = current

            for (other in (current + 1) until size) {
                println("current=$current other=$other $this")
                if (this[other] < this[lowest]) {
                    lowest = other
                }
            }

            if (lowest != current) {
                Collections.swap(this, lowest, current)
            }

            println(this)
        }
    }

    fun <T : Comparable<T>> MutableList<T>.insertionSort() {
        for (current in 1 until size) {
            val range = (1..current).reversed()
            println("range=$range")
            for (shifting in range) {
                println("current=$current remaining=$shifting")
                if (this[shifting] < this[shifting - 1]) {
                    Collections.swap(this, shifting, shifting - 1)
                } else {
                    break
                }
            }
            println(this)
        }
    }

    /**
     * Given a list of Comparable elements, bring all instances of a given value in the list to the right side of the list.
     */
    fun <T : Comparable<T>> MutableList<T>.rightAlign(element: T) {
        val first = indexOfFirst { it == element }
        val last = indexOfLast { it == element }
        if (first < 0 || last < 0) return

        val shiftIndex = last - first
        val firstAtEnd = lastIndex - shiftIndex
        var index = indexOf(element)

        while (index != firstAtEnd) {
            for (current in index until size - 1) {
                Collections.swap(this, current, current + 1)
            }
            index = indexOf(element)
        }
    }
}
