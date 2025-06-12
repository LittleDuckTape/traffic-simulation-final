import java.util.*;

/**
 * Runs simulation tests for different modes and input sizes
 * Measures and prints runtime and complexities
 */
public class Benchmark {
    public static void runTest(String mode, int vehicleCount, Graph graph) {
        SimulationMode sim = new SimulationMode(mode);
        Random r = new Random();
        List<String> nodes = new ArrayList<>(graph.getNodes());

        // Generate test vehicles with 10% being emergency vehicles
        for (int i = 0; i < vehicleCount; i++) {
            // Pick random start and end node
            String start = nodes.get(r.nextInt(nodes.size()));
            String end = nodes.get(r.nextInt(nodes.size()));
            while (end.equals(start)) {
                end = nodes.get(r.nextInt(nodes.size()));
            }

            List<String> path = AStar.findPath(graph, start, end);
            if (path.isEmpty()) {
                i--;
                continue;
            }

            String direction = path.size() > 1 ? path.get(1) : end;

            Vehicle v = (i % 10 == 0)
                    ? new EmergencyVehicle("EV" + i, direction, path)
                    : new Vehicle("V" + i, direction, 2, path);

            sim.add(v);
        }

        System.out.println("===== MODE: " + mode.toUpperCase() + " =====");
        long start = System.nanoTime();

        // Processes all vehicles in the current node
        while (!sim.isEmpty()) {
            Vehicle v = sim.process();
            System.out.printf("Processed: %s via %s → %s (Priority: %d)%n",
                    v.getId(),
                    v.getPath().get(0),
                    v.getPath().get(v.getPath().size() - 1),
                    v.getPriority());
        }

        long end = System.nanoTime();
        double seconds = (end - start) / 1e9;
        System.out.printf("Mode: %s | Input: %d | Runtime: %.5f s%n", mode, vehicleCount, seconds);

        // Print theoretical time & space complexity
        switch (mode) {
            case "queue" -> {
                System.out.println("Time Complexity: O(1) enqueue, O(1) dequeue");
                System.out.println("Space Complexity: O(n)");
            }
            case "stack" -> {
                System.out.println("Time Complexity: O(1) push, O(1) pop");
                System.out.println("Space Complexity: O(n)");
            }
            case "priority" -> {
                System.out.println("Time Complexity: O(log n) insert, O(log n) remove");
                System.out.println("Space Complexity: O(n)");
            }
        }
        System.out.println(); // For spacing
    }
}
