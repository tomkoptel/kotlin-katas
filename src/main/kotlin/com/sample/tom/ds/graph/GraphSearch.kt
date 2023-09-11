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
        val stack = LinkedList<Vertex<T>>()
        val visited = arrayListOf<Vertex<T>>()
        val pushed = mutableSetOf<Vertex<T>>()

        stack.push(source)
        pushed.add(source)
        visited.add(source)

        outer@ while (true) {
            if (stack.isEmpty()) break

            val vertex = stack.peek()!!
            val neighbors = edges(vertex)

            if (neighbors.isEmpty()) {
                stack.pop()
                continue
            }

            for (i in 0 until neighbors.size) { // 4
                val destination = neighbors[i].destination
                if (destination !in pushed) {
                    stack.push(destination)
                    pushed.add(destination)
                    visited.add(destination)
                    continue@outer // 5
                }
            }
            stack.pop() // 6
        }

        return visited
    }

    fun <T : Any> Graph<T>.depthFirstSearchRecursive(source: Vertex<T>): List<Vertex<T>> {
        val stack = LinkedList<Vertex<T>>()
        val visited = arrayListOf<Vertex<T>>()
        val pushed = mutableSetOf<Vertex<T>>()

        stack.push(source)
        pushed.add(source)
        visited.add(source)

        depthFirstSearchRecursive(stack, visited, pushed)

        return visited
    }

    private fun <T : Any> Graph<T>.depthFirstSearchRecursive(
        stack: LinkedList<Vertex<T>>,
        visited: ArrayList<Vertex<T>>,
        pushed: MutableSet<Vertex<T>>,
    ) {
        val vertex = stack.peek() ?: return
        val neighbors = edges(vertex)
        if (neighbors.isEmpty()) {
            stack.pop()
            return
        }

        for (neighbour in neighbors) {
            val destination = neighbour.destination
            if (!visited.contains(destination)) {
                stack.push(destination)
                pushed.add(destination)
                visited.add(destination)
                depthFirstSearchRecursive(stack, visited, pushed)
            }
        }

    }
}
