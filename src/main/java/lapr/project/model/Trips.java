package lapr.project.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Trips {
    private int id;
    private int idPortWarehouseDeparture;
    private int idPortWarehouseArrival;
    private int idVehicle;
    private Date endDate;
    private final List<AISMessage> aisMessagesList;
    private final List<ArrivalDeparture> ArrivalDeps;
    private boolean isOver;
    private Duration duration;
    public Trips ( int id, int idPortWarehouseDeparture, int idPortWarehouseArrival, int idVehicle, Date endDate, List <ArrivalDeparture> ArrivalDeps){
        this.id = id;
        this.idPortWarehouseDeparture = idPortWarehouseDeparture;
        this.idPortWarehouseArrival = idPortWarehouseArrival;
        this.idVehicle = idVehicle;
        this.endDate = endDate;
        this.isOver = false;
        this.ArrivalDeps = ArrivalDeps;
        aisMessagesList = new ArrayList<>();

    }
    public Trips (){
        this.isOver = false;
        aisMessagesList = new ArrayList<>();
        ArrivalDeps = new ArrayList<>();
    }
    public int getId(){
        return this.id;
    }
    public int getIdPortWarehouseDeparture(){
        return this.idPortWarehouseDeparture;
    }
    public int getIdPortWarehouseArrival(){
        return this.idPortWarehouseArrival;
    }
    public int getIdVehicle(){
        return this.idVehicle;
    }
    public Date getEndDate(){
        return this.endDate;
    }
    public List<ArrivalDeparture> getADLIst(){return this.ArrivalDeps;}
    public void addArrivalDeparture(ArrivalDeparture ad){
        this.ArrivalDeps.add(ad);
    }

    public int getAISMessageListSize() {
        return aisMessagesList.size();
    }

    public AISMessage getAIsMessage(int i) {
        return aisMessagesList.get(i);
    }

    public List<AISMessage> getAisMessagesList() {
        return aisMessagesList;
    }

    public LocalDateTime getStart() {
        return aisMessagesList.get(0).getBaseDateTime();
    }

    public LocalDateTime getEnd() {
        return aisMessagesList.get(aisMessagesList.size() - 1).getBaseDateTime();
    }

    /**
     * Sets the trip duration after it is over
     */
    private void setTripDuration() {
        duration = Duration.between(this.aisMessagesList.get(0).getBaseDateTime(), this.aisMessagesList.get(aisMessagesList.size() - 1).getBaseDateTime());
    }

    /**
     * @param aism to be added to the trip list of AISMessage
     */
    public void addAISMessage(AISMessage aism) {
        aisMessagesList.add(aism);
    }

    /**
     * imports a list to the list of ais messages
     *
     * @param msgList ais message list to import
     */
    public void addAISMessage(List<AISMessage> msgList) {
        for (AISMessage msg : msgList) {
            addAISMessage(msg);
        }
    }

    /**
     * Changes the trip status to complet
     */
    public void endTrip() {
        this.isOver = true;
        setTripDuration();
    }

    /**
     * @return if trip has already been finished
     */
    public boolean getTripStatus() {
        return isOver;
    }

    /**
     * @return trip distance in nautical miles
     */
    public double getTripDistance() {
        double distance = 0;
        for (int i = 0; i < aisMessagesList.size() - 1; i++) {
            distance = distance + aisMessagesList.get(i).getDistanceAIMes(aisMessagesList.get(i + 1));
        }
        return distance;
    }

    /**
     * @return trip duration in hours
     */
    public long getTripDuration() {
        return duration.toHours();
    }
}
