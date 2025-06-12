import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Create a basic graph representing the road network
        Graph graph = new Graph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");

        graph.addEdge("A", "B", 2);
        graph.addEdge("A", "C", 4);
        graph.addEdge("B", "C", 1);
        graph.addEdge("C", "D", 1);
        graph.addEdge("B", "D", 5);

        // Run benchmark using each data structure mode
        int[] sizes = {100, 500, 1000};
        for (int size : sizes) {
            Benchmark.runTest("queue", size, graph);
            Benchmark.runTest("stack", size, graph);
            Benchmark.runTest("priority", size, graph);
            System.out.println("----------------------");
        }
    }
}
