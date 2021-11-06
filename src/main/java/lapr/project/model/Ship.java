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
    private int MMSI;
    /**
     *
     */
    private String vesselName;
    /**
     *
     */
    private String IMO;
    /**
     *
     */
    private String callSign;
    /**
     *
     */
    private int vesselType;
    /**
     *
     */
    private int length;
    /**
     *
     */
    private int width;
    /**
     *
     */
    private int draft;
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
     *
     * @return total distance done in trips by the ship
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
     *
     * @return ship's total time spent in the sea
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
        else
            throw new IllegalArgumentException("This message is already saved in this ship");
    }

    /**
     *
     * @param trip  to be added to the ship
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
