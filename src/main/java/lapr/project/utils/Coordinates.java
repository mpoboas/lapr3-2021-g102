package lapr.project.utils;

public class Coordinates {
    /**
     * Latitude
     */
    private final double latitude;
    /**
     * Longitude
     */
    private final double longitude;

    /**
     * Constructor of the class coordinates
     * @param latitude ship latitude
     * @param longitude ship longitude
     */
    public Coordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
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
}
