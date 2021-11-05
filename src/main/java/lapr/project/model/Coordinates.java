package lapr.project.model;

public class Coordinates {
    double lat;
    double lon;
    public Coordinates(double lat, double lon){
        this.lat = lat;
        this.lon = lon;
    }


    public double getDistanceCord(Coordinates c){//delivers distence between 2 coordinates in nautical miles
        double cat1 = this.lat - c.lat;
        double cat2 = this.lon - c.lon;
        return ((cat1*cat1) +(cat2*cat2)) * 60;
    }

}
