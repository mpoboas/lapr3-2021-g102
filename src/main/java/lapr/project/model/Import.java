package lapr.project.model;

import lapr.project.stores.ShipStore;


import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Import {
    private List<Ship> listOfImportedShips;
    private ShipStore shipStore;

    public Import() {
        shipStore = new ShipStore(); // Mudar dps para company.getShipStore()
        listOfImportedShips = new ArrayList<>();
    }

    public void importShipsData(String fileDirectory) throws IOException {
        //listOfImportedShips = new ArrayList<>();

        //Path pathToFile = Paths.get(fileDirectory);
        //BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII);

        Scanner readfile = new Scanner(new File(fileDirectory));
        readfile.nextLine();
        while (readfile.hasNextLine()) {
            String[] attributes = readfile.nextLine().split(",");
            Ship ship;
            if (!shipAlreadyImported(attributes[0])) {
                ship = createShip(attributes);
            } else {
                ship = getShipByMMSI(attributes[0]);
            }
            ship.addAISMessage(createAISMessage(attributes));

            if (!listOfImportedShips.contains(ship))
                listOfImportedShips.add(ship);
        }
        readfile.close();
    }

    public boolean shipAlreadyImported(String mmsiS) {
        int mmsi = Integer.parseInt(mmsiS);
        for (Ship s : listOfImportedShips) {
            if (s.getMMSI() == mmsi)
                return true;
        }
        return false;
    }

    public Ship getShipByMMSI(String mmsiS) {
        int mmsi = Integer.parseInt(mmsiS);
        for (Ship s : listOfImportedShips) {
            if (s.getMMSI() == mmsi)
                return s;
        }
        throw new IllegalArgumentException("Not found");
    }

    public Ship createShip(String[] attributes) {
        int mmsi = Integer.parseInt(attributes[0]);
        String vesselName = attributes[7];
        String imo = attributes[8];
        String callSign = attributes[9];
        int vesselType = Integer.parseInt(attributes[10]);
        int length = Integer.parseInt(attributes[11]);
        int width = Integer.parseInt(attributes[12]);
        double draft = Double.parseDouble(attributes[13]);
        int cargo;
        if (attributes[14].equals("NA")) {
            cargo = -1;
        } else
            cargo = Integer.parseInt(attributes[14]);
        char transceiverClass = attributes[15].charAt(0);
        return shipStore.createShip(mmsi, vesselName, imo, callSign, vesselType, length, width, draft, cargo, transceiverClass);
    }

    public AISMessage createAISMessage(String[] attributes) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy H:mm");
        LocalDateTime baseDateTime = LocalDateTime.parse(attributes[1], dtf);
        double latitude = Double.parseDouble(attributes[2]);
        double longitude = Double.parseDouble(attributes[3]);
        double COG = Double.parseDouble(attributes[4]);
        double SOG = Double.parseDouble(attributes[5]);
        int heading = Integer.parseInt(attributes[6]);
        return new AISMessage(baseDateTime, latitude, longitude, COG, SOG, heading);
    }

    public void addShips() {
        for (Ship s : listOfImportedShips) {
            shipStore.addShip(s);
        }
    }

    public List<Ship> getListOfImportedShips() {
        return listOfImportedShips;
    }
}
