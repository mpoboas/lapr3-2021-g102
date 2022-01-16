package lapr.project.utils.graph;

import lapr.project.model.Place;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BinaryOperator;

// NOTE : MISSING ALLPATHS METHOD
public class AlgorithmsNetwork {
    private AlgorithmsNetwork() {

    }

    /**
     * Computes shortest-path distance from a source vertex to all reachable
     * vertices of a graph g with non-negative edge weights
     * This implementation uses Dijkstra's algorithm
     *
     * @param g        Graph instance
     * @param vOrig    Vertex that will be the source of the path
     * @param visited  set of previously visited vertices
     * @param pathKeys minimum path vertices keys
     * @param dist     minimum distances
     */
    private static void shortestPathDijkstra(Graph<Place, Double> g, Place vOrig,
                                             Comparator<Double> ce, BinaryOperator<Double> sum, Double zero,
                                             boolean[] visited, Place[] pathKeys, Double[] dist) {
        int vKey = g.key(vOrig);
        dist[vKey] = zero;
        pathKeys[vKey] = vOrig;
        while (vOrig != null) {
            vKey = g.key(vOrig);
            visited[vKey] = true;
            for (Edge<Place, Double> edge : g.outgoingEdges(vOrig)) {
                int vkeyAdj = g.key(edge.getVDest());
                if (!visited[vkeyAdj]) {
                    Double s = sum.apply(dist[vKey], edge.getWeight());
                    if (dist[vkeyAdj] == null || ce.compare(dist[vkeyAdj], s) > 0) {
                        dist[vkeyAdj] = s;
                        pathKeys[vkeyAdj] = vOrig;

                    }
                }
            }

            Double minDist = null;
            vOrig = null;
            for (Place vert : g.vertices()) {
                int i = g.key(vert);
                if (!visited[i] && (dist[i] != null) && ((minDist == null) || ce.compare(dist[i], minDist) > 0)) {
                    minDist = dist[i];
                    vOrig = vert;
                }
            }
        }


    }


    /**
     * Shortest-path between two vertices
     *
     * @param g         graph
     * @param vOrig     origin vertex
     * @param vDest     destination vertex
     * @param ce        comparator between elements of type E
     * @param sum       sum two elements of type E
     * @param zero      neutral element of the sum in elements of type E
     * @param shortPath returns the vertices which make the shortest path
     * @return if vertices exist in the graph and are connected, true, false otherwise
     */
    public static Double shortestPath(Graph<Place, Double> g, Place vOrig, Place vDest,
                                      Comparator<Double> ce, BinaryOperator<Double> sum, Double zero,
                                      LinkedList<Place> shortPath) {
        if (!g.validVertex(vOrig) || !g.validVertex(vDest))
            return null;

        shortPath.clear();
        int numVerts = g.numVertices();
        boolean[] visited = new boolean[numVerts];
        Place[] pathKeys = new Place[numVerts];

        Double[] dist = new Double[numVerts];

        shortestPathDijkstra(g, vOrig, ce, sum, zero, visited, pathKeys, dist);
        Double lenghtPath = dist[g.key(vDest)];
        if (lenghtPath != null) {
            getPath(g, vOrig, vDest, pathKeys, shortPath);
            return lenghtPath;
        }
        return null;
    }

    /**
     * Shortest-path between a vertex and all other vertices
     *
     * @param g     graph
     * @param vOrig start vertex
     * @param ce    comparator between elements of type E
     * @param sum   sum two elements of type E
     * @param zero  neutral element of the sum in elements of type E
     * @param paths returns all the minimum paths
     * @param dists returns the corresponding minimum distances
     * @return if vOrig exists in the graph true, false otherwise
     */
    public static boolean shortestPaths(Graph<Place, Double> g, Place vOrig,
                                    Comparator<Double> ce, BinaryOperator<Double> sum, Double zero,
                                    ArrayList<LinkedList<Place>> paths, ArrayList<Double> dists) {
        if (!g.validVertex(vOrig)) return false;

        paths.clear();
        dists.clear();
        int numVerts = g.numVertices();
        boolean[] visited = new boolean[numVerts];
        Place[] pathKeys = new Place[numVerts];
        Double[] dist = new Double[numVerts];
        shortestPathDijkstra(g, vOrig, ce, sum, zero, visited, pathKeys, dist);
        dists.clear();
        paths.clear();
        for (int i = 0; i < numVerts; i++) {
            paths.add(null);
            dists.add(null);
        }
        for (Place vDst : g.vertices()) {
            int i = g.key(vDst);
            if (dist[i] != null) {
                LinkedList<Place> shortPath = new LinkedList<>();
                getPath(g, vOrig, vDst, pathKeys, shortPath);
                paths.set(i, shortPath);
                dists.set(i, dist[i]);
            }
        }

        return true;
    }


    /**
     * Extracts from pathKeys the minimum path between voInf and vdInf
     * The path is constructed from the end to the beginning
     *
     * @param g        Graph instance
     * @param vOrig    information of the Vertex origin
     * @param vDest    information of the Vertex destination
     * @param pathKeys minimum path vertices keys
     * @param path     stack with the minimum path (correct order)
     */
    private static <V, E> void getPath(Graph<V, E> g, V vOrig, V vDest,
                                       V[] pathKeys, LinkedList<V> path) {
        path.add(vDest);
        if (g.key(vOrig) != g.key(vDest)) {
            vDest = pathKeys[g.key(vDest)];
            getPath(g, vOrig, vDest, pathKeys, path);
        }
    }
    /**
     * Performs the nearest search starting in a vertex
     *
     * @param g       Graph instance
     * @param vOrig   vertex of graph g that will be the source of the search
     * @param visited set of previously visited vertices
     * @param qdfs    return LinkedList with vertices of nearest neighbour search
     */
    private static void nearestNeighbourSearch(Graph<Place, Double> g, Place vOrig, boolean[] visited, LinkedList<Place> qdfs) {
        int vKey = g.key(vOrig);
        if (visited[vKey]) return;

        qdfs.add(vOrig);
        visited[vKey] = true;

        Place current = vOrig;
        double minDist = 999999999999999.9;
        for (Place vAdj : g.adjVertices(vOrig)) {
            if (!visited[g.key(vAdj)]) {
                if (g.edge(vOrig, vAdj).getWeight() <= minDist) {
                    minDist = g.edge(vOrig, vAdj).getWeight();
                    current = vAdj;
                }
            }
        }
        nearestNeighbourSearch(g, current, visited, qdfs);
    }

    /**
     * Performs nearest neighbour search starting in a vertex
     *
     * @param g    Graph instance
     * @param vert vertex of graph g that will be the source of the search
     * @return a LinkedList with the vertices of the search
     */
    public static LinkedList<Place> nearestNeighbour(Graph<Place, Double> g, Place vert) {
        if (!g.validVertex(vert))
            return null;
        LinkedList<Place> qdfs = new LinkedList<>();
        boolean[] visited = new boolean[g.numVertices()];
        nearestNeighbourSearch(g, vert, visited, qdfs);
        return qdfs;
    }

    /**
     * Performs the search of a cycle in a list of vertices of a graph
     * @param g Graph instance
     * @param path list of connecting vertices
     * @return the cycle obtained by the search
     */
    public static LinkedList<Place> searchCycle(Graph<Place, Double> g, LinkedList<Place> path) {
        LinkedList<Place> result = new LinkedList<>(path);
        while (result.size() > 2) {
            if (g.edge(result.getLast(), result.getFirst()) != null) {
                result.add(result.getFirst());
                return result;
            } else {
                result.removeLast();
            }
        }
        throw new IllegalStateException("Not Cycle Detected");
    }

    /**
     * From a list of cycles selects the ones that contain more vertices
     * @param cycles list of cycles
     * @return list of the biggest cycles
     */
    public static ArrayList<LinkedList<Place>> searchLargestCycle(ArrayList<LinkedList<Place>> cycles){
        ArrayList<LinkedList<Place>> result = new ArrayList<>();
        int sizemax = cycles.get(0).size();
        for (LinkedList<Place> list: cycles) {
            if (list.size() > sizemax)
                sizemax = list.size();
        }
        for (LinkedList<Place> list: cycles) {
            if (list.size() == sizemax)
                result.add(list);
        }
        return result;
    }

    /**
     * From a list of cycles selects the one with less weight
     * @param g Graph instance
     * @param cycles list of cycles
     * @return the less weighted cycle
     */
    public static LinkedList<Place> getLessWeightedBiggestCycle(Graph<Place, Double> g, ArrayList<LinkedList<Place>> cycles){
        int index = 0;
        double totalWeight= AlgorithmsNetwork.calculateTotalWeight( g,cycles.get(0));
        for (LinkedList<Place> cycle: cycles) {
            if (AlgorithmsNetwork.calculateTotalWeight(g,cycle) < totalWeight){
                totalWeight= AlgorithmsNetwork.calculateTotalWeight(g,cycle);
                index = cycles.indexOf(cycle);
            }
        }
        return cycles.get(index);
    }

    /**
     * Calculates the total weight of a path in a cycle
     * @param g Graph instance
     * @param path path to get the weight
     * @return the weight of the path
     */
    public static double calculateTotalWeight(Graph<Place, Double> g, LinkedList<Place> path){
        double result = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            Edge<Place,Double> edge = g.edge(path.get(i),path.get(i + 1));
            if (edge!=null)
                result = result + edge.getWeight();
        }
        return result;
    }


}
