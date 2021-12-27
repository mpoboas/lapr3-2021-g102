package lapr.project.controller;

import lapr.project.data.ClossenessPlaces;
import lapr.project.model.Place;
import lapr.project.utils.Pair;

import java.io.IOException;
import java.util.List;

public class ClossenessPlacesController {
    private final int nPlaces;
    private Place p;
    private final ClossenessPlaces clossenessPlaces;
    private List<Pair<Place,Double>> placesList;
    public ClossenessPlacesController(Place p, int n){
        this.nPlaces = n;
        this.p = p;
        this.clossenessPlaces = new ClossenessPlaces(this.p,this.nPlaces);
    }
    public void buildNetwork() throws IOException {
        this.clossenessPlaces.buildFreightNetworkFromDB();
    }
    public void setPort(Place p){
        this.p = p;
        clossenessPlaces.setPort(p);
    }
    private void generateList(){
        this.clossenessPlaces.getAdjPlaces();
        this.placesList = this.clossenessPlaces.getClossenessPlaces();
    }
    public ClossenessPlaces getClossenessPlaces(){return this.clossenessPlaces;}
    public List<Pair<Place,Double>> getClossenessPlacesList() {
        if (this.placesList == null) {
            generateList();
        } else {
            return this.placesList;
        }
        return this.placesList;
    }
}
