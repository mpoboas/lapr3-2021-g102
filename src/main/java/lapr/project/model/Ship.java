package lapr.project.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Ship implements Comparable<Ship> {

    String shipName;

    int nGenerators;

    double capacity;

    /**
     * the mmsi code of the ship
     */
    private final int MMSI;
    /**
     * the vessel name of the ship
     */
    private final String vesselName;
    /**
     * the imo code of the ship
     */
    private final String IMO;
    /**
     * the call sign of the ship
     */
    private final String callSign;
    /**
     * the vessel type of the ship
     */
    private final int vesselType;
    /**
     * the length of the ship
     */
    private final int length;
    /**
     * the width of the ship
     */
    private final int width;
    /**
     * the draft of the ship
     */
    private final double draft;
    /**
     * all the ais messages of the ship
     */
    private List<AISMessage> aisMessagesList;
    /**
     * the cargo of the ship
     */
    private int cargo;
    /**
     * the transceiver class of the ship
     */
    private char transceiverClass;

    private final List<Trips> tripsList;

    /**
     *
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
     */
    public Ship(int mmsi, String vesselName, String imo, String callSign, int vesselType, int length, int width, double draft, int cargo, char transceiverClass) {
        MMSI = mmsi;
        this.vesselName = vesselName;
        IMO = imo;
        this.callSign = callSign;
        this.vesselType = vesselType;
        this.length = length;
        this.width = width;
        this.draft = draft;
        this.cargo = cargo;
        this.transceiverClass = transceiverClass;
        aisMessagesList = new ArrayList<>();
        this.tripsList = new ArrayList<>();
    }
    /**
     *
     * @return ship MMSI
     */
    public int getMMSI(){ return MMSI;}

    /**
     *
     * @return ship average speed in knots (nautical miles per hour)
     */
    public double getAvgSpeed(){return getTotalDistance()/getTotalTimeinSea();}

    /**
     * total distance done in trips by the ship
     * @return distance
     */
    public double getTotalDistance(){
        double distance = 0;
        for (Trips trips : tripsList) {
            distance = distance + trips.getTripDistance();
        }
        return distance;
    }

    /**
     *
     * @return total number of trips done by ship
     */
    public int numberOfMovements(){return tripsList.size();}
    /**
     *
     * @return distance between first registerd movement and last
     */
    public double deltaDistance(){return aisMessagesList.get(0).getDistanceAIMes(aisMessagesList.get(aisMessagesList.size()-1));}

    /**
     * ship's total time spent in the sea
     *
     * @return time
     */
    public double getTotalTimeinSea(){
        double time = 0;
        for (Trips trips : tripsList) {
            time = time + trips.getTripDuration();
        }
        return time;
    }
    /**
     *
     * @param msg ais message to be added to the ship
     */
    public void addAISMessage(AISMessage msg) {
        if (!aisMessagesList.contains(msg))
            aisMessagesList.add(msg);
    }

    /**
     * imports a list to the list of ais messages
     * @param msgList ais message list to import
     */
    public void addAISMessage(List<AISMessage> msgList){
        for (AISMessage msg: msgList) {
            addAISMessage(msg);
        }
    }

    /**
     * Gets the list of all the AIS messages from the ship
     * @return list of AIS messages
     */
    public List<AISMessage> getAisMessagesList() {
        return aisMessagesList;
    }

    /**
     *
     * @param i index of AISMessage
     * @return corresponding aismessage
     */
    public AISMessage getAISM(int i){return aisMessagesList.get(i);}

    /**
     *
     * @param trip  to be added to the ship
     *
     */
    public void addTrip(Trips trip) {
        tripsList.add(trip);
    }
    public Trips getTrip(int i){
        return tripsList.get(i);
    }

    /**
     * Checks if another object is equal to this onde
     * @param o the object to compare
     * @return false if the object is null and of other class, true if is the same object or the mmsi is the same
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Ship ship = (Ship) o;
        return MMSI == ship.MMSI || Objects.equals(IMO, ship.IMO) || Objects.equals(callSign, ship.callSign);
    }

    /**
     *
     * @return the hascode of the ship
     */
    @Override
    public int hashCode() {
        return Objects.hash(MMSI, IMO, callSign);
    }

    /**
     * Compares this ship to other ship
     * @param o the ship to compare
     * @return zero if the ships are equal, less than zero if the other object is bigger or more than zero if this ship is bigger
     */
    @Override
    public int compareTo(Ship o) {
        return MMSI - o.MMSI;
    }

    /**
     *
     * @return the ship information
     */
    @Override
    public String toString() {
        return "Ship{" +
                "MMSI=" + MMSI +
                ", vesselName='" + vesselName + '\'' +
                ", IMO='" + IMO + '\'' +
                ", callSign='" + callSign + '\'' +
                ", vesselType=" + vesselType +
                ", length=" + length +
                ", width=" + width +
                ", draft=" + draft +
                ", cargo=" + cargo +
                ", transceiverClass=" + transceiverClass +
                '}' + '\n' + aisMessagesList;
    }
}
