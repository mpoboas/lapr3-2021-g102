package lapr.project.model;

import lapr.project.stores.FreightNetwork;
import lapr.project.utils.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ShortestPath {
    private final Place vOrig;
    private final Place vDest;
    private final List<Place> listPlaces;
    private final LinkedList<Place> pathNplaces;
    private double nPlacesDistance;
    private final LinkedList<Place> seaPath;
    private double seaPathDistance;
    private final LinkedList<Place> landPath;
    private double landPathDistance;
    private final LinkedList<Place> directPath;
    private double directPathDistance;
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

    private double pathBetweenVertex(Place vOrig, Place vDest,LinkedList<Place> pathBetweenVertx) {
        return this.network.shortestPath(vOrig, vDest, pathBetweenVertx);
    }
    private void swapElements(LinkedList<Place> list, Place place1, Place place2){
        int indexPlace1 = list.indexOf(place1);
        int indexPlace2 = list.indexOf(place2);
        if (indexPlace1 == -1 || indexPlace2 == -1) {
            return;
        }
        list.set(indexPlace1,place2);
        list.set(indexPlace2,place1);
    }


    private List<LinkedList<Place>> allPossibleOrdersOfVisit(){
        List<LinkedList<Place>> possibleCombinations = new ArrayList<>();
        LinkedList<Place> origList = new LinkedList<>(this.listPlaces);
        possibleCombinations.add(origList);
        int switchsLast = 0;
        for(int i = 1 ; i < getFactorial(this.listPlaces.size()); i++) {
            possibleCombinations.add(possibleCombinations.get(i - 1));
            if (switchsLast % 2 == 0) {
                swapElements(possibleCombinations.get(i), possibleCombinations.get(0).getLast(), possibleCombinations.get(i).get(possibleCombinations.get(i).indexOf(possibleCombinations.get(0).getLast()) - 1));
            } else {
                swapElements(possibleCombinations.get(i), possibleCombinations.get(0).getLast(), possibleCombinations.get(i).get(possibleCombinations.get(i).indexOf(possibleCombinations.get(0).getLast()) + 1));
            }
            if ((i + 1) % this.listPlaces.size() == 0) {
                ++switchsLast;
                if((i+1) % (this.listPlaces.size()*2)==0){
                    swapElements(possibleCombinations.get(i), possibleCombinations.get(i).getFirst(), possibleCombinations.get(i).get(possibleCombinations.get(i).indexOf(possibleCombinations.get(i).getFirst()) + 1));

                }else{
                    swapElements(possibleCombinations.get(i), possibleCombinations.get(i).getLast(), possibleCombinations.get(i).get(possibleCombinations.get(i).indexOf(possibleCombinations.get(i).getLast()) - 1));
                }
            }
        }
        return possibleCombinations;
    }
    private int getFactorial(int value){
        int i = value -1;
        int res = value;
        while(i>0){
            res = res * i;
            --i;
        }
        return res;
    }

    private void createPathNPlaces() {
        this.nPlacesDistance = 0;
        Place finalPlace = vDest;
        List<Pair<LinkedList<Place>,Double>> listOfPossibleRoutes = new ArrayList<>();
        List<LinkedList<Place>> allPossibleVisits =  allPossibleOrdersOfVisit() ;
        Place nextPlace;
        double pathDist;
        LinkedList<Place> currentPartPath = new LinkedList<>();
        for(int i = 0; i < allPossibleVisits.size()  ; i++){
            Place currentPlace = vOrig;
            LinkedList<Place> currentPath = new LinkedList<>();
            pathDist = 0;
            //get Next combo of places
            LinkedList<Place> comboPLaces= allPossibleVisits.get(i);

            for(int j = 0; j < comboPLaces.size(); j++){
                nextPlace = comboPLaces.get(j);
                currentPartPath.clear();
                pathDist = pathDist + pathBetweenVertex(currentPlace, nextPlace, currentPartPath);
                currentPath.addAll(currentPartPath);
                currentPlace = nextPlace;
            }
            currentPartPath.clear();
            pathDist = pathDist + pathBetweenVertex(currentPlace, finalPlace, currentPartPath);
            currentPath.addAll(currentPartPath);
            Pair<LinkedList<Place>,Double> pair = new Pair<>(currentPath,pathDist);
            listOfPossibleRoutes.add(pair);

        }
        listOfPossibleRoutes.sort(this::compare);
        this.pathNplaces.addAll(listOfPossibleRoutes.get(0).getFirst());
        this.nPlacesDistance = listOfPossibleRoutes.get(0).getSecond();
    }
    private int compare(Pair<LinkedList<Place>,Double> p1, Pair<LinkedList<Place>,Double> p2){
        return p1.getSecond().compareTo(p2.getSecond());
    }

    private void seaPath() {
        try {
            this.seaPathDistance = this.network.shortestPathSea(this.vOrig, this.vDest, this.seaPath);
        }catch(NullPointerException e ){
            this.seaPathDistance = 0;
        }
    }

    private void landPath() {
        this.landPathDistance = this.network.shortestPathLand(this.vOrig, this.vDest, this.landPath);
    }

    private void directPath() {
        this.directPathDistance = this.network.shortestPath(this.vOrig, this.vDest, this.directPath);
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

    public double getDirectPathDistance() {
        return directPathDistance;
    }

    public double getLandPathDistance() {
        return landPathDistance;
    }

    public double getnPlacesDistance() {
        return nPlacesDistance;
    }

    public double getSeaPathDistance() {
        return seaPathDistance;
    }
}
