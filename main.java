import java.util.*;
import java.io.*;

public class main {
    public static void main(String[] args) {
        // Create a new FlightEdge object
        FlightEdge flightEdge = new FlightEdge();
        
        try {
            // Properly initialize Scanner with a File object
            Scanner input = new Scanner(new File("test.txt"));
            
            // Read the data from the file
            while (input.hasNextLine()) {
                String line = input.nextLine();
                // Fix the split delimiter by escaping the pipe character
                String[] data = line.split("\\|");
                
                // Add validation to ensure we have enough elements
                if (data.length >= 4) {
                    String source = data[0];
                    String destination = data[1];
                    // Trim any whitespace before parsing
                    int cost = Integer.parseInt(data[2].trim());
                    int time = Integer.parseInt(data[3].trim());
                    flightEdge.setArray(source, destination, cost, time);
                }
            }
            
            // Close the scanner to prevent resource leaks
            input.close();
            
        } catch (FileNotFoundException e) {
            System.err.println("File not found: FlightData.txt");
        } catch (NumberFormatException e) {
            System.err.println("Error parsing number: " + e.getMessage());
        }

        flightEdge.setEdge();
        flightEdge.displayEdge();

        Graph graph = new Graph();
        Node dallas = new Node("Dallas");
        Node austin = new Node("Austin");
        flightEdge.searchDFS(dallas, austin);
    }
}