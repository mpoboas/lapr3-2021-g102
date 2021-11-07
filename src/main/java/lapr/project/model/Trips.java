package lapr.project.model;

import lapr.project.utils.Coordinates;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Trips {
    private final List<AISMessage> aisMessagesList;
    private boolean isOver;
    private Duration duration;
    public Trips(){
        this.isOver = false;
        aisMessagesList = new ArrayList<>();
    }

    public AISMessage getAIsMessage(int i){return aisMessagesList.get(i);}

    public AISMessage getLastAISMessage(){return aisMessagesList.get(aisMessagesList.size()-1);}

    public LocalDateTime getStart(){
        return aisMessagesList.get(0).getBaseDateTime();
    }

    /**
     * Sets the trip duration after it is over
     */
    private void setTripDuration(){
        duration = Duration.between(this.aisMessagesList.get(0).getBaseDateTime(),this.aisMessagesList.get(aisMessagesList.size()-1).getBaseDateTime() );
    }
    /**
     *
     * @param aism to be added to the trip list of AISMessage
     */
    public void addAISMessage(AISMessage aism){
        aisMessagesList.add(aism);
    }

    /**
     * Changes the trip status to complet
     */
    public void endTrip(){
        this.isOver = true;
        setTripDuration();
    }

    /**
     *
     * @return if trip has already been finished
     */
    public boolean getTripStatus(){
        return isOver;
    }

    /**
     *
     * @return trip distance in nautical miles
     */
    public double getTripDistance(){
    double distance =0;
    for (int i = 0 ; i< aisMessagesList.size()-1; i++){
        distance = distance + aisMessagesList.get(i).getDistanceAIMes(aisMessagesList.get(i+1));
    }
    return distance;
    }

    /**
     *
     * @return trip duration in hours
     */
    public long getTripDuration(){return duration.toHours();}


}
