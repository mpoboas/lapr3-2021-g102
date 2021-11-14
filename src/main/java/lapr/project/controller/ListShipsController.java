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
    private List<Map.Entry<Ship, List<Double>>> linkedShipList;

    /**
     *
     * @param asc is order ascending
     * @param criteria field to order list, 0 for distance, 1 for n movements
     */
    public ListShipsController(boolean asc, int criteria, ShipStore shipStore){
        this.shipStore = shipStore;
        this.op = 0;
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
        this.op = 1;
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
    List<Map.Entry<Ship, List<Double>>> orderByNmovements(Map<Ship, List<Double>>  shipList , boolean asc) {
        List<Map.Entry<Ship, List<Double>>> list = new LinkedList<>(shipList.entrySet());
        if (asc) {
            list.sort(Comparator.comparing(shipListEntry -> shipListEntry.getValue().get(1)));
        }else{
            list.sort((shipListEntry, t1) -> t1.getValue().get(1).compareTo(shipListEntry.getValue().get(1)));
        }
        return list;

    }

    /**
     *
     * @param shipList hashmap with every ship
     * @return top n ships of the hashmap
     */
     Map<Ship, List<Double>> getTopN(Map<Ship, List<Double>>  shipList){
        Map <Ship, List<Double>> list = new HashMap<>();
        if(this.nShips > shipList.size()){
            this.nShips = shipList.size();
        }
        for(int i = 0 ; i< this.nShips  ; i++){
            Map.Entry<Ship, List<Double>> entry = shipList.entrySet().iterator().next();
            list.put(entry.getKey(),entry.getValue());
            shipList.remove(entry.getKey());
        }
        return list;
    }

    List<Map.Entry<Ship, List<Double>>> getShipLinkedList(){
         return this.linkedShipList;
    }

    /**
     *
     * @param shipList hash map of ships yet to be ordered
     * @param asc is order ascending
     */
    List<Map.Entry<Ship, List<Double>>> orderByTotalDistance(Map<Ship, List<Double>>  shipList, boolean asc){
        List<Map.Entry<Ship, List<Double>>> list = new LinkedList<>(shipList.entrySet());
        if (asc) {
            list.sort(Comparator.comparing(shipListEntry -> shipListEntry.getValue().get(0)));
        }else{
            list.sort((shipListEntry, t1) -> t1.getValue().get(0).compareTo(shipListEntry.getValue().get(0)));
        }

        return list;
    }

    /**
     * groups ship map by vessel type
     * @param list hash map of ships yet ot be grouped by vessel
     */
    List<Map.Entry<Ship, List<Double>>> groupVesselType(List<Map.Entry<Ship, List<Double>>> list){
        list.sort(Comparator.comparing(shipListEntry -> shipListEntry.getKey().getVesselType()));
        return list;
    }

    /**
     *
     * @param shipList receives the list of all ships
     * @return A ship map meeting requirements by the user
     */
     Map<Ship, List<Double>> generateMap(List<Ship> shipList){
        Map<Ship, List<Double>> shipMap = new HashMap<>();
        for (Ship ship : shipList) {
            List<Double> Params = new ArrayList<>();
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
            if(criteria == 0){this.linkedShipList=orderByTotalDistance(shipMap,asc);}
            if(criteria == 1){this.linkedShipList=orderByNmovements(shipMap, asc);}
        }
        if(op==1){
            this.linkedShipList=orderByTotalDistance(shipMap, asc);
            if(this.linkedShipList.size()> this.nShips &&this.linkedShipList.size() - 1 > this.nShips ) {
                    linkedShipList.subList(this.nShips + 1, this.linkedShipList.size()).clear();
            }
            this.linkedShipList =groupVesselType(this.linkedShipList);
        }
        return shipMap;
    }

    /**
     *
     * @return A string with the required ships
     */
    public String toString() {
         StringBuilder string = new StringBuilder("List of ships\n");
         String sep = "\t|\t";
        if (op == 0) {
            string.append("|\tMMSI\t|\tNumber of movements\t|\tTravelled distance\t|\tDelta Distance\t|\n");
            for (Map.Entry<Ship, List<Double>> shipListEntry : linkedShipList) {
                string.append("|\t").append(shipListEntry.getKey().getMMSI()).append(sep).append(shipListEntry.getValue().get(1)).append(sep).append(shipListEntry.getValue().get(0)).append(sep).append(shipListEntry.getValue().get(2)).append("\t|\n");
            }
        }
        if(op == 1) {
            string.append("|\tMMSI\t|\tVessel type\t|\tVessel Name\t|\tKilometers Traveled\t|\tMeanSOG\t|\n ");
            for(int i = 0; i< linkedShipList.size() && i< this.nShips;i++){
                string.append("|\t").append(linkedShipList.get(i).getKey().getMMSI()).append(sep).append(linkedShipList.get(i).getKey().getVesselType()).append(sep).append(linkedShipList.get(i).getKey().getVesselName()).append(sep).append(linkedShipList.get(i).getValue().get(0)).append(sep).append(linkedShipList.get(i).getValue().get(1)).append("\t|\n");
            }
        }
        return string.toString();
    }
}




