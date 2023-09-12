package com.sample.tom.ds.graph

import com.sample.tom.ds.graph.GraphSearch.breadthFirstSearch
import com.sample.tom.ds.graph.GraphSearch.breadthFirstSearchRecursive
import com.sample.tom.ds.graph.GraphSearch.depthFirstSearch
import com.sample.tom.ds.graph.GraphSearch.depthFirstSearchRecursive
import com.sample.tom.ds.graph.GraphSearch.hasCycles
import com.sample.tom.ds.graph.GraphSearch.isConnected
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class GraphSearchTest {
    private val graph = AdjacencyList<String>()
    private val singapore = graph.createVertex("Singapore")
    private val tokyo = graph.createVertex("Tokyo")
    private val hongKong = graph.createVertex("Hong Kong")
    private val detroit = graph.createVertex("Detroit")
    private val sanFrancisco = graph.createVertex("San Francisco")
    private val washingtonDC = graph.createVertex("Washington DC")
    private val austinTexas = graph.createVertex("Austin Texas")
    private val seattle = graph.createVertex("Seattle")

    @Test
    fun breadthFirstSearch() {
        connectedGraph().breadthFirstSearch(singapore).map { it.data } shouldBe listOf(
            "Singapore", "Hong Kong", "Tokyo", "San Francisco", "Detroit", "Washington DC", "Seattle", "Austin Texas"
        )
    }

    @Test
    fun breadthFirstSearchRecursive() {
        connectedGraph().breadthFirstSearchRecursive(singapore).map { it.data } shouldBe listOf(
            "Singapore", "Hong Kong", "Tokyo", "San Francisco", "Detroit", "Washington DC", "Seattle", "Austin Texas"
        )
    }

    @Test
    fun `isDisconnected for connected should be false`() {
        connectedGraph().isConnected.shouldBeTrue()
    }

    @Test
    fun `isDisconnected for disconnected should be true`() {
        disConnectedGraph().isConnected.shouldBeFalse()
    }

    @Test
    fun `hasCycles for graph without cycles should be false`() {
        val graphNoCycles = AdjacencyList<String>()
        val a = graphNoCycles.createVertex("A")
        val b = graphNoCycles.createVertex("B")
        val c = graphNoCycles.createVertex("C")
        val d = graphNoCycles.createVertex("D")
        val e = graphNoCycles.createVertex("E")

        graphNoCycles.addDirectedEdge(a, b, 0.0)
        graphNoCycles.addDirectedEdge(a, c, 0.0)
        graphNoCycles.addDirectedEdge(b, d, 0.0)
        graphNoCycles.addDirectedEdge(c, e, 0.0)

        graphNoCycles.hasCycles.shouldBeFalse()
    }

    @Test
    fun `hasCycles for graph with cycles should be true`() {
        val graphWithCycle = AdjacencyList<String>()
        val a = graphWithCycle.createVertex("A")
        val b = graphWithCycle.createVertex("B")
        val c = graphWithCycle.createVertex("C")
        val d = graphWithCycle.createVertex("D")
        val e = graphWithCycle.createVertex("E")

        graphWithCycle.addDirectedEdge(a, b, 0.0)
        graphWithCycle.addDirectedEdge(a, c, 0.0)
        graphWithCycle.addDirectedEdge(b, d, 0.0)
        graphWithCycle.addDirectedEdge(c, e, 0.0)
        graphWithCycle.addDirectedEdge(e, a, 0.0)

        graphWithCycle.hasCycles.shouldBeTrue()
    }

    @Test
    fun testDepthFirstSearch() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        val c = graph.createVertex("C")
        val d = graph.createVertex("D")
        val e = graph.createVertex("E")

        graph.addDirectedEdge(a, b, 0.0)
        graph.addDirectedEdge(a, c, 0.0)
        graph.addDirectedEdge(b, d, 0.0)
        graph.addDirectedEdge(c, e, 0.0)

        graph.depthFirstSearch(a).map { it.data } shouldBe listOf(a, b, d, c, e).map { it.data }
    }

    @Test
    fun testDepthFirstSearch2() {
        // Create a graph
        val graph = AdjacencyList<Int>()
        val vertexA = graph.createVertex(1)
        val vertexB = graph.createVertex(2)
        val vertexC = graph.createVertex(3)
        val vertexD = graph.createVertex(4)

        // Add edges
        graph.addDirectedEdge(vertexA, vertexB, null)
        graph.addDirectedEdge(vertexA, vertexC, null)
        graph.addDirectedEdge(vertexB, vertexD, null)

        graph.depthFirstSearch(vertexA).map { it.data } shouldBe listOf(vertexA, vertexB, vertexD, vertexC).map { it.data }
    }

    @Test
    fun depthFirstSearchRecursive() {
        val graph = AdjacencyList<String>()
        val a = graph.createVertex("A")
        val b = graph.createVertex("B")
        val c = graph.createVertex("C")
        val d = graph.createVertex("D")
        val e = graph.createVertex("E")

        graph.addDirectedEdge(a, b, 0.0)
        graph.addDirectedEdge(a, c, 0.0)
        graph.addDirectedEdge(b, d, 0.0)
        graph.addDirectedEdge(c, e, 0.0)

        graph.depthFirstSearchRecursive(a).map { it.data } shouldBe listOf(a, b, d, c, e).map { it.data }
    }

    @Test
    fun depthFirstSearchRecursive2() {
        // Create a graph
        val graph = AdjacencyList<Int>()
        val vertexA = graph.createVertex(1)
        val vertexB = graph.createVertex(2)
        val vertexC = graph.createVertex(3)
        val vertexD = graph.createVertex(4)

        // Add edges
        graph.addDirectedEdge(vertexA, vertexB, null)
        graph.addDirectedEdge(vertexA, vertexC, null)
        graph.addDirectedEdge(vertexB, vertexD, null)

        graph.depthFirstSearchRecursive(vertexA).map { it.data } shouldBe listOf(vertexA, vertexB, vertexD, vertexC).map { it.data }
    }

    private fun connectedGraph(): Graph<String> {
        graph.add(EdgeType.DIRECTED, singapore, hongKong, 300.0)
        graph.add(EdgeType.DIRECTED, singapore, tokyo, 500.0)
        graph.add(EdgeType.DIRECTED, hongKong, tokyo, 250.0)
        graph.add(EdgeType.DIRECTED, tokyo, detroit, 450.0)
        graph.add(EdgeType.DIRECTED, tokyo, washingtonDC, 300.0)
        graph.add(EdgeType.DIRECTED, hongKong, sanFrancisco, 600.0)
        graph.add(EdgeType.DIRECTED, detroit, austinTexas, 50.0)
        graph.add(EdgeType.DIRECTED, austinTexas, washingtonDC, 292.0)
        graph.add(EdgeType.DIRECTED, sanFrancisco, washingtonDC, 337.0)
        graph.add(EdgeType.DIRECTED, washingtonDC, seattle, 277.0)
        graph.add(EdgeType.DIRECTED, sanFrancisco, seattle, 218.0)
        graph.add(EdgeType.DIRECTED, austinTexas, sanFrancisco, 297.0)
        return graph
    }

    private fun disConnectedGraph(): Graph<String> {
        graph.add(EdgeType.DIRECTED, singapore, hongKong, 300.0)
        graph.add(EdgeType.DIRECTED, singapore, tokyo, 500.0)
        graph.add(EdgeType.DIRECTED, tokyo, detroit, 450.0)
        graph.add(EdgeType.DIRECTED, tokyo, washingtonDC, 300.0)
        return graph
    }
}
