package lapr.project.stores;

import lapr.project.data.*;
import lapr.project.model.*;
import lapr.project.utils.Conversions;
import lapr.project.utils.PortTree;
import lapr.project.utils.graph.MatrixGraph;

import java.util.*;

public class FreightNetwork {
    /**
     * The graph
     */
    private final MatrixGraph<Place, Double> graph;

    /**
     * Constructor of the Freight Network, with no received parameters.
     */
    public FreightNetwork() {
        this.graph = new MatrixGraph<>(false);
    }

    /**
     * Gets all the data needed (list of countries, ports, borders and seadists) from the database and builds the freight network.
     *
     * @param databaseConnection the database connection
     * @param n                  the desired number to be used for the n closest ports search
     */
    public void buildFreightNetworkFromDatabase(DatabaseConnection databaseConnection, int n) {
        List<Country> countries = new CountryStoreDB().getCountriesFromDatabase(databaseConnection);
        List<Border> borders = new BorderStoreDB().getAllBorders(databaseConnection);
        List<Port> ports = new PortStoreDB().getPortsFromDatabase(databaseConnection);
        List<SeaDist> seaDists = new SeadistStoreDB().getAllSeadists(databaseConnection);
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
        insertConnectionBetweenCountries(borders);
        insertConnectionBetweenPortsSameCountry(ports);
        insertConnectionBetweenCapitalAndClosestPort(countries, ports);
        insertConnectionBetweenNClosestPorts(ports, seaDists, n);
    }

    /**
     * Inserts the connection between countries.
     * @param borders the list of borders
     */
    private void insertConnectionBetweenCountries(List<Border> borders) {
        for (Border border : borders) {
            this.graph.addEdge(border.getCountry1(), border.getCountry2(), calculateWeight(border.getCountry1(), border.getCountry2()));
        }
    }

    /**
     * Inserts the connection between ports of the same country.
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
     * @param n the desired number of closest ports
     */
    private void insertConnectionBetweenNClosestPorts(List<Port> ports, List<SeaDist> seaDists, int n) {
        seaDists.removeIf(s -> s.getFrom_country().equals(s.getTo_country()));
        List<List<SeaDist>> listaDeLista = new ArrayList<>();
        for (Port p : ports) {
            List<SeaDist> seadistFromSamePort = new ArrayList<>();
            for (SeaDist s : seaDists) {
                if (p.equals(s.getFrom_port()))
                    seadistFromSamePort.add(s);
                Collections.sort(seadistFromSamePort);
                if (!seadistFromSamePort.isEmpty())
                    listaDeLista.add(seadistFromSamePort);
            }
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
