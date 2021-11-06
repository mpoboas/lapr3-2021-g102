package lapr.project.model;

public class Port {

    private final int id;
    private final String name;
    private final String continent;
    private final String country;
    private final double latitude;
    private final double longitude;

    public Port(int id , String name, String continent, String country, double latitude, double longitude){
        this.id  =id;
        this.name = name;
        this.continent = continent;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
