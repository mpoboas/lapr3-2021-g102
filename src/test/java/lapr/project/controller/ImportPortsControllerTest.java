package lapr.project.controller;

import lapr.project.stores.PortStore;
import lapr.project.stores.ShipStore;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ImportPortsControllerTest {
    String existingFileDirectory = "data/data-shipsports/data-shipsports/data-ships&ports/sports.csv";
    String fileNotFound=  "portos";

    @Test
    void testImportPortsController() {
        ImportPortsController ctrl = new ImportPortsController(new PortStore());

        try {
            ctrl.importPortsData(existingFileDirectory);
            assertNotEquals(0,ctrl.getImporter().getListOfImportedPorts().size());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ctrl.importPortsData(fileNotFound);
        } catch (Exception e) {
            assertEquals(FileNotFoundException.class,e.getClass());
        }

        ctrl.addImportedPorts();
        assertNotEquals(0,ctrl.getImporter().getStore().getPortTree().inOrder().size());
    }


}