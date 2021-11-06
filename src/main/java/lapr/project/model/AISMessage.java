package lapr.project.model;

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
     * ship latitude
     */
    private final double latitude;
    /**
     * ship longitude
     */
    private final double longitude;
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
     * Constructor of the class ais message
     * @param baseDateTime Date/time of AIS message
     * @param latitude ship latitude
     * @param longitude ship longitude
     * @param COG Course over ground
     * @param SOG Speed over ground
     * @param heading ship heading
     */
    public AISMessage(LocalDateTime baseDateTime,double latitude,double longitude,double COG,double SOG,int heading){
        this.baseDateTime = baseDateTime;
        this.latitude=latitude;
        this.longitude=longitude;
        this.COG= COG;
        this.SOG=SOG;
        this.heading=heading;
    }

    /**
     *
     * @return the time and date
     */
    public LocalDateTime getBaseDateTime() {
        return baseDateTime;
    }

    /**
     *
     * @return latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     *
     * @return longitude
     */
    public double getLongitude() {
        return longitude;
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
     * @param c other AISMessage
     * calculates the distance between two AISMessages by using the Pythagorean theorem
     * @return distance between AISMessage in nautical miles
     */
    public double getDistanceAIMes(AISMessage c){
            double cat1 = this.latitude - c.latitude;
            double cat2 = this.longitude - c.longitude;
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
}
