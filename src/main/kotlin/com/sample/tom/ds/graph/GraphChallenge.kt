package com.sample.tom.ds.graph

import java.util.LinkedList

object GraphChallenge {
    fun <T: Any> Graph<T>.distanceNonRecursive(vertexA: Vertex<T>, vertexB: Vertex<T>): Int {
        val stack = LinkedList<Edge<T>>()
        edges(vertexA).forEach { stack.push(it) }
        var paths = 0
        while (stack.isNotEmpty()) {
            val edge = stack.pop()
            if (edge.destination == vertexB) {
                paths++
            } else {
                edges(edge.destination).forEach { stack.push(it) }
            }
        }
        return paths
    }
}
