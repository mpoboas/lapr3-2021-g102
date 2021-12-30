package lapr.project.model;

import java.awt.geom.Point2D;

public class Port extends Place implements Comparable<Port> {

    private final int id;
    private final String name;
    private final Point2D.Double coords;
    private final int capacity;

    public Port(int id, String name, String continent, String country, double longitude, double latitude,int capacity) {
        super(continent, country, longitude, latitude);
        this.id = id;
        this.name = name;
        //this.continent = continent;
        //this.country = country;
        //this.latitude = latitude;
        //this.longitude = longitude;
        coords = new Point2D.Double(longitude, latitude);
        this.capacity = capacity;
    }

    public int getid() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

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

    public Point2D.Double getCoords() {
        return coords;
    }

    public int getCapacity(){return this.capacity;}

    @Override
    public int compareTo(Port o) {
        return this.id - o.id;
    }

    @Override
    public String toString() {
        return "Port with id: " + this.id +
                "; Latitude: " + this.getLatitude() +
                ", Longitude: " + this.getLongitude();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Port port = (Port) o;
        return id == port.id && Double.compare(port.getLatitude(), getLatitude()) == 0 && Double.compare(port.getLongitude(), getLongitude()) == 0;
    }
}
