package lapr.project.controller;

import lapr.project.model.Company;
import lapr.project.model.Ship;
import lapr.project.stores.ShipStore;
import lapr.project.utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class PairOfShipsController {
    /**
     * The app
     */
    private App app;

    /**
     * The company
     */
    private final Company company;

    /**
     * Constructor of the covid report controller, through a company object.
     *
     * @param company the company object
     */
    public PairOfShipsController(Company company) {
        this.company = company;
    }

    /**
     * Constructor of the pair of ships controller, with no received parameters.
     */
    public PairOfShipsController() {
        this(App.getInstance().getCompany());
        app = App.getInstance();
    }

    /**
     * Returns the ship store.
     *
     * @return ship store instance
     */
    public ShipStore getShipStore() {
        return this.company.getShipStore();
    }

    /**
     * Returns a list of pairs of ships with routes with close departure/arrival coordinates and with different Travelled Distance.
     * Sorted by the first ship´s MMSI and in descending order of the Travelled Distance difference.
     *
     * @return pairs of ships list
     */
    public List<Pair<Ship, Ship>> getCloseCoordsShips() {
        return new ArrayList<>(this.company.getShipStore().getCloseCoordsShips());
    }

    /**
     * Writes all the pairs´ information into a output file.
     * @param pairList list of pairs
     * @return string related to all the pairs of ships
     *
     */
    public String toString(List<Pair<Ship, Ship>> pairList) {
        StringBuilder stringBuilder = new StringBuilder("Ship1 MMSI   Ship2 MMSI   Movs   TravelDist   Movs   TravelDist\n");
        stringBuilder.append("------------------------------------------------------------\n");
        if (!pairList.isEmpty()) {
            for (Pair<Ship, Ship> pairOfShips : pairList) {
                stringBuilder.append(String.format("%-13d%-13d%-7d%-13f%-7d%-9f\n", pairOfShips.getFirst().getMMSI(), pairOfShips.getSecond().getMMSI(), pairOfShips.getFirst().numberOfMovements(), pairOfShips.getFirst().getTotalDistance(), pairOfShips.getSecond().numberOfMovements(), pairOfShips.getSecond().getTotalDistance()));
            }
        } else stringBuilder.append("List is empty.\n");

        return stringBuilder.toString();
    }
}
