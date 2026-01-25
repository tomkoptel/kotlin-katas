package com.sample.tom.ds.graph

import java.util.PriorityQueue

class Dijkstra<T : Any>(
    private val graph: Graph<T>,
) {
    fun getAllShortestPath(source: Vertex<T>): Map<Vertex<T>, List<Edge<T>>> {
        val paths = mutableMapOf<Vertex<T>, List<Edge<T>>>()

        val pathsFromSource = shortestPath(source)
        val otherVertices = graph.allVertices.toSet().minus(source)
        otherVertices.forEach { target ->
            paths[target] = route(target, pathsFromSource)
        }

        return paths
    }

    fun shortestPath(
        start: Vertex<T>,
        finish: Vertex<T>,
    ): List<Edge<T>> {
        val paths = shortestPath(start)
        return route(finish, paths)
    }

    private fun shortestPath(start: Vertex<T>): MutableMap<Vertex<T>, Visit<T>> {
        val paths = mutableMapOf<Vertex<T>, Visit<T>>()
        paths[start] = Visit(VisitType.START)
        val distanceComparator =
            Comparator<Vertex<T>> { first, second ->
                (distance(second, paths) - distance(first, paths)).toInt()
            }
        val priorityQueue = PriorityQueue(distanceComparator)
        priorityQueue.add(start)

        while (priorityQueue.isNotEmpty()) {
            val vertex = priorityQueue.poll()
            val edges = graph.edges(vertex)

            edges.forEach { edge ->
                val weight = edge.weight ?: return@forEach
                val notVisited = paths[edge.destination] == null
                if (notVisited || distance(vertex, paths) + weight < distance(edge.destination, paths)) {
                    paths[edge.destination] = Visit(VisitType.EDGE, edge)
                    priorityQueue.add(edge.destination)
                }
            }
        }

        return paths
    }

    private fun distance(
        destination: Vertex<T>,
        paths: MutableMap<Vertex<T>, Visit<T>>,
    ): Double = route(destination, paths).sumOf { it.weight ?: 0.0 }

    private fun route(
        destination: Vertex<T>,
        paths: MutableMap<Vertex<T>, Visit<T>>,
    ): List<Edge<T>> {
        var vertex = destination
        val path = mutableListOf<Edge<T>>()

        loop@ while (true) {
            val visit = paths[vertex] ?: break

            when (visit.type) {
                VisitType.START -> break@loop
                VisitType.EDGE ->
                    visit.edge?.let {
                        path.add(it)
                        vertex = it.source
                    }
            }
        }

        return path
    }

    data class Visit<T : Any>(
        val type: VisitType,
        val edge: Edge<T>? = null,
    )

    enum class VisitType {
        START,
        EDGE,
    }
}
