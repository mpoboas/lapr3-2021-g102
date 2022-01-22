package lapr.project.controller;

import lapr.project.model.Company;
import lapr.project.model.Place;
import lapr.project.stores.FreightNetwork;
import lapr.project.utils.graph.Edge;
import lapr.project.utils.graph.MatrixGraph;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ColorMapController {
    /**
     * The app
     */
    private App app;
    /**
     * The company
     */
    private final Company company;

    /**
     * The freight network
     */
    private FreightNetwork fn = new FreightNetwork();

    /**
     * Constructor receiving the company as a parameter
     * @param comp the company
     */
    public ColorMapController(Company comp) {
        this.company = comp;
    }

    /**
     * Constructor receiving no parameters
     */
    public ColorMapController() {
        this(App.getInstance().getCompany());
        app = App.getInstance();
        fn = company.getFreightNetwork();
    }

    /**
     * Converts a graph to an adjacency list
     * @param graph the graph
     * @return      the adjacency list
     */
    public static ArrayList<ArrayList<Integer>> graphToAdjList(MatrixGraph<Place, Double> graph) {
        Edge<Place, Double>[][] edgeMatrix = graph.getEdgeMatrix();
        int l = edgeMatrix[0].length;
        ArrayList<ArrayList<Integer>> adjListArray
                = new ArrayList<ArrayList<Integer>>(l);

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

    /**
     * Colors an adjacency list using a greedy algorithm
     * @param numVert   number of vertices
     * @param adj       adjacency list
     * @return          array with the vertices and their colors
     */
    public static int[] colorMap(int numVert, ArrayList<ArrayList<Integer>> adj) {
        int[] result = new int[numVert];
        Arrays.fill(result, -1);
        result[0] = 0;
        boolean[] available = new boolean[numVert];
        Arrays.fill(available, true);

        for (int u = 1; u < numVert; u++) {
            for (int i : adj.get(u)) {
                if (result[i] != -1)
                    available[result[i]] = false;
            }

            int current;
            for (current = 0; current < numVert; current++) {
                if (available[current])
                    break;
            }
            result[u] = current;
            Arrays.fill(available, true);
        }
        return result;
    }

    /**
     * Prints a colored map
     * @param numVert   number of vertices
     * @param result    map
     */
    public static void printColoredMap(int numVert, int[] result) {
        for (int u = 0; u < numVert; u++)
            System.out.println("Vertex " + u + " --->  Color " + result[u]);
        System.out.println("Total number of colors: " + (Collections.max(Arrays.asList(ArrayUtils.toObject(result))) + 1));
    }
}
