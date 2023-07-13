package com.sample.tom.sort

import java.util.*

object OquadraticSortAlgorithms {
    fun <T : Comparable<T>> ArrayList<T>.bubbleSort() {
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
}
