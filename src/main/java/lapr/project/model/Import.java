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
    /**
     * List of the ships imported from the file
     */
    private List<Ship> listOfImportedShips;
    /**
     * Store class that stores the ships imported
     */
    private final ShipStore shipStore;

    /**
     * Import class constructor
     */
    public Import(ShipStore store) {
        shipStore = store; // Mudar dps para company.getShipStore()
        listOfImportedShips = new ArrayList<>();
    }

    /**
     * Imports all the ships from a file and adds them to the list of imported tests
     *
     * @param fileDirectory file directory that contains the ships information
     * @throws IOException
     */
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

    /**
     * Checks if a ship was already imported
     *
     * @param mmsiS mmsi of the ship to check if its imported
     * @return true if the ship was already imported, false if not
     */
    public boolean shipAlreadyImported(String mmsiS) {
        int mmsi = Integer.parseInt(mmsiS);
        for (Ship s : listOfImportedShips) {
            if (s.getMMSI() == mmsi)
                return true;
        }
        return false;
    }

    /**
     * Gets the store used to import the ships
     *
     * @return store instance
     */
    public ShipStore getShipStore() {
        return this.shipStore;
    }

    /**
     * Searches for a determined ship by its mmsi
     *
     * @param mmsiS mmsi of the boat to find
     * @return the ship found
     */
    public Ship getShipByMMSI(String mmsiS) {
        int mmsi = Integer.parseInt(mmsiS);
        for (Ship s : listOfImportedShips) {
            if (s.getMMSI() == mmsi)
                return s;
        }
        throw new IllegalArgumentException("Not found");
    }

    /**
     * Creates an object of ship from the data of the file
     *
     * @param attributes an array of all the attributes to create the ship
     * @return the object ship created
     */
    public Ship createShip(String[] attributes) {
        int mmsi = Integer.parseInt(attributes[0]);
        String vesselName = attributes[7];
        String imo = attributes[8];
        String callSign = attributes[9];
        int vesselType = Integer.parseInt(attributes[10]);
        int length = Integer.parseInt(attributes[11]);
        int width = Integer.parseInt(attributes[12]);
        double draft = Double.parseDouble(attributes[13]);
        return shipStore.createShip(mmsi, vesselName, imo, callSign, vesselType, length, width, draft);
    }

    /**
     * Creates an object of aismessage from the data of the file
     *
     * @param attributes an array of all the attributes to create the aismessage
     * @return the object aismessage created
     */
    public AISMessage createAISMessage(String[] attributes) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy H:mm");
        LocalDateTime baseDateTime = LocalDateTime.parse(attributes[1], dtf);
        double latitude = Double.parseDouble(attributes[2]);
        double longitude = Double.parseDouble(attributes[3]);
        double COG = Double.parseDouble(attributes[4]);
        double SOG = Double.parseDouble(attributes[5]);
        int heading = Integer.parseInt(attributes[6]);
        int cargo;
        if (attributes[14].equals("NA")) {
            cargo = -1;
        } else
            cargo = Integer.parseInt(attributes[14]);
        char transceiverClass = attributes[15].charAt(0);
        return new AISMessage(baseDateTime, latitude, longitude, COG, SOG, heading, cargo, transceiverClass);
    }

    /**
     * Adds all the ships imported to the ship store
     */
    public void addShips() {
        for (Ship s : listOfImportedShips) {
            shipStore.addShip(s);
        }
    }

    /**
     * Gets the list of the ships imported
     *
     * @return imported ships
     */
    public List<Ship> getListOfImportedShips() {
        return listOfImportedShips;
    }
}
