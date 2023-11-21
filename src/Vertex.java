public class Vertex {
    // Atribut
    String label;
    private boolean flagvisited;
    // Constructor
    public Vertex(String label) {
        this.label = label;
        flagvisited = false;
    }
    // Getter label
    public String getLabel() {
        return label;
    }
    // Setter Getter flagVisited
    public void setFlagVisited(boolean flagvisited) {
        this.flagvisited = flagvisited;
    }
    public boolean isFlagVisited() {
        return flagvisited;
    }
}