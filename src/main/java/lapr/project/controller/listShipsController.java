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
     * @param asc
     * @param criteria
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
     * @param nships
     * @param start
     * @param end
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
     * @return
     */
    private List<Ship> getShipList(){
        Iterable <Ship> shipListI = shipStore.getListOfShipsASCOrd();
        List<Ship> shipList = new ArrayList<>();
        shipListI.forEach(shipList::add);
        return shipList;
    }

    /**
     *
     * @param shipList
     * @param asc
     */

    private void orderByNmovements(Map<Ship, List<Double>>  shipList , boolean asc) {
        List<Map.Entry<Ship, List<Double>>> list = new LinkedList<Map.Entry<Ship, List<Double>>>(shipListMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Ship, List<Double>>>() {
            @Override
            public int compare(Map.Entry<Ship, List<Double>> shipListEntry, Map.Entry<Ship, List<Double>> t1) {
                return t1.getValue().get(1).compareTo(shipListEntry.getValue().get(1));
            }
        });
        shipList = new HashMap<>();
        for (int i = 0 ; i < list.size() ; i++) {
            shipList.put(list.get(i).getKey(),list.get(i).getValue());
        }
    }

    /**
     *
     * @param shipList
     * @return
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
     * @param shipList
     * @param asc
     */
    private void orderByTotalDistance(Map<Ship, List<Double>>  shipList, boolean asc){
        List<Map.Entry<Ship, List<Double>>> list = new LinkedList<Map.Entry<Ship, List<Double>>>(shipListMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Ship, List<Double>>>() {
            @Override
            public int compare(Map.Entry<Ship, List<Double>> shipListEntry, Map.Entry<Ship, List<Double>> t1) {
                return shipListEntry.getValue().get(0).compareTo(t1.getValue().get(0));
            }
        });
        shipList = new HashMap<>();
        for (int i = 0 ; i < list.size() ; i++) {
            shipList.put(list.get(i).getKey(),list.get(i).getValue());
        }
    }

    /**
     * groups ship map by vessel tyoe
     * @param shipList
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
        for(int i = 0;i < shipList.size();i++){
            List<Double> Params = new ArrayList();
            Params.add(shipList.get(i).getTotalDistance());
            if (op == 0){
                Params.add(shipList.get(i).getTotalDistance());
                Params.add((double)shipList.get(i).numberOfMovements());
                Params.add(shipList.get(i).deltaDistance());
            }
            if(op == 1){
                Params.add(shipList.get(i).getDistBetweenDates(start,end));
                Params.add(shipList.get(i).getAvgSpeed(Params.get(0),shipList.get(i).getTimeInSeaBetweenDates(start,end)));
            }
            shipMap.put(shipList.get(i), Params);
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




