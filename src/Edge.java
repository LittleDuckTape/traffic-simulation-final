/**
 * Represents a road between two intersections (nodes)
 * - Destination: where the road leads
 * - Weight: cost to travel (e.g., time or congestion)
 */
public class Edge {
    private final String destination;
    private final int weight;

    public Edge(String destination, int weight) {
        this.destination = destination;
        this.weight = weight;
    }

    public String getDestination() {
        return destination;
    }

    public int getWeight() {
        return weight;
    }
}
