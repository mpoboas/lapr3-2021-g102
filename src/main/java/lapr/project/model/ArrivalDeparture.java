package lapr.project.model;

import java.sql.Timestamp;

public class ArrivalDeparture {
    private final int idTrip;
    private final int portWareDeparture;
    private final int portWarehouseArrival;
    private final Timestamp departureDate;
    private final Timestamp arrivalDate;
    private final int cargoManifestIdLoad;
    private final int cargoManifestIdUnload;
    public ArrivalDeparture(int idTrip, int portWareDeparture, int portWarehouseArrival, Timestamp departureDate, Timestamp arrivalDate, int cargoManifestIdLoad, int cargoManifestIdUnload){
        this.idTrip = idTrip;
        this.portWareDeparture = portWareDeparture;
        this.portWarehouseArrival = portWarehouseArrival;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.cargoManifestIdLoad = cargoManifestIdLoad;
        this.cargoManifestIdUnload = cargoManifestIdUnload;
    }

    public int getIdTrip() {
        return this.idTrip;
    }

    public int getPortWareDeparture() {
        return this.portWareDeparture;
    }

    public int getPortWarehouseArrival() {
        return this.portWarehouseArrival;
    }

    public Timestamp getArrivalDate() {
        return this.arrivalDate;
    }

    public Timestamp getDepartureDate() {
        return this.departureDate;
    }

    public int getCargoManifestIdLoad() {
        return this.cargoManifestIdLoad;
    }

    public int getCargoManifestIdUnload() {
        return this.cargoManifestIdUnload;
    }
}
