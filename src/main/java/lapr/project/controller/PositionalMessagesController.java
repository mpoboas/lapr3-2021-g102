package lapr.project.controller;


import lapr.project.model.AISMessage;
import lapr.project.model.Ship;
import lapr.project.stores.ShipStore;

import java.time.LocalDateTime;
import java.util.List;

public class PositionalMessagesController {
    /**
     * The Ship store
     */
    private final ShipStore store;

    /**
     * PositionalMessagesController constructor receiving the store as a parameter
     * @param store the store
     */
    public PositionalMessagesController(ShipStore store) {
        this.store = store;
    }

    /**
     * Get's a ship from a code (MMSI, IMO or CallSign)
     *
     * @param code the code
     * @return the ship
     */
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

    /**
     * Gets a ship's positional messages at a certain date
     *
     * @param ship the ship
     * @param date the date
     * @return List of positional messages at the selected date
     */
    public List<AISMessage> getPositionalMessagesAtDate(Ship ship, LocalDateTime date) {
        return ship.getAISAtDate(date);
    }

    /**
     * Gets a ship's positional messages between two dates
     *
     * @param ship the ship
     * @param startDateTime the start date
     * @param endDateTime the end date
     * @return List of positional messages between the two dates
     */
    public List<AISMessage> getPositionalMessagesBetweenTwoDates(Ship ship, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return ship.getAISBetweenTwoDates(startDateTime, endDateTime);
    }
}
