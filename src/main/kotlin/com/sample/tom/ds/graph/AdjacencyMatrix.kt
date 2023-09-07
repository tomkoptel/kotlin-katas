package com.sample.tom.ds.graph

class AdjacencyMatrix<T : Any> : Graph<T> {
    private val vertices = arrayListOf<Vertex<T>>()
    private val weights = arrayListOf<ArrayList<Double?>>()

    override fun createVertex(data: T): Vertex<T> {
        val vertex = Vertex(vertices.count(), data)
        vertices.add(vertex)
        weights.forEach { it.add(null) }
        val row = ArrayList<Double?>(vertices.count())
        repeat(vertices.count()) {
            row.add(null)
        }
        weights.add(row)
        return vertex
    }

    override fun addDirectedEdge(source: Vertex<T>, destination: Vertex<T>, weight: Double?) {
        weights[source.index][destination.index] = weight
    }

    override fun edges(source: Vertex<T>): ArrayList<Edge<T>> {
        val edges = arrayListOf<Edge<T>>()
        (0 until weights.size).forEach { column ->
            val weight = weights[source.index][column]
            if (weight != null) {
                edges.add(Edge(source, vertices[column], weight))
            }
        }
        return edges
    }

    override fun weight(source: Vertex<T>, destination: Vertex<T>): Double? {
        return weights[source.index][destination.index]
    }

    override val allVertices: List<Vertex<T>>
        get() = vertices

    override fun toString(): String {
        val verticesDescription = vertices.joinToString("\n") { "${it.index}:${it.data}" }
        val grid = arrayListOf<String>()
        weights.forEach {
            var row = ""
            (0 until weights.size).forEach { columnIndex ->
                row += if (columnIndex >= it.size) {
                    "ø\t\t"
                } else {
                    it[columnIndex]?.let { "$it\t" } ?: "ø\t\t"
                }
            }
            grid.add(row)
        }
        val edgesDescription = grid.joinToString("\n")
        return "$verticesDescription\n\n$edgesDescription"
    }
}
