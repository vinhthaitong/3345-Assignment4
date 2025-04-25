import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        // Create a new graph
        Graph graph = new Graph();
        
        // First, build the graph from flight data
        try {
            // Read flight connections from a file
            File flightDataFile = new File("test.txt");
            Scanner input = new Scanner(flightDataFile);

            // Read the connection data from the file
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] data = line.split("\\|");

                // Add validation to ensure we have enough elements
                if (data.length >= 4) {
                    String source = data[0].trim();
                    String destination = data[1].trim();
                    int cost = Integer.parseInt(data[2].trim());
                    int time = Integer.parseInt(data[3].trim());
                    
                    // Add this connection to the graph
                    graph.addConnection(source, destination, cost, time);
                }
            }
            // Close the scanner to prevent resource leaks
            input.close();
            
            System.out.println("Graph built successfully with the following nodes:");
            graph.print();
            
        } catch (FileNotFoundException e) {
            System.err.println("Flight data file not found: " + e.getMessage());
            return; // Exit if we can't build the graph
        } catch (Exception e) {
            System.err.println("Error building graph: " + e.getMessage());
            e.printStackTrace();
            return; // Exit if we can't build the graph
        }
        
        // Now read source-destination pairs and perform DFS
        try {
            // Properly initialize Scanner with a File object
            File inputFile = new File("Input.txt");
            Scanner userInput = new Scanner(inputFile);
            
            while (userInput.hasNextLine()) {
                String line = userInput.nextLine();
                // Split the line into source and destination
                String[] data = line.split("\\|");
                if (data.length >= 2) {
                    String source = data[0].trim();
                    String destination = data[1].trim();
                    
                    System.out.println("\n======================================");
                    System.out.println("Finding paths from " + source + " to " + destination + ":");
                    System.out.println("======================================");
                    
                    graph.DFS(source, destination);
                }
            }
            // Close the scanner to prevent resource leaks
            userInput.close();
        } catch (FileNotFoundException e) {
            System.err.println("Input file not found: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error during DFS search: " + e.getMessage());
            e.printStackTrace();
        }
    }
}