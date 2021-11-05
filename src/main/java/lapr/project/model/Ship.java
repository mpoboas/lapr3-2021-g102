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

    List<Trips> tripsList;

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
    }

    public Ship(){

        this.tripsList = new ArrayList<Trips>();
    }

    public double getAvgSpeed(){return getTotalDistance()/getTotalTimeinSea();}
    public double getTotalDistance(){
        double distance = 0;
        for(int i = 0 ; i < tripsList.size();i++){
            //distance = distance + tripsList.get(i).gettripDistance();
        }
        return distance;

    }
    //public int numberOfMovements(){}
    //public double deltaDistance(){}
    public double getTotalTimeinSea(){
        double time = 0;
        for(int i = 0 ; i < tripsList.size();i++){
            time = time + tripsList.get(i).getTripDuration();
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
