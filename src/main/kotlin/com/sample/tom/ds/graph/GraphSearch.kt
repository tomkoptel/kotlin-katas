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

    val <T : Any> Graph<T>.isConnected: Boolean
        get() {
            val vertex = allVertices.firstOrNull() ?: return false
            return breadthFirstSearch(vertex).containsAll(allVertices)
        }

    val <T : Any> Graph<T>.hasCycles: Boolean
        get() {
            val source = allVertices.firstOrNull() ?: return false
            val stack = LinkedList<Pair<Vertex<T>, Iterator<Edge<T>>>>()
            val visited = mutableSetOf<Vertex<T>>()

            stack.push(source to edges(source).iterator())

            while (stack.isNotEmpty()) {
                val (current, edgesIterator) = stack.element()
                if (edgesIterator.hasNext()) {
                    val edge = edgesIterator.next()
                    val neighbour = edge.destination
                    if (neighbour in visited) {
                        return true
                    } else {
                        visited.add(neighbour)
                        stack.push(neighbour to edges(neighbour).iterator())
                    }
                } else {
                    stack.pop()
                    visited.remove(current)
                }
            }

            return false
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

    fun <T : Any> Graph<T>.depthFirstSearch(source: Vertex<T>): List<Vertex<T>> {
        val stack = LinkedList<Iterator<Edge<T>>>()
        val visited = mutableSetOf<Vertex<T>>()
        val track = arrayListOf<Vertex<T>>()

        stack.push(edges(source).iterator())
        visited.add(source)
        track.add(source)

        while (stack.isNotEmpty()) {
            val edges = stack.element()

            if (edges.hasNext()) {
                val edge = edges.next()
                val neighbour = edge.destination
                if (neighbour !in visited) {
                    visited.add(neighbour)
                    track.add(neighbour)
                    stack.push(edges(neighbour).iterator())
                }
            } else {
                stack.pop()
            }
        }

        return track
    }

    fun <T : Any> Graph<T>.depthFirstSearchRecursive(source: Vertex<T>): List<Vertex<T>> {
        val visited = arrayListOf<Vertex<T>>()
        val pushed = mutableSetOf<Vertex<T>>()

        depthFirstSearchRecursive(source, visited, pushed)

        return visited
    }

    private fun <T : Any> Graph<T>.depthFirstSearchRecursive(
        source: Vertex<T>,
        visited: ArrayList<Vertex<T>>,
        pushed: MutableSet<Vertex<T>>,
    ) {
        visited.add(source)
        pushed.add(source)

        val neighbors = edges(source)
        for (neighbour in neighbors) {
            val destination = neighbour.destination
            if (destination !in pushed) {
                depthFirstSearchRecursive(destination, visited, pushed)
            }
        }
    }
}
