import java.util.*;

public class AirportGraph {
    private Map<String, Map<String, Route>> graph;

    public AirportGraph() {
        this.graph = new HashMap<>();
    }

    public void addRoute(String departure, String arrival, int distance, int fuelPrice) {
        graph.computeIfAbsent(departure, k -> new HashMap<>());
        graph.computeIfAbsent(arrival, k -> new HashMap<>());

        Route route = new Route(departure, arrival, distance, fuelPrice);
        graph.get(departure).put(arrival, route);
        graph.get(arrival).put(departure, route);
    }

    public Map<String, Map<String, Route>> getGraph() {
        return graph;
    }

    public static class Route {
        private String departure;
        private String arrival;
        private int distance;
        private int fuelPrice;

        public Route(String departure, String arrival, int distance, int fuelPrice) {
            this.departure = departure;
            this.arrival = arrival;
            this.distance = distance;
            this.fuelPrice = fuelPrice;
        }

        public String getDeparture() {
            return departure;
        }

        public String getArrival() {
            return arrival;
        }

        public int getDistance() {
            return distance;
        }

        public int getFuelPrice() {
            return fuelPrice;
        }
    }
}
