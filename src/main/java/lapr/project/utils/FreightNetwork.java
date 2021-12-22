package lapr.project.utils;

import lapr.project.data.*;
import lapr.project.model.*;
import lapr.project.utils.graph.MatrixGraph;

import java.util.*;

public class FreightNetwork {
    /**
     * The graph
     */
    private final MatrixGraph<Place, Double> graph;
    /**
     * The list of countries
     */
    private List<Country> countries;
    /**
     * The list of ports
     */
    private List<Port> ports;
    /**
     * The list of borders between countries
     */
    private List<Border> borders;
    /**
     * The list of seadists
     */
    private List<SeaDist> seaDists;

    /**
     * Constructor of the Freight Network, with no received parameters.
     */
    public FreightNetwork() {
        this.graph = new MatrixGraph<>(false);
    }

    /**
     * Gets all the data needed (list of countries, ports, borders and seadists) from the database.
     *
     * @param cf the database connection factory
     */
    public void getDataFromDatabase(ConnectionFactory cf) {
        this.countries = new CountryStoreDB().getCountriesFromDatabase(cf.getDatabaseConnection());
        this.ports = new PortStoreDB().getPortsFromDatabase(cf.getDatabaseConnection());
        this.borders = new BorderStoreDB().getAllBorders(cf.getDatabaseConnection());
        this.seaDists = new SeadistStoreDB().getAllSeadists(cf.getDatabaseConnection());
    }

    /**
     * Builds the network.
     *
     * @param n the desired number to be used for the n closest ports search.
     */
    public void buildFreightNetwork(int n) {
        insertConnectionBetweenCountries();
        insertConnectionBetweenPortsSameCountry();
        insertConnectionBetweenCapitalAndClosestPort();
        insertConnectionBetweenNClosestPorts(n);
    }

    /**
     * Inserts the connection between countries.
     */
    private void insertConnectionBetweenCountries() {
        for (Border border : this.borders) {
            this.graph.addEdge(border.getCountry1(), border.getCountry2(), calculateWeight(border.getCountry1(), border.getCountry2()));
        }
    }

    /**
     * Inserts the connection between ports of the same country.
     */

    private void insertConnectionBetweenPortsSameCountry() {
        for (Port p1 : this.ports) {
            for (Port p2 : this.ports) {
                if (!p1.equals(p2)) {
                    if (p1.getCountry().equals(p2.getCountry()))
                        this.graph.addEdge(p1, p2, calculateWeight(p1, p2));
                }
            }
        }
    }

    /**
     * Inserts the connection between the capital of the country and the closest port to it.
     */
    private void insertConnectionBetweenCapitalAndClosestPort() {
        Map<Country, PortTree> portsByCountry = new HashMap<>();
        for (Country country : this.countries) {
            List<Port> portsOfCountry = new ArrayList<>();
            for (Port port : this.ports) {
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
     * @param n the desired number of closest ports
     */
    private void insertConnectionBetweenNClosestPorts(int n) {
        this.seaDists.removeIf(s -> s.getFrom_country().equals(s.getTo_country()));
        List<List<SeaDist>> listaDeLista = new ArrayList<>();
        for (Port p : this.ports) {
            List<SeaDist> seadistFromSamePort = new ArrayList<>();
            for (SeaDist s : this.seaDists) {
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
     * Sets the list of countries.
     */
    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    /**
     * Sets the list of ports.
     */
    public void setPorts(List<Port> ports) {
        this.ports = ports;
    }

    /**
     * Sets the list of borders.
     */
    public void setBorders(List<Border> borders) {
        this.borders = borders;
    }

    /**
     * Sets the list of seadists.
     */
    public void setSeaDists(List<SeaDist> seaDists) {
        this.seaDists = seaDists;
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

    @Override
    public String toString() {
        return this.graph.toString();
    }
}
