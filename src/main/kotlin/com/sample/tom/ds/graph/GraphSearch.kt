package com.sample.tom.ds.graph

import java.util.LinkedList

object GraphSearch {
    fun <T : Any> Graph<T>.breadthFirstSearch(source: Vertex<T>): List<Vertex<T>> {
        val queue = LinkedList<Vertex<T>>()
        val enqueued = mutableSetOf<Vertex<T>>()
        val visited = mutableListOf<Vertex<T>>()

        queue.add(source)
        enqueued.add(source)

        while (queue.isNotEmpty()) {
            val vertex = queue.poll()
            edges(vertex).forEach {
                if (it.destination !in enqueued) {
                    queue.add(it.destination)
                    enqueued.add(it.destination)
                }
            }
            visited.add(vertex)
        }

        return visited
    }

    val <T : Any> Graph<T>.isConnected: Boolean get() {
        val vertex = allVertices.firstOrNull() ?: return false
        return breadthFirstSearch(vertex).containsAll(allVertices)
    }

    fun <T : Any> Graph<T>.breadthFirstSearchRecursive(source: Vertex<T>): List<Vertex<T>> {
        val queue = LinkedList<Vertex<T>>()
        val enqueued = mutableSetOf<Vertex<T>>()
        val visited = mutableListOf<Vertex<T>>()

        queue.add(source)
        enqueued.add(source)

        breadthFirstSearchRecursive(
            queue,
            enqueued,
            visited
        )

        return visited
    }

    private fun <T : Any> Graph<T>.breadthFirstSearchRecursive(
        queue: LinkedList<Vertex<T>>,
        enqueued: MutableSet<Vertex<T>>,
        visited: MutableList<Vertex<T>>,
    ) {
        val source = queue.poll() ?: return
        visited.add(source)
        edges(source).forEach { edge ->
            if (!enqueued.contains(edge.destination)) {
                queue.add(edge.destination)
                enqueued.add(edge.destination)
            }
        }
        breadthFirstSearchRecursive(
            queue,
            enqueued,
            visited
        )
    }
}