import java.util.*;

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
        
        // Create a new node representing the edge with the correct properties
        Node edgeNode = new Node(destination, destination, cost, time);
        sourceList.add(edgeNode);
    }

    public void addNode(Node node) {
        // Create ArrayList of LinkedList with isolated nodes with no connections
        LinkedList<Node> currentList = new LinkedList<Node>();
        currentList.add(node);
        aList.add(currentList);
    }

    public void addEdge(Node data) {
        // Create connections between two existing nodes
        int sourceIndex = findNodeIndex(data.sourceData);
        int destIndex = findNodeIndex(data.destinationData);

        if (sourceIndex != -1 && destIndex != -1) {
            LinkedList<Node> currentList = aList.get(sourceIndex);
            // Use the cost and time from the provided data
            Node edgeNode = new Node(data.destinationData, data.destinationData, data.cost, data.time);
            currentList.add(edgeNode);
        } else {
            System.out.println("Warning: Node not found - source: " + data.sourceData +
                    ", destination: " + data.destinationData);
        }
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

    public void print() {
        for (int i = 0; i < aList.size(); i++) {
            LinkedList<Node> currentList = aList.get(i);
            for (int j = 0; j < currentList.size(); j++) {
                Node node = currentList.get(j);
                System.out.print(node.sourceData + " -> ");
            }
            System.out.println();
        }
    }

    // Simplified DFS method that takes string parameters instead of Node
    public void DFS(String source, String destination, String option) {
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
        
        // Track cost and time for each path
        int[] currentCost = new int[1];
        int[] currentTime = new int[1];
        currentCost[0] = 0;
        currentTime[0] = 0;
        
        // Collection to store all paths
        List<Path> allPaths = new ArrayList<>();
        
        // Find all paths
        DFShelper(sourceIndex, destIndex, visited, pathList, currentCost, currentTime, allPaths);
        
        if (allPaths.isEmpty()) {
            System.out.println("No paths found from " + source + " to " + destination);
            return;
        }
        
        // Create a comparator for sorting paths by cost
        Comparator<Path> costComparator = new Comparator<Path>() {
            @Override
            public int compare(Path path1, Path path2) {
                return Integer.compare(path1.totalCost, path2.totalCost);
            }
        };
        
        // Create a comparator for sorting paths by time
        Comparator<Path> timeComparator = new Comparator<Path>() {
            @Override
            public int compare(Path path1, Path path2) {
                return Integer.compare(path1.totalTime, path2.totalTime);
            }
        };

        if(option.equals("C")) {
            // Sort paths by cost using Collections.sort with costComparator
            Collections.sort(allPaths, costComparator);
            for (int i = 0; i < allPaths.size(); i++) {
                System.out.println("\tPath " + (i+1) + ": " + allPaths.get(i));
            }
        } else if(option.equals("T")) {
            // Sort paths by time using Collections.sort with timeComparator
            Collections.sort(allPaths, timeComparator);
            for (int i = 0; i < allPaths.size(); i++) {
                System.out.println("\tPath " + (i+1) + ": " + allPaths.get(i));
            }
        } else {
            System.out.println("Invalid option. Please choose 'C' for cost or 'T' for time.");
        }
        
        // Display summary of best paths
        // Need to re-sort by cost to get the best cost path
        Collections.sort(allPaths, costComparator);
        Path bestCostPath = allPaths.get(0);
        
        // Re-sort by time to get the best time path
        Collections.sort(allPaths, timeComparator);
        Path bestTimePath = allPaths.get(0);
        
        System.out.println("Path Summary:");
        System.out.println("\tTotal number of paths: " + allPaths.size());
        System.out.println("\tBest path by cost: " + bestCostPath);
        System.out.println("\tBest path by time: " + bestTimePath);
    }

    private void DFShelper(int currentIndex, int destIndex, boolean[] visited, 
                          ArrayList<String> pathList, int[] currentCost, int[] currentTime,
                          List<Path> allPaths) {
        // Validate index before proceeding
        if (currentIndex < 0 || currentIndex >= aList.size()) {
            System.out.println("Invalid index: " + currentIndex);
            return;
        }
        
        // If destination is found, add the path to our collection
        if (currentIndex == destIndex) {
            allPaths.add(new Path(pathList, currentCost[0], currentTime[0]));
            return;
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
                
                // Add cost and time
                currentCost[0] += neighbor.cost;
                currentTime[0] += neighbor.time;
                
                // Recursive call
                DFShelper(neighborIndex, destIndex, visited, pathList, currentCost, currentTime, allPaths);
                
                // Remove the neighbor from path to backtrack
                pathList.remove(pathList.size() - 1);
                
                // Subtract cost and time when backtracking
                currentCost[0] -= neighbor.cost;
                currentTime[0] -= neighbor.time;
            }
        }
        
        // Mark the current node as unvisited to allow it to be part of other paths
        visited[currentIndex] = false;
    }
}