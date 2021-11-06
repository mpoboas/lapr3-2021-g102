package lapr.project.model;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ImportTest {
    Import imp = new Import();
    String file = "ships_for_Tests.csv";

    @Test
    void importShipsData() {
        try {
            imp.importShipsData(file);
            for (Ship s: imp.getListOfImportedShips()) {
                System.out.println(s.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void addShips() {
        imp.addShips();
    }
}