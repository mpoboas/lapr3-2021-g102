package lapr.project.model;

import lapr.project.utils.Coordinates;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * This class represents the ais message
 */
public class AISMessage implements Comparable<AISMessage> {

    /**
     * Date/time of AIS message.
     */
    private final LocalDateTime baseDateTime;

    /**
     * Coordinates of the ship
     */
    private final Coordinates coordinates;
    /**
     *Course over ground
     */
    private final double COG;
    /**
     *Speed over ground
     */
    private final double SOG;
    /**
     * ship heading
     */
    private final int heading;
    /**
     * the cargo of the ship
     */
    private final int cargo;
    /**
     * the transceiver class of the ship
     */
    private final char transceiverClass;

    /**
     * Constructor of the class ais message
     * @param baseDateTime Date/time of AIS message
     * @param latitude ship latitude
     * @param longitude ship longitude
     * @param COG Course over ground
     * @param SOG Speed over ground
     * @param heading ship heading
     */
    public AISMessage(LocalDateTime baseDateTime,double latitude,double longitude,double COG,double SOG,int heading,int cargo,char transceiverClass){
        this.baseDateTime = baseDateTime;
        this.coordinates = new Coordinates(latitude, longitude);
        this.COG= COG;
        this.SOG=SOG;
        this.heading=heading;
        this.cargo = cargo;
        this.transceiverClass = transceiverClass;
    }

    /**
     *
     * @return the time and date
     */
    public LocalDateTime getBaseDateTime() {
        return baseDateTime;
    }

    /**
     * @return coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     *
     * @return course over ground
     */
    public double getCOG() {
        return COG;
    }

    /**
     *
     * @return speed over ground
     */
    public double getSOG() {
        return SOG;
    }

    /**
     *
     * @return heading
     */
    public int getHeading() {
        return heading;
    }

    /**
     *
     * @return cargo of the ship
     */
    public int getCargo() {
        return cargo;
    }

    /**
     *
     * @return transceiver class of the ship
     */
    public char getTransceiverClass() {
        return transceiverClass;
    }

    /**
     *
     * @param c other AISMessage
     * calculates the distance between two AISMessages by using the Pythagorean theorem
     * @return distance between AISMessage in nautical miles
     */
    public double getDistanceAIMes(AISMessage c){
            double cat1 = this.coordinates.getLatitude() - c.coordinates.getLatitude();
            double cat2 = this.coordinates.getLongitude() - c.coordinates.getLongitude();
            return Math.sqrt((cat1 * cat1) + (cat2 * cat2)) * 60;
    }

    /**
     * Checks if other object is equal to this one
     * @param o the other object
     * @return true if the baseDateTime is equal, false if not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AISMessage that = (AISMessage) o;
        return Objects.equals(baseDateTime, that.baseDateTime);
    }

    /**
     * The hash code of this class
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(baseDateTime);
    }

    /**
     * Compares this object to other
     * @param o the object to compare
     * @return zero if they're equal a negative number if this is lesser and positive if its bigger
     */
    @Override
    public int compareTo(AISMessage o) {
        return this.baseDateTime.compareTo(o.baseDateTime);
    }

    @Override
    public String toString() {
        return "AISMessage{" +
                "baseDateTime=" + baseDateTime +
                ", latitude=" + coordinates.getLatitude() +
                ", longitude=" + coordinates.getLongitude() +
                ", COG=" + COG +
                ", SOG=" + SOG +
                ", heading=" + heading +
                '}' + '\n';
    }
}
