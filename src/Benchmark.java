import java.util.*;

/**
 * Benchmark class for testing and comparing different data structures in CLI mode.
 * This runs the simulation with fixed input sizes and all 3 modes (queue, stack, priority).
 */
public class Benchmark {

    /**
     * Runs the test for a given data structure mode and input size.
     */
    public static void runTest(String mode, int vehicleCount) {
        SimulationMode sim = new SimulationMode(mode);
        Graph graph = new Graph();

        // Sample graph with 4 nodes
        graph.addNode("A"); graph.addNode("B"); graph.addNode("C"); graph.addNode("D");
        graph.addEdge("A", "B", 2); graph.addEdge("A", "C", 4);
        graph.addEdge("B", "C", 1); graph.addEdge("C", "D", 1); graph.addEdge("B", "D", 5);

        Random r = new Random();
        List<String> nodes = new ArrayList<>(graph.getNodes());

        for (int i = 0; i < vehicleCount; i++) {
            String start = nodes.get(r.nextInt(nodes.size()));
            String end = nodes.get(r.nextInt(nodes.size()));
            while (end.equals(start)) {
                end = nodes.get(r.nextInt(nodes.size()));
            }

            List<String> path = AStar.findPath(graph, start, end);
            if (path.isEmpty()) { i--; continue; }

            String direction = path.size() > 1 ? path.get(1) : end;

            Vehicle v = (i % 10 == 0)
                    ? new EmergencyVehicle("EV" + i, direction, path)
                    : new Vehicle("V" + i, direction, 2, path);

            sim.add(v);
        }

        System.out.println("===== MODE: " + mode.toUpperCase() + " =====");

        long start = System.nanoTime();
        while (!sim.isEmpty()) {
            Vehicle v = sim.process();
            System.out.printf("Processed: %s via %s → %s (Priority: %d)%n",
                    v.getId(), v.getPath().get(0), v.getPath().get(v.getPath().size() - 1), v.getPriority());
        }
        long end = System.nanoTime();
        double seconds = (end - start) / 1e9;

        System.out.printf("Mode: %s | Input: %d | Runtime: %.5f s%n", mode, vehicleCount, seconds);

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
        System.out.println();
    }

    /**
     * CLI entry point to run all modes with various input sizes.
     */
    public static void main(String[] args) {
        int[] sizes = {100, 500, 1000}; // test sizes

        for (int size : sizes) {
            runTest("queue", size);
            runTest("stack", size);
            runTest("priority", size);
            System.out.println("----------");
        }
    }
}
