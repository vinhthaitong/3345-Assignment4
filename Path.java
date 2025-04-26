import java.util.ArrayList;

// Path class to store complete path information
public class Path {
    ArrayList<String> nodes;
    int totalCost;
    int totalTime;

    public Path(ArrayList<String> nodes, int cost, int time) {
        this.nodes = new ArrayList<>(nodes);
        this.totalCost = cost;
        this.totalTime = time;
    }

    @Override
    public String toString() {
        return String.join(" -> ", nodes) + " Cost: " + totalCost + ", Time: " + totalTime;
    }
}