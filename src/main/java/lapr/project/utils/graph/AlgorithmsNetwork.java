package lapr.project.utils.graph;

import lapr.project.model.Place;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BinaryOperator;

// NOTE : MISSING ALLPATHS and getPaths METHOD
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
        path.add(vOrig);
        for (V pathKey : pathKeys) {
            if (pathKey != null) {
                path.add(pathKey);
            }
        }
        path.add(vDest);


    }


}
