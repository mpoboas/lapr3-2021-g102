package lapr.project.controller;

import lapr.project.model.Ship;
import lapr.project.stores.ShipStore;

import java.time.LocalDateTime;
import java.util.*;

public class listShipsController {
    Map<Ship, List<Double>> shipListMap;
    ShipStore shipStore;
    private int nShips;
    private int op;
    private LocalDateTime start;
    private LocalDateTime end;
    public listShipsController(){
        shipStore = new ShipStore();
        op = 0;
        this.shipListMap = generateMap(getShipList());
    }
    public listShipsController(int nships, LocalDateTime start, LocalDateTime end){
        this.nShips = nships;
        shipStore = new ShipStore();
        op = 1;
        this.start = start;
        this.end=end;
        this.shipListMap = generateMap(getShipList());

    }
    private List<Ship> getShipList(){
        Iterable <Ship> shipListI = shipStore.getListOfShipsASCOrd();
        List<Ship> shipList = new ArrayList<>();
        shipListI.forEach(shipList::add);
        return shipList;
    }

    private void orderByNmovements(Map<Ship, List<Double>>  shipList ) {
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
    private Map<Ship, List<Double>> getTopN(Map<Ship, List<Double>>  shipList){
        Map <Ship, List<Double>> list = new HashMap<>();
        for(int i = 0 ; i< this.nShips; i++){
            Map.Entry<Ship, List<Double>> entry = shipList.entrySet().iterator().next();
            list.put(entry.getKey(),entry.getValue());
            shipList.remove(entry.getKey());
        }
        return list;
    }

    private void orderByTotalDistance(Map<Ship, List<Double>>  shipList){
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

    private void groupVesselType(Map<Ship, List<Double>>  shipList){

    }
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

        }
        if(op==1){
            orderByTotalDistance(shipMap);
            shipMap = getTopN(shipMap);
            groupVesselType(shipMap);
        }
        return shipMap;
    }
}




