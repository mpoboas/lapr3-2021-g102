package lapr.project.controller;

import lapr.project.model.Ship;
import lapr.project.stores.ShipStore;

import java.time.LocalDateTime;
import java.util.*;

public class listShipsController {
    Map<Ship, List<Double>> shipListMap;
    ShipStore shipStore;
    private int nShips;
    private final int op;
    /**
     * Checks which field is going to be ordered in the list 0 for distance, 1 for n movements
     */
    private int criteria;
    /**
     * chacks of order is ascending or descending
     */
    private final boolean asc;
    private LocalDateTime start;
    private LocalDateTime end;

    /**
     *
     * @param asc is order ascending
     * @param criteria field to order list, 0 for distance, 1 for n movements
     */
    public listShipsController(boolean asc, int criteria){
        shipStore = new ShipStore();
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
    public listShipsController(int nships, LocalDateTime start, LocalDateTime end){
        this.nShips = nships;
        shipStore = new ShipStore();
        op = 1;
        this.start = start;
        this.end=end;
        this.asc =false;
        this.shipListMap = generateMap(getShipList());

    }

    /**
     *
     * @return a list of all ships stored
     */
    private List<Ship> getShipList(){
        Iterable <Ship> shipListI = shipStore.getListOfShipsASCOrd();
        List<Ship> shipList = new ArrayList<>();
        shipListI.forEach(shipList::add);
        return shipList;
    }

    /**
     *
     * @param shipList ship hash map to be ordered
     * @param asc is the order ascending
     */
    private void orderByNmovements(Map<Ship, List<Double>>  shipList , boolean asc) {
        List<Map.Entry<Ship, List<Double>>> list = new LinkedList<>(shipList.entrySet());
        if (this.asc) {
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
    private Map<Ship, List<Double>> getTopN(Map<Ship, List<Double>>  shipList){
        Map <Ship, List<Double>> list = new HashMap<>();
        for(int i = 0 ; i< this.nShips; i++){
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
    private void orderByTotalDistance(Map<Ship, List<Double>>  shipList, boolean asc){
        List<Map.Entry<Ship, List<Double>>> list = new LinkedList<>(shipList.entrySet());
        if (this.asc) {
            list.sort(Comparator.comparing(shipListEntry -> shipListEntry.getValue().get(0)));
        }else{
            list.sort((shipListEntry, t1) -> t1.getValue().get(0).compareTo(shipListEntry.getValue().get(0)));
        }
        shipList = new HashMap<>();
        for (Map.Entry<Ship, List<Double>> shipListEntry : list) {
            shipList.put(shipListEntry.getKey(), shipListEntry.getValue());
        }
    }

    /**
     * groups ship map by vessel tyoe
     * @param shipList hash map of ships yet ot be grouped by vessel
     */
    private void groupVesselType(Map<Ship, List<Double>>  shipList){

    }

    /**
     *
     * @param shipList receives the list of all ships
     * @return A ship map metting requiremnts by the user
     */
    private Map<Ship, List<Double>> generateMap(List<Ship> shipList){
        Map<Ship, List<Double>> shipMap = new HashMap<>();
        for (Ship ship : shipList) {
            List<Double> Params = new ArrayList();
            if (op == 0) {
                Params.add(ship.getTotalDistance());
                Params.add((double) ship.numberOfMovements());
                Params.add(ship.deltaDistance());
            }
            if (op == 1) {
                Params.add(ship.getDistBetweenDates(start, end));
                Params.add(ship.getAvgSpeed(Params.get(0), ship.getTimeInSeaBetweenDates(start, end)));
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
}




