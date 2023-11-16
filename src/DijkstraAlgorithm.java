import java.util.*;

public class DijkstraAlgorithm {
    private AirportGraph airportGraph;

    public DijkstraAlgorithm(AirportGraph airportGraph) {
        this.airportGraph = airportGraph;
    }

    public List<AirportGraph.Route> findShortestRoute(String startAirport, String endAirport) {
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        PriorityQueue<String> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        distances.put(startAirport, 0);
        priorityQueue.add(startAirport);

        while (!priorityQueue.isEmpty()) {
            String currentAirport = priorityQueue.poll();

            for (Map.Entry<String, AirportGraph.Route> entry : airportGraph.getGraph().get(currentAirport).entrySet()) {
                String neighbor = entry.getKey();
                int newDistance = distances.get(currentAirport) + entry.getValue().getDistance();

                if (newDistance < distances.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    distances.put(neighbor, newDistance);
                    previous.put(neighbor, currentAirport);
                    priorityQueue.add(neighbor);
                }
            }
        }

        return reconstructPath(startAirport, endAirport, previous);
    }

    private List<AirportGraph.Route> reconstructPath(String startAirport, String endAirport, Map<String, String> previous) {
        List<AirportGraph.Route> path = new ArrayList<>();
        String current = endAirport;

        while (previous.containsKey(current)) {
            String predecessor = previous.get(current);
            path.add(airportGraph.getGraph().get(predecessor).get(current));
            current = predecessor;
        }

        Collections.reverse(path);
        return path;
    }
}
