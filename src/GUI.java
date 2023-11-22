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
    private JLabel kodeAsalLabel, kodeTujuanLabel, jarakLabel, fuelLabel;
    private JTextField kodeAsalField, kodeTujuanField, jarakField, fuelCostField;
    private JButton addButton, findRouteButton;
    private DefaultTableModel resultTableModel;
    private JTable resultTable;
    private Graph airportGraph;

    public GUI() {
        // Set up frame
        setTitle("Airport Data and Route Finder");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        // Container
        Container contentPane = getContentPane();
        contentPane.setLayout(null);
        contentPane.setBackground(Color.decode("#BBE6F7"));

        // Initialize the graph
        airportGraph = new Graph(11);

        // Create components tabel Airport
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Bandara Asal");
        tableModel.addColumn("Bandara Tujuan");
        tableModel.addColumn("Jarak (Km)");
        tableModel.addColumn("Fuel Cost (RP)");

        dataTable = new JTable(tableModel);
        dataTable.setFont(new Font("Verdana", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(dataTable);
        scrollPane.setBounds(20, 20, 950, 200);
        JTableHeader tableHeader = dataTable.getTableHeader();
        tableHeader.setFont(new Font("Verdana", Font.BOLD, 14));
        tableHeader.setBackground(Color.decode("#0077F4"));

        // Labels
        kodeAsalLabel = new JLabel("Kode Bandara Asal");
        kodeAsalLabel.setFont(new Font("Verdana", Font.BOLD, 16));
        kodeAsalLabel.setBounds(20, 250, 200, 25);

        kodeTujuanLabel = new JLabel("Kode Bandara Tujuan");
        kodeTujuanLabel.setFont(new Font("Verdana", Font.BOLD, 16));
        kodeTujuanLabel.setBounds(20, 290, 200, 25);

        jarakLabel = new JLabel("Jarak (Km)");
        jarakLabel.setFont(new Font("Verdana", Font.BOLD, 16));
        jarakLabel.setBounds(20, 330, 200, 25);

        fuelLabel = new JLabel("Fuel cost (Rp)");
        fuelLabel.setFont(new Font("Verdana", Font.BOLD, 16));
        fuelLabel.setBounds(20, 370, 200, 25);

        // Text fields
        kodeAsalField = new JTextField(10);
        kodeAsalField.setBounds(250, 250, 100, 30);
        kodeAsalField.setFont(new Font("Verdana", Font.PLAIN, 16));

        kodeTujuanField = new JTextField(10);
        kodeTujuanField.setBounds(250, 290, 100, 30);
        kodeTujuanField.setFont(new Font("Verdana", Font.PLAIN, 16));

        jarakField = new JTextField(10);
        jarakField.setBounds(250, 330, 100, 30);
        jarakField.setFont(new Font("Verdana", Font.PLAIN, 16));

        fuelCostField = new JTextField(10);
        fuelCostField.setBounds(250, 370, 100, 30);
        fuelCostField.setFont(new Font("Verdana", Font.PLAIN, 16));

        // Buttons
        addButton = new JButton("Add");
        addButton.setBounds(450, 370, 100, 30);
        addButton.setBackground(Color.decode("#0077F4"));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Verdana", Font.BOLD, 14));

        findRouteButton = new JButton("Find Route");
        findRouteButton.setBounds(570, 370, 150, 30);
        findRouteButton.setBackground(Color.decode("#0077F4"));
        findRouteButton.setForeground(Color.WHITE);
        findRouteButton.setFont(new Font("Verdana", Font.BOLD, 14));

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
        resultScrollPane.setBounds(20, 420, 950, 200);

        JTableHeader tableHeader2 = resultTable.getTableHeader();
        tableHeader2.setFont(new Font("Verdana", Font.BOLD, 14));
        tableHeader2.setBackground(Color.decode("#0077F4"));

        // Add komponen di container
        contentPane.add(scrollPane);
        contentPane.add(kodeAsalLabel);
        contentPane.add(kodeTujuanLabel);
        contentPane.add(jarakLabel);
        contentPane.add(fuelLabel);
        contentPane.add(kodeAsalField);
        contentPane.add(kodeTujuanField);
        contentPane.add(jarakField);
        contentPane.add(fuelCostField);
        contentPane.add(addButton);
        contentPane.add(findRouteButton);
        contentPane.add(resultScrollPane);

        // Set up button actions
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addData();
            }
        });

        findRouteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findRoute();
            }
        });

        initializeGraphWithData();

    }

    private void addData() {
        try {
            String kodeAsal = kodeAsalField.getText();
            String kodeTujuan = kodeTujuanField.getText();
            int jarak = Integer.parseInt(jarakField.getText());
            int fuelCost = Integer.parseInt(fuelCostField.getText());

            airportGraph.addEdge(kodeAsal, kodeTujuan, jarak, fuelCost);

            // Add data to the table
            Object[] rowData = {kodeAsal, kodeTujuan, jarak, fuelCost};
            tableModel.addRow(rowData);

            // Clear input fields
            kodeAsalField.setText("");
            kodeTujuanField.setText("");
            jarakField.setText("");
            fuelCostField.setText("");

            JOptionPane.showMessageDialog(this, "Data added successfully!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid numbers for Jarak and Fuel Cost.");
        }
    }

    private void findRoute() {
        String kodeAsal = kodeAsalField.getText().toUpperCase().trim();
        String kodeTujuan = kodeTujuanField.getText().toUpperCase().trim();

        if (kodeAsal.isEmpty() || kodeTujuan.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Kode Bandara Asal and Kode Bandara Tujuan.");
            return;
        }

        airportGraph.dijkstraShortestPath(kodeAsal, kodeTujuan);

        // Display results in the result table
        ArrayList<String> resultDepartureArrival = airportGraph.getResultDepartureArrival();
        ArrayList<String> resultPath = airportGraph.getResultPath();
        int totalDistance = airportGraph.getTotalDistance();
        int totalFuelCost = airportGraph.getTotalFuelCost();

        // Check if the result is not empty
        if (!resultPath.isEmpty()) {
            Object[] rowData = {resultDepartureArrival.get(0), resultDepartureArrival.get(1),
                    resultPath.toString(), totalDistance, totalFuelCost};
            resultTableModel.addRow(rowData);
        } else {
            JOptionPane.showMessageDialog(this, "No route found between the specified airports.");
        }
    }

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

    private void updateTableWithGraphData() {
        // Clear the existing data in the table
        tableModel.setRowCount(0);

        // Get the vertices and edges from the graph
        Vertex[] vertices = airportGraph.getVertexList();
        ArrayList<Edge> edges = airportGraph.getEdges();

        // Iterate through the edges in the graph and add them to the table
        for (Edge edge : edges) {
            Object[] rowData = {vertices[edge.getVertexA()].getLabel(),
                    vertices[edge.getVertexB()].getLabel(),
                    edge.getWeight(),
                    edge.getFuelCost()};
            tableModel.addRow(rowData);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI();
            }
        });
    }
}
