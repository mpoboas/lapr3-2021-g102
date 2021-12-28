package lapr.project.controller;

import lapr.project.data.ClossenessPlaces;
import lapr.project.model.Place;
import lapr.project.utils.Pair;

import java.io.IOException;
import java.util.List;

public class ClossenessPlacesController {
    /**
     * number of places
     */
    private int nPlaces;
    /**
     * place of reference
     */
    private Place p;
    /**
     * data class
     */
    private final ClossenessPlaces clossenessPlaces;
    /**
     * closseness places list
     */
    private List<Pair<Place,Double>> placesList;

    /**
     * controller initializer
     * @param p place of reference
     * @param n number of places to be listed
     */
    public ClossenessPlacesController(Place p, int n){
        this.nPlaces = n;
        this.p = p;
        this.clossenessPlaces = new ClossenessPlaces(this.p,this.nPlaces);
    }

    /**
     * method to build network from database
     * @throws IOException exception
     */
    public void buildNetwork() throws IOException {
        this.clossenessPlaces.buildFreightNetworkFromDB();
    }

    /**
     * set a new port
     * @param p new place
     */
    public void setPort(Place p){
        this.p = p;
        clossenessPlaces.setPort(p);
    }

    /**
     * set a new numbe rof places
     * @param n of places
     */
    public void setnPlaces(int n){
        this.nPlaces = n;
        clossenessPlaces.setnPlaces(n);
    }

    /**
     * generate list
     */
    private void generateList(){
        this.clossenessPlaces.getAdjPlaces();
        this.placesList = this.clossenessPlaces.getClosenessPlaces();
    }

    /**
     * get data class
     * @return data class
     */
    public ClossenessPlaces getClossenessPlaces(){return this.clossenessPlaces;}

    /**
     * get clsseness places lsit
     * @return list
     */
    public List<Pair<Place,Double>> getClossenessPlacesList() {
        if (this.placesList == null) {
            generateList();
        } else {
            return this.placesList;
        }
        return this.placesList;
    }
}
