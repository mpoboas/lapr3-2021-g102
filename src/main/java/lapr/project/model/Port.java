package lapr.project.model;

import java.awt.geom.Point2D;

public class Port extends Place implements Comparable<Port> {

    private final int id;
    private final String name;
    //private final String continent;
    //private final String country;
    //private final double latitude; // eixo y
   // private final double longitude; //eixo x
    private final Point2D.Double coords;

    public Port(int id , String name, String continent, String country, double longitude, double latitude){
        super(continent,country,longitude, latitude);
        this.id  =id;
        this.name = name;
        //this.continent = continent;
        //this.country = country;
        //this.latitude = latitude;
        //this.longitude = longitude;
        coords = new Point2D.Double(longitude,latitude);
    }
    public int getid(){return this.id;}
    public String getName(){return this.name;}
    public String getContinent() {
        return super.getContinent();
    }
    public String getCountry() {
        return super.getCountry();
    }
    public double getLatitude() {
        return super.getLatitude();
    }
    public double getLongitude() {
        return super.getLongitude();
    }



    @Override
    public int compareTo(Port o) {
        return this.id - o.id;
    }

    @Override
    public String toString() {
        return "Port{" +
                "id=" + id +
                ", latitude=" + getLatitude() +
                ", longitude=" + getLongitude() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Port port = (Port) o;
        return id == port.id && Double.compare(port.getLatitude(), getLatitude()) == 0 && Double.compare(port.getLongitude(), getLongitude()) == 0;
    }

    public Point2D.Double getCoords() {
        return coords;
    }
}
