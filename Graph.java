import java.util.ArrayList;
import java.util.LinkedList;

public class Graph{
    ArrayList<LinkedList<Node>> aList = new ArrayList<LinkedList<Node>>();

    Graph(){
        aList = new ArrayList<LinkedList<Node>>();
    }

    public void addNode(Node node){
        LinkedList<Node> currentList = new LinkedList<Node>();
        currentList.add(node);
        aList.add(currentList);
    }

    public void addEdge(Node source, Node destination){
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
    private int findNodeIndex(String data) {
        for (int i = 0; i < aList.size(); i++) {
            if (!aList.get(i).isEmpty() && 
                aList.get(i).getFirst().data.equals(data)) {
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
    }
}