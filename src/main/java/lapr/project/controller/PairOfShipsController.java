package lapr.project.controller;

import lapr.project.model.Ship;
import lapr.project.stores.ShipStore;
import lapr.project.utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class PairOfShipsController {
    private final ShipStore shipStore;

    public PairOfShipsController(ShipStore shipStore) {
        this.shipStore = shipStore;
    }

    /**
     * Returns a list of pairs of ships with routes with close departure/arrival coordinates and with different Travelled Distance.
     * Sorted by the first ship´s MMSI and in descending order of the Travelled Distance difference.
     *
     * @return pairs of ships list
     */
    public List<Pair<Ship, Ship>> getCloseCoordsShips() {
        return new ArrayList<>(this.shipStore.getCloseCoordsShips());
    }

    public ShipStore getShipStore() {
        return this.shipStore;
    }
}
