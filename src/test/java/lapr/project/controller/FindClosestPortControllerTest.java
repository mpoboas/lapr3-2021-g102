package lapr.project.controller;

import lapr.project.model.AISMessage;
import lapr.project.model.Port;
import lapr.project.model.Ship;
import lapr.project.stores.PortStore;
import lapr.project.stores.ShipStore;
import lapr.project.utils.Coordinates;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FindClosestPortControllerTest {
    FindClosestPortController ctrl = new FindClosestPortController();
    ShipStore shipStore = ctrl.getShipStore();
    PortStore portStore = ctrl.getPortStore();

    ImportPortsController impPortCtrl = new ImportPortsController();
    ImportShipsController impShipCtrl = new ImportShipsController();
    String portsFile = "data/data-shipsports/data-shipsports/data-ships&ports/sports.csv";
    String shipsFile = "data/data-shipsports/data-shipsports/data-ships&ports/bships.csv";



    @Test
    void testFindClosestPort() throws IOException {

        //IMPORT PORTS
        try {
            impPortCtrl.importPortsData(portsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        impPortCtrl.addImportedPorts();

        //IMPORT SHIPS
        try {
            impShipCtrl.importShipData(shipsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        impShipCtrl.addShips();

        AuthController authCtrl = new AuthController();
        authCtrl.doLogin("shipcaptain@lei.sem3.pt","123456");

        Ship testShip = shipStore.getShipByCallSign("DHBN");
        LocalDateTime exampleDateTime = LocalDateTime.of(2020, Month.DECEMBER, 31, 0, 0);
        ctrl.findClosestPort("DHBN",exampleDateTime);
        Coordinates shipCoordinates = testShip.getAISAtDateTime(exampleDateTime).getCoordinates();
        Port expected = portStore.getPortTree().findNearestNeighbour(shipCoordinates.getLongitude(), shipCoordinates.getLatitude());
        assertEquals(expected, ctrl.findClosestPort("DHBN",exampleDateTime));
    }

}