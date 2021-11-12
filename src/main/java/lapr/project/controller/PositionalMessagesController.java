package lapr.project.controller;

import lapr.project.mappers.ShipMapper;
import lapr.project.mappers.dto.ShipDTO;
import lapr.project.model.AISMessage;
import lapr.project.model.Ship;
import lapr.project.stores.ShipStore;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PositionalMessagesController {
    private final ShipStore store;

    public PositionalMessagesController(ShipStore store) {
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

    public List<AISMessage> getPositionalMessagesAtDate(Ship ship, LocalDateTime date) {
        return ship.getAISAtDate(date);
    }

    public List<AISMessage> getPositionalMessagesBetweenTwoDates(Ship ship, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return ship.getAISBetweenTwoDates(startDateTime, endDateTime);
    }
}
