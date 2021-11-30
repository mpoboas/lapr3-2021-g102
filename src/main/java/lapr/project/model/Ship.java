package lapr.project.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

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
    private final List<AISMessage> aisMessagesList;


    private final List<Trips> tripsList;

    /**
     * @param mmsi       the mmsi code of the ship
     * @param vesselName the vessel name of the ship
     * @param imo        the imo code of the ship
     * @param callSign   the call sign of the ship
     * @param vesselType the vessel type of the ship
     * @param length     the length of the ship
     * @param width      the width of the ship
     * @param draft      the draft of the ship
     */
    public Ship(int mmsi, String vesselName, String imo, String callSign, int vesselType, int length, int width, double draft) {
        MMSI = mmsi;
        this.vesselName = vesselName;
        IMO = imo;
        this.callSign = callSign;
        this.vesselType = vesselType;
        this.length = length;
        this.width = width;
        this.draft = draft;

        aisMessagesList = new ArrayList<>();
        this.tripsList = new ArrayList<>();
    }

    /**
     * @return ship MMSI
     */
    public int getMMSI() {
        return MMSI;
    }

    /**
     * @return vessel name of the ship
     */
    public String getVesselName() {
        return vesselName;
    }

    /**
     * @return imo of the ship
     */
    public String getIMO() {
        return IMO;
    }

    /**
     * @return call sign of the ship
     */
    public String getCallSign() {
        return callSign;
    }

    /**
     * @return vessel type of the ship
     */
    public int getVesselType() {
        return vesselType;
    }

    /**
     * @return length of the ship
     */
    public int getLength() {
        return length;
    }

    /**
     * @return width of the ship
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return draft of the ship
     */
    public double getDraft() {
        return draft;
    }

    /**
     * @return the max SOG of the ship
     */
    public double getMaxSOG() {
        double max = 0;
        for (AISMessage msg : aisMessagesList) {
            if (msg.getSOG() > max) max = msg.getSOG();
        }
        return max;
    }

    /**
     * @return the max COG of the ship
     */
    public double getMaxCOG() {
        double max = 0;
        for (AISMessage msg : aisMessagesList) {
            if (msg.getCOG() > max) max = msg.getCOG();
        }
        return max;
    }

    /**
     * @return the mean SOG of the ship
     */
    public double getMeanSOG() {
        double sum = 0;
        double mean ;
        for (AISMessage msg : aisMessagesList) {
            sum += msg.getSOG();
        }
        mean = sum / aisMessagesList.size();
        return mean;
    }

    /**
     * @return the mean COG of the ship
     */
    public double getMeanCOG() {
        double sum = 0;
        double mean;
        for (AISMessage msg : aisMessagesList) {
            sum += msg.getCOG();
        }
        mean = sum / aisMessagesList.size();
        return mean;
    }

    /**
     * @return the ship's departure latitude
     */
    public double getDepartureLatitude() {
        return aisMessagesList.get(0).getCoordinates().getLatitude();
    }

    /**
     * @return the ship's departure longitude
     */
    public double getDepartureLongitude() {
        return aisMessagesList.get(0).getCoordinates().getLongitude();
    }

    /**
     * @return the ship's arrival latitude
     */
    public double getArrivalLatitude() {
        return aisMessagesList.get(aisMessagesList.size() - 1).getCoordinates().getLatitude();
    }

    /**
     * @return the ship's arrival longitude
     */
    public double getArrivalLongitude() {
        return aisMessagesList.get(aisMessagesList.size() - 1).getCoordinates().getLongitude();
    }

    /**
     * @return the start time
     */
    public LocalDateTime getStartBDT() {
        return aisMessagesList.get(0).getBaseDateTime();
    }

    /**
     * @return the end time
     */
    public LocalDateTime getEndBDT() {
        return aisMessagesList.get(aisMessagesList.size() - 1).getBaseDateTime();
    }


    /**
     * @return ship average speed in knots (nautical miles per hour)
     */
    public double getAvgSpeed(double dist, double time) {
        return dist / time;
    }

    /**
     * total distance done in trips by the ship
     *
     * @return distance
     */
    public double getTotalDistance() {
        double distance = 0;
        for (Trips trips : tripsList) {
            distance = distance + trips.getTripDistance();
        }
        return distance;
    }

    /**
     * total distance done in trips by the ship
     * THIS IS A TEMPORARY METHOD. TO BE DELETED!
     * @return total distance
     */
    public double getTotalDistance2() {
        double distance = 0;
        for (int i = 0; i < aisMessagesList.size() - 1; i++) {
            distance = distance + aisMessagesList.get(i).getDistanceAIMes(aisMessagesList.get(i + 1));
        }
        return distance;
    }

    /**
     * @param start of period required by user
     * @param end   of period required by user
     * @return distance done by ship int tha period
     */
    public double getDistBetweenDates(LocalDateTime start, LocalDateTime end) {
        double distance = 0;
        for (int i = 0; i < aisMessagesList.size(); i++) {
            if (aisMessagesList.get(i).getBaseDateTime().isAfter(start) && aisMessagesList.get(i).getBaseDateTime().isBefore(end) && i > 0) {
                distance = distance + aisMessagesList.get(i).getDistanceAIMes(aisMessagesList.get(i - 1));
            }
        }
        return distance;
    }

    /**
     * @param start date of required period
     * @param end   date of required period
     * @return number of hours spent in sea between those dates
     */
    public double getTimeInSeaBetweenDates(LocalDateTime start, LocalDateTime end) {
        double time = 0;
        for (int i = 0; i < aisMessagesList.size(); i++) {
            if (aisMessagesList.get(i).getBaseDateTime().isAfter(start) && aisMessagesList.get(i).getBaseDateTime().isBefore(end) && i > 0) {
                time = time + Duration.between(aisMessagesList.get(i - 1).getBaseDateTime(), aisMessagesList.get(i).getBaseDateTime()).toHours();
            }
        }
        return time;
    }

    /**
     * @return total number of trips done by ship
     */
    public int numberOfMovements() {return aisMessagesList.size();
    }

    /**
     * @return distance between first registerd movement and last
     */
    public double deltaDistance() {
        return aisMessagesList.get(0).getDistanceAIMes(aisMessagesList.get(aisMessagesList.size() - 1));
    }

    /**
     * ship's total time spent in the sea
     *
     * @return time
     */
    public double getTotalTimeinSea() {
        double time = 0;
        for (Trips trips : tripsList) {
            time = time + trips.getTripDuration();
        }
        return time;
    }

    /**
     * ship's total time spent in the sea
     * THIS IS A TEMPORARY METHOD. TO BE REMOVED!
     * @return total time in sea
     */
    public double getTotalTimeinSea2(){
        Duration duration;
        duration = Duration.between(this.aisMessagesList.get(0).getBaseDateTime(), this.aisMessagesList.get(aisMessagesList.size() - 1).getBaseDateTime());
        return duration.toHours();
    }

    /**
     * @param msg ais message to be added to the ship
     */
    public void addAISMessage(AISMessage msg) {

        if (!aisMessagesList.contains(msg)) {
            aisMessagesList.add(msg);
            Collections.sort(aisMessagesList);
        }
    }

    /**
     * imports a list to the list of ais messages
     *
     * @param msgList ais message list to import
     */
    public void addAISMessage(List<AISMessage> msgList) {
        for (AISMessage msg : msgList) {
            addAISMessage(msg);
        }
    }

    /**
     * Gets the list of all the AIS messages from the ship
     *
     * @return list of AIS messages
     */
    public List<AISMessage> getAisMessagesList() {
        return aisMessagesList;
    }

    /**
     * @param i index of AISMessage
     * @return corresponding AIS message
     */
    public AISMessage getAISM(int i) {
        return aisMessagesList.get(i);
    }

    /**
     * //@param ship the ship to get the AISMessages from
     * @param date the date for the search
     * @return the list of positional messages at a certain date for a given ship
     */
    public List<AISMessage> getAISAtDate(LocalDateTime date) {
        List<AISMessage> list = new ArrayList<>();
        for (AISMessage msg : getAisMessagesList()) {
            if (msg.getBaseDateTime().getDayOfMonth() == date.getDayOfMonth() && msg.getBaseDateTime().getMonthValue() == date.getMonthValue())
                list.add(msg);
        }
        return list;
    }

    /**
     * @param dateTime the date and time for the search
     * @return the list of positional messages at a certain date for a given ship
     */
    public AISMessage getAISAtDateTime(LocalDateTime dateTime) {
        for (Trips trip : this.getTripsList()) {
            for (AISMessage msg : trip.getAisMessagesList()) {
                if (msg.getBaseDateTime().equals(dateTime))
                    return msg;
            }
        }
        throw new NoSuchElementException("No AIS message was found.");
    }

    /**
     * //@param ship          the ship to get the AISMessages from
     * @param startDateTime the start date for the search
     * @param endDateTime   the end date for the search
     * @return the list of positional messages between two dates for a given ship
     */
    public List<AISMessage> getAISBetweenTwoDates(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<AISMessage> list = new ArrayList<>();
        for (AISMessage msg : getAisMessagesList()) {
            if (msg.getBaseDateTime().isAfter(startDateTime) && msg.getBaseDateTime().isBefore(endDateTime))
                list.add(msg);
        }
        return list;
    }

    /**
     * @param trip to be added to the ship
     */
    public void addTrip(Trips trip) {
        tripsList.add(trip);
    }

    /**
     * @param i trip index to get
     * @return the corresponding trip instance
     */
    public Trips getTrip(int i) {
        return tripsList.get(i);
    }

    /**
     * Gets all the trips from a ship
     *
     * @return a list of trips from a ship
     */
    public List<Trips> getTripsList() {
        return new ArrayList<>(this.tripsList);
    }

    /**
     * Checks if another object is equal to this onde
     *
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
     * @return the hascode of the ship
     */
    @Override
    public int hashCode() {
        return Objects.hash(MMSI, IMO, callSign);
    }

    /**
     * Compares this ship to other ship
     *
     * @param o the ship to compare
     * @return zero if the ships are equal, less than zero if the other object is bigger or more than zero if this ship is bigger
     */
    @Override
    public int compareTo(Ship o) {
        return MMSI - o.MMSI;
    }

    /**
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
                '}' + '\n' + aisMessagesList;
    }
}
