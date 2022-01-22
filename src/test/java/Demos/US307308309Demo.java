package Demos;

import lapr.project.data.ConnectionFactory;
import lapr.project.data.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Scanner;

public class US307308309Demo {
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) throws IOException, SQLException {
        DatabaseConnection databaseConnection = ConnectionFactory.getInstance().getDatabaseConnection();
        int option;
        do {

            System.out.println("US307 - Get a warning whenever a cargo manifest exceeds a warehouse's available capacity");
            System.out.println("US308 - Ensure that the number of containers in a manifest does not exceed the ship´s available capacity");
            System.out.println("US309 - Ensure that a cargo manifest can't be added to a ship that's occupied, at a certain date");
            System.out.println("Type 307, 308 or 309 to choose a US. Type 1 to send in custom values or type 0 to exit:");
            option = scan.nextInt();
            switch (option) {
                case 307:
                    insertArrivalDeparture(4,501,502, LocalDateTime.parse("2022-01-10T00:45:00"),LocalDateTime.parse("2022-01-10T01:00:00"), LocalDateTime.parse("2022-01-10T00:55:00"),0,31, databaseConnection);
                    break;
                case 308:
                    insertArrivalDeparture(4,501,502, LocalDateTime.parse("2022-01-10T00:45:00"),LocalDateTime.parse("2022-01-10T01:00:00"), LocalDateTime.parse("2022-01-10T00:55:00"),30,0, databaseConnection);
                    break;
                case 309:
                    insertArrivalDeparture(4,501,502, LocalDateTime.parse("2022-01-10T00:45:00"),LocalDateTime.parse("2022-01-10T01:00:00"), LocalDateTime.parse("2022-01-10T00:55:00"),40,0, databaseConnection);
                    break;
                case 1:
                    System.out.println("Trip ID:");
                    int idTrip = scan.nextInt();
                    System.out.println("Port Warehouse Departure ID:");
                    int idPortWarehouseDeparture = scan.nextInt();
                    System.out.println("Port Warehouse Arrival ID:");
                    int idPortWarehouseArrival = scan.nextInt();
                    System.out.println("Departure date (e.g: 2022-01-10T00:00:00):");
                    LocalDateTime departureDate = LocalDateTime.parse(scan.next());
                    System.out.println("Arrival date (e.g: 2022-01-10T00:00:00):");
                    LocalDateTime arrivalDate = LocalDateTime.parse(scan.next());
                    System.out.println("Estimated arrival date (e.g: 2022-01-10T00:00:00):");
                    LocalDateTime estimatedArrivalDate = LocalDateTime.parse(scan.next());
                    System.out.println("Loading Cargo Manifest ID:");
                    int idCargoManifestLoad = scan.nextInt();
                    System.out.println("Unloading Cargo Manifest ID:");
                    int idCargoManifestUnload = scan.nextInt();
                    insertArrivalDeparture(idTrip, idPortWarehouseDeparture, idPortWarehouseArrival, departureDate, arrivalDate, estimatedArrivalDate, idCargoManifestUnload, idCargoManifestLoad, databaseConnection);
            }
        } while (option != 0);

    }

    private static void insertArrivalDeparture(int idTrip, int idPortWarehouseDeparture, int idPortWarehouseArrival, LocalDateTime departureDate, LocalDateTime arrivalDate, LocalDateTime estimatedArrivalDate, int idCargoManifestLoad, int idCargoManifestUnload, DatabaseConnection databaseConnection) throws SQLException {
        String sqlCommand = "INSERT INTO ARRIVALDEPARTURE(IDTRIP,IDPORTWAREHOUSEDEPARTURE,IDPORTWAREHOUSEARRIVAL,DEPARTUREDATE,ARRIVALDATE,ESTIMATEDARRIVALDATE,IDCARGOMANIFESTLOAD,IDCARGOMANIFESTUNLOAD) VALUES( ? , ? , ? , ? , ? , ? , ? , ? )";
        Connection connection = databaseConnection.getConnection();

        try (PreparedStatement insertArrivalDeparture = connection.prepareStatement(sqlCommand)) {
            insertArrivalDeparture.setInt(1, idTrip);
            insertArrivalDeparture.setInt(2, idPortWarehouseDeparture);
            insertArrivalDeparture.setInt(3, idPortWarehouseArrival);
            Timestamp departureDateTimestamp = Timestamp.valueOf(departureDate);
            insertArrivalDeparture.setTimestamp(4, departureDateTimestamp);
            Timestamp arrivalDateTimestamp = Timestamp.valueOf(arrivalDate);
            insertArrivalDeparture.setTimestamp(5, arrivalDateTimestamp);
            Timestamp estimatedArrivalDateTimestamp = Timestamp.valueOf(estimatedArrivalDate);
            insertArrivalDeparture.setTimestamp(6, estimatedArrivalDateTimestamp);
            if (idCargoManifestLoad == 0) insertArrivalDeparture.setNull(7, idCargoManifestLoad);
            else insertArrivalDeparture.setInt(7, idCargoManifestLoad);
            if (idCargoManifestUnload == 0) insertArrivalDeparture.setNull(8, idCargoManifestUnload);
            else insertArrivalDeparture.setInt(8, idCargoManifestUnload);
            insertArrivalDeparture.executeUpdate();
        }
    }

}
