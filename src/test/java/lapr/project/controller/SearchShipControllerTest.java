package lapr.project.controller;

import lapr.project.model.Import;
import lapr.project.stores.ShipStore;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SearchShipControllerTest {
    ShipStore store = new ShipStore();
    Import imp = new Import(store);

    @Test
    void searchShip() {
        SearchShipController ctrl = new SearchShipController(store);
        try {
            imp.importShipsData("data/data-shipsports/data-shipsports/data-ships&ports/sships.csv");
            imp.addShips();
            //System.out.println(store.getListOfShipsASCOrd());

        } catch (IOException e) {
            e.printStackTrace();
        }

        ctrl.searchShip("212351000");
        ctrl.searchShip("IMO9395044");
        ctrl.searchShip("WDG5171");
    }
}