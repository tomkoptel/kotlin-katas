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
}
