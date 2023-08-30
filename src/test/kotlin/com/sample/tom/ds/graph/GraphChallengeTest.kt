package com.sample.tom.ds.graph

import com.sample.tom.ds.graph.GraphChallenge.distanceNonRecursive
import com.sample.tom.ds.graph.GraphChallenge.distanceRecursive
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class GraphChallengeTest {
    private val graph = AdjacencyList<String>()
    private val vertexA = graph.createVertex("A")
    private val vertexB = graph.createVertex("B")
    private val vertexC = graph.createVertex("C")
    private val vertexD = graph.createVertex("D")
    private val vertexE = graph.createVertex("E")

    @Test
    fun `for non cyclic distance between vertices non recursive`() {
        populateNonCyclicGraph()
        graph.distanceNonRecursive(vertexA, vertexE) shouldBe 5
    }

    @Test
    fun `for non cyclic distance between vertices recursive`() {
        populateNonCyclicGraph()
        graph.distanceRecursive(vertexA, vertexE) shouldBe 5
    }

    @Test
    fun `for cyclic distance between vertices non recursive`() {
        populateCyclicGraph()
        graph.distanceNonRecursive(vertexA, vertexE) shouldBe 3
    }

    @Test
    fun `for cyclic distance between vertices recursive`() {
        populateCyclicGraph()
        graph.distanceRecursive(vertexA, vertexE) shouldBe 3
    }

    private fun populateNonCyclicGraph() {
        graph.addDirectedEdge(vertexA, vertexB, 0.0)
        graph.addDirectedEdge(vertexA, vertexC, 0.0)
        graph.addDirectedEdge(vertexA, vertexD, 0.0)
        graph.addDirectedEdge(vertexA, vertexE, 0.0)
        graph.addDirectedEdge(vertexB, vertexD, 0.0)
        graph.addDirectedEdge(vertexB, vertexC, 0.0)
        graph.addDirectedEdge(vertexC, vertexE, 0.0)
        graph.addDirectedEdge(vertexD, vertexE, 0.0)
    }

    private fun populateCyclicGraph() {
        graph.addUndirectedEdge(vertexA, vertexB, 0.0)
        graph.addUndirectedEdge(vertexB, vertexC, 0.0)
        graph.addUndirectedEdge(vertexC, vertexD, 0.0)
        graph.addUndirectedEdge(vertexD, vertexE, 0.0)
        graph.addUndirectedEdge(vertexE, vertexA, 0.0)
        graph.addUndirectedEdge(vertexA, vertexE, 0.0)
    }
}
