package lapr.project.controller;

import lapr.project.data.ClossenessPlaces;
import lapr.project.model.Company;
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

    private final Company company;

    /**
     * controller initializer
     * @param p place of reference
     * @param n number of places to be listed
     */
    public ClossenessPlacesController(Company company, Place p, int n){
        this.company = company;
        this.nPlaces = n;
        this.p = p;
        this.clossenessPlaces = new ClossenessPlaces(company.getFreightNetwork(),this.p,this.nPlaces);
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
    public void generateList(){
        this.clossenessPlaces.getClossenessPlacesNetwork();
        this.placesList = this.clossenessPlaces.getClosestPlaces();
    }

    /**
     * get data class
     * @return data class
     */
    public ClossenessPlaces getClossenessPlaces(){return this.clossenessPlaces;}

    /**
     * get closest places list
     * @return list
     */
    public List<Pair<Place,Double>> getClosestPlacesList() {
        if (this.placesList == null) {
            generateList();
        } else {
            return this.placesList;
        }
        return this.placesList;
    }


}
