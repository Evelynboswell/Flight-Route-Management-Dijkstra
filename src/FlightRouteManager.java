import java.util.List;
import java.util.Scanner;

public class FlightRouteManager {
    private AirportGraph airportGraph;
    private DijkstraAlgorithm dijkstraAlgorithm;

    public FlightRouteManager(AirportGraph airportGraph) {
        this.airportGraph = airportGraph;
        this.dijkstraAlgorithm = new DijkstraAlgorithm(airportGraph);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the departure airport code: ");
        String departureAirport = scanner.nextLine().toUpperCase();

        System.out.print("Enter the arrival airport code: ");
        String arrivalAirport = scanner.nextLine().toUpperCase();

        List<AirportGraph.Route> shortestRoute = dijkstraAlgorithm.findShortestRoute(departureAirport, arrivalAirport);

        if (shortestRoute.isEmpty()) {
            System.out.println("No route found between " + departureAirport + " and " + arrivalAirport);
        } else {
            displayRoute(shortestRoute, departureAirport, arrivalAirport);
        }
    }

    private void displayRoute(List<AirportGraph.Route> route, String departureAirport, String arrivalAirport) {
        int totalDistance = 0;
        int totalFuelCost = 0;

        System.out.println("Route:");

        StringBuilder routeOutput = new StringBuilder();
        for (AirportGraph.Route segment : route) {
            String segmentOutput = segment.getDeparture() + " - " + segment.getArrival();
            routeOutput.append(segmentOutput).append(" -> ");

            totalDistance += segment.getDistance();
            totalFuelCost += segment.getDistance() * segment.getFuelPrice();
        }

        if (routeOutput.length() > 0) {
            // Remove the trailing " -> "
            routeOutput.setLength(routeOutput.length() - 4);
            System.out.println(routeOutput.toString());
        }

        System.out.println("Total distance: " + totalDistance + " km");
        System.out.println("Total fuel cost: IDR " + totalFuelCost);
    }

    public static void main(String[] args) {
        AirportGraph airportGraph = new AirportGraph();

        // Add routes to the airport graph
        airportGraph.addRoute("CGK", "JOG", 481, 6575);
        airportGraph.addRoute("CGK", "SUB", 694, 6521);
        airportGraph.addRoute("CGK", "UPG", 1262, 6625);
        airportGraph.addRoute("CGK", "BPN", 917, 6536);
        airportGraph.addRoute("CGK", "BTH", 860, 6500);
        airportGraph.addRoute("CGK", "PKY", 917, 6536);
        airportGraph.addRoute("CGK", "PKU", 924, 6528);

        airportGraph.addRoute("JOG", "DPS", 407, 6599);
        airportGraph.addRoute("JOG", "BPN", 872, 6532);
        airportGraph.addRoute("JOG", "PKY", 1031, 6531);

        airportGraph.addRoute("SUB", "DPS", 1023, 6543);
        airportGraph.addRoute("SUB", "BPN", 812, 6527);
        airportGraph.addRoute("SUB", "PKY", 1031, 6531);
        airportGraph.addRoute("SUB", "BTH", 461, 6511);

        airportGraph.addRoute("UPG", "BPN", 791, 6505);
        airportGraph.addRoute("UPG", "DPS", 632, 6556);
        airportGraph.addRoute("BPN", "SUB", 812, 6527);

        airportGraph.addRoute("YIA", "DPS", 837, 6544);
        airportGraph.addRoute("YIA", "KNO", 2464, 6556);
        airportGraph.addRoute("YIA", "UPG", 1202, 6599);

        airportGraph.addRoute("PKU", "KNO", 1018, 6532);

        FlightRouteManager flightRouteManager = new FlightRouteManager(airportGraph);
        flightRouteManager.run();
    }
}
