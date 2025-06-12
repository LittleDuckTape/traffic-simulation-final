import java.util.List;

/**
 * Represents a single vehicle in the simulation
 * Each vehicle contains:
 * - ID (e.g., V1, EV10)
 * - Direction
 * - Priority: 1 = emergency, 2 = normal
 * - A* path (start to destination list of nodes)
 */
public class Vehicle {
    private final String id;
    private final String direction;
    private final int priority; // 1 = emergency, 2 = normal
    private final List<String> path; // Stores A* route

    public Vehicle(String id, String direction, int priority, List<String> path) {
        this.id = id;
        this.direction = direction;
        this.priority = priority;
        this.path = path;
    }

    public String getId() { return id; }
    public String getDirection() { return direction; }
    public int getPriority() { return priority; }
    public List<String> getPath() { return path; }
}
