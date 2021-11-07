package lapr.project.stores;

import lapr.project.model.Ship;
import lapr.project.utils.BST;

/**
 * This class represents the tree of the ships
 */
public class ShipStore {
    /**
     * All the ships stored in a binary search tree
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
    public Ship createShip(int mmsi, String vesselName, String imo, String callSign, int vesselType, int length, int width, double draft, int cargo, char transceiverClass) {
        return new Ship(mmsi, vesselName, imo, callSign, vesselType, length, width, draft, cargo, transceiverClass);
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
        return shipBST.find(ship) == null;
    }

    /**
     * Adds a ship to the store or adds some ship information if that ship is already in the store
     *
     * @param ship the ship to add
     * @return true if the ship was added or got more aismessages, false if not
     */
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
     * Searches for the same ship from the list of ships
     *
     * @param ship the ship to get the duplicate
     * @return the ship with the same information
     */
    public Ship getShipDuplicated(Ship ship) {
        Ship sp = null;
        for (Ship s : getListOfShipsASCOrd()) {
            if (s.equals(ship))
                sp = s;
        }
        return sp;
    }

    /**
     * Searches for a ship by its mmsi
     *
     * @param mmsi mmsi to identify the ship
     * @return the ship with that mmsi or throws an exception if the ship was not found
     */
    public Ship getShipByMMSI(int mmsi) {
        for (Ship s : getListOfShipsASCOrd()) {
            if (s.getMMSI() == mmsi)
                return s;
        }
        throw new IllegalArgumentException("The ship with the MMSI: " + mmsi + " was not found");
    }

    /**
     * Searches for a ship by its imo
     *
     * @param imo mmsi to identify the ship
     * @return the ship with that imo or throws an exception if the ship was not found
     */
    public Ship getShipByIMO(String imo) {
        for (Ship s : getListOfShipsASCOrd()) {
            if (s.getIMO().equals(imo))
                return s;
        }
        throw new IllegalArgumentException("The ship with the IMO: " + imo + " was not found");
    }

    /**
     * Searches for a ship by its call sign
     *
     * @param callSign mmsi to identify the ship
     * @return the ship with that call sign or throws an exception if the ship was not found
     */
    public Ship getShipByCallSign(String callSign) {
        for (Ship s : getListOfShipsASCOrd()) {
            if (s.getCallSign().equals(callSign))
                return s;
        }
        throw new IllegalArgumentException("The ship with the Call Sign: " + callSign + " was not found");
    }

    /*public void getCloseCoordsShips() {
        List<Ship> shipList = (List<Ship>) this.getListOfShipsASCOrd();

        for (int i = 0; i < shipList.size(); i++) {
            if (!(shipList.get(i).getTotalDistance() < 10000)) { // Acceptance Criteria: "Do not consider ships with Travelled Distance less than 10kms"
                List<Trips> ship1TripList = new ArrayList<>(shipList.get(i).getTripList());
                for (int j = i + 1; j < shipList.size(); j++) {
                    if (!(shipList.get(j).getTotalDistance() < 10000) && (shipList.get(i).getTotalDistance() != shipList.get(j).getTotalDistance())) { // Acceptance Criteria: "Do not consider ships with Travelled Distance less than 10kms"
                        List<Trips> ship2TripList = new ArrayList<>(shipList.get(i).getTripList());

                        for (Trips ship1Trip : ship1TripList) {
                            for (Trips ship2Trip : ship2TripList) {
                                AISMessage ship1TripDepartureAisMessage = ship1Trip.getAIsMessage(0);
                                AISMessage ship1TripArrivalAisMessage = ship1Trip.getLastAISMessage();
                                AISMessage ship2TripDepartureAisMessage = ship2Trip.getAIsMessage(0);
                                AISMessage ship2TripArrivalAisMessage = ship2Trip.getLastAISMessage();
                                if (ship1TripDepartureAisMessage.getDistanceAIMes(ship2TripDepartureAisMessage) < 5000) {

                                } else if (ship1TripDepartureAisMessage.getDistanceAIMes(ship2TripArrivalAisMessage) < 5000) {

                                } else if (ship1TripArrivalAisMessage.getDistanceAIMes(ship2TripDepartureAisMessage) < 5000) {

                                } else if (ship1TripArrivalAisMessage.getDistanceAIMes(ship2TripArrivalAisMessage) < 5000) {

                                }
                            }
                        }
                    }
                    //List<AISMessage> aisMessageList = new ArrayList<>(shipList.get(i).getAisMessagesList());
                }
            }
        }
    }*/
}