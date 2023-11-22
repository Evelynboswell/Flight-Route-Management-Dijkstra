import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;

public class Graph {
    // Atribut graph
    private int maxVertex;
    private Vertex[] vertexList;
    private int[][] adjacencyMatrix;
    private int countVertex = 0;
    private ArrayList<Edge> edges;
    
    private ArrayList<String> resultDepartureArrival;
    private ArrayList<String> resultPath;
    private int totalDistance;
    private int totalFuelCost;
    
    
    // Constructor
    public Graph(int maxVertex) {
        this.maxVertex = maxVertex;
        vertexList = new Vertex[maxVertex];
        adjacencyMatrix = new int [maxVertex][maxVertex];
        edges = new ArrayList<>();
        for(int i = 0; i<vertexList.length; i++) {
            for(int j = 0; j < vertexList.length; j++) {
                adjacencyMatrix[i][j] = 0;
            }
        }
    }
    // Method untuk menambahkan vertex/simpul
    public void addVertex(String label) {
        if (countVertex < maxVertex) {
            vertexList[countVertex] = new Vertex(label);
            countVertex++;
        } else {
            System.out.println("Melebihi limit vertex.");
        }
    }
    // Method untuk menambahkan edge dan bobotnya
    public void addEdge(int start, int end, int weight) {
        adjacencyMatrix[start][end] = weight;
        adjacencyMatrix[end][start] = weight;
    }
    // Method untuk menambahkan edge dan bobotnya
    public void addEdge(String startLabel, String endLabel, int weight, int fuelCost) {
        int start = indexVertex(startLabel);
        int end = indexVertex(endLabel);
        if (start != -1 && end != -1) {
            adjacencyMatrix[start][end] = weight;
            adjacencyMatrix[end][start] = weight;

            Edge edge = new Edge(start, end, weight, fuelCost);
            edges.add(edge);
        }
    }

    // Method untuk mencari index dari vertex
    private int indexVertex(String label) {
        for (int i = 0; i < countVertex; i++) {
            if (vertexList[i].getLabel().equals(label)) {
                return i;
            }
        }
        return -1;
    }
    // Method untuk menampilkan Adjacency Matrix
    @Override
    public String toString() {
        System.out.println("");
        System.out.println("Matrix Adjacency");
        System.out.println("---------------------------------");
        System.out.print("    ");
        for(int i = 0; i < countVertex; i++) {
            System.out.printf(" %-2S", (vertexList[i].getLabel()));
        }
        System.out.println("");
        for(int i = 0; i < countVertex; i++) {
            System.out.printf(" %-4S", (vertexList[i].getLabel())+" ");
            for(int j = 0; j < countVertex; ++j) {
                System.out.printf("%-3S", adjacencyMatrix[i][j]);
            }
            System.out.println("");
        }
        return "";
    }
    // Method for Dijkstra's Algorithm
    public void dijkstraShortestPath(String startLabel, String destinationLabel) {
        int startIndex = indexVertex(startLabel);
        int destinationIndex = indexVertex(destinationLabel);

        if (startIndex == -1 || destinationIndex == -1) {
            System.out.println("Vertex Tidak Ditemukan: " + (startIndex == -1 ? startLabel : destinationLabel));
            return;
        }

        int[] distance = new int[countVertex];
        boolean[] visited = new boolean[countVertex];
        int[] predecessor = new int[countVertex];

        // Initialize distances with infinity and mark all vertices as unvisited
        initializeDijkstraArrays(distance, visited, predecessor);

        // Distance from the start vertex to itself is 0
        distance[startIndex] = 0;

        for (int i = 0; i < countVertex - 1; i++) {
            int currentVertex = findMinDistanceVertex(distance, visited);
            visited[currentVertex] = true;

            for (int j = 0; j < countVertex; j++) {
                if (!visited[j] && adjacencyMatrix[currentVertex][j] != 0 &&
                        distance[currentVertex] != Integer.MAX_VALUE &&
                        distance[currentVertex] + adjacencyMatrix[currentVertex][j] < distance[j]) {
                    distance[j] = distance[currentVertex] + adjacencyMatrix[currentVertex][j];
                    predecessor[j] = currentVertex;
                }
            }
        }

        // Build the path
        LinkedList<String> path = buildPath(predecessor, startIndex, destinationIndex);
        
        // Store the result
        resultDepartureArrival = new ArrayList<>();
        resultDepartureArrival.add(vertexList[startIndex].getLabel());
        resultDepartureArrival.add(vertexList[destinationIndex].getLabel());

        resultPath = new ArrayList<>();
        resultPath.addAll(path);

        totalDistance = distance[destinationIndex];
        totalFuelCost = calculateTotalFuelCost(path);
        
        // Print the result with total cost
        System.out.println("Rute terpendek dari " + startLabel + " ke " + destinationLabel + "\t: " + path);
        System.out.println("Total Jarak\t\t\t: " + totalDistance);
        System.out.println("Total Fuel cost\t\t\t: " + totalFuelCost);   
    }

    private void initializeDijkstraArrays(int[] distance, boolean[] visited, int[] predecessor) {
        Arrays.fill(distance, Integer.MAX_VALUE);
        Arrays.fill(visited, false);
    }

    private LinkedList<String> buildPath(int[] predecessor, int startIndex, int destinationIndex) {
        LinkedList<String> path = new LinkedList<>();
        int current = destinationIndex;

        while (current != startIndex) {
            path.addFirst(vertexList[current].getLabel());
            current = predecessor[current];
        }

        path.addFirst(vertexList[startIndex].getLabel());
        return path;
    }

    private int calculateTotalFuelCost(LinkedList<String> path) {
        int totalFuelCost = 0;

        for (int i = 0; i < path.size() - 1; i++) {
            int start = indexVertex(path.get(i));
            int end = indexVertex(path.get(i + 1));

            for (Edge edge : edges) {
                if ((edge.getVertexA() == start && edge.getVertexB() == end) ||
                    (edge.getVertexA() == end && edge.getVertexB() == start)) {
                    totalFuelCost += edge.getFuelCost();
                    break;
                }
            }
        }

        return totalFuelCost;
    }

    private int findMinDistanceVertex(int[] distance, boolean[] visited) {
        int minDistance = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int i = 0; i < countVertex; i++) {
            if (!visited[i] && distance[i] < minDistance) {
                minDistance = distance[i];
                minIndex = i;
            }
        }

        return minIndex;
    }
    public ArrayList<Edge> getEdges() {
        return edges;
    }
    public Vertex[] getVertexList() {
        return vertexList;
    }
    public ArrayList<String> getResultDepartureArrival() {
        return resultDepartureArrival;
    }

    public ArrayList<String> getResultPath() {
        return resultPath;
    }

    public int getTotalDistance() {
        return totalDistance;
    }

    public int getTotalFuelCost() {
        return totalFuelCost;
    }
}