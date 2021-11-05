package lapr.project.model;

import java.util.ArrayList;
import java.util.List;

public class Ship {
    int MMSI;
    String shipName;

    int nGenerators;
    String callSign;
    double widht;
    double lengh;
    double capacity;
    double draft;
    List<Trips> tripsList;
    public Ship(){

        this.tripsList = new ArrayList<Trips>();
    }


    public double getAvgSpeed(){return getTotalDistance()/getTotalTimeinSea();}
    public double getTotalDistance(){
        double distance = 0;
        for(int i = 0 ; i < tripsList.size();i++){
            //distance = distance + tripsList.get(i).gettripDistance();
        }
        return distance;

    }
    //public int numberOfMovements(){}
    //public double deltaDistance(){}
    public double getTotalTimeinSea(){
        double time = 0;
        for(int i = 0 ; i < tripsList.size();i++){
            time = time + tripsList.get(i).getTripDuration();
        }
        return time;
    }
}
