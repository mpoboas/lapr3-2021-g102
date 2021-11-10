package lapr.project.model;

import lapr.project.stores.ShipStore;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ImportTest {
    ShipStore store = new ShipStore();
    Import imp = new Import(store);
    String file = "ships_for_Tests.csv";

    @Test
    void importShipsData() {
        try {
            imp.importShipsData(file);
            for (Ship s: imp.getListOfImportedShips()) {
                System.out.println(s.toString());
            }
            imp.addShips();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*@Test
    void addShips() {
        imp.addShips();
    }*/
}