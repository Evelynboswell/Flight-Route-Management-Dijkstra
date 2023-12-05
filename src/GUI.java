import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.table.JTableHeader;

public class GUI extends JFrame {

    private DefaultTableModel tableModel;
    private JTable dataTable;
    private JLabel kodeAsalLabel, kodeTujuanLabel;
    private JTextField kodeAsalField, kodeTujuanField;
    private JTextArea descriptionArea, namaBandara;
    private JButton findRouteButton, clearButton;
    private JLabel logoLabel;
    private ImageIcon logo;
    private DefaultTableModel resultTableModel;
    private JTable resultTable;
    private Graph airportGraph;

    public GUI() {
        // Set up frame
        setTitle("Airport Data and Route Finder");
        setSize(1480, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        // Container
        Container contentPane = getContentPane();
        contentPane.setLayout(null);
        contentPane.setBackground(Color.WHITE);

        // Inisialisasi graph
        airportGraph = new Graph(15);

        // Tabel bandara
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Bandara Asal");
        tableModel.addColumn("Bandara Tujuan");
        tableModel.addColumn("Jarak (Km)");
        tableModel.addColumn("Fuel Cost (RP)");

        dataTable = new JTable(tableModel);
        dataTable.setFont(new Font("Verdana", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(dataTable);
        scrollPane.setBounds(550, 20, 880, 200);
        JTableHeader tableHeader = dataTable.getTableHeader();
        tableHeader.setFont(new Font("Verdana", Font.BOLD, 14));
        tableHeader.setBackground(Color.decode("#0077F4"));

        // Logo
        logo = new ImageIcon("logo.png");
        logo.setImage(logo.getImage().getScaledInstance(200, 150,
                Image.SCALE_SMOOTH));
        logoLabel = new JLabel(logo);
        logoLabel.setBounds(30, 0, 200, 200);

        // Labels
        kodeAsalLabel = new JLabel("Kode Bandara Asal");
        kodeAsalLabel.setFont(new Font("Verdana", Font.BOLD, 16));
        kodeAsalLabel.setBounds(550, 250, 200, 25);

        kodeTujuanLabel = new JLabel("Kode Bandara Tujuan");
        kodeTujuanLabel.setFont(new Font("Verdana", Font.BOLD, 16));
        kodeTujuanLabel.setBounds(550, 290, 200, 25);

        // Text fields
        kodeAsalField = new JTextField(10);
        kodeAsalField.setBounds(800, 250, 100, 30);
        kodeAsalField.setFont(new Font("Verdana", Font.PLAIN, 16));

        kodeTujuanField = new JTextField(10);
        kodeTujuanField.setBounds(800, 290, 100, 30);
        kodeTujuanField.setFont(new Font("Verdana", Font.PLAIN, 16));

        // Text Area
        descriptionArea = new JTextArea("Notes:\n"
                + "- Mencari Rute    : 1. Masukkan kode bandara asal\n"
                + "\t        2. Masukkan kode bandara tujuan\n"
                + "\t        3. Klik tombol 'Find Route'");
        descriptionArea.setBounds(50, 410, 400, 170);
        descriptionArea.setFont(new Font("Verdana", Font.PLAIN, 13));
        descriptionArea.setBackground(Color.WHITE);
        descriptionArea.setEditable(false);

        namaBandara = new JTextArea("Kode Bandara |  Nama Bandara\n"
                + "YIA	   Yogyakarta International Airport\n"
                + "KNO	   Kualanamu International Airport\n"
                + "CGK 	   Soekarno-Hatta International Airport\n"
                + "DPS	   Ngurah Rai International Airport\n"
                + "UPG	   Sultan Hasanuddin International Airport\n"
                + "SUB	   Juanda International Airport\n"
                + "PKY	   Pekalongan Airport\n"
                + "BDJ	   Bandar Udara Internasional Sultan Syarif Kasim II\n"
                + "BTH	   Bathang Nai Bonar Airport\n"
                + "PNK	   Sultan Aji Muhammad Sulaiman Sepinggan Airport\n"
                + "MDC	   Supadio Airport");
        namaBandara.setBounds(50, 180, 550, 210);
        namaBandara.setFont(new Font("Verdana", Font.PLAIN, 13));
        namaBandara.setBackground(Color.WHITE);
        namaBandara.setEditable(false);

        // Buttons
        findRouteButton = new JButton("Find Route");
        findRouteButton.setBounds(800, 350, 140, 30);
        findRouteButton.setBackground(Color.decode("#0077F4"));
        findRouteButton.setForeground(Color.WHITE);
        findRouteButton.setFont(new Font("Verdana", Font.BOLD, 14));

        clearButton = new JButton("Clear");
        clearButton.setBounds(950, 350, 100, 30);
        clearButton.setBackground(Color.decode("#0077F4"));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFont(new Font("Verdana", Font.BOLD, 14));

        // Tabel hasil
        resultTableModel = new DefaultTableModel();
        resultTableModel.addColumn("Bandara Asal");
        resultTableModel.addColumn("Bandara Tujuan");
        resultTableModel.addColumn("Rute");
        resultTableModel.addColumn("Total Jarak (Km)");
        resultTableModel.addColumn("Total Fuel Cost (Rp)");

        resultTable = new JTable(resultTableModel);
        resultTable.setFont(new Font("Verdana", Font.PLAIN, 14));
        resultTable.setFillsViewportHeight(true);
        JScrollPane resultScrollPane = new JScrollPane(resultTable);
        resultScrollPane.setBounds(550, 410, 880, 140);

        JTableHeader tableHeader2 = resultTable.getTableHeader();
        tableHeader2.setFont(new Font("Verdana", Font.BOLD, 14));
        tableHeader2.setBackground(Color.decode("#0077F4"));

        // Add komponen di container
        contentPane.add(scrollPane);
        contentPane.add(logoLabel);
        contentPane.add(kodeAsalLabel);
        contentPane.add(kodeTujuanLabel);
        contentPane.add(kodeAsalField);
        contentPane.add(kodeTujuanField);
        contentPane.add(descriptionArea);
        contentPane.add(namaBandara);
        contentPane.add(findRouteButton);
        contentPane.add(clearButton);
        contentPane.add(resultScrollPane);

        // Menambahkan actionListener pada buttons
        findRouteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findRoute();
            }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Menghapus input
                kodeAsalField.setText("");
                kodeTujuanField.setText("");

                // Menghapus isi tabel hasil
                resultTableModel.setRowCount(0);
            }
        });
        // Memanggil method ini untuk menginisialisasikan data yang tertulis
        initializeGraphWithData();
    }
    // Method untuk mencari rute
    private void findRoute() {
        String kodeAsal = kodeAsalField.getText().toUpperCase().trim();
        String kodeTujuan = kodeTujuanField.getText().toUpperCase().trim();

        if (kodeAsal.isEmpty() || kodeTujuan.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Masukkan Kode Bandara Asal and Kode Bandara Tujuan.");
            return;
        }
        // Cek apakah vertex berada pada graf
        int startIndex = airportGraph.indexVertex(kodeAsal);
        int destinationIndex = airportGraph.indexVertex(kodeTujuan);

        if (startIndex == -1 || destinationIndex == -1) {
            JOptionPane.showMessageDialog(this,
                    "Satu atau dua bandara tidak terdaftar.");
            return;
        }
        // Memanggil method ini untuk mencari rute dgn Dijkstra
        airportGraph.dijkstraShortestPath(kodeAsal, kodeTujuan);

        // Menampilkan hasil di tabel result
        ArrayList<String> resultDepartureArrival =
                airportGraph.getResultDepartureArrival();
        ArrayList<String> resultPath = airportGraph.getResultPath();
        // Cek apakah resultPath kosong
        if (resultPath != null) {
            int totalDistance = airportGraph.getTotalDistance();
            int totalFuelCost = airportGraph.getTotalFuelCost();

            // Cek apakah resultPath kosong
            if (!resultPath.isEmpty()) {
                Object[] rowData = {
                        resultDepartureArrival.get(0),
                        resultDepartureArrival.get(1),
                        resultPath.toString(),
                        totalDistance,
                        totalFuelCost
                };
                resultTableModel.addRow(rowData);
            } else {
                JOptionPane.showMessageDialog(this,
                        "No route found between the specified airports.");
            }
        } else {
            // Menampilkan pesan jika terdapat error menemukan rute
            JOptionPane.showMessageDialog(this,
                    "Error finding route. Please check your input.");
        }
    }
    // Method untuk memasukkan data yang telah ada sebelumnya
    private void initializeGraphWithData() {
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
        updateTableWithGraphData();
    }
    // Method untuk mengupdate data pada tabel
    private void updateTableWithGraphData() {
        // Mengambil vertex dan edges dari graf
        Vertex[] vertices = airportGraph.getVertexList();
        ArrayList<Edge> edges = airportGraph.getEdges();

        // Iterasi semua edge pada graf dan tambahkan ke dalam tabel
        for (Edge edge : edges) {
            Object[] rowData = {vertices[edge.getVertexA()].getLabel(),
                    vertices[edge.getVertexB()].getLabel(),
                    edge.getWeight(),
                    edge.getFuelCost()};
            tableModel.addRow(rowData);
        }
    }
    // Main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI();
            }
        });
    }
}
