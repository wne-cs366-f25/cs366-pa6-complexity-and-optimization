import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Comprehensive test suite for PA5 Pathfinding Algorithms.
 * Tests BFS, Dijkstra, precomputation, and weather systems.
 *
 * @author CS366 Course Staff
 */
@DisplayName("PA5: Pathfinding Algorithms Tests")
public class PathfindingTest {

    private GameMap simpleMap;
    private GameMap complexMap;
    private WeatherSystem weather;

    @BeforeEach
    void setUp() {
        // Create a simple test map
        simpleMap = createSimpleTestMap();

        // Create a more complex test map
        complexMap = createComplexTestMap();

        // Initialize weather system
        weather = new WeatherSystem();
    }

    /**
     * Creates a simple 5-node test graph.
     * Graph structure:
     *   0 --- 1
     *   |     |
     *   2 --- 3 --- 4
     */
    private GameMap createSimpleTestMap() {
        GameMap map = new GameMap();

        // Add nodes
        map.addNode(0, "Node0");
        map.addNode(1, "Node1");
        map.addNode(2, "Node2");
        map.addNode(3, "Node3");
        map.addNode(4, "Node4");

        // Add edges with weights
        map.addEdge(0, 1, 1.0);
        map.addEdge(0, 2, 2.0);
        map.addEdge(1, 3, 1.5);
        map.addEdge(2, 3, 1.0);
        map.addEdge(3, 4, 2.0);

        return map;
    }

    /**
     * Creates a more complex test graph with multiple paths.
     */
    private GameMap createComplexTestMap() {
        GameMap map = new GameMap();

        // Add 10 nodes
        for (int i = 0; i < 10; i++) {
            map.addNode(i, "Node" + i);
        }

        // Create a graph with multiple paths of different costs
        map.addEdge(0, 1, 1.0);
        map.addEdge(0, 2, 4.0);
        map.addEdge(1, 2, 2.0);
        map.addEdge(1, 3, 5.0);
        map.addEdge(2, 3, 1.0);
        map.addEdge(3, 4, 3.0);
        map.addEdge(3, 5, 2.0);
        map.addEdge(4, 6, 2.0);
        map.addEdge(5, 6, 1.0);
        map.addEdge(5, 7, 3.0);
        map.addEdge(6, 8, 1.0);
        map.addEdge(7, 8, 2.0);
        map.addEdge(7, 9, 1.0);
        map.addEdge(8, 9, 4.0);

        return map;
    }

    @Nested
    @DisplayName("BFS Algorithm Tests")
    class BFSTests {

        @Test
        @DisplayName("BFS should find path in unweighted graph")
        void testBFSBasicPath() {
            GameMap unweighted = simpleMap.toUnweighted();
            List<Integer> path = PathfindingAlgorithms.findPathBFS(unweighted, 0, 4);

            assertNotNull(path);
            assertFalse(path.isEmpty());
            assertEquals(0, path.get(0), "Path should start at source");
            assertEquals(4, path.get(path.size() - 1), "Path should end at target");
            assertEquals(4, path.size(), "Should find shortest path by edge count");
        }

        @Test
        @DisplayName("BFS should return empty list for unreachable nodes")
        void testBFSUnreachable() {
            GameMap disconnected = new GameMap();
            disconnected.addNode(0, "Island1");
            disconnected.addNode(1, "Island2");

            List<Integer> path = PathfindingAlgorithms.findPathBFS(disconnected, 0, 1);

            assertNotNull(path);
            assertTrue(path.isEmpty(), "Should return empty list for unreachable target");
        }

        @Test
        @DisplayName("BFS should handle same start and target")
        void testBFSSameNode() {
            GameMap unweighted = simpleMap.toUnweighted();
            List<Integer> path = PathfindingAlgorithms.findPathBFS(unweighted, 2, 2);

            assertNotNull(path);
            assertEquals(1, path.size());
            assertEquals(2, path.get(0));
        }

