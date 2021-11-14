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

    public boolean searchShip(String codeInserted) {
        ShipDTO shipDTO;
        try {
            int mmsi = Integer.parseInt(codeInserted);
            shipDTO = getShipByMMSI(mmsi);
        } catch (NumberFormatException e) {
            if (codeInserted.charAt(0) == 'I' && codeInserted.charAt(1) == 'M' && codeInserted.charAt(2) == 'O')
                shipDTO = getShipByIMO(codeInserted);
            else shipDTO = getShipByCallSign(codeInserted);
        }
        String fileName = "Ship" + codeInserted + ".txt";
        return writeToFile(shipDTO, fileName);
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

    public boolean writeToFile(ShipDTO ship, String fileName) {
        boolean success = false;
        FileWriter myWriter = null;
        try {
            myWriter = new FileWriter(fileName, true);
            myWriter.write(ship.toString());
            success = true;
        } catch (IOException e) {
            System.out.println("An error ocurred in the writing the information of the ship.");
            e.printStackTrace();
        } finally {
            try {
                if (myWriter != null)
                    myWriter.close();
            } catch (IOException e) {
                System.out.println("An error ocurred in the writing the information of the ship.");
                e.printStackTrace();

            }
        }
        return success;
    }
}
