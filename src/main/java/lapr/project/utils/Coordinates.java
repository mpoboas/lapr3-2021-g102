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

    public Coordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
