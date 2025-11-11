import java.io.*;
import java.util.*;

/**
 * GameMap represents the game world as a weighted graph.
 * Nodes represent provinces/tiles, edges represent borders with movement costs.
 *
 * This class models the map structure used in grand strategy games where
 * provinces have varying sizes and terrain affects movement speed.
 *
 * @author CS366 Course Staff
 */
public class GameMap {
    private int numNodes;
    private Map<Integer, String> nodeNames;
    private Map<Integer, List<Edge>> adjacencyList;

    /**
     * Edge represents a connection between two provinces with a movement cost.
     */
    public static class Edge {
        public final int target;
        public final double weight;

        public Edge(int target, double weight) {
            this.target = target;
            this.weight = weight;
        }
    }

    /**
     * Creates an empty game map.
     */
    public GameMap() {
        this.nodeNames = new HashMap<>();
        this.adjacencyList = new HashMap<>();
        this.numNodes = 0;
    }

    /**
     * Creates a game map from a file.
     *
     * File format:
     * <num_nodes>
     * <node_id> <node_name>
     * ...
     * <num_edges>
     * <source_id> <target_id> <weight>
     * ...
     *
     * @param filename Path to the map file
     * @throws IOException if file cannot be read
     */
    public GameMap(String filename) throws IOException {
        this();
        loadFromFile(filename);
    }

    /**
     * Loads a map from a file.
     *
     * @param filename Path to the map file
     * @throws IOException if file cannot be read
     */
    public void loadFromFile(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            // Read number of nodes
            numNodes = Integer.parseInt(reader.readLine().trim());

            // Read node definitions
            for (int i = 0; i < numNodes; i++) {
                String[] parts = reader.readLine().trim().split(" ", 2);
                int nodeId = Integer.parseInt(parts[0]);
                String nodeName = parts.length > 1 ? parts[1] : "Node_" + nodeId;
                nodeNames.put(nodeId, nodeName);
                adjacencyList.put(nodeId, new ArrayList<>());
            }

            // Read number of edges
            int numEdges = Integer.parseInt(reader.readLine().trim());

            // Read edge definitions (undirected graph)
            for (int i = 0; i < numEdges; i++) {
                String[] parts = reader.readLine().trim().split(" ");
                int source = Integer.parseInt(parts[0]);
                int target = Integer.parseInt(parts[1]);
                double weight = Double.parseDouble(parts[2]);

                // Add edge in both directions for undirected graph
                adjacencyList.get(source).add(new Edge(target, weight));
                adjacencyList.get(target).add(new Edge(source, weight));
            }
        }
    }

    /**
     * Adds a node to the map.
     *
     * @param nodeId Unique identifier for the node
     * @param nodeName Name of the province/tile
     */
    public void addNode(int nodeId, String nodeName) {
        if (!nodeNames.containsKey(nodeId)) {
            nodeNames.put(nodeId, nodeName);
            adjacencyList.put(nodeId, new ArrayList<>());
            numNodes++;
        }
    }

    /**
     * Adds an undirected edge between two nodes.
     *
     * @param source Source node ID
     * @param target Target node ID
     * @param weight Movement cost for this edge
     */
    public void addEdge(int source, int target, double weight) {
        if (adjacencyList.containsKey(source) && adjacencyList.containsKey(target)) {
            adjacencyList.get(source).add(new Edge(target, weight));
            adjacencyList.get(target).add(new Edge(source, weight));
        }
    }

    /**
     * Gets the number of nodes in the map.
     *
     * @return Number of nodes
     */
    public int getNumNodes() {
        return numNodes;
    }

    /**
     * Gets all node IDs in the map.
     *
     * @return Set of all node IDs
     */
    public Set<Integer> getNodeIds() {
        return new HashSet<>(nodeNames.keySet());
    }

    /**
     * Gets the name of a node.
     *
     * @param nodeId Node ID
     * @return Node name, or null if node doesn't exist
     */
    public String getNodeName(int nodeId) {
        return nodeNames.get(nodeId);
    }

    /**
     * Gets all edges from a given node.
     *
     * @param nodeId Source node ID
     * @return List of edges from this node, or empty list if node doesn't exist
     */
    public List<Edge> getEdges(int nodeId) {
        return adjacencyList.getOrDefault(nodeId, new ArrayList<>());
    }

    /**
     * Checks if a node exists in the map.
     *
     * @param nodeId Node ID to check
     * @return true if node exists, false otherwise
     */
    public boolean hasNode(int nodeId) {
        return nodeNames.containsKey(nodeId);
    }

    /**
     * Creates an unweighted version of this map (all edge weights = 1).
     * Useful for testing BFS algorithm.
     *
     * @return New GameMap with all edge weights set to 1
     */
    public GameMap toUnweighted() {
        GameMap unweighted = new GameMap();

        // Copy nodes
        for (Map.Entry<Integer, String> entry : nodeNames.entrySet()) {
            unweighted.addNode(entry.getKey(), entry.getValue());
        }

        // Copy edges with weight = 1
        Set<String> addedEdges = new HashSet<>();
        for (Map.Entry<Integer, List<Edge>> entry : adjacencyList.entrySet()) {
            int source = entry.getKey();
            for (Edge edge : entry.getValue()) {
                String edgeKey = Math.min(source, edge.target) + "-" + Math.max(source, edge.target);
                if (!addedEdges.contains(edgeKey)) {
                    unweighted.addEdge(source, edge.target, 1.0);
                    addedEdges.add(edgeKey);
                }
            }
        }

        return unweighted;
    }

    /**
     * Gets the edge weight between two nodes.
     *
     * @param source Source node ID
     * @param target Target node ID
     * @return Edge weight, or -1 if no edge exists
     */
    public double getEdgeWeight(int source, int target) {
        if (!adjacencyList.containsKey(source)) {
            return -1;
        }

        for (Edge edge : adjacencyList.get(source)) {
            if (edge.target == target) {
                return edge.weight;
            }
        }

        return -1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("GameMap with ").append(numNodes).append(" nodes:\n");
        for (Map.Entry<Integer, String> entry : nodeNames.entrySet()) {
            int nodeId = entry.getKey();
            sb.append("  ").append(nodeId).append(" (").append(entry.getValue()).append(")");
            sb.append(" -> ");
            for (Edge edge : adjacencyList.get(nodeId)) {
                sb.append(edge.target).append("[").append(edge.weight).append("] ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}