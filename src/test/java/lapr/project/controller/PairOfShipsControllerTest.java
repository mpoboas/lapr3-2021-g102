package lapr.project.controller;

import lapr.project.model.Company;
import lapr.project.model.Ship;
import lapr.project.utils.Pair;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PairOfShipsControllerTest {

    @Test
    void getShipStore() {
        PairOfShipsController pairOfShipsController = new PairOfShipsController();
        assertNotNull(pairOfShipsController.getShipStore());
    }

    @Test
    void getShipStoreCompany() {
        Company company = new Company("CargoCompany");
        PairOfShipsController pairOfShipsController = new PairOfShipsController(company);
        assertNotNull(pairOfShipsController.getShipStore());
    }

    @Test
    public void getCloseCoordsShips() {
        PairOfShipsController pairOfShipsController = new PairOfShipsController();
        ImportShipsController importShipsController = new ImportShipsController(pairOfShipsController.getShipStore());
        String file = "data/data-shipsports/data-shipsports/data-ships&ports/sships.csv";
        try {
            importShipsController.importShipData(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        importShipsController.addShips();
        List<Pair<Ship,Ship>> pairOfShipsList = pairOfShipsController.getCloseCoordsShips();

        assertEquals("Ship1 MMSI   Ship2 MMSI   Movs   TravelDist   Movs   TravelDist\n" +
                "------------------------------------------------------------\n" +
                "List is empty.\n",pairOfShipsController.toString(pairOfShipsList));
        assertNotNull(pairOfShipsList);
        assertEquals(0, pairOfShipsList.size());
    }


    @Test
    void listOfPairsNotEmpty() {
        PairOfShipsController pairOfShipsController = new PairOfShipsController();
        List<Pair<Ship,Ship>> pairOfShipsList = new ArrayList<>();
        Ship ex1 = new Ship(112, "122", "test", "Ship", 12, 1200, 600, 21);
        Ship ex2 = new Ship(113, "122", "test", "Ship", 12, 1200, 600, 21);
        pairOfShipsList.add(new Pair<>(ex1, ex2));
        assertEquals("Ship1 MMSI   Ship2 MMSI   Movs   TravelDist   Movs   TravelDist\n" +
                "------------------------------------------------------------\n" +
                "112          113          0      0.000000     0      0.000000 \n",pairOfShipsController.toString(pairOfShipsList));
    }
}