package com.sample.tom.ds.graph

import java.util.*
import kotlin.math.roundToInt

object Prim {
    fun <T : Any> produceMinimumSpanningTree(
        graph: AdjacencyList<T>,
    ): Pair<Double, AdjacencyList<T>> {
        var cost = 0.0
        val visited = mutableSetOf<Vertex<T>>()
        val comparator = Comparator<Edge<T>> { first, second ->
            ((first.weight ?: 0.0) - (second.weight ?: 0.0)).roundToInt()
        }
        val queue = PriorityQueue(comparator)
        val mst = AdjacencyList<T>()
        mst.copyVertices(graph)

        val start = graph.allVertices.firstOrNull() ?: return (cost to mst)
        visited.add(start)

        addAvailableEdges(start, graph, visited, queue)

        while (queue.isNotEmpty()) {
            val smallestEdge = queue.remove()
            val vertex = smallestEdge.destination
            if (visited.contains(vertex)) continue

            visited.add(vertex)
            cost += smallestEdge.weight ?: 0.0

            mst.add(EdgeType.UNDIRECTED, smallestEdge.source, smallestEdge.destination, smallestEdge.weight)
            addAvailableEdges(vertex, graph, visited, queue)
        }

        return (cost to mst)
    }

    private fun <T : Any> addAvailableEdges(
        vertex: Vertex<T>,
        graph: Graph<T>,
        visited: Set<Vertex<T>>,
        priorityQueue: PriorityQueue<Edge<T>>,
    ) {
        graph.edges(vertex).forEach {
            if (!visited.contains(it.destination)) {
                priorityQueue.add(it)
            }
        }
    }
}