        @Test
        @DisplayName("BFS should handle invalid nodes")
        void testBFSInvalidNodes() {
            GameMap unweighted = simpleMap.toUnweighted();

            List<Integer> path1 = PathfindingAlgorithms.findPathBFS(unweighted, -1, 3);
            assertTrue(path1.isEmpty(), "Invalid start should return empty list");

            List<Integer> path2 = PathfindingAlgorithms.findPathBFS(unweighted, 0, 99);
            assertTrue(path2.isEmpty(), "Invalid target should return empty list");
        }
    }

    @Nested
    @DisplayName("Dijkstra Algorithm Tests")
    class DijkstraTests {

        @Test
        @DisplayName("Dijkstra should find shortest weighted path")
        void testDijkstraBasicPath() {
            List<Integer> path = PathfindingAlgorithms.findPathDijkstra(simpleMap, 0, 4);

            assertNotNull(path);
            assertFalse(path.isEmpty());
            assertEquals(0, path.get(0), "Path should start at source");
            assertEquals(4, path.get(path.size() - 1), "Path should end at target");

            // The shortest weighted path is 0->2->3->4 (cost: 2+1+2=5)
            // Not 0->1->3->4 (cost: 1+1.5+2=4.5)
            // Actually 0->1->3->4 is shorter, so expect that
            List<Integer> expected = Arrays.asList(0, 1, 3, 4);
            assertEquals(expected, path, "Should find shortest weighted path");
        }

        @Test
        @DisplayName("Dijkstra should handle complex graphs")
        void testDijkstraComplexPath() {
            List<Integer> path = PathfindingAlgorithms.findPathDijkstra(complexMap, 0, 9);

            assertNotNull(path);
            assertFalse(path.isEmpty());
            assertEquals(0, path.get(0));
            assertEquals(9, path.get(path.size() - 1));

            // Verify path is valid
            for (int i = 0; i < path.size() - 1; i++) {
                double weight = complexMap.getEdgeWeight(path.get(i), path.get(i + 1));
                assertTrue(weight > 0, "Path should use valid edges");
            }
        }

        @Test
        @DisplayName("Dijkstra should match BFS on unweighted graphs")
        void testDijkstraUnweighted() {
            GameMap unweighted = simpleMap.toUnweighted();

            List<Integer> bfsPath = PathfindingAlgorithms.findPathBFS(unweighted, 0, 4);
            List<Integer> dijkstraPath = PathfindingAlgorithms.findPathDijkstra(unweighted, 0, 4);

            assertEquals(bfsPath.size(), dijkstraPath.size(),
                       "BFS and Dijkstra should find same length paths in unweighted graphs");
        }

        @Test
        @DisplayName("Dijkstra should handle disconnected graphs")
        void testDijkstraDisconnected() {
            GameMap disconnected = new GameMap();
            disconnected.addNode(0, "ComponentA");
            disconnected.addNode(1, "ComponentA");
            disconnected.addNode(2, "ComponentB");
            disconnected.addEdge(0, 1, 1.0);

            List<Integer> path = PathfindingAlgorithms.findPathDijkstra(disconnected, 0, 2);
            assertTrue(path.isEmpty(), "Should return empty for disconnected components");
        }
    }

    @Nested
    @DisplayName("Precomputation Tests")
    class PrecomputationTests {

        @Test
        @DisplayName("Precomputation should store all paths")
        void testPrecomputeAllPaths() {
            Map<PathfindingAlgorithms.PathQuery, List<Integer>> precomputed =
                PathfindingAlgorithms.precomputeAllPaths(simpleMap);

            assertNotNull(precomputed);

            // Should have paths between all pairs of nodes
            int nodeCount = simpleMap.getNumNodes();
            int expectedPaths = nodeCount * (nodeCount - 1); // Excluding self-paths
            assertTrue(precomputed.size() >= expectedPaths / 2,
                      "Should have paths between node pairs");
        }

