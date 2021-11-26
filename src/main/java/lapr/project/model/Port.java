package lapr.project.model;

import java.awt.geom.Point2D;

public class Port implements Comparable<Port> {

    private final int id;
    private final String name;
    private final String continent;
    private final String country;
    private final double latitude; // eixo y
    private final double longitude; //eixo y
    private final Point2D.Double coords;

    public Port(int id , String name, String continent, String country, double longitude, double latitude){
        this.id  =id;
        this.name = name;
        this.continent = continent;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        coords = new Point2D.Double(longitude,latitude);
    }
    public int getid(){return this.id;}
    public String getName(){return this.name;}
    public String getContinent(){return this.continent;}
    public String getCountry(){return this.country;}
    public double getLatitude(){return this.latitude;}
    public double getLongitude(){return this.longitude;}



    @Override
    public int compareTo(Port o) {
        return this.id - o.id;
    }

    @Override
    public String toString() {
        return "Port{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Port port = (Port) o;
        return id == port.id && Double.compare(port.latitude, latitude) == 0 && Double.compare(port.longitude, longitude) == 0;
    }

    public Point2D.Double getCoords() {
        return coords;
    }
}
