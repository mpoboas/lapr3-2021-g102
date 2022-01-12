package lapr.project.model;

import lapr.project.stores.FreightNetwork;

import java.util.LinkedList;
import java.util.List;

public class ShortestPath {
    private final Place vOrig;
    private final Place vDest;
    private final List<Place> listPlaces;
    private final LinkedList<Place> pathNplaces;
    private final LinkedList<Place> seaPath;
    private final LinkedList<Place> landPath;
    private final LinkedList<Place> directPath;
    private final FreightNetwork network;
    private final ClossenessPlaces cp;

    public ShortestPath(Place vOrig, Place vDest, List<Place> places, FreightNetwork network) {
        this.vOrig = vOrig;
        this.vDest = vDest;
        this.listPlaces = places;
        this.pathNplaces = new LinkedList<>();
        this.seaPath = new LinkedList<>();
        this.landPath = new LinkedList<>();
        this.directPath = new LinkedList<>();
        this.network = network;
        this.cp = new ClossenessPlaces(this.network, this.vOrig, 1);
    }

    private LinkedList<Place> pathBetweenVertex(Place vOrig, Place vDest) {
        LinkedList<Place> pathBetweenVertx = new LinkedList<>();
        this.network.shortestPath(vOrig, vDest, pathBetweenVertx);
        return pathBetweenVertx;
    }

    private Place getNextPlace(Place currentPlace, List<Place> remainingPlaces) {
        return this.cp.getClossestPlaceFromList(currentPlace, remainingPlaces);
    }

    private void createPathNPlaces() {
        Place currentPlace = vOrig;
        Place finalPlace = vDest;
        List<Place> listOfPlace = this.listPlaces;
        Place nextPlace;
        for (int i = 0; i < listOfPlace.size(); i++) {
            nextPlace = getNextPlace(currentPlace, listOfPlace);
            this.pathNplaces.addAll(pathBetweenVertex(currentPlace, nextPlace));
            listOfPlace.remove(nextPlace);
            currentPlace = nextPlace;
        }
        this.pathNplaces.addAll(pathBetweenVertex(currentPlace, finalPlace));
    }

    private void seaPath() {
        this.network.shortestPathSea(this.vOrig, this.vDest, this.seaPath);
    }

    private void landPath() {
        this.network.shortestPathSea(this.vOrig, this.vDest, this.landPath);
    }

    private void directPath() {
        this.network.shortestPath(this.vOrig, this.vDest, this.directPath);
    }
    public LinkedList<Place> getSeaPath() {
        if (this.pathNplaces.isEmpty()) {
            seaPath();
        }
        return this.seaPath;
    }

    public LinkedList<Place> getLandPath() {
        if (this.landPath.isEmpty()) {
            landPath();
        }
        return this.landPath;
    }
    public LinkedList<Place> getDirectPath() {
        if (this.directPath.isEmpty()) {
            directPath();
        }
        return this.directPath;
    }
    public LinkedList<Place> getpathNplaces() {
        if (this.pathNplaces.isEmpty()) {
            createPathNPlaces();
        }
        return this.pathNplaces;
    }
}
