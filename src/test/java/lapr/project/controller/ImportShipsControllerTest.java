package lapr.project.controller;

import lapr.project.stores.ShipStore;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ImportShipsControllerTest {
    String existingFileDirectory = "data/data-shipsports/data-shipsports/data-ships&ports/sships.csv";
    String fileNotFound=  "barcos";

    @Test
    public void testImportController(){
        ImportShipsController ctrl = new ImportShipsController(new ShipStore());

        try {
            ctrl.importShipData(existingFileDirectory);
            assertNotEquals(0,ctrl.getImp().getListOfImportedShips().size());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ctrl.importShipData(fileNotFound);
        } catch (Exception e) {
            assertEquals(FileNotFoundException.class,e.getClass());
        }

        ctrl.addShips();
        assertNotEquals(0,ctrl.getImp().getShipStore().getShipList().size());
    }

}