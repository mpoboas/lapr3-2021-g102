package lapr.project.model;

import lapr.project.stores.PortStore;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class imports the ports from a file
 */
public class ImportPorts {

    /**
     * List of all the ports from the file
     */
    private final List<Port> listOfImportedPorts;

    /**
     * The class that stores the ports imported
     */
    private final PortStore store;

    public ImportPorts(PortStore store){
        this.store = store;
        listOfImportedPorts = new ArrayList<>();
    }

    /**
     * From a file gets all the attributes and creates the object
     * @param fileDirectory file directory that contains the attributes
     * @throws IOException
     */
    public void importPortsData(String fileDirectory) throws IOException {
        try(Scanner read = new Scanner(new File(fileDirectory))) {
            read.nextLine();
            while (read.hasNextLine()) {
                String[] attributes = read.nextLine().split(",");
                if (!portAlreadyImported(attributes[2]))
                    listOfImportedPorts.add(createPort(attributes));
            }
        }

    }

    /**
     * Verifies if the port was already imported
     * @param code code of the port
     * @return true if it was already imported, false if not
     */
    public boolean portAlreadyImported(String code){
        int id = Integer.parseInt(code);
        for (Port p: listOfImportedPorts) {
            if (p.getid() == id)
                return true;
        }
        return false;
    }

    /**
     * From an array of attributes creates the object of ports
     * @param attributes array of the attributes
     * @return the created port
     */
    private Port createPort(String[] attributes){
        String continent = attributes[0];
        String country = attributes[1];
        int code = Integer.parseInt(attributes[2]);
        String name = attributes[3];
        double latitude = Double.parseDouble(attributes[4]);
        double longitude = Double.parseDouble(attributes[5]);
        int capacity = Integer.parseInt(attributes[6]);
        return store.createPort(code, name, continent, country, longitude, latitude , capacity);
    }

    /**
     * @return gets the list of the ports from the file
     */
    public List<Port> getListOfImportedPorts(){
        return listOfImportedPorts;
    }

    /**
     * Adds all the ports imported to the store
     */
    public void addPorts(){
        store.buildPortTree(this.listOfImportedPorts);
        listOfImportedPorts.clear();
    }

    /**
     * @return gets the store that saves the ports
     */
    public PortStore getStore(){
        return this.store;
    }


}
