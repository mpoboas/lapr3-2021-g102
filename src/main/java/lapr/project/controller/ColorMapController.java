package lapr.project.controller;

import lapr.project.model.Company;
import lapr.project.model.Place;
import lapr.project.stores.FreightNetwork;
import lapr.project.utils.graph.Edge;
import lapr.project.utils.graph.MatrixGraph;
import org.apache.commons.lang3.ArrayUtils;

import java.util.*;

public class ColorMapController {
    /**
     * The app
     */
    private App app;
    /**
     * The company
     */
    private final Company company;

    private FreightNetwork fn = new FreightNetwork();

    public ColorMapController(Company comp) {
        this.company = comp;
    }

    public ColorMapController() {
        this(App.getInstance().getCompany());
        app = App.getInstance();
        fn = company.getFreightNetwork();
    }

    public static ArrayList<ArrayList<Integer>> graphToAdjList(MatrixGraph<Place, Double> graph)
    {
        Edge<Place, Double>[][] edgeMatrix = graph.getEdgeMatrix();
        // no of vertices
        int l = edgeMatrix[0].length;
        ArrayList<ArrayList<Integer>> adjListArray
                = new ArrayList<ArrayList<Integer>>(l);

        // Create a new list for each
        // vertex such that adjacent
        // nodes can be stored
        for (int i = 0; i < l; i++) {
            adjListArray.add(new ArrayList<Integer>());
        }

        int i, j;
        for (i = 0; i < edgeMatrix[0].length; i++) {
            for (j = 0; j < edgeMatrix.length; j++) {
                if (edgeMatrix[i][j] != null) {
                    adjListArray.get(i).add(j);
                }
            }
        }

        return adjListArray;
    }

    public static int[] colorMap(int numVert, ArrayList<ArrayList<Integer>> adj) {
        int[] result = new int[numVert];

        // Initialize all vertices as unassigned
        Arrays.fill(result, -1);

        // Assign the first color to first vertex
        result[0] = 0;

        // A temporary array to store the available colors. False
        // value of available[current] would mean that the color current is
        // assigned to one of its adjacent vertices
        boolean[] available = new boolean[numVert];

        // Initially, all colors are available
        Arrays.fill(available, true);

        // Assign colors to remaining V-1 vertices
        for (int u = 1; u < numVert; u++) {
            // Process all adjacent vertices and flag their colors
            // as unavailable
            for (int i : adj.get(u)) {
                if (result[i] != -1)
                    available[result[i]] = false;
            }

            // Find the first available color
            int current;
            for (current = 0; current < numVert; current++) {
                if (available[current])
                    break;
            }

            result[u] = current; // Assign the found color

            // Reset the values back to true for the next iteration
            Arrays.fill(available, true);
        }
        return result;
    }
    
    public static void printColoredMap(int numVert, int[] result) {
        for (int u = 0; u < numVert; u++)
            System.out.println("Vertex " + u + " --->  Color " + result[u]);
        System.out.println("Total number of colors: " + (Collections.max(Arrays.asList(ArrayUtils.toObject(result))) + 1));
    }
}
