package lapr.project.stores;

import lapr.project.model.Ship;
import lapr.project.utils.BST;

import java.util.List;

/**
 * This class represents the tree of the ships
 */
public class ShipStore {
    /**
     *
     */
    BST<Ship> shipBST = new BST<>();

    /**
     * @param mmsi             the mmsi code of the ship
     * @param vesselName       the vessel name of the ship
     * @param imo              the imo code of the ship
     * @param callSign         the call sign of the ship
     * @param vesselType       the vessel type of the ship
     * @param length           the length of the ship
     * @param width            the width of the ship
     * @param draft            the draft of the ship
     * @param cargo            the cargo of the ship
     * @param transceiverClass the transceiver class of the ship
     * @return a new instance of ship
     */
    public Ship createShip(int mmsi, String vesselName, String imo, String callSign, int vesselType, int length, int width, int draft, int cargo, char transceiverClass) {
        return new Ship(mmsi, vesselName, imo, callSign, vesselType, length, width, draft, cargo, transceiverClass);
    }

    public boolean validateShip(Ship ship) {
        if (ship == null)
            return false;
        return shipBST.find(ship) == null;
    }

    public boolean addShip(Ship ship) {
        if (validateShip(ship)) {
            shipBST.insert(ship);
            return true;
        } else {
            Ship s = getShipDuplicated(ship);
            if (!s.getAisMessagesList().equals(ship.getAisMessagesList())) {
                s.addAISMessage(ship.getAisMessagesList());
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the list of all ships in ascend order
     *
     * @return the list of ships
     */
    public Iterable<Ship> getListOfShipsASCOrd() {
        return shipBST.inOrder();
    }

    /**
     * @param ship
     * @return
     */
    public Ship getShipDuplicated(Ship ship) {
        Ship sp = null;
        for (Ship s : getListOfShipsASCOrd()) {
            if (s.equals(ship))
                sp = s;
        }
        return sp;
    }
}