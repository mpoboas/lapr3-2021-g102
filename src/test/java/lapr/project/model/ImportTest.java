package lapr.project.model;

import lapr.project.stores.ShipStore;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ImportTest {
    ShipStore store = new ShipStore();
    Import imp = new Import(store);
    String file = "ships_for_Tests.csv";
    String file2 = "data/data-shipsports/data-shipsports/data-ships&ports/sships.csv";

    @Test
    void importShipsData() {
        try {
            imp.importShipsData(file2);
            assertNotEquals(0,imp.getListOfImportedShips().size());
            /*
            for (Ship s: imp.getListOfImportedShips()) {
                System.out.println(s.toString());
            }*/
            imp.addShips();
            assertNotEquals(0,imp.getShipStore().getShipList().size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}