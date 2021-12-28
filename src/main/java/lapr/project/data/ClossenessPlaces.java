package lapr.project.data;

import lapr.project.model.Place;
import lapr.project.stores.FreightNetwork;
import lapr.project.utils.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClossenessPlaces {
    private int nPlaces;
    private final FreightNetwork network;
    private final List<Pair<Place,Double>> closenessPlaces;
    private Place p;

    /**
     * initialize data class
     * @param p place of reference
     * @param n number of places to be listed
     */
    public ClossenessPlaces(Place p ,int n){
        this.nPlaces = n;
        this.p = p;
        this.network = new FreightNetwork();
        this.closenessPlaces = new ArrayList<>();
    }

    /**
     * buid network from database
     * @throws IOException exception
     */
    public void buildFreightNetworkFromDB() throws IOException {
        ConnectionFactory cf = new ConnectionFactory();
        this.network.buildFreightNetworkFromDatabase(cf.getDatabaseConnection(),this.nPlaces);
    }

    /**
     * get freight network
     * @return freigth network
     */
    public FreightNetwork getNetwork(){
        return this.network;
    }
    /**
     * set a new port
     * @param p new place
     */
    public void setPort(Place p){
        this.p = p;
    }
    /**
     * set a new numbe rof places
     * @param n of places
     */
    public void setnPlaces(int n){
        this.nPlaces = n;
    }

    /**
     * get clossest places to the refence place
     */
    public void getAdjPlaces(){
        List<Place> list = new ArrayList<>(this.network.getGraph().adjVertices(p));
        for (Place place : list) {
            Pair<Place, Double> pair = new Pair<>(place, getDistance(place));
            this.closenessPlaces.add(pair);
        }
        if(this.closenessPlaces.size()>nPlaces){
            this.closenessPlaces.sort(this::compare);
            if (this.closenessPlaces.size() > this.nPlaces) {
                this.closenessPlaces.subList(this.nPlaces, this.closenessPlaces.size()).clear();
            }
        }
    }

    /**
     * method to compare distances
     * @param p1 place 1
     * @param p2 place 2
     * @return result of the comparison
     */
    private int compare(Pair<Place,Double>p1, Pair<Place,Double> p2){
        return p1.getSecond().compareTo(p2.getSecond());
    }

    /**
     * method to get list of clossenss places
     * @return list
     */
    public List<Pair<Place,Double>> getClosenessPlaces(){
        return this.closenessPlaces;
    }

    /**
     * get the distance of a certain place to the refence place
     * @param p1 place to be analyzed
     * @return distnce between two
     */
    public double getDistance(Place p1){
        return this.network.getGraph().edge(this.p, p1).getWeight();
    }

}
