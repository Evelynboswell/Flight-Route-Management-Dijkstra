public class Edge {
    // Atribut
    private int vertexA, vertexB, weight, fuelCost;
    // Constuctor
    public Edge(int vertexA, int vertexB, int weight, int fuelCost) {
        this.vertexA = vertexA;
        this.vertexB = vertexB;
        this.weight = weight;
        this.fuelCost = fuelCost;
    }
    public Edge() {}
    // Setter and getter
    public int getVertexA() {
        return vertexA;
    }
    public void setVertexA(int vertexA) {
        this.vertexA = vertexA;
    }
    public int getVertexB() {
        return vertexB;
    }
    public void setVertexB(int vertexB) {
        this.vertexB = vertexB;
    }
    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public int getFuelCost() {
        return fuelCost;
    }
}