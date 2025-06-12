import java.util.*;

/**
 * SimulationRunner acts as the backend logic engine.
 * It handles:
 * - Graph creation
 * - Vehicle generation with A* pathfinding
 * - Processing vehicles through a data structure mode
 * - Returning a formatted result for display (used by GUI)
 */
public class SimulationRunner {
    public static String runSimulation(String mode, int vehicleCount) {
        StringBuilder output = new StringBuilder();

        // Ensures valid mode
        if (!Set.of("queue", "stack", "priority").contains(mode)) {
            throw new IllegalArgumentException("Invalid simulation mode: " + mode);
        }

        // Create the selected data structure mode (queue/stack/priority)
        SimulationMode sim = new SimulationMode(mode);

        // Create a simple road map
        Graph graph = new Graph();
        graph.addNode("A"); graph.addNode("B"); graph.addNode("C"); graph.addNode("D");
        graph.addEdge("A", "B", 2);
        graph.addEdge("A", "C", 4);
        graph.addEdge("B", "C", 1);
        graph.addEdge("C", "D", 1);
        graph.addEdge("B", "D", 5);

        // Generate vehicles randomly with A* paths
        Random r = new Random();
        List<String> nodes = new ArrayList<>(graph.getNodes());

        for (int i = 0; i < vehicleCount; i++) {
            String start = nodes.get(r.nextInt(nodes.size()));
            String end = nodes.get(r.nextInt(nodes.size()));
            while (end.equals(start)) {
                end = nodes.get(r.nextInt(nodes.size()));
            }

            List<String> path = AStar.findPath(graph, start, end);
            if (path.isEmpty()) { i--; continue; } // skip if no path

            String direction = path.size() > 1 ? path.get(1) : end;

            // Every 10th vehicle is an emergency
            Vehicle v = (i % 10 == 0)
                    ? new EmergencyVehicle("EV" + i, direction, path)
                    : new Vehicle("V" + i, direction, 2, path);

            sim.add(v);
        }

        // Run simulation and measure time
        output.append("===== MODE: ").append(mode.toUpperCase()).append(" =====\n");
        long startTime = System.nanoTime();

        while (!sim.isEmpty()) {
            Vehicle v = sim.process();
            output.append(String.format("Processed: %s via %s → %s (Priority: %d)\n",
                    v.getId(),
                    v.getPath().get(0),
                    v.getPath().get(v.getPath().size() - 1),
                    v.getPriority()));
        }

        long endTime = System.nanoTime();
        double runtime = (endTime - startTime) / 1e9;

        // Output runtime
        output.append("\n-------------------------\n");
        output.append(String.format("Mode: %s | Input: %d | Runtime: %.5f s\n\n", mode, vehicleCount, runtime));

        // Output complexity explanation
        switch (mode) {
            case "queue" -> output.append("Time Complexity: O(1) enqueue, O(1) dequeue\nSpace Complexity: O(n)\n");
            case "stack" -> output.append("Time Complexity: O(1) push, O(1) pop\nSpace Complexity: O(n)\n");
            case "priority" -> output.append("Time Complexity: O(log n) insert, O(log n) remove\nSpace Complexity: O(n)\n");
        }

        return output.toString();
    }
}
