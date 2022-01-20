package lapr.project.stores;

import lapr.project.data.*;
import lapr.project.model.*;
import lapr.project.utils.Conversions;
import lapr.project.utils.PortTree;
import lapr.project.utils.graph.AlgorithmsNetwork;
import lapr.project.utils.graph.MatrixGraph;

import java.util.*;

public class FreightNetwork {
    /**
     * The graph
     */
    private final MatrixGraph<Place, Double> graph;
    private int n;
    private List<Border> listOfBorders;
    private List<Country> listOfCountry;
    private List<SeaDist> listOfSeadist;
    private List<Port> listOfPort;


    /**
     * Constructor of the Freight Network, with no received parameters.
     */
    public FreightNetwork() {
        this.graph = new MatrixGraph<>(false);
    }

    /**
     * Gets all the needed data (list of countries, ports, borders and seadists) from the database and builds the freight network.
     *
     * @param databaseConnection the database connection
     * @param n                  the desired number to be used for the n closest ports search
     */
    public void buildFreightNetworkFromDatabase(DatabaseConnection databaseConnection, int n) {
        List<Country> countries = new CountryStoreDB().getCountriesFromDatabase(databaseConnection);
        List<Border> borders = new BorderStoreDB().getBordersFromDatabase(databaseConnection,countries);
        //List<Border> borders = new BorderStoreDB().getBordersFromDatabase(databaseConnection);
        List<Port> ports = new PortStoreDB().getPortsFromDatabase(databaseConnection);
        List<SeaDist> seaDists = new SeadistStoreDB().getSeadistsFromDatabase(databaseConnection,ports,countries);
        //List<SeaDist> seaDists = new SeadistStoreDB().getSeadistsFromDatabase(databaseConnection);
        buildFreightNetworkFromGivenData(countries, borders, ports, seaDists, n);
    }

    /**
     * Builds the freight network, through a list of countries, borders, ports and seadists and through a number of closest ports.
     *
     * @param countries the list of countries.
     * @param borders   the list of borders
     * @param ports     the list of ports
     * @param seaDists  the list of seadists
     * @param n         the desired number to be used for the n closest ports search.
     */
    public void buildFreightNetworkFromGivenData(List<Country> countries, List<Border> borders, List<Port> ports, List<SeaDist> seaDists, int n) {
        this.n = n;
        this.listOfBorders = new ArrayList<>(borders);
        this.listOfCountry = new ArrayList<>(countries);
        this.listOfPort = new ArrayList<>(ports);
        this.listOfSeadist = new ArrayList<>(seaDists);
        insertConnectionBetweenCountries(borders);
        insertConnectionBetweenPortsSameCountry(ports);
        insertConnectionBetweenCapitalAndClosestPort(countries, ports);
        insertConnectionBetweenNClosestPorts(ports, seaDists, n);
    }

    /**
     * Inserts the connection between countries.
     *
     * @param borders the list of borders
     */
    private void insertConnectionBetweenCountries(List<Border> borders) {
        for (Border border : borders) {
            this.graph.addEdge(border.getCountry1(), border.getCountry2(), calculateWeight(border.getCountry1(), border.getCountry2()));
        }
    }

    /**
     * Inserts the connection between ports of the same country.
     *
     * @param ports the list of ports
     */

    private void insertConnectionBetweenPortsSameCountry(List<Port> ports) {
        for (Port p1 : ports) {
            for (Port p2 : ports) {
                if (!p1.equals(p2)) {
                    if (p1.getCountry().equals(p2.getCountry()))
                        this.graph.addEdge(p1, p2, calculateWeight(p1, p2));
                }
            }
        }
    }

    /**
     * Inserts the connection between the capital of the country and the closest port to it.
     *
     * @param countries the list of countries
     * @param ports     the list of ports
     */
    private void insertConnectionBetweenCapitalAndClosestPort(List<Country> countries, List<Port> ports) {
        Map<Country, PortTree> portsByCountry = new HashMap<>();
        for (Country country : countries) {
            List<Port> portsOfCountry = new ArrayList<>();
            for (Port port : ports) {
                if (port.getCountry().equals(country.getCountry()))
                    portsOfCountry.add(port);
            }

            if (!portsOfCountry.isEmpty()) {
                PortTree tree = new PortTree();
                tree.buildKDTree(portsOfCountry);
                portsByCountry.put(country, tree);
            }
        }
        for (Country s : portsByCountry.keySet()) {
            Port closest = portsByCountry.get(s).findNearestNeighbour(s.getLongitude(), s.getLatitude());
            this.graph.addEdge(s, closest, calculateWeight(s, closest));
        }
    }

    /**
     * Inserts the connections between each port of a country and the n closest ports of any other country.
     *
     * @param ports    the list of ports
     * @param seaDists the list of seadists
     * @param n        the desired number of closest ports
     */
    private void insertConnectionBetweenNClosestPorts(List<Port> ports, List<SeaDist> seaDists, int n) {
        seaDists.removeIf(s -> s.getFrom_country().getAlpha2Code().equals(s.getTo_country().getAlpha2Code()));
        List<List<SeaDist>> listaDeLista = new ArrayList<>();
        for (Port p : ports) {
            List<SeaDist> seadistSamePort = new ArrayList<>();
            for (SeaDist s : seaDists) {
                if (p.equals(s.getFrom_port()) || p.equals(s.getTo_port()))
                    seadistSamePort.add(s);
            }
            Collections.sort(seadistSamePort);
            if (!seadistSamePort.isEmpty())
                listaDeLista.add(seadistSamePort);
        }
        for (List<SeaDist> slist : listaDeLista) {
            int i = 0;
            while (i < n && i < slist.size()) {
                SeaDist actual = slist.get(i);
                this.graph.addEdge(actual.getFrom_port(), actual.getTo_port(), (double) actual.getSeaDistance());
                i++;
            }
        }
    }

    /**
     * Calculate the weight of an edge.
     *
     * @param place1 the first place
     * @param place2 the second place
     */
    private double calculateWeight(Place place1, Place place2) {
        return calculateWeight(place1.getLatitude(), place1.getLongitude(), place2.getLatitude(), place1.getLongitude());
    }

    /**
     * Calculate the weight of a connection.
     *
     * @param latitude1  the latitude of the first place
     * @param longitude1 the longitude of the first place
     * @param latitude2  the latitude of the second place
     * @param longitude2 the latitude of the second place
     */
    private double calculateWeight(Double latitude1, Double longitude1, Double latitude2, Double longitude2) {
        double cat2 = (longitude1 - longitude2) * Math.cos(((latitude1 * Math.PI / 180) + (latitude2 * Math.PI / 180)) / 2);
        double cat1 = latitude1 - latitude2;
        double result = Math.sqrt((cat1 * cat1) + (cat2 * cat2)) * 60.04;

        return Conversions.nauticalMilesToKm(result);
    }

    public Double shortestPath(Place vOrig, Place vDest) {
        LinkedList<Place> shortPath = new LinkedList<>();
        return AlgorithmsNetwork.shortestPath(this.graph, vOrig, vDest, Double::compare, Double::sum, (double) 0, shortPath);
    }

    public Double shortestPath(Place vOrig, Place vDest, LinkedList<Place> path) {
        return AlgorithmsNetwork.shortestPath(this.graph, vOrig, vDest, Double::compare, Double::sum, (double) 0, path);
    }

    public Double shortestPathSea(Place vOrig, Place vDest, LinkedList<Place> path) {
        FreightNetwork copyOfNetwork = new FreightNetwork();
        copyOfNetwork.buildFreightNetworkFromGivenData(this.listOfCountry,new ArrayList<>(),this.listOfPort,this.listOfSeadist,this.n);
        return AlgorithmsNetwork.shortestPath(copyOfNetwork.getGraph(), vOrig, vDest, Double::compare, Double::sum, (double) 0, path);
    }

    public Double shortestPathLand(Place vOrig, Place vDest, LinkedList<Place> path) {
        FreightNetwork copyOfNetwork = new FreightNetwork();
        copyOfNetwork.buildFreightNetworkFromGivenData(this.listOfCountry,this.listOfBorders,this.listOfPort,new ArrayList<>(),this.n);
        return AlgorithmsNetwork.shortestPath(copyOfNetwork.getGraph(), vOrig, vDest, Double::compare, Double::sum, (double) 0, path);
    }

    /**
     * Gets the n ports with greater centrality (greatest number of shortest paths that pass through it).
     *
     * @param numPorts the desired number of most critical ports
     * @return the list of ports with greater centrality
     */
    public List<Port> getMostCriticalPorts(int numPorts) {
        List<Map.Entry<Port, Integer>> sortedList = new ArrayList<>(getNumberOfShortestPathsPerPort().entrySet());
        sortedList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        List<Port> portsWithGreaterCentrality = new ArrayList<>();
        for (Map.Entry<Port, Integer> portIntegerEntry : sortedList.subList(0, numPorts)) {
            portsWithGreaterCentrality.add(portIntegerEntry.getKey());
        }

        return portsWithGreaterCentrality;
    }

    /**
     * Gets the number of shortest paths per port of the network.
     *
     * @return the map with port keys and number of shortest paths values.
     */
    private Map<Port, Integer> getNumberOfShortestPathsPerPort() {
        Map<Port, Integer> portShortestPathsMap = new HashMap<>(); // Map of ports and a counter of shortest paths that pass through it
        for (Place vert : this.graph.vertices()) {
            if (vert instanceof Port) portShortestPathsMap.put((Port) vert, 0);
        }

        ArrayList<LinkedList<Place>> paths = new ArrayList<>();
        for (Place vert : this.graph.vertices()) {
            if (AlgorithmsNetwork.shortestPaths(this.graph, vert, Double::compare, Double::sum, 0.0, paths, new ArrayList<>())) {
                for (LinkedList<Place> placeLinkedList : paths) {
                    for (Place p : placeLinkedList) {
                        if (p instanceof Port && portShortestPathsMap.containsKey(p))
                            portShortestPathsMap.put((Port) p, portShortestPathsMap.get(p) + 1);
                    }
                }
            }
        }

        return portShortestPathsMap;
    }

    /**
     * Gets the biggest cycle that contain more locations with less weight
     *
     * @return the list of connected places
     */
    public LinkedList<Place> getEfficientCycle() {
        ArrayList<LinkedList<Place>> largestMinDist = new ArrayList<>();
        for (Place placeO : this.graph.vertices()) {
            LinkedList<Place> list = AlgorithmsNetwork.nearestNeighbour(this.graph, placeO);
            largestMinDist.add(list);
        }
        ArrayList<LinkedList<Place>> cycles = new ArrayList<>();
        for (LinkedList<Place> list : largestMinDist) {
            try {
                cycles.add(AlgorithmsNetwork.searchCycle(this.graph, list));
            } catch (Exception ignored) {
            }
        }
        return AlgorithmsNetwork.getLessWeightedBiggestCycle(this.graph,AlgorithmsNetwork.searchLargestCycle(cycles));
    }



    /**
     * Gets the matrix graph.
     */
    public MatrixGraph<Place, Double> getGraph() {
        return this.graph;
    }

    @Override
    public String toString() {
        return this.graph.toString();
    }
}
