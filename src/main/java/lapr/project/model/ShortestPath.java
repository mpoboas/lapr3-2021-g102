package lapr.project.model;

import lapr.project.stores.FreightNetwork;
import lapr.project.utils.Pair;

import java.util.ArrayList;
import java.util.Collections;
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

    /**
     *
     * @param vOrig place of origin
     * @param vDest place of destiny
     * @param places place with obligatory passage
     * @param network freigth network
     */

    public ShortestPath(Place vOrig, Place vDest, List<Place> places, FreightNetwork network) {
        this.vOrig = vOrig;
        this.vDest = vDest;
        this.listPlaces = places;
        this.pathNplaces = new LinkedList<>();
        this.seaPath = new LinkedList<>();
        this.landPath = new LinkedList<>();
        this.directPath = new LinkedList<>();
        this.network = network;
    }

    /**
     *
     * @param vOrig place of origin
     * @param vDest place of destiny
     * @param pathBetweenVertx Path list
     * @return path distance
     */
    private double pathBetweenVertex(Place vOrig, Place vDest,LinkedList<Place> pathBetweenVertx) {
        return this.network.shortestPath(vOrig, vDest, pathBetweenVertx);
    }

    /**
     * method to swap elements in linked list
     * @param list list
     * @param place1 place to be swithhed
     * @param place2 place to be swithhed
     */
    private void swapElements(LinkedList<Place> list, Place place1, Place place2){
        int indexPlace1 = list.indexOf(place1);
        int indexPlace2 = list.indexOf(place2);
        if (indexPlace1 == -1 || indexPlace2 == -1) {
            return;
        }
        Collections.swap(list,indexPlace1,indexPlace2);
    }

    /**
     *
     * @return list of all permutations of list of places
     */
    public List<LinkedList<Place>> allPossibleOrdersOfVisit(){
        List<LinkedList<Place>> possibleCombinations = new ArrayList<>();
        LinkedList<Place> origList = new LinkedList<>(this.listPlaces);
        possibleCombinations.add(origList);
        boolean leftToRight = true;
        int listIndex = 1;
        LinkedList<Place> list = new LinkedList<>();
        if(this.listPlaces.size()==1){
            return possibleCombinations;
        }
        if(this.listPlaces.size()==2){
            list.addAll(possibleCombinations.get(0));
            swapElements(list,list.getFirst(),list.getLast());
            possibleCombinations.add(list);
            return possibleCombinations;
        }
        for(int i = 1 ; i < getFactorial(this.listPlaces.size()-1)+1; i++) {
            for(int k = 0; k < this.listPlaces.size()-1; k++){
                possibleCombinations.add(new LinkedList<>(possibleCombinations.get(listIndex-1)));
                if (leftToRight){
                    swapElements(possibleCombinations.get(listIndex),possibleCombinations.get(listIndex).get(this.listPlaces.size()-1-k),possibleCombinations.get(listIndex).get(this.listPlaces.size()-2-k));
                }else{
                    swapElements(possibleCombinations.get(listIndex),possibleCombinations.get(listIndex).get(k),possibleCombinations.get(listIndex).get(k+1));
                }
                ++listIndex;
            }
            possibleCombinations.add(new LinkedList<>(possibleCombinations.get(listIndex-1)));
            if(leftToRight){
                swapElements(possibleCombinations.get(listIndex),possibleCombinations.get(listIndex).getLast(),possibleCombinations.get(listIndex).get(possibleCombinations.get(listIndex).size()-2));
            }else{
                swapElements(possibleCombinations.get(listIndex),possibleCombinations.get(listIndex).getFirst(),possibleCombinations.get(listIndex).get(1));
            }
            leftToRight = !leftToRight;
            ++listIndex;
        }
        return possibleCombinations;
    }

    /**
     * method to get the ffactorial of a number
     * @param value value
     * @return factorial
     */
    private int getFactorial(int value){
        if(value ==1)
            return 1;
        if(value ==0 || value ==-1)
            return 0;


        return value * getFactorial(value -1);
    }

    private void createPathNPlaces() {
        this.nPlacesDistance = 0;
        List<Pair<LinkedList<Place>,Double>> listOfPossibleRoutes = new ArrayList<>();
        List<LinkedList<Place>> allPossibleVisits =  allPossibleOrdersOfVisit() ;
        Place nextPlace;
        double pathDist;
        LinkedList<Place> currentPartPath = new LinkedList<>();
        for (LinkedList<Place> allPossibleVisit : allPossibleVisits) {
            LinkedList<Place> currentPath = new LinkedList<>();
            pathDist = 0;
            //get Next combo of places
            allPossibleVisit.addFirst(vOrig);
            allPossibleVisit.addLast(vDest);
            Place currentPlace = allPossibleVisit.get(0);
            for (Place comboPLace : allPossibleVisit) {
                nextPlace = comboPLace;
                currentPartPath.clear();
                pathDist = pathDist + pathBetweenVertex(currentPlace, nextPlace, currentPartPath);
                revertList(currentPartPath);
                currentPath.addAll(currentPartPath);
                currentPlace = nextPlace;
            }
            currentPartPath.clear();
            Pair<LinkedList<Place>, Double> pair = new Pair<>(currentPath, pathDist);
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
            revertList(this.seaPath);
        }catch(NullPointerException e ){
            this.seaPathDistance = 0;
        }
    }
    private void revertList(LinkedList<Place> list){
        LinkedList<Place> tempList = new LinkedList<>();
       for(int i = 0; i < list.size();i++){
           tempList.addFirst(list.get(i));
       }
       list.clear();
       list.addAll(tempList);
       tempList.clear();
    }

    private void landPath() {
        try {
        this.landPathDistance = this.network.shortestPathLand(this.vOrig, this.vDest, this.landPath);
        revertList(this.landPath);
        }catch (NullPointerException e){
            this.landPathDistance = 0;
        }
    }

    private void directPath() {
            this.directPathDistance = this.network.shortestPath(this.vOrig, this.vDest, this.directPath);
            revertList(this.directPath);
    }
    public LinkedList<Place> getSeaPath() {
        if (this.seaPath.isEmpty()) {
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
