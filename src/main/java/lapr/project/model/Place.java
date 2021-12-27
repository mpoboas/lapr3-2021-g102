package lapr.project.model;


public class Place {
    /**
     * The continent name
     */
    private final String continent;
    /**
     * The country name
     */
    private final String country;
    /**
     * The latitude of the place
     */
    private final double latitude;
    /**
     * The longitude of the place
     */
    private final double longitude;

    /**
     * Constructor of the Place, through a continent name, a country name, a longitude and a latitude.
     *
     * @param continent the continent name
     * @param country   the country name
     * @param longitude the longitude value
     * @param latitude  the latitude value
     */
    public Place(String continent, String country, double longitude, double latitude) {
        this.continent = continent;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;

    }

    /**
     * Gets the continent string.
     */
    public String getContinent() {
        return this.continent;
    }

    /**
     * Gets the country string.
     */
    public String getCountry() {
        return this.country;
    }

    /**
     * Gets the latitude value.
     */
    public double getLatitude() {
        return this.latitude;
    }

    /**
     * Gets the longitude value.
     */
    public double getLongitude() {
        return this.longitude;
    }

    public int compare(Place p1, Place p2){
        return p1.continent.compareTo(p2.continent);
    }
}
