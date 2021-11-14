package lapr.project.controller;

import lapr.project.model.Ship;
import lapr.project.stores.ShipStore;
import lapr.project.utils.Pair;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PairOfShipsController {
    /**
     * The ship store
     */
    private final ShipStore shipStore;

    /**
     * Pair of ships controller constructor.
     *
     * @param shipStore the ship store
     */
    public PairOfShipsController(ShipStore shipStore) {
        this.shipStore = shipStore;
    }
    /**
     * Returns the ship store.
     *
     * @return ship store instance
     */
    public ShipStore getShipStore() {
        return this.shipStore;
    }

    /**
     * Returns a list of pairs of ships with routes with close departure/arrival coordinates and with different Travelled Distance.
     * Sorted by the first ship´s MMSI and in descending order of the Travelled Distance difference.
     *
     * @return pairs of ships list
     */
    public List<Pair<Ship, Ship>> getCloseCoordsShips() {
        List<Pair<Ship, Ship>> closeCoordsShips = new ArrayList<>(this.shipStore.getCloseCoordsShips());
        writeToFile(closeCoordsShips);
        return closeCoordsShips;
    }

    /**
     * Writes all the pairs´ information into a output file.
     *  @param pairList list of pairs
     *
     */
    private void writeToFile(List<Pair<Ship, Ship>> pairList) {
        FileWriter myWriter = null;
        try {
            myWriter = new FileWriter("CloseCoordsPairs.txt", true);
            myWriter.write("Ship1 MMSI   Ship2 MMSI   Movs   TravelDist   Movs   TravelDist\n");
            myWriter.write("------------------------------------------------------------\n");
            if (!pairList.isEmpty()) {
                for (Pair<Ship, Ship> pairOfShips : pairList) {
                    myWriter.write(String.format("%-13d%-13d%-7d%-13f%-7d%-9f\n", pairOfShips.getFirst().getMMSI(), pairOfShips.getSecond().getMMSI(), pairOfShips.getFirst().numberOfMovements(), pairOfShips.getFirst().getTotalDistance(), pairOfShips.getSecond().numberOfMovements(), pairOfShips.getSecond().getTotalDistance()));
                }
            } else myWriter.write("List is empty.");
        } catch (IOException e) {
            System.out.println("An error occurred in the writing of the information of the pair.");
            e.printStackTrace();
        } finally {
            try {
                if (myWriter != null)
                    myWriter.close();
            } catch (IOException e) {
                System.out.println("An error occurred in the writing of the information of the pair.");
                e.printStackTrace();
            }
        }
    }
}
