package lapr.project.controller;

import lapr.project.model.Import;
import lapr.project.model.Ship;
import lapr.project.stores.ShipStore;
import lapr.project.utils.Pair;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PairOfShipsControllerTest {


    @Test
    void getShipStore() {
        PairOfShipsController pairOfShipsController = new PairOfShipsController();
        assertNotNull(pairOfShipsController.getShipStore());
    }

    @Test
    public void getCloseCoordsShipsSships() {
        PairOfShipsController pairOfShipsController = new PairOfShipsController();
        Import imp = new Import(pairOfShipsController.getShipStore());
        String file = "data/data-shipsports/data-shipsports/data-ships&ports/sships.csv";
        try {
            imp.importShipsData(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        imp.addShips();
        List<Pair<Ship,Ship>> pairOfShipsList = pairOfShipsController.getCloseCoordsShips();

        assertNotNull(pairOfShipsList);
        assertEquals(0, pairOfShipsList.size());
    }
}