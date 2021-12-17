package lapr.project.model;


public class Place {

    private final String continent;
    private final String country;
    private final double latitude; // eixo y
    private final double longitude; //eixo x

    public Place(String continent, String country, double longitude, double latitude){
        this.continent = continent;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;

    }

    public String getContinent(){return this.continent;}
    public String getCountry(){return this.country;}
    public double getLatitude(){return this.latitude;}
    public double getLongitude(){return this.longitude;}
}
