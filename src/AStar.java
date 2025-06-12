import java.util.*;

/**
 * A* Algorithm to find the shortest path from a start to goal node in the graph
 * Uses a heuristic (dummy hash-based) for estimated distance
 */
public class AStar {
    public static List<String> findPath(Graph graph, String start, String goal) {
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        Map<String, Integer> gScore = new HashMap<>();
        Map<String, Integer> fScore = new HashMap<>();
        Map<String, String> cameFrom = new HashMap<>();

        for (String node : graph.getNodes()) {
            gScore.put(node, Integer.MAX_VALUE);
            fScore.put(node, Integer.MAX_VALUE);
        }

        gScore.put(start, 0);
        fScore.put(start, heuristic(start, goal));
        openSet.add(new Node(start, 0, heuristic(start, goal)));

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            if (current.name.equals(goal)) {
                return reconstructPath(cameFrom, current.name);
            }

            for (Edge edge : graph.getNeighbors(current.name)) {
                int tentative_g = gScore.get(current.name) + edge.getWeight();
                if (tentative_g < gScore.get(edge.getDestination())) {
                    cameFrom.put(edge.getDestination(), current.name);
                    gScore.put(edge.getDestination(), tentative_g);
                    fScore.put(edge.getDestination(), tentative_g + heuristic(edge.getDestination(), goal));
                    openSet.add(new Node(edge.getDestination(), tentative_g, heuristic(edge.getDestination(), goal)));
                }
            }
        }

        return List.of(); // no path found
    }

    private static int heuristic(String a, String b) {
        return Math.abs(a.hashCode() - b.hashCode()) % 10;
    }

    private static List<String> reconstructPath(Map<String, String> cameFrom, String current) {
        LinkedList<String> path = new LinkedList<>();
        path.addFirst(current);
        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            path.addFirst(current);
        }
        return path;
    }
}
