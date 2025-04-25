import java.util.ArrayList;

public class FlightEdge{
    protected String source;
    protected String destination;
    protected int cost;
    protected int time;

    Graph graph = new Graph();

    // Using ArrayLists to store multiple edges
    ArrayList<String> sourceList = new ArrayList<String>();
    ArrayList<String> destinationList = new ArrayList<String>();
    ArrayList<Integer> costList = new ArrayList<Integer>();
    ArrayList<Integer> timeList = new ArrayList<Integer>();

    void setArray(String source, String destination, int cost, int time) {
        sourceList.add(source);
        destinationList.add(destination);
        costList.add(cost);
        timeList.add(time);
    }

    void setEdge() {
        // First, add all unique nodes to the graph
        ArrayList<String> addedNodes = new ArrayList<>();

        // Add all source nodes
        for (int i = 0; i < sourceList.size(); i++) {
            String src = sourceList.get(i);
            if (!addedNodes.contains(src)) {
                graph.addNode(new Node(src));
                addedNodes.add(src);
            }
        }

        // Add all destination nodes
        for (int i = 0; i < destinationList.size(); i++) {
            String dest = destinationList.get(i);
            if (!addedNodes.contains(dest)) {
                graph.addNode(new Node(dest));
                addedNodes.add(dest);
            }
        }

        // Now add edges using existing nodes
        for (int i = 0; i < sourceList.size(); i++) {
            Node sourceNode = findNodeInGraph(sourceList.get(i));
            Node destNode = findNodeInGraph(destinationList.get(i));

            if (sourceNode != null && destNode != null) {
                graph.addEdge(sourceNode, destNode);
            }
        }
    }

    // Helper method to find a source node in the ArrayList
    private Node findNodeInGraph(String data) {
        for (int i = 0; i < graph.aList.size(); i++) {
            if (!graph.aList.get(i).isEmpty() && graph.aList.get(i).getFirst().data.equals(data)) {
                return graph.aList.get(i).getFirst();
            }
        }
        return null;
    }

    public void searchDFS(Node source, Node destination) {
        graph.DFS(source, destination);
    }


    String getSource() {
        return source;
    }
    String getDestination() {
        return destination;
    }
    int getCost() {
        return cost;
    }
    int getTime() {
        return time;
    }

    void displayEdge() {
        graph.print();
    }
}
