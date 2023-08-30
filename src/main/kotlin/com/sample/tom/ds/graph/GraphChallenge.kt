package com.sample.tom.ds.graph

import java.util.LinkedList
import java.util.concurrent.atomic.AtomicInteger

object GraphChallenge {
    fun <T : Any> Graph<T>.distanceNonRecursive(vertexA: Vertex<T>, vertexB: Vertex<T>): Int {
        val stack = LinkedList<Edge<T>>()
        edges(vertexA).forEach { stack.push(it) }
        val visited = mutableSetOf<Vertex<T>>()
        var paths = 0
        while (stack.isNotEmpty()) {
            val edge = stack.pop()
            val source = edge.source
            val destination = edge.destination
            if (destination == vertexB) {
                paths++
            } else {
                if (destination !in visited) {
                    edges(destination).forEach { stack.push(it) }
                    visited.add(source)
                }
            }
        }
        return paths
    }

    fun <T : Any> Graph<T>.distanceRecursive(vertexA: Vertex<T>, vertexB: Vertex<T>): Int {
        val numberOfPaths = AtomicInteger(0)
        val visited = mutableSetOf<Vertex<T>>()
        paths(vertexA, vertexB, visited, numberOfPaths)
        return numberOfPaths.get()
    }

    private fun <T : Any> Graph<T>.paths(
        source: Vertex<T>,
        destination: Vertex<T>,
        visited: MutableSet<Vertex<T>>,
        numberOfPaths: AtomicInteger,
    ) {
        visited.add(source)
        if (source == destination) {
            numberOfPaths.incrementAndGet()
        } else {
            edges(source).forEach { edge ->
                if (edge.destination !in visited) {
                    paths(edge.destination, destination, visited, numberOfPaths)
                }
            }
        }
        visited.remove(source)
    }
}
