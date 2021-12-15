package lapr.project.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ImportTrip {
    private final List<Trips> listOfImportedTrips;
    public ImportTrip(){
        this.listOfImportedTrips = new ArrayList<>();
    }

    public void importTripsData(String fileDirectory){
        try(Scanner read = new Scanner(new File(fileDirectory))) {
            read.nextLine();
            while (read.hasNextLine()) {
                String[] attributes = read.nextLine().split(",");
                if (!containerAlreadyImported(attributes[0]))
                    listOfImportedTrips.add(createTrip(attributes));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public boolean containerAlreadyImported(String code){
        int id = Integer.parseInt(code);
        for (Trips p: listOfImportedTrips) {
            if (p.getId() == id )
                return true;
        }
        return false;
    }
    public List<Trips> getListOfImportedTrips(){
        return listOfImportedTrips;
    }

    private Trips createTrip(String[] atributtes){
        int idtrip = Integer.parseInt(atributtes[0]);
        int idPortDeparture = Integer.parseInt(atributtes[1]);
        int idPortWarehouseArrival = Integer.parseInt(atributtes[2]);
        int idVehicle = Integer.parseInt(atributtes[3]);
        Date endDate = new Date(atributtes[4]);
        return new Trips(idtrip, idPortDeparture, idPortWarehouseArrival, idVehicle, endDate);
    }
}
