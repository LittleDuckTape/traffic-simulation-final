import java.util.*;

/**
 * Graph represents a road network
 * - Each node is an intersection
 * - Each edge represents a road with a weight (distance, delay)
 */
public class Graph {
    private final Map<String, List<Edge>> adj = new HashMap<>();

    public void addNode(String node) {
        adj.putIfAbsent(node, new ArrayList<>());
    }

    public void addEdge(String from, String to, int weight) {
        adj.get(from).add(new Edge(to, weight));
    }

    public List<Edge> getNeighbors(String node) {
        return adj.getOrDefault(node, new ArrayList<>());
    }

    public Set<String> getNodes() {
        return adj.keySet();
    }
}
