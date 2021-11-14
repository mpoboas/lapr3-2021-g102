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
    Import imp = new Import(new ShipStore());

    @Test
    void getShipStore() {
        PairOfShipsController pairOfShipsController = new PairOfShipsController(imp.getShipStore());
        assertNotNull(pairOfShipsController.getShipStore());
    }

    @Test
    public void getCloseCoordsShipsSships() {
        String file = "data/data-shipsports/data-shipsports/data-ships&ports/bships.csv";
        try {
            imp.importShipsData(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        imp.addShips();
        PairOfShipsController pairOfShipsController = new PairOfShipsController(imp.getShipStore());
        List<Pair<Ship,Ship>> pairOfShipsList = pairOfShipsController.getCloseCoordsShips();

        assertNotNull(pairOfShipsList);
        assertEquals(0, pairOfShipsList.size());
    }
}