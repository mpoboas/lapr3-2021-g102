package lapr.project.utils;

import lapr.project.data.*;
import lapr.project.model.*;
import lapr.project.utils.graph.MatrixGraph;

import java.util.*;

public class FreightNetwork {

    private final MatrixGraph<Place, Double> graph;


    private List<Country> countries;
    private List<Port> ports;
    private List<Border> borders;
    private List<SeaDist> seaDists;

    public FreightNetwork() {
        graph = new MatrixGraph<>(false);
    }

    public void getDataFromDatabase(ConnectionFactory cf) {
        countries = new CountryStoreDB().getCountriesFromDatabase(cf.getDatabaseConnection());
        ports = new PortStoreDB().getPortsFromDatabase(cf.getDatabaseConnection());
        borders = new BorderStoreDB().getAllBorders(cf.getDatabaseConnection());
        seaDists = new ArrayList<>(); /*new SeadistStoreDB().*/
    }

    public void buildFreightNetwork(int n){
        insertConnectionBetweenCountries();
        insertConnectionBetweenPortsSameCountry();
        insertConnectionBetweenCapitalAndClosestPort();
        //insertConnectionBetweenNClosestPorts(n);
    }

    private void insertConnectionBetweenCountries() {
        for (Border border : borders) {
            graph.addEdge(border.getCountry1(), border.getCountry2(), calculateWeight(border.getCountry1(), border.getCountry2()));
        }
    }

    private void insertConnectionBetweenPortsSameCountry() {
        for (Port p1 : ports) {
            for (Port p2 : ports) {
                if (!p1.equals(p2)) {
                    if (p1.getCountry().equals(p2.getCountry()))
                        graph.addEdge(p1, p2, calculateWeight(p1,p2));
                }
            }
        }
    }

    private void insertConnectionBetweenCapitalAndClosestPort(){
        Map<Country,PortTree> portsByCountry = new HashMap<>();
        for (Country country:countries) {
            List<Port> portsOfCountry = new ArrayList<>();
            for (Port port:ports) {
                if (port.getCountry().equals(country.getCountry()))
                    portsOfCountry.add(port);
            }

            if (!portsOfCountry.isEmpty()){
                PortTree tree = new PortTree();
                tree.buildKDTree(portsOfCountry);
                portsByCountry.put(country,tree);
            }
        }
        for (Country s : portsByCountry.keySet()) {
            Port closest = portsByCountry.get(s).findNearestNeighbour(s.getLongitude(), s.getLatitude());
            graph.addEdge(s, closest, calculateWeight(s,closest));
        }
    }
/*
    private void insertConnectionBetweenNClosestPorts(int n){
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
                graph.addEdge(actual.getFrom_port(), actual.getTo_port(),(double) actual.getSeaDistance());
                i++;
            }
        }
    }*/

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }
    public void setPorts(List<Port> ports) {
        this.ports = ports;
    }
    public void setBorders(List<Border> borders) {
        this.borders = borders;
    }
    public void setSeaDists(List<SeaDist> seaDists) {
        this.seaDists = seaDists;
    }


    private double calculateWeight(Place place1,Place place2){
        return calculateWeight(place1.getLatitude(),place1.getLongitude(),place2.getLatitude(), place1.getLongitude());
    }

    private double calculateWeight(Double latitude1, Double longitude1,Double latitude2, Double longitude2){
        double cat2 = (longitude1 - longitude2)* Math.cos(((latitude1 *Math.PI /180)+ (latitude2*Math.PI /180))/2);;
        double cat1 = latitude1 - latitude2;
        double result = Math.sqrt((cat1 * cat1) + (cat2 * cat2)) * 60.04;

        return Conversions.nauticalMilesToKm(result);
    }
    
}
