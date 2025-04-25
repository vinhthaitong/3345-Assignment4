import java.util.ArrayList;
import java.util.LinkedList;

public class Graph{
    ArrayList<LinkedList<Node>> aList = new ArrayList<LinkedList<Node>>();

    Graph(){
        aList = new ArrayList<LinkedList<Node>>();
    }

    public void addNode(Node node){
        // Create ArrayList of LinkedList with isolated nodes with no connections
        LinkedList<Node> currentList = new LinkedList<Node>();
        currentList.add(node);
        aList.add(currentList);
    }

    public void addEdge(Node source, Node destination){
        // Create connections between two existing nodes
        int sourceIndex = findNodeIndex(source.data);
        int destIndex = findNodeIndex(destination.data);
        
        if (sourceIndex != -1 && destIndex != -1) {
            LinkedList<Node> currentList = aList.get(sourceIndex);
            Node destNode = aList.get(destIndex).getFirst();
            currentList.add(destNode);
        } else {
            System.out.println("Warning: Node not found - source: " + source.data + 
                               ", destination: " + destination.data);
        }
    }
    
    // Helper method to find node index by data value
    protected int findNodeIndex(String data) {
        for (int i = 0; i < aList.size(); i++) {
            if (!aList.get(i).isEmpty() && aList.get(i).getFirst().data.equals(data)) {
                return i;
            }
        }
        return -1;
    }

    public void checkEdge(Node source, Node destination){
        int sourceIndex = findNodeIndex(source.data);
        int destIndex = findNodeIndex(destination.data);
        
        if (sourceIndex != -1 && destIndex != -1) {
            LinkedList<Node> currentList = aList.get(sourceIndex);
            Node destNode = aList.get(destIndex).getFirst();
            for(Node node : currentList){
                if(node.data.equals(destNode.data)){
                    System.out.println("Edge exists");
                    return;
                }
            }
        }
    }

    public void print(){
        for(LinkedList<Node> currList : aList){
            for(Node node : currList){
                System.out.print(node.data + " -> ");
            }
            System.out.println();
        }
        System.out.println(aList.size());
    }

    public void DFS(Node source, Node destination){
        int sourceIndex = findNodeIndex(source.data);
        int destIndex = findNodeIndex(destination.data);
        // Implement DFS logic here
        boolean[] visited = new boolean[aList.size()];

        ArrayList<String> pathList = new ArrayList<String>();
        pathList.add(source.data);

        DFShelper(sourceIndex, destIndex, visited, pathList);
    }

    private void DFShelper(int currentIndex, int destIndex, boolean[] visited, ArrayList<String> pathList){
        // Mark the current node as visited
        visited[currentIndex] = true;

        // If destination is found, print the path
        if (currentIndex == destIndex) {
            System.out.println();
            System.out.println(String.join(" -> ", pathList));
        } else {
            // If current vertex is not destination, explore all adjacent vertices
            System.out.println(currentIndex);
            LinkedList<Node> neighbors = aList.get(currentIndex);
            System.out.println(neighbors.size());

            for (int i = 1; i < neighbors.size(); i++) {
                System.out.println(neighbors.get(i).data);
                Node neighbor = neighbors.get(i);
                int neighborIndex = findNodeIndex(neighbor.data);

                // Only proceed if the neighbor exists and is not visited
                if (neighborIndex != -1 && !visited[neighborIndex]) {
                    // Add neighbor to path
                    pathList.add(neighbor.data);
                    System.out.println("Adding to path: " + neighbor.data);

                    // Recursive call
                    DFShelper(neighborIndex, destIndex, visited, pathList);

                    // Remove the neighbor from path to backtrack
                    pathList.remove(pathList.size() - 1);
                }
            }
        }

        // Mark the current node as unvisited to allow it to be part of other paths
        visited[currentIndex] = false;
    }
}