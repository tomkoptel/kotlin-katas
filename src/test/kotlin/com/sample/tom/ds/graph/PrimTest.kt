package com.sample.tom.ds.graph

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class PrimTest {
    @Test
    fun `build minimum spanning trees`() {
        val graph = buildGraph()
        val (cost, mst) = Prim.produceMinimumSpanningTree(graph)
        cost shouldBe 15.0
        "$mst" shouldBe """
            1 ---> [ 3 ]
            2 ---> [ 3, 5 ]
            3 ---> [ 1, 2, 6 ]
            4 ---> [ 6 ]
            5 ---> [ 2 ]
            6 ---> [ 3, 4 ]

        """.trimIndent()
    }

    private fun buildGraph(): AdjacencyList<Int> {
        val graph = AdjacencyList<Int>()

        // Create vertices
        val vertex1 = graph.createVertex(1)
        val vertex2 = graph.createVertex(2)
        val vertex3 = graph.createVertex(3)
        val vertex4 = graph.createVertex(4)
        val vertex5 = graph.createVertex(5)
        val vertex6 = graph.createVertex(6)

        graph.addUndirectedEdge(vertex3, vertex1, 1.0)
        graph.addUndirectedEdge(vertex3, vertex6, 4.0)
        graph.addUndirectedEdge(vertex3, vertex2, 5.0)
        graph.addUndirectedEdge(vertex4, vertex6, 2.0)
        graph.addUndirectedEdge(vertex1, vertex3, 1.0)
        graph.addUndirectedEdge(vertex5, vertex2, 3.0)
        graph.addUndirectedEdge(vertex2, vertex3, 5.0)
        graph.addUndirectedEdge(vertex6, vertex3, 4.0)
        graph.addUndirectedEdge(vertex6, vertex4, 2.0)

        return graph
    }
}
