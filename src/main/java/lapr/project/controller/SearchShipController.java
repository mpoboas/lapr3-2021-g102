package lapr.project.controller;

import lapr.project.mappers.ShipMapper;
import lapr.project.mappers.dto.ShipDTO;
import lapr.project.model.Ship;
import lapr.project.stores.ShipStore;

import java.io.FileWriter;
import java.io.IOException;

public class SearchShipController {

    private final ShipStore store;



    public SearchShipController(ShipStore store) {
        this.store = store;
    }

    public ShipDTO searchShip(String codeInserted) {
        ShipDTO shipDTO;
        try {
            int mmsi = Integer.parseInt(codeInserted);
            shipDTO = getShipByMMSI(mmsi);
        } catch (NumberFormatException e) {
            if (codeInserted.charAt(0) == 'I' && codeInserted.charAt(1) == 'M' && codeInserted.charAt(2) == 'O')
                shipDTO = getShipByIMO(codeInserted);
            else shipDTO = getShipByCallSign(codeInserted);
        }
        return shipDTO;
    }

    public ShipDTO getShipByMMSI(int mmsi) {
        Ship ship = store.getShipByMMSI(mmsi);
        return new ShipMapper().toDTO(ship);
    }

    public ShipDTO getShipByIMO(String imo) {
        Ship ship = store.getShipByIMO(imo);
        return new ShipMapper().toDTO(ship);
    }

    public ShipDTO getShipByCallSign(String callSign) {
        Ship ship = store.getShipByCallSign(callSign);
        return new ShipMapper().toDTO(ship);
    }

}
