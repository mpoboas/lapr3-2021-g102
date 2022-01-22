package lapr.project.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ImportTrip {
    private final List<Trips> listOfImportedTrips;

    public ImportTrip() {
        this.listOfImportedTrips = new ArrayList<>();
    }

    public void importTripsData(String fileDirectory) {
        try (Scanner read = new Scanner(new File(fileDirectory))) {
            read.nextLine();
            while (read.hasNextLine()) {
                String[] attributes = read.nextLine().split(",");
                Trips trip;
                if (!tripAlreadyImported(attributes[0])) {
                    trip = createTrip(attributes);
                } else {
                    trip = getTripByID(attributes[0]);
                }
                trip.addArrivalDeparture(createAD(attributes));
                if (!listOfImportedTrips.contains(trip))
                    listOfImportedTrips.add(trip);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public Trips getTripByID(String id) {
        int p = Integer.parseInt(id);
        for (Trips s : listOfImportedTrips) {
            if (s.getId() == p)
                return s;
        }
        throw new IllegalArgumentException("Not found");
    }

    public boolean tripAlreadyImported(String code) {
        int id = Integer.parseInt(code);
        for (Trips p : listOfImportedTrips) {
            if (p.getId() == id)
                return true;
        }
        return false;
    }

    public List<Trips> getListOfImportedTrips() {
        return listOfImportedTrips;
    }

    private Trips createTrip(String[] atributtes) {
        int idtrip = Integer.parseInt(atributtes[0]);
        int idPortDeparture = Integer.parseInt(atributtes[1]);
        int idPortWarehouseArrival = Integer.parseInt(atributtes[2]);
        int idVehicle = Integer.parseInt(atributtes[3]);
        Date endDate = new Date(atributtes[4]);
        return new Trips(idtrip, idPortDeparture, idPortWarehouseArrival, idVehicle, endDate, new ArrayList<>());
    }

    private ArrivalDeparture createAD(String[] attributes) {
        Date dd = (new Date(attributes[7]));
        Date ad = (new Date(attributes[8]));
        Date ead = (new Date(attributes[9]));
        int idPortWarehouseDeparture = Integer.parseInt(attributes[5]);
        int idPortWarehouseArrival = Integer.parseInt(attributes[6]);
        Timestamp departureDate = new Timestamp(dd.getTime());
        Timestamp arrivalDate = new Timestamp(ad.getTime());
        Timestamp estimatedArrivalDate = new Timestamp(ead.getTime());
        int idCargoManifestLoad;
        int idCargoManifestUnload;
        if (attributes[10].equalsIgnoreCase("null")) {
            idCargoManifestLoad = 0;
        } else {
            idCargoManifestLoad = Integer.parseInt(attributes[10]);
        }
        if (attributes[11].equalsIgnoreCase("null")) {
            idCargoManifestUnload = 0;
        } else {
            idCargoManifestUnload = Integer.parseInt(attributes[11]);
        }
        return new ArrivalDeparture(Integer.parseInt(attributes[0]), idPortWarehouseDeparture, idPortWarehouseArrival, departureDate, arrivalDate, estimatedArrivalDate, idCargoManifestLoad, idCargoManifestUnload);
    }
}
