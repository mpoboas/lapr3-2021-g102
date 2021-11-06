package lapr.project.model;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Trips {
    List<AISMessage> AIsMessages;
    private boolean isOver;
    private Duration duration;
    public Trips(){
        this.isOver = false;
        AIsMessages = new ArrayList<>();
    }
    /**
     * Sets the trip duration after it is over
     */
    private void setTripDuration(){
        duration = Duration.between(this.AIsMessages.get(0).getBaseDateTime(),this.AIsMessages.get(AIsMessages.size()-1).getBaseDateTime() );
    }
    /**
     *
     * @param aism to be added to the trip list of AISMessage
     */
    public void addAISMessage(AISMessage aism){
        AIsMessages.add(aism);
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
    for (int i = 0 ; i< AIsMessages.size(); i++){
        distance = distance + AIsMessages.get(i).getDistanceAIMes(AIsMessages.get(i+1));
    }
    return distance;
    }

    /**
     *
     * @return trip duration in hours
     */
    public long getTripDuration(){return duration.toHours();}


}
