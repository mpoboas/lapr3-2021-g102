package lapr.project.model;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Trips {
    Port startingPort;
    Port destination;
    List<AISMessage> AIsMessages;
    private Duration duration;
    public Trips(){

        AIsMessages = new ArrayList<AISMessage>();
    }
    private void setTripDuration(){
        duration = Duration.between(this.AIsMessages.get(0).getBaseDateTime(),this.AIsMessages.get(AIsMessages.size()-1).getBaseDateTime() );
    }
    public double getTripDistance(){//gets trip distance in nautical miles
    double distance =0;
    for (int i = 0 ; i< AIsMessages.size(); i++){
        distance = distance + AIsMessages.get(i).getDistanceAIMes(AIsMessages.get(i+1));
    }
    return distance;
    }
    public long getTripDuration(){return duration.toHours();}


}