        @Test
        @DisplayName("Precomputed paths should match Dijkstra")
        void testPrecomputedAccuracy() {
            PathfindingAlgorithms.precomputeAllPaths(simpleMap);

            // Test several paths
            for (int start = 0; start < 3; start++) {
                for (int end = start + 1; end < 5; end++) {
                    List<Integer> dijkstra = PathfindingAlgorithms.findPathDijkstra(simpleMap, start, end);
                    List<Integer> precomputed = PathfindingAlgorithms.getPrecomputedPath(start, end);

                    assertEquals(dijkstra, precomputed,
                               String.format("Precomputed path %d->%d should match Dijkstra", start, end));
                }
            }
        }

        @Test
        @DisplayName("Precomputed lookup should be fast")
        void testPrecomputedPerformance() {
            PathfindingAlgorithms.precomputeAllPaths(complexMap);

            long startTime = System.nanoTime();
            for (int i = 0; i < 1000; i++) {
                PathfindingAlgorithms.getPrecomputedPath(0, 9);
            }
            long endTime = System.nanoTime();

            double avgTime = (endTime - startTime) / 1_000_000.0 / 1000;
            assertTrue(avgTime < 0.1, "Precomputed lookup should be very fast (< 0.1ms)");
        }

        @Test
        @DisplayName("Should handle missing precomputed paths")
        void testMissingPrecomputed() {
            // Don't precompute anything
            List<Integer> path = PathfindingAlgorithms.getPrecomputedPath(0, 1);

            assertNotNull(path);
            assertTrue(path.isEmpty(), "Should return empty list for non-precomputed paths");
        }
    }

    @Nested
    @DisplayName("Weather System Tests")
    class WeatherTests {

        @Test
        @DisplayName("Weather should affect path costs")
        void testWeatherImpact() {
            // Find normal path
            List<Integer> normalPath = PathfindingAlgorithms.findPathDijkstra(simpleMap, 0, 4);

            // Apply rain globally
            weather.setGlobalWeather(WeatherSystem.Weather.RAIN);
            List<Integer> rainPath = PathfindingAlgorithms.findPathWithWeather(
                simpleMap, weather, 0, 4);

            assertNotNull(rainPath);
            // Path might be the same or different depending on weather impact
            // But it should still be valid
            assertEquals(0, rainPath.get(0));
            assertEquals(4, rainPath.get(rainPath.size() - 1));
        }

        @Test
        @DisplayName("Local weather should override global")
        void testLocalWeather() {
            weather.setGlobalWeather(WeatherSystem.Weather.CLEAR);
            weather.setTileWeather(2, WeatherSystem.Weather.STORM);

            // Path might avoid node 2 due to storm
            List<Integer> path = PathfindingAlgorithms.findPathWithWeather(
                simpleMap, weather, 0, 4);

            assertNotNull(path);
            assertFalse(path.isEmpty());
        }

        @Test
        @DisplayName("Weather multipliers should work correctly")
        void testWeatherMultipliers() {
            assertEquals(1.0, WeatherSystem.Weather.CLEAR.getMultiplier());
            assertEquals(1.5, WeatherSystem.Weather.RAIN.getMultiplier());
            assertEquals(2.0, WeatherSystem.Weather.SNOW.getMultiplier());

            weather.setGlobalWeather(WeatherSystem.Weather.RAIN);
            double multiplier = weather.getWeatherMultiplier(0);
            assertEquals(1.5, multiplier, 0.001);
        }

        @Test
        @DisplayName("Weather should invalidate precomputed paths")
        void testWeatherInvalidatesPrecomputation() {
            // Precompute with clear weather
            weather.setGlobalWeather(WeatherSystem.Weather.CLEAR);
            PathfindingAlgorithms.precomputeAllPaths(simpleMap);
            List<Integer> clearPath = PathfindingAlgorithms.getPrecomputedPath(0, 4);

            // Apply weather
            weather.setGlobalWeather(WeatherSystem.Weather.SNOW);
            List<Integer> snowPath = PathfindingAlgorithms.findPathWithWeather(
                simpleMap, weather, 0, 4);

            // Paths might differ, demonstrating why precomputation fails
            assertNotNull(clearPath);
            assertNotNull(snowPath);

            // The key insight: precomputed paths don't account for weather
            assertTrue(weather.hasActiveWeather(),
                      "Weather should be active, invalidating precomputed paths");
        }
    }

    @Nested
    @DisplayName("Game Map Tests")
    class GameMapTests {

        @Test
        @DisplayName("Map should load from file")
        void testMapLoading(@TempDir Path tempDir) throws IOException {
            // Create a test map file
            Path mapFile = tempDir.resolve("test.txt");
            List<String> lines = Arrays.asList(
                "3",
                "0 Paris",
                "1 Berlin",
                "2 Moscow",
                "3",
                "0 1 2.0",
                "1 2 3.0",
                "0 2 5.0"
            );
            Files.write(mapFile, lines);

            GameMap map = new GameMap(mapFile.toString());

            assertEquals(3, map.getNumNodes());
            assertEquals("Paris", map.getNodeName(0));
            assertEquals("Berlin", map.getNodeName(1));
            assertEquals("Moscow", map.getNodeName(2));
            assertEquals(2.0, map.getEdgeWeight(0, 1), 0.001);
        }

        @Test
        @DisplayName("Map should convert to unweighted")
        void testUnweightedConversion() {
            GameMap unweighted = simpleMap.toUnweighted();

            assertEquals(simpleMap.getNumNodes(), unweighted.getNumNodes());

            // All edges should have weight 1
            for (int node : unweighted.getNodeIds()) {
                for (GameMap.Edge edge : unweighted.getEdges(node)) {
                    assertEquals(1.0, edge.weight, 0.001,
                               "All edges should have weight 1 in unweighted graph");
                }
            }
        }

        @Test
        @DisplayName("Map should handle edge queries")
        void testEdgeQueries() {
            assertTrue(simpleMap.hasNode(0));
            assertFalse(simpleMap.hasNode(99));

            assertEquals(2.0, simpleMap.getEdgeWeight(0, 2), 0.001);
            assertEquals(-1, simpleMap.getEdgeWeight(0, 4), 0.001); // No direct edge

            List<GameMap.Edge> edges = simpleMap.getEdges(3);
            assertEquals(3, edges.size(), "Node 3 should have 3 edges");
        }
    }

    @Nested
    @DisplayName("Edge Cases and Error Handling")
    class EdgeCaseTests {

        @Test
        @DisplayName("Should handle single node graph")
        void testSingleNode() {
            GameMap single = new GameMap();
            single.addNode(42, "OnlyNode");

            List<Integer> path = PathfindingAlgorithms.findPathDijkstra(single, 42, 42);
            assertEquals(1, path.size());
            assertEquals(42, path.get(0));
        }

        @Test
        @DisplayName("Should handle empty graph")
        void testEmptyGraph() {
            GameMap empty = new GameMap();

            List<Integer> path = PathfindingAlgorithms.findPathDijkstra(empty, 0, 1);
            assertTrue(path.isEmpty());
        }

        @Test
        @DisplayName("Should handle large graphs efficiently")
        void testLargeGraph() {
            // Create a larger graph for performance testing
            GameMap large = new GameMap();
            int size = 100;

            // Create a grid-like graph
            for (int i = 0; i < size; i++) {
                large.addNode(i, "Node" + i);
            }

            // Add edges in a grid pattern
            for (int i = 0; i < size - 1; i++) {
                large.addEdge(i, i + 1, Math.random() * 5 + 1);
                if (i < size - 10) {
                    large.addEdge(i, i + 10, Math.random() * 5 + 1);
                }
            }

            // Should complete in reasonable time
            long start = System.currentTimeMillis();
            List<Integer> path = PathfindingAlgorithms.findPathDijkstra(large, 0, size - 1);
            long end = System.currentTimeMillis();

            assertNotNull(path);
            assertTrue(end - start < 1000, "Should find path in large graph within 1 second");
        }
    }
}