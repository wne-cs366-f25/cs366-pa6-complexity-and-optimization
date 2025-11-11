import java.io.IOException;
import java.util.*;

/**
 * CS366 PA5 - Graph Algorithms
 * Implementation of pathfinding algorithms for strategic game maps.
 *
 * This class demonstrates how game developers optimize pathfinding
 * and the tradeoffs between different approaches.
 *
 * @author [Student Name]
 */
public class PathfindingAlgorithms {

    // Storage for precomputed paths
    private static Map<PathQuery, List<Integer>> precomputedPaths = new HashMap<>();

    /**
     * PathQuery represents a pathfinding query (start, target pair).
     */
    public static class PathQuery {
        public final int start;
        public final int target;

        public PathQuery(int start, int target) {
            this.start = start;
            this.target = target;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            PathQuery query = (PathQuery) obj;
            return start == query.start && target == query.target;
        }

        @Override
        public int hashCode() {
            return Objects.hash(start, target);
        }
    }

    /**
     * PathNode used for Dijkstra's algorithm priority queue.
     */
    private static class PathNode implements Comparable<PathNode> {
        public final int nodeId;
        public final double distance;

        public PathNode(int nodeId, double distance) {
            this.nodeId = nodeId;
            this.distance = distance;
        }

        @Override
        public int compareTo(PathNode other) {
            return Double.compare(this.distance, other.distance);
        }
    }

    /**
     * Method 1: Breadth-First Search for unweighted graphs.
     * Finds the shortest path in terms of number of edges.
     *
     * Time Complexity: O(V + E)
     * Space Complexity: O(V)
     *
     * @param map The game map (treated as unweighted)
     * @param start Starting node ID
     * @param target Target node ID
     * @return List of node IDs representing the path, or empty list if no path exists
     */
    public static List<Integer> findPathBFS(GameMap map, int start, int target) {
        // TODO: Implement BFS algorithm
        // 1. Check if start and target nodes exist
        // 2. Handle case where start equals target
        // 3. Use a queue for BFS traversal
        // 4. Track visited nodes and parent pointers
        // 5. Reconstruct path from parent pointers
        // 6. Return empty list if target is unreachable

        throw new UnsupportedOperationException("TODO: Implement BFS pathfinding");
    }

    /**
     * Method 2: Dijkstra's algorithm for weighted graphs.
     * Finds the shortest path considering edge weights.
     *
     * Time Complexity: O((V + E) log V) with binary heap
     * Space Complexity: O(V)
     *
     * @param map The game map with weighted edges
     * @param start Starting node ID
     * @param target Target node ID
     * @return List of node IDs representing the path, or empty list if no path exists
     */
    public static List<Integer> findPathDijkstra(GameMap map, int start, int target) {
        // TODO: Implement Dijkstra's algorithm
        // 1. Check if start and target nodes exist
        // 2. Handle case where start equals target
        // 3. Initialize distances to infinity (except start = 0)
        // 4. Use PriorityQueue<PathNode> for efficient minimum extraction
        // 5. Track visited nodes and parent pointers
        // 6. Relax edges and update distances
        // 7. Reconstruct path from parent pointers
        // 8. Return empty list if target is unreachable

        throw new UnsupportedOperationException("TODO: Implement Dijkstra pathfinding");
    }

    /**
     * Method 3: Precompute all shortest paths in the graph.
     * Runs Dijkstra from each node to build a complete lookup table.
     *
     * Time Complexity: O(V² log V + VE)
     * Space Complexity: O(V²)
     *
     * @param map The game map to precompute paths for
     * @return Map containing all precomputed paths
     */
    public static Map<PathQuery, List<Integer>> precomputeAllPaths(GameMap map) {
        // TODO: Implement path precomputation
        // 1. Clear any existing precomputed paths
        // 2. For each node as source:
        //    a. Run Dijkstra from that source
        //    b. Store path to every other node
        // 3. Store results in precomputedPaths map
        // 4. Return the complete map

        throw new UnsupportedOperationException("TODO: Implement path precomputation");
    }

    /**
     * Method 4: Retrieve a precomputed path.
     * O(1) lookup after precomputation.
     *
     * @param start Starting node ID
     * @param target Target node ID
     * @return Precomputed path, or empty list if not found
     */
    public static List<Integer> getPrecomputedPath(int start, int target) {
        // TODO: Look up path in precomputed table
        // Return empty list if not found

        throw new UnsupportedOperationException("TODO: Implement precomputed path retrieval");
    }

    /**
     * Method 5: Dijkstra with dynamic weather system.
     * Shows why precomputation fails with dynamic game state.
     *
     * @param map The game map
     * @param weather Weather system affecting movement
     * @param start Starting node ID
     * @param target Target node ID
     * @return List of node IDs representing the path
     */
    public static List<Integer> findPathWithWeather(GameMap map, WeatherSystem weather,
                                                   int start, int target) {
        // TODO: Implement weather-aware Dijkstra
        // Similar to regular Dijkstra, but:
        // 1. Apply weather multipliers to edge weights during traversal
        // 2. Use weather.applyWeatherToEdge() for each edge
        // 3. Cannot use precomputed paths (weather changes dynamically)

        throw new UnsupportedOperationException("TODO: Implement weather-aware pathfinding");
    }

    /**
     * Method 6: Compare all pathfinding approaches.
     * Demonstrates performance characteristics and tradeoffs.
     *
     * @param map The game map
     * @param queries List of path queries to test
     * @param weather Weather system for testing
     */
    public static void compareApproaches(GameMap map, List<PathQuery> queries,
                                        WeatherSystem weather) {
        System.out.println("=== Pathfinding Algorithm Comparison ===\n");

        // Convert to unweighted for BFS
        GameMap unweightedMap = map.toUnweighted();

        // Timing variables
        long startTime, endTime;
        double totalTime;

        // Test BFS on unweighted graph
        System.out.println("1. BFS (Unweighted Graph):");
        startTime = System.nanoTime();
        for (PathQuery query : queries) {
            List<Integer> path = findPathBFS(unweightedMap, query.start, query.target);
        }
        endTime = System.nanoTime();
        totalTime = (endTime - startTime) / 1_000_000.0;
        System.out.printf("   Total time: %.2f ms\n", totalTime);
        System.out.printf("   Average per query: %.3f ms\n\n", totalTime / queries.size());

        // Test Dijkstra on weighted graph
        System.out.println("2. Dijkstra (Weighted Graph):");
        startTime = System.nanoTime();
        for (PathQuery query : queries) {
            List<Integer> path = findPathDijkstra(map, query.start, query.target);
        }
        endTime = System.nanoTime();
        totalTime = (endTime - startTime) / 1_000_000.0;
        System.out.printf("   Total time: %.2f ms\n", totalTime);
        System.out.printf("   Average per query: %.3f ms\n\n", totalTime / queries.size());

        // Test precomputation
        System.out.println("3. Precomputed Paths:");
        startTime = System.nanoTime();
        Map<PathQuery, List<Integer>> precomputed = precomputeAllPaths(map);
        endTime = System.nanoTime();
        double precomputeTime = (endTime - startTime) / 1_000_000.0;
        System.out.printf("   Precomputation time: %.2f ms\n", precomputeTime);

        startTime = System.nanoTime();
        for (PathQuery query : queries) {
            List<Integer> path = getPrecomputedPath(query.start, query.target);
        }
        endTime = System.nanoTime();
        totalTime = (endTime - startTime) / 1_000_000.0;
        System.out.printf("   Query time: %.3f ms\n", totalTime);
        System.out.printf("   Average per query: %.3f µs\n", (totalTime * 1000) / queries.size());
        System.out.printf("   Break-even at: %d queries\n\n",
                         (int)(precomputeTime / (totalTime / queries.size())));

        // Test with weather
        System.out.println("4. Dijkstra with Weather:");
        weather.setGlobalWeather(WeatherSystem.Weather.RAIN);
        startTime = System.nanoTime();
        for (PathQuery query : queries) {
            List<Integer> path = findPathWithWeather(map, weather, query.start, query.target);
        }
        endTime = System.nanoTime();
        totalTime = (endTime - startTime) / 1_000_000.0;
        System.out.printf("   Total time: %.2f ms\n", totalTime);
        System.out.printf("   Average per query: %.3f ms\n", totalTime / queries.size());
        System.out.println("   Note: Precomputed paths INVALID with weather!\n");

        // Analysis
        System.out.println("=== Analysis ===");
        System.out.println("- BFS is fastest for unweighted graphs but ignores terrain");
        System.out.println("- Dijkstra handles weighted edges but slower per query");
        System.out.println("- Precomputation has high upfront cost but instant queries");
        System.out.println("- Weather makes precomputation useless - must recalculate");
        System.out.println("\nGame Design Insight:");
        System.out.println("This is why games like EU4 limit dynamic features - they break optimizations!");
    }

    /**
     * Helper method to reconstruct path from parent pointers.
     *
     * @param parents Map of node to parent node
     * @param start Starting node
     * @param target Target node
     * @return Path from start to target
     */
    private static List<Integer> reconstructPath(Map<Integer, Integer> parents,
                                                 int start, int target) {
        List<Integer> path = new ArrayList<>();

        // Build path backwards from target to start
        Integer current = target;
        while (current != null && current != start) {
            path.add(0, current);
            current = parents.get(current);
        }

        // Add start node if path exists
        if (current != null) {
            path.add(0, start);
        }

        return path;
    }

    /**
     * Main method for testing and demonstration.
     */
    public static void main(String[] args) {
        System.out.println("CS366 PA5 - Strategic Game Pathfinding\n");

        try {
            // Load test map
            String mapFile = "maps/test_simple.txt";
            System.out.println("Loading map from " + mapFile + "...");
            GameMap map = new GameMap(mapFile);
            System.out.println("Map loaded with " + map.getNumNodes() + " nodes\n");

            // Create test queries
            List<PathQuery> queries = new ArrayList<>();
            queries.add(new PathQuery(0, 5));
            queries.add(new PathQuery(1, 7));
            queries.add(new PathQuery(2, 9));
            queries.add(new PathQuery(3, 8));
            queries.add(new PathQuery(0, 9));

            // Create weather system
            WeatherSystem weather = new WeatherSystem();

            // Run comparison
            compareApproaches(map, queries, weather);

            // Demonstrate specific path
            System.out.println("\n=== Example Path ===");
            List<Integer> path = findPathDijkstra(map, 0, 9);
            System.out.print("Path from 0 to 9: ");
            for (int i = 0; i < path.size(); i++) {
                System.out.print(path.get(i));
                if (i < path.size() - 1) System.out.print(" -> ");
            }
            System.out.println();

        } catch (IOException e) {
            System.err.println("Error loading map: " + e.getMessage());
            System.err.println("Make sure to create the test map file first!");
        } catch (UnsupportedOperationException e) {
            System.err.println("\nImplementation incomplete: " + e.getMessage());
            System.err.println("Complete all TODO methods to see full comparison.");
        }
    }
}