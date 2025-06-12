/**
 * Node class used in A* pathfinding
 * Implements Comparable, so it can be sorted by estimated cost (f = g + h)
 */
public class Node implements Comparable<Node> {
    public String name;
    public int g, h;

    public Node(String name, int g, int h) {
        this.name = name;
        this.g = g;
        this.h = h;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.g + this.h, other.g + other.h);
    }
}
