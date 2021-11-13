package lapr.project.stores;

import lapr.project.model.AISMessage;
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
        List<Pair<Ship, Ship>> closeCoordsShipList = new ArrayList<>();
        List<Ship> shipList = this.getListOfShipsASCOrd();

        for (int i = 0; i < shipList.size(); i++) { // Iterate through ship list to get the first element of the possible Pair
            if (!(Conversions.nauticalMilesToKm(shipList.get(i).getTotalDistance()) < 10)) { // Acceptance Criteria: "Do not consider ships with Travelled Distance less than 10kms"
                List<Trips> ship1TripList = new ArrayList<>(shipList.get(i).getTripsList());
                for (int j = i + 1; j < shipList.size(); j++) { // Iterate through ship list to get the second element of the possible Pair
                    boolean isPair = false;
                    if (!(Conversions.nauticalMilesToKm(shipList.get(i).getTotalDistance()) < 10) && (shipList.get(i).getTotalDistance() != shipList.get(j).getTotalDistance())) { // Acceptance Criteria: "Do not consider ships with Travelled Distance less than 10kms; Ships must have different Travelled Distance"
                        List<Trips> ship2TripList = new ArrayList<>(shipList.get(j).getTripsList());
                        for (Trips ship1Trip : ship1TripList) { // Iterate through first ship trip list
                            AISMessage departureAIS1 = ship1Trip.getAIsMessage(0);
                            AISMessage arrivalAIS1 = ship1Trip.getAIsMessage(ship1Trip.getAISMessageListSize() - 1);

                            if (Validations.validateAISMessageForCalculations(departureAIS1) && Validations.validateAISMessageForCalculations(arrivalAIS1)) { // Verify if AIS message is valid for calculations
                                for (Trips ship2Trip : ship2TripList) { // Iterate through second ship trip list
                                    AISMessage departureAIS2 = ship2Trip.getAIsMessage(0);
                                    AISMessage arrivalAIS2 = ship2Trip.getAIsMessage(ship2Trip.getAISMessageListSize() - 1);

                                    if (Validations.validateAISMessageForCalculations(departureAIS2) && Validations.validateAISMessageForCalculations(arrivalAIS2)) { // Verify if AIS message is valid for calculations
                                        // If departure/arrival coordinates are no more than 5km away from each other, create Pair
                                        double distance1 = Conversions.nauticalMilesToKm(departureAIS1.getDistanceAIMes(departureAIS2));
                                        double distance2 = Conversions.nauticalMilesToKm(arrivalAIS1.getDistanceAIMes(arrivalAIS2));
                                        if (distance1 <= 5 || distance2 <= 5) isPair = true;
                                    }
                                }
                            }
                        }

                        if (isPair) {
                            Pair<Ship, Ship> pairOfShips = new Pair<>(shipList.get(i), shipList.get(j));
                            closeCoordsShipList.add(pairOfShips);
                        }
                    }
                }
            }
        }

        closeCoordsShipList.sort(new SortByMMSITravDistance());
        return closeCoordsShipList;
    }

    public List<Ship> getShipList() {
        Iterable<Ship> shipListI = this.getListOfShipsASCOrd();
        List<Ship> shipList = new ArrayList<>();
        shipListI.forEach(shipList::add);
        return shipList;
    }
}