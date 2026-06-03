import java.util.*;

class DijkstraShortestPath {

    static class Pair {
        int vertex;
        int distance;

        Pair(int vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }
    }

    static void dijkstra(int vertices, ArrayList<ArrayList<Pair>> graph, int source) {

        int[] dist = new int[vertices];

        // Initially all distances are infinity
        Arrays.fill(dist, Integer.MAX_VALUE);

        // Distance from source to itself is 0
        dist[source] = 0;

        PriorityQueue<Pair> pq = new PriorityQueue<>(
            (a, b) -> a.distance - b.distance
        );

        pq.add(new Pair(source, 0));

        while (!pq.isEmpty()) {

            Pair current = pq.poll();

            int currentVertex = current.vertex;
            int currentDistance = current.distance;

            // Skip if we already found a better distance
            if (currentDistance > dist[currentVertex]) {
                continue;
            }

            for (Pair neighbor : graph.get(currentVertex)) {

                int nextVertex = neighbor.vertex;
                int edgeWeight = neighbor.distance;

                if (dist[currentVertex] + edgeWeight < dist[nextVertex]) {
                    dist[nextVertex] = dist[currentVertex] + edgeWeight;
                    pq.add(new Pair(nextVertex, dist[nextVertex]));
                }
            }
        }

        System.out.println("Shortest distance from source " + source + ":");

        for (int i = 0; i < vertices; i++) {
            System.out.println("To vertex " + i + " = " + dist[i]);
        }
    }

    static void addEdge(ArrayList<ArrayList<Pair>> graph, int u, int v, int weight) {
        graph.get(u).add(new Pair(v, weight));
        graph.get(v).add(new Pair(u, weight)); 
        // Remove this line if graph is directed
    }

    public static void main(String[] args) {

        int vertices = 5;

        ArrayList<ArrayList<Pair>> graph = new ArrayList<>();

        for (int i = 0; i < vertices; i++) {
            graph.add(new ArrayList<>());
        }

        addEdge(graph, 0, 1, 2);
        addEdge(graph, 0, 2, 4);
        addEdge(graph, 1, 2, 1);
        addEdge(graph, 1, 3, 7);
        addEdge(graph, 2, 4, 3);
        addEdge(graph, 3, 4, 1);

        int source = 0;

        dijkstra(vertices, graph, source);
    }
}