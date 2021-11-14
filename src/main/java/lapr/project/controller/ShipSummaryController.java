package lapr.project.controller;

import lapr.project.model.Ship;
import lapr.project.stores.ShipStore;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ShipSummaryController {

    private final ShipStore store;

    public ShipSummaryController(ShipStore store) {
        this.store = store;
    }

    public Ship getShip(String code) {
        Ship ship;
        int mmsi;
        try {
            mmsi = Integer.parseInt(code);
            ship = store.getShipByMMSI(mmsi);
        } catch (NumberFormatException e) {
            if (code.charAt(0) == 'I' && code.charAt(1) == 'M' && code.charAt(2) == 'O') {
                ship = store.getShipByIMO(code);
            } else ship = store.getShipByCallSign(code);
        }
        return ship;
    }

    public void getShipSummary(Ship ship) {

        int MMSI = ship.getMMSI();
        String vesselName = ship.getVesselName();
        LocalDateTime startBDT = ship.getStartBDT();
        LocalDateTime endBDT = ship.getEndBDT();
        double totalMovementTime = ship.getTotalTimeinSea2();
        double totalMovements = ship.numberOfMovements();
        double maxSOG = ship.getMaxSOG();
        double maxCOG = ship.getMaxCOG();
        double meanSOG = ship.getMeanSOG();
        double meanCOG = ship.getMeanCOG();
        double departureLatitude = ship.getDepartureLatitude();
        double departureLongitude = ship.getDepartureLongitude();
        double arrivalLatitude = ship.getArrivalLatitude();
        double arrivalLongitude = ship.getArrivalLongitude();
        double totalDistance = ship.getTotalDistance2();
        double deltaDistance = ship.deltaDistance();

        System.out.println("Ship's Summary{" +
                "MMSI=" + MMSI +
                ", Vessel Name='" + vesselName + '\'' +
                ", Start Base Date Time='" + startBDT + '\'' +
                ", End Base Date Time='" + endBDT + '\'' +
                ", Total Movement Time=" + totalMovementTime +
                ", Total Number of Movements=" + totalMovements +
                ", Max SOG=" + maxSOG +
                ", Max COG=" + maxCOG +
                ", Mean SOG=" + meanSOG +
                ", Mean COG=" + meanCOG +
                ", Total Distance=" + totalDistance +
                ", Delta Distance=" + deltaDistance +
                ", Departure Latitude=" + departureLatitude +
                ", Departure Longitude=" + departureLongitude +
                ", Arrival Latitude=" + arrivalLatitude +
                ", Arrival Longitude=" + arrivalLongitude +
                '}' + '\n');
    }



}
