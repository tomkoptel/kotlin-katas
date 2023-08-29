package com.sample.tom.ds.graph

import com.sample.tom.ds.graph.GraphChallenge.distanceNonRecursive
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class GraphChallengeTest {
    @Test
    fun `distance between vertices non recursive`() {
        val (graph, vertexA, vertexE) = pepareTestData()
        graph.distanceNonRecursive(vertexA, vertexE) shouldBe 5
    }

    private fun pepareTestData(): Triple<AdjacencyList<String>, Vertex<String>, Vertex<String>> {
        val graph = AdjacencyList<String>()
        val vertexA = graph.createVertex("A")
        val vertexB = graph.createVertex("B")
        val vertexC = graph.createVertex("C")
        val vertexD = graph.createVertex("D")
        val vertexE = graph.createVertex("E")
        graph.addDirectedEdge(vertexA, vertexB, 0.0)
        graph.addDirectedEdge(vertexA, vertexC, 0.0)
        graph.addDirectedEdge(vertexA, vertexD, 0.0)
        graph.addDirectedEdge(vertexA, vertexE, 0.0)
        graph.addDirectedEdge(vertexB, vertexD, 0.0)
        graph.addDirectedEdge(vertexB, vertexC, 0.0)
        graph.addDirectedEdge(vertexC, vertexE, 0.0)
        graph.addDirectedEdge(vertexD, vertexE, 0.0)
        return Triple(graph, vertexA, vertexE)
    }
}
