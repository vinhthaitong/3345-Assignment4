import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;

public class Graph {
    ArrayList<LinkedList<Node>> aList = new ArrayList<LinkedList<Node>>();

    Graph() {
        aList = new ArrayList<LinkedList<Node>>();
    }

    // Add a node or find if it already exists in the graph
    public int addOrFindNode(String nodeData) {
        int index = findNodeIndex(nodeData);
        if (index == -1) {
            // Node doesn't exist, create it
            Node newNode = new Node(nodeData, nodeData, 0, 0);
            LinkedList<Node> currentList = new LinkedList<Node>();
            currentList.add(newNode);
            aList.add(currentList);
            return aList.size() - 1;  // Return the index of the newly added node
        }
        return index;  // Return the index of the existing node
    }

    // Add an edge using just string names and edge properties
    public void addConnection(String source, String destination, int cost, int time) {
        // Ensure both nodes exist in the graph
        int sourceIndex = addOrFindNode(source);
        int destIndex = addOrFindNode(destination);

        // Create the edge from source to destination
        LinkedList<Node> sourceList = aList.get(sourceIndex);
        Node destNode = aList.get(destIndex).getFirst();

        // Create a new node representing the edge with the correct properties
        Node edgeNode = new Node(destNode.sourceData, destNode.sourceData, cost, time);
        sourceList.add(edgeNode);
    }

    public void addNode(Node node) {
        // Create ArrayList of LinkedList with isolated nodes with no connections
        LinkedList<Node> currentList = new LinkedList<Node>();
        currentList.add(node);
        aList.add(currentList);
    }

    // Helper method to find node index by data value
    protected int findNodeIndex(String data) {
        for (int i = 0; i < aList.size(); i++) {
            if (!aList.get(i).isEmpty() && aList.get(i).getFirst().sourceData.equals(data)) {
                return i;
            }
        }
        return -1;
    }

    public void checkEdge(Node data) {
        int sourceIndex = findNodeIndex(data.sourceData);
        int destIndex = findNodeIndex(data.destinationData);

        if (sourceIndex != -1 && destIndex != -1) {
            LinkedList<Node> currentList = aList.get(sourceIndex);
            Node destNode = aList.get(destIndex).getFirst();
            for (Node node : currentList) {
                if (node.sourceData.equals(destNode.destinationData)) {
                    System.out.println("Edge exists");
                    return;
                }
            }
        }
    }

    public void print() {
        for (LinkedList<Node> currList : aList) {
            for (Node node : currList) {
                System.out.print(node.sourceData + " -> ");
            }
            System.out.println();
        }
    }

    // Simplified DFS method that takes string parameters instead of Node
    public void DFS(String source, String destination) {
        // Create a dummy node for compatibility
        Node data = new Node(source, destination, 0, 0);

        int sourceIndex = findNodeIndex(source);
        int destIndex = findNodeIndex(destination);

        // Check if both source and destination nodes exist
        if (sourceIndex == -1 || destIndex == -1) {
            System.out.println("Error: One or both nodes not found in the graph.");
            System.out.println("Source: " + source + " (index: " + sourceIndex + ")");
            System.out.println("Destination: " + destination + " (index: " + destIndex + ")");
            return;
        }

        // Implement DFS logic here
        boolean[] visited = new boolean[aList.size()];
        ArrayList<String> pathList = new ArrayList<String>();
        pathList.add(source);
        int[] cost = new int[1];
        int[] time = new int[1];
        cost[0] = 0;
        time[0] = 0;

        System.out.println("All possible paths from " + source + " to " + destination + ":");

        // Keep track of the number of paths found
        int[] pathCount = new int[1]; // Using array to allow modification in the recursive method
        pathCount[0] = 0;

        DFShelper(sourceIndex, destIndex, visited, pathList, pathCount, cost, time);

        if (pathCount[0] == 0) {
            System.out.println("No paths found from " + source + " to " + destination);
        } else {
            System.out.println("Total number of paths: " + pathCount[0]);
        }
    }

    private void DFShelper(int currentIndex, int destIndex, boolean[] visited,
                           ArrayList<String> pathList, int[] pathCount, int[] cost, int[] time) {
        // Validate index before proceeding
        if (currentIndex < 0 || currentIndex >= aList.size()) {
            System.out.println("Invalid index: " + currentIndex);
            return;
        }

        // If destination is found, print the path and update path count
        if (currentIndex == destIndex) {
            pathCount[0]++;
            System.out.println("Path " + pathCount[0] + ": " + String.join(" -> ", pathList) + ".Time " + time[0] + " Cost: " + cost[0]);
            return; // No need to mark as visited and then unvisited when we find destination
        }

        // Mark the current node as visited
        visited[currentIndex] = true;

        // If current vertex is not destination, explore all adjacent vertices
        LinkedList<Node> neighbors = aList.get(currentIndex);

        // Start from index 1 as index 0 is the node itself
        for (int i = 1; i < neighbors.size(); i++) {
            Node neighbor = neighbors.get(i);
            int neighborIndex = findNodeIndex(neighbor.sourceData);

            // Only proceed if the neighbor exists and is not visited
            if (neighborIndex != -1 && !visited[neighborIndex]) {
                // Add neighbor to path
                pathList.add(neighbor.sourceData);

                cost[0] += neighbor.cost;
                time[0] += neighbor.time;

                // Recursive call
                DFShelper(neighborIndex, destIndex, visited, pathList, pathCount, cost, time);

                // Remove the neighbor from path to backtrack
                cost[0] -= neighbor.cost;
                time[0] -= neighbor.time;
                pathList.remove(pathList.size() - 1);
            }
        }

        // Mark the current node as unvisited to allow it to be part of other paths
        visited[currentIndex] = false;
    }
}