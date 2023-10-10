package com.sample.tom.ds.graph

import io.kotest.matchers.maps.shouldContain
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DijkstraTest {
    private val graph = AdjacencyList<String>()
    private val singapore = graph.createVertex("Singapore")
    private val tokyo = graph.createVertex("Tokyo")
    private val hongKong = graph.createVertex("Hong Kong")
    private val detroit = graph.createVertex("Detroit")
    private val sanFrancisco = graph.createVertex("San Francisco")
    private val washingtonDC = graph.createVertex("Washington DC")
    private val austinTexas = graph.createVertex("Austin Texas")
    private val seattle = graph.createVertex("Seattle")

    @BeforeEach
    fun prepareGraph() {
        graph.add(EdgeType.UNDIRECTED, singapore, hongKong, 300.0)
        graph.add(EdgeType.UNDIRECTED, singapore, tokyo, 500.0)
        graph.add(EdgeType.UNDIRECTED, hongKong, tokyo, 250.0)
        graph.add(EdgeType.UNDIRECTED, tokyo, detroit, 450.0)
        graph.add(EdgeType.UNDIRECTED, tokyo, washingtonDC, 300.0)
        graph.add(EdgeType.UNDIRECTED, hongKong, sanFrancisco, 600.0)
        graph.add(EdgeType.UNDIRECTED, detroit, austinTexas, 50.0)
        graph.add(EdgeType.UNDIRECTED, austinTexas, washingtonDC, 292.0)
        graph.add(EdgeType.UNDIRECTED, sanFrancisco, washingtonDC, 337.0)
        graph.add(EdgeType.UNDIRECTED, washingtonDC, seattle, 277.0)
        graph.add(EdgeType.UNDIRECTED, sanFrancisco, seattle, 218.0)
        graph.add(EdgeType.UNDIRECTED, austinTexas, sanFrancisco, 297.0)
    }

    @Test
    fun smokeTest() {
        val dijkstra = Dijkstra(graph)
        val route = dijkstra.shortestPath(singapore, washingtonDC)
        route shouldBe listOf(
            Edge(source = tokyo, destination = washingtonDC, weight = 300.0),
            Edge(source = singapore, destination = tokyo, weight = 500.0)
        )
    }

    @Test
    fun getAllShortestPath() {
        val dijkstra = Dijkstra(graph)
        val paths = dijkstra.getAllShortestPath(singapore)
        paths.shouldContain(tokyo to listOf(Edge(source = singapore, destination = tokyo, weight = 500.0)))
        paths.shouldContain(hongKong to listOf(Edge(source = singapore, destination = hongKong, weight = 300.0)))
        paths.shouldContain(
            detroit to listOf(
                Edge(source = tokyo, destination = detroit, weight = 450.0),
                Edge(source = singapore, destination = tokyo, weight = 500.0)
            )
        )
        paths.shouldContain(
            sanFrancisco to listOf(
                Edge(source = hongKong, destination = sanFrancisco, weight = 600.0),
                Edge(source = singapore, destination = hongKong, weight = 300.0)
            )
        )
        paths.shouldContain(
            washingtonDC to listOf(
                Edge(source = tokyo, destination = washingtonDC, weight = 300.0),
                Edge(source = singapore, destination = tokyo, weight = 500.0)
            )
        )
        paths.shouldContain(
            austinTexas to listOf(
                Edge(source = detroit, destination = austinTexas, weight = 50.0),
                Edge(source = tokyo, destination = detroit, weight = 450.0),
                Edge(source = singapore, destination = tokyo, weight = 500.0)
            )
        )
        paths.shouldContain(
            seattle to listOf(
                Edge(source = washingtonDC, destination = seattle, weight = 277.0),
                Edge(source = tokyo, destination = washingtonDC, weight = 300.0),
                Edge(source = singapore, destination = tokyo, weight = 500.0)
            )
        )
    }
}
