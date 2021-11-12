package lapr.project.controller;

import lapr.project.model.Ship;
import lapr.project.stores.ShipStore;

import java.time.LocalDateTime;
import java.util.*;

import static lapr.project.utils.Conversions.nauticalMilesToKm;


public class ListShipsController {
    private final Map<Ship, List<Double>> shipListMap;
    private final ShipStore shipStore;
    private int nShips;
    private final int op;
    /**
     * Checks which field is going to be ordered in the list 0 for distance, 1 for n movements
     */
    private int criteria;
    /**
     * checks of order is ascending or descending
     */
    private final boolean asc;
    private LocalDateTime start;
    private LocalDateTime end;

    /**
     *
     * @param asc is order ascending
     * @param criteria field to order list, 0 for distance, 1 for n movements
     */
    public ListShipsController(boolean asc, int criteria, ShipStore shipStore){
        this.shipStore = shipStore;
        op = 0;
        this.criteria = criteria;
        this.asc = asc ;
        this.shipListMap = generateMap(getShipList());
    }

    /**
     *
     * @param nships number of ships to be listed
     * @param start start date required by user
     * @param end end date required by user
     */
    public ListShipsController(int nships, LocalDateTime start, LocalDateTime end, ShipStore shipStore){
        this.nShips = nships;
        op = 1;
        this.start = start;
        this.end=end;
        this.asc =false;
        this.shipStore = shipStore;
        this.shipListMap = generateMap(getShipList());

    }

    public Map<Ship, List<Double>> getShipMap(){return shipListMap;}


    /**
     *
     * @return a list of all ships stored
     */
     List<Ship> getShipList(){
        return shipStore.getShipList();
    }

    /**
     *
     * @param shipList ship hash map to be ordered
     * @param asc is the order ascending
     */
     void orderByNmovements(Map<Ship, List<Double>>  shipList , boolean asc) {
        List<Map.Entry<Ship, List<Double>>> list = new LinkedList<>(shipList.entrySet());
        if (asc) {
            list.sort(Comparator.comparing(shipListEntry -> shipListEntry.getValue().get(1)));
        }else{
            list.sort((shipListEntry, t1) -> t1.getValue().get(1).compareTo(shipListEntry.getValue().get(1)));
        }
        shipList = new HashMap<>();
        for (Map.Entry<Ship, List<Double>> shipListEntry : list) {
            shipList.put(shipListEntry.getKey(), shipListEntry.getValue());
        }
    }

    /**
     *
     * @param shipList hashmap with every ship
     * @return top n ships of the hashmap
     */
     Map<Ship, List<Double>> getTopN(Map<Ship, List<Double>>  shipList){
        Map <Ship, List<Double>> list = new HashMap<>();
        for(int i = 0 ; i< this.nShips  ; i++){
            Map.Entry<Ship, List<Double>> entry = shipList.entrySet().iterator().next();
            list.put(entry.getKey(),entry.getValue());
            shipList.remove(entry.getKey());
        }
        return list;
    }

    /**
     *
     * @param shipList hash map of ships yet to be ordered
     * @param asc is order ascending
     */
     void orderByTotalDistance(Map<Ship, List<Double>>  shipList, boolean asc){
        List<Map.Entry<Ship, List<Double>>> list = new LinkedList<>(shipList.entrySet());
        if (asc) {
            list.sort(Comparator.comparing(shipListEntry -> shipListEntry.getValue().get(0)));
        } else {
            list.sort((shipListEntry, t1) -> t1.getValue().get(0).compareTo(shipListEntry.getValue().get(0)));
        }
        shipList = new HashMap<>();
        for (Map.Entry<Ship, List<Double>> shipListEntry : list) {
            shipList.put(shipListEntry.getKey(), shipListEntry.getValue());
        }
    }

    /**
     * groups ship map by vessel type
     * @param shipList hash map of ships yet ot be grouped by vessel
     */
     void groupVesselType(Map<Ship, List<Double>>  shipList){
        List<Map.Entry<Ship, List<Double>>> list = new LinkedList<>(shipList.entrySet());
        list.sort(Comparator.comparing(shipListEntry -> shipListEntry.getKey().getVesselType()));
        shipList = new HashMap<>();
        for (Map.Entry<Ship, List<Double>> shipListEntry : list) {
            shipList.put(shipListEntry.getKey(), shipListEntry.getValue());
        }
    }

    /**
     *
     * @param shipList receives the list of all ships
     * @return A ship map meeting requirements by the user
     */
     Map<Ship, List<Double>> generateMap(List<Ship> shipList){
        Map<Ship, List<Double>> shipMap = new HashMap<>();
        for (Ship ship : shipList) {
            List<Double> Params = new ArrayList();
            if (op == 0) {
                Params.add(nauticalMilesToKm(ship.getTotalDistance()));
                Params.add((double) ship.numberOfMovements());
                Params.add(nauticalMilesToKm(ship.deltaDistance()));
            }
            if (op == 1) {
                Params.add(nauticalMilesToKm(ship.getDistBetweenDates(start, end)));
                Params.add(nauticalMilesToKm(ship.getAvgSpeed(Params.get(0), ship.getTimeInSeaBetweenDates(start, end))));
            }
            shipMap.put(ship, Params);
        }
        if(op==0){
            if(criteria == 0){orderByTotalDistance(shipMap,asc);}
            if(criteria == 1){orderByNmovements(shipMap, asc);}
        }
        if(op==1){
            orderByTotalDistance(shipMap, asc);
            shipMap = getTopN(shipMap);
            groupVesselType(shipMap);
        }
        return shipMap;
    }

    /**
     *
     * @return A string with the required ships
     */
    public String toString() {
         StringBuilder string = new StringBuilder("List of ships\n");
        if (op == 0) {
            string.append("|\tMMSI\t|\tNumber of movements\t|\tTravelled distance\t|\tDelta Distance\t|\n");
            for( Map.Entry<Ship, List<Double>> entry : shipListMap.entrySet() ){
                string.append("|\t").append(entry.getKey().getMMSI()).append("\t|\t").append(entry.getValue().get(1)).append("\t|\t").append(entry.getValue().get(0)).append("\t|\t").append(entry.getValue().get(2)).append("\t|\n");
            }
        }
        if(op == 1) {
            string.append("|\tMMSI\t|\tVessel Type\t|\tKilometers Traveled\t|\tMeanSOG\t|\n ");
            for( Map.Entry<Ship, List<Double>> entry : shipListMap.entrySet() ){
                string.append("|\t").append(entry.getKey().getMMSI()).append("\t|\t").append(entry.getKey().getVesselName()).append("\t|\t").append(entry.getValue().get(0)).append("\t|\t").append(entry.getValue().get(1)).append("\t|\n");
            }
        }
        return string.toString();
    }
}




