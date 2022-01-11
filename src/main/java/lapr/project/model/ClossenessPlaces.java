package lapr.project.model;

import lapr.project.stores.FreightNetwork;
import lapr.project.utils.Pair;

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
    public ClossenessPlaces(FreightNetwork fn, Place p ,int n){
        this.nPlaces = n;
        this.p = p;
        this.network = fn;
        this.closenessPlaces = new ArrayList<>();
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
    public void getClossenessPlacesNetwork(){
        List<Place> list = new ArrayList<>(this.network.getGraph().vertices());
        for (Place place : list) {
            Pair<Place, Double> pair = new Pair<>(place, this.network.shortestPath(this.p, place));
            this.closenessPlaces.add(pair);
        }
        this.closenessPlaces.sort(this::compare);
        if(this.closenessPlaces.size()>nPlaces) {
            this.closenessPlaces.subList(this.nPlaces, this.closenessPlaces.size()).clear();
        }
        this.closenessPlaces.sort(this::compareContinents);

    }

    public Place getClossestPlaceFromList(Place currentPlace, List<Place> places){
        List<Pair<Place,Double>> placeList = new ArrayList<>();
        for (Place place : places) {
            Pair<Place, Double> pair = new Pair<>(place, this.network.shortestPath(currentPlace, place));
            placeList.add(pair);
        }
        placeList.sort(this::compare);
        return placeList.get(0).getFirst();
    }

    /**
     * method to compare distances
     * @param p1 place 1
     * @param p2 place 2
     * @return result of the comparison
     */
    private int compare(Pair<Place,Double> p1, Pair<Place,Double> p2){
        return p1.getSecond().compareTo(p2.getSecond());
    }
    private int compareContinents(Pair<Place,Double> p1, Pair<Place,Double> p2){
        return p1.getFirst().compare(p1.getFirst(),p2.getFirst());
    }


    /**
     * method to get list of clossenss places
     * @return list
     */
    public List<Pair<Place,Double>> getClosestPlaces(){
        return this.closenessPlaces;
    }

}
