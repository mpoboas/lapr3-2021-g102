package lapr.project.controller;

import lapr.project.mappers.dto.ShipDTO;
import lapr.project.model.Ship;
import lapr.project.stores.ShipStore;

public class PositionalMessagesController {
    private final ShipStore store;

    public PositionalMessagesController(ShipStore store) {
        this.store = store;
    }

    public Ship getShip(String code) {
        int mmsi;
        try {
            mmsi = Integer.parseInt(code);
        } catch (NumberFormatException e) {
            if (code.charAt(0) == 'I' && code.charAt(1) == 'M' && code.charAt(2) == 'O')
                return store.getShipByIMO(code);
            else return store.getShipByCallSign(code);
        }
        return store.getShipByMMSI(mmsi);
    }

    public void getPositionalMessagesAtDate(Ship ship, DateTime)


}
