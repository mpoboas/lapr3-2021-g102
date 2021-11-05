package lapr.project.model;

import java.util.Date;

public class Trips {
    Port startingPort;
    Port destination;
    Date startDate;
    Date endDate;
    public Trips(Date sDate, Date eDate){
        this.startDate = sDate;
        this.endDate = eDate;
    }





    public double getTripDistance(){

    }
    public double getTripDuration(){return (this.endDate.getTime()-this.startDate.getTime())/(60*60 * 1000);}


}
