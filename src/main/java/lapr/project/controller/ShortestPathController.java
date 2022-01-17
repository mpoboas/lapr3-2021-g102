package lapr.project.controller;

import lapr.project.model.Company;
import lapr.project.model.Place;
import lapr.project.model.ShortestPath;
import lapr.project.utils.Pair;

import java.util.LinkedList;
import java.util.List;

public class ShortestPathController {
    ShortestPath sp;
    Company comp;
    public ShortestPathController(List<Place> listPlaces, Company comp, Place pInit, Place pDest){
        this.comp = comp;
        this.sp = new ShortestPath(pInit,pDest,listPlaces,comp.getFreightNetwork());
    }
    public Pair<LinkedList<Place>,Double> getFull(){
        return new Pair<>(getFullPath(),getFullPathDist());
    }
    public Pair<LinkedList<Place>,Double> getDirect(){
        return new Pair<>(getDirectPath(),getDirectPathDist());
    }
    public Pair<LinkedList<Place>,Double> getSea(){
        return new Pair<>(getSeaPath(),getSeaPathDist());
    }
    public Pair<LinkedList<Place>,Double> getLand(){
        return new Pair<>(getLandPath(),getLandPathDist());
    }
    public double getFullPathDist(){
        return this.sp.getnPlacesDistance();
    }
    public LinkedList<Place> getFullPath(){
        return this.sp.getpathNplaces();
    }
    public double getLandPathDist(){
        return this.sp.getLandPathDistance();
    }
    public LinkedList<Place> getLandPath(){
        return this.sp.getLandPath();
    }
    public double getSeaPathDist(){
        return this.sp.getSeaPathDistance();
    }
    public LinkedList<Place> getSeaPath(){
        return this.sp.getSeaPath();
    }
    public double getDirectPathDist(){
        return this.sp.getDirectPathDistance();
    }
    public LinkedList<Place> getDirectPath(){
        return this.sp.getDirectPath();
    }
}
