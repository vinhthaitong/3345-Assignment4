public class Node{
    String sourceData;
    String destinationData;
    int cost;
    int time;

    Node(String source, String destination, int cost, int time) {
        this.sourceData = source;
        this.destinationData = destination;
        this.cost = cost;
        this.time = time;
    }

}
