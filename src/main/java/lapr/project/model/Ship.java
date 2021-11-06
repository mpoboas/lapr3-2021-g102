package lapr.project.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Ship implements Comparable<Ship> {

    String shipName;

    int nGenerators;

    double capacity;

    /**
     *
     */
    private final int MMSI;
    /**
     *
     */
    private final String vesselName;
    /**
     *
     */
    private final String IMO;
    /**
     *
     */
    private final String callSign;
    /**
     *
     */
    private final int vesselType;
    /**
     *
     */
    private final int length;
    /**
     *
     */
    private final int width;
    /**
     *
     */
    private final int draft;
    /**
     *
     */
    private List<AISMessage> aisMessagesList;
    /**
     *
     */
    private int cargo;
    /**
     *
     */
    private char transceiverClass;

    private List<Trips> tripsList;

    public Ship(int mmsi, String vesselName, String imo, String callSign, int vesselType, int length, int width, int draft, int cargo, char transceiverClass) {
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
    //public int numberOfMovements(){return tripsList.size;} //yet to be confirmed if thats the required value
    //public double deltaDistance(){}

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

    public List<AISMessage> getAisMessagesList() {
        return aisMessagesList;
    }

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
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Ship ship = (Ship) o;
        return MMSI == ship.MMSI && Objects.equals(IMO, ship.IMO) && Objects.equals(callSign, ship.callSign);
    }

    @Override
    public int hashCode() {
        return Objects.hash(MMSI, IMO, callSign);
    }

    @Override
    public int compareTo(Ship o) {
        return MMSI - o.MMSI;
    }
}
