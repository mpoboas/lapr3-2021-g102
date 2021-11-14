package lapr.project.stores;

import lapr.project.model.Ship;
import lapr.project.model.Trips;
import lapr.project.utils.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the tree of the ships
 */
public class ShipStore {
    /**
     * All the ships stored in a binary search tree ordered by mmsi
     */
    ShipTree shipBSTMMSI = new ShipTree("MMSI");
    /**
     * All the ships stored in a binary search tree ordered by IMO
     */
    ShipTree shipBSTIMO = new ShipTree("IMO");
    /**
     * All the ships stored in a binary search tree ordered by call sign
     */
    ShipTree shipBSTCallSign = new ShipTree("Call Sign");


    /**
     * @param mmsi       the mmsi code of the ship
     * @param vesselName the vessel name of the ship
     * @param imo        the imo code of the ship
     * @param callSign   the call sign of the ship
     * @param vesselType the vessel type of the ship
     * @param length     the length of the ship
     * @param width      the width of the ship
     * @param draft      the draft of the ship
     * @return a new instance of ship
     */
    public Ship createShip(int mmsi, String vesselName, String imo, String callSign, int vesselType, int length, int width, double draft) {
        return new Ship(mmsi, vesselName, imo, callSign, vesselType, length, width, draft);
    }

    /**
     * This class adds a ship to all the trees
     *
     * @param ship ship to add to the trees
     */
    public void insertShip(Ship ship) {
        shipBSTMMSI.insert(ship);
        shipBSTIMO.insert(ship);
        shipBSTCallSign.insert(ship);
    }

    /**
     * Checks if a ship is already in store
     *
     * @param ship the ship to check
     * @return false if the ship is null or if the ship is already stores, true if is not stored
     */
    public boolean validateShip(Ship ship) {
        if (ship == null)
            return false;
        return shipBSTMMSI.find(ship) == null;
    }

    /**
     * Adds a ship to the store or adds some ship information if that ship is already in the store
     *
     * @param ship the ship to add
     * @return true if the ship was added or got more aismessages, false if not
     */
    public boolean addShip(Ship ship) {
        if (validateShip(ship)) {
            Trips trip = new Trips();
            trip.addAISMessage(ship.getAisMessagesList());
            ship.addTrip(trip);
            insertShip(ship);
            return true;
        } else {
            Ship s = shipBSTMMSI.getShip(ship);
            if (!s.getAisMessagesList().equals(ship.getAisMessagesList())) {
                s.addAISMessage(ship.getAisMessagesList());
                Trips trip = new Trips();
                trip.addAISMessage(s.getAisMessagesList());
                s.addTrip(trip);
                return true;
            }
        }
        return false;
    }

    public ShipTree getShipBSTMMSI() {
        return shipBSTMMSI;
    }

    /**
     * Gets the list of all ships in ascend order by mmsi
     *
     * @return the list of ships
     */
    public List<Ship> getListOfShipsASCOrd() {
        List<Ship> shipList = new ArrayList<>();
        shipBSTMMSI.inOrder().forEach(shipList::add);
        return shipList;
    }

    /**
     * Searches for a ship by its mmsi
     *
     * @param mmsi mmsi to identify the ship
     * @return the ship with that mmsi or throws an exception if the ship was not found
     */
    public Ship getShipByMMSI(int mmsi) {
        Ship ship = this.shipBSTMMSI.searchShipByMMSI(mmsi);
        if (ship != null)
            return ship;
        /*
        for (Ship s : getListOfShipsASCOrd()) {
            if (s.getMMSI() == mmsi)
                return s;
        }*/
        throw new IllegalArgumentException("The ship with the MMSI: " + mmsi + " was not found");
    }

    /**
     * Searches for a ship by its imo
     *
     * @param imo mmsi to identify the ship
     * @return the ship with that imo or throws an exception if the ship was not found
     */
    public Ship getShipByIMO(String imo) {
        Ship ship = this.shipBSTIMO.searchShipByIMO(imo);
        if (ship != null)
            return ship;

        /*for (Ship s : getListOfShipsASCOrd()) {
            if (s.getIMO().equals(imo))
                return s;
        }*/
        throw new IllegalArgumentException("The ship with the IMO: " + imo + " was not found");
    }

    /**
     * Searches for a ship by its call sign
     *
     * @param callSign mmsi to identify the ship
     * @return the ship with that call sign or throws an exception if the ship was not found
     */
    public Ship getShipByCallSign(String callSign) {
        Ship ship = this.shipBSTCallSign.searchShipByCallSign(callSign);
        if (ship != null)
            return ship;
        /*for (Ship s : getListOfShipsASCOrd()) {
            if (s.getCallSign().equals(callSign))
                return s;
        }*/
        throw new IllegalArgumentException("The ship with the Call Sign: " + callSign + " was not found");
    }

    /**
     * Returns a list of pairs of ships with routes with close departure/arrival coordinates and with different Travelled Distance.
     * Sorted by the first ship´s MMSI and in descending order of the Travelled Distance difference.
     *
     * @return pairs of ships list
     */
    public List<Pair<Ship, Ship>> getCloseCoordsShips() {
        List<Pair<Ship, Ship>> closeCoordsShips = this.getShipBSTMMSI().getCloseCoordsShips();

        for (int i = 0; i < closeCoordsShips.size(); i++) {
            for (int j = i + 1; j < closeCoordsShips.size(); j++) {
                if (closeCoordsShips.get(i).getFirst() == closeCoordsShips.get(j).getSecond())
                    closeCoordsShips.remove(closeCoordsShips.get(j));
            }
        }
        closeCoordsShips.sort(new SortByMMSITravDistance());
        return closeCoordsShips;
    }

    public List<Ship> getShipList() {
        Iterable<Ship> shipListI = this.getListOfShipsASCOrd();
        List<Ship> shipList = new ArrayList<>();
        shipListI.forEach(shipList::add);
        return shipList;
    }
}