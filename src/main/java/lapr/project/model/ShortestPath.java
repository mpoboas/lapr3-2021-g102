package lapr.project.model;

import lapr.project.stores.FreightNetwork;

import java.util.LinkedList;
import java.util.List;

public class ShortestPath {
    private Place vOrig;
    private Place vDest;
    private List<Place> ListPlaces;
    private LinkedList<Place> fullPath;
    private FreightNetwork network;
    private ClossenessPlaces cp;

    public ShortestPath(Place vOrig, Place vDest, List<Place> places, FreightNetwork network){
        this.vOrig = vOrig;
        this.vDest = vDest;
        this.ListPlaces = places;
        this.fullPath = new LinkedList<>();
        this.network = network;
        this.cp = new ClossenessPlaces(this.network,this.vOrig,1);
    }
    private LinkedList<Place> pathBetweenVertex(Place vOrig,Place vDest){
        LinkedList<Place> pathBetweenVertx = new LinkedList<>();
        this.network.shortestPath(vOrig,vDest,pathBetweenVertx);
        return pathBetweenVertx;
    }
    private Place getNextPlace(Place currentPlace, List<Place> remainingPlaces){
        return this.cp.getClossestPlaceFromList(currentPlace,remainingPlaces);
    }
    private void createPath(){
        Place currentPlace = vOrig;
        Place finalPlace = vDest;
        List<Place> listOfPlace = this.ListPlaces;
        Place nextPlace;
        for(int i = 0; i < listOfPlace.size(); i++){
            nextPlace =  getNextPlace(currentPlace,listOfPlace);
            this.fullPath.addAll(pathBetweenVertex(currentPlace,nextPlace));
            listOfPlace.remove(nextPlace);
            currentPlace = nextPlace;
        }
        this.fullPath.addAll(pathBetweenVertex(currentPlace,finalPlace));
    }
    public LinkedList<Place> getFullPath(){
        if(this.fullPath.isEmpty()){
            createPath();
        }
        return this.fullPath;
    }
}
