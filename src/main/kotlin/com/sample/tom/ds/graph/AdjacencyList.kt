package com.sample.tom.ds.graph

class AdjacencyList<T : Any> : Graph<T> {
    private val adjacencies = mutableMapOf<Vertex<T>, ArrayList<Edge<T>>>()

    override fun createVertex(data: T): Vertex<T> {
        val vertex = Vertex(adjacencies.count(), data)
        adjacencies[vertex] = ArrayList()
        return vertex
    }

    override fun addDirectedEdge(source: Vertex<T>, destination: Vertex<T>, weight: Double?) {
        val edge = Edge(source, destination, weight)
        adjacencies[source]?.add(edge)
    }

    override fun edges(source: Vertex<T>): ArrayList<Edge<T>> = adjacencies[source] ?: ArrayList()

    override fun weight(source: Vertex<T>, destination: Vertex<T>): Double? = edges(source).firstOrNull { it.destination == destination }?.weight

    override val allVertices: List<Vertex<T>>
        get() = adjacencies.keys.toList()

    override fun toString(): String = buildString {
        adjacencies.forEach { (vertex, edges) ->
            val edgeString = edges.joinToString { it.destination.data.toString() }
            append("${vertex.data} ---> [ $edgeString ]\n")
        }
    }
}
