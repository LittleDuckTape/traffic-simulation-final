import java.util.*;

/**
 * Controls how vehicles are processed at an intersection
 * Supports three (3) modes:
 * - Queue (FIFO)
 * - Stack (LIFO)
 * - Priority Queue (emergency vehicles first)
 */
public class SimulationMode {
    Deque<Vehicle> queue;
    Stack<Vehicle> stack;
    PriorityQueue<Vehicle> pqueue;
    String mode;

    /**
     * Initializes the appropriate data structure based on mode
     */
    public SimulationMode(String mode) {
        this.mode = mode;
        switch (mode) {
            case "queue" -> queue = new ArrayDeque<>();
            case "stack" -> stack = new Stack<>();
            case "priority" -> pqueue = new PriorityQueue<>(Comparator.comparingInt(Vehicle::getPriority));
        }
    }

    /**
     * Adds a vehicle to the data structure
     */
    public void add(Vehicle v) {
        switch (mode) {
            case "queue" -> queue.add(v);
            case "stack" -> stack.push(v);
            case "priority" -> pqueue.add(v);
        }
    }

    /**
     * Processes and removes a vehicle from the structure
     */
    public Vehicle process() {
        return switch (mode) {
            case "queue" -> queue.poll();
            case "stack" -> stack.isEmpty() ? null : stack.pop();
            case "priority" -> pqueue.poll();
            default -> null;
        };
    }

    /**
     * Checks whether the structure is empty.
     */
    public boolean isEmpty() {
        return switch (mode) {
            case "queue" -> queue.isEmpty();
            case "stack" -> stack.isEmpty();
            case "priority" -> pqueue.isEmpty();
            default -> true;
        };
    }
}
