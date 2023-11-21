import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner uwu = new Scanner(System.in);
        Graph airportGraph = new Graph(11);
        //Daftar semua airport
        airportGraph.addVertex("YIA");
        airportGraph.addVertex("KNO");
        airportGraph.addVertex("CGK");
        airportGraph.addVertex("DPS");
        airportGraph.addVertex("UPG");
        airportGraph.addVertex("SUB");
        airportGraph.addVertex("PKY");
        airportGraph.addVertex("BDJ");
        airportGraph.addVertex("BTH");
        airportGraph.addVertex("PNK");
        airportGraph.addVertex("MDC");

        //YIA
        airportGraph.addEdge("YIA", "CGK", 425, 15937500);
        airportGraph.addEdge("YIA", "KNO", 1785, 66937500);
        airportGraph.addEdge("YIA", "UPG", 1093, 40987500);
        airportGraph.addEdge("YIA", "DPS", 568, 21300000);

        //CGK
        airportGraph.addEdge("CGK", "KNO", 1421, 53287500);
        airportGraph.addEdge("CGK", "BTH", 853, 31987500);
        airportGraph.addEdge("CGK", "PKY", 916, 34350000);
        airportGraph.addEdge("CGK", "BDJ", 945, 35437500);
        airportGraph.addEdge("CGK", "DPS", 982, 36825000);
        airportGraph.addEdge("CGK", "SUB", 690, 25875000);

        //SUB
        airportGraph.addEdge("SUB", "PKY", 586, 21975000);
        airportGraph.addEdge("SUB", "BDJ", 488, 18300000);
        airportGraph.addEdge("SUB", "DPS", 303, 11362500);

        //DPS
        airportGraph.addEdge("DPS", "UPG", 634, 23775000);

        //BDJ
        airportGraph.addEdge("BDJ", "PNK", 689, 23837500);

        //UPG
        airportGraph.addEdge("UPG", "MDC", 946, 35475000);

        //User Input
        System.out.print("Masukkan Kode Bandara Asal\t: ");
        String bandaraAwal = uwu.nextLine().toUpperCase().trim();
        System.out.print("Masukkan Kode Bandara Tujuan\t: ");
        String bandaraTujuan = uwu.nextLine().toUpperCase().trim();
        System.out.println("");
        airportGraph.dijkstraShortestPath(bandaraAwal, bandaraTujuan);
    }
}