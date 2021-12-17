package lapr.project.model;

import lapr.project.stores.PortStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class imports the seadists from a file.
 */
public class ImportSeadist {

    private static final String countryFile = "data/Data-Sprint3-4/countries.csv";
    private static final String portFile = "data/data-shipsports/data-shipsports/data-ships&ports/bports.csv";

    /**
     * Imports all the attributes from a file and creates the list of instances of Seadist.
     *
     * @param fileDirectory file path that contains the attributes
     * @return the list of instances of Seadist
     */
    public static List<SeaDist> importSeadistData(String fileDirectory) throws IOException {
        ImportPorts importPorts = new ImportPorts(new PortStore());
        importPorts.importPortsData(portFile);
        return importSeadistData(ImportCountry.importCountries(countryFile), importPorts.getListOfImportedPorts(), fileDirectory);
    }

    /**
     * Imports all the attributes from a file and creates the list of instances of Seadist.
     *
     * @param countryList   list of countries
     * @param portList      list of ports
     * @param fileDirectory file path that contains the attributes
     * @return the list of instances of Seadist
     */
    public static List<SeaDist> importSeadistData(List<Country> countryList, List<Port> portList, String fileDirectory) throws FileNotFoundException {
        List<SeaDist> seadistList = new ArrayList<>();
        try (Scanner read = new Scanner(new File(fileDirectory))) {
            read.nextLine();
            while (read.hasNextLine()) {
                String[] attributes = read.nextLine().split(",");
                Country fromCountry = null;
                Country toCountry = null;
                Port fromPort = null;
                Port toPort = null;

                for (Country country : countryList) {
                    if (attributes[0].equals(country.getCountry()))
                        fromCountry = country;
                    if (attributes[3].equals(country.getCountry()))
                        toCountry = country;
                }

                for (Port port : portList) {
                    if (Integer.parseInt(attributes[1]) == port.getid())
                        fromPort = port;
                    if (Integer.parseInt(attributes[4]) == port.getid())
                        toPort = port;
                }

                if (fromCountry != null && toCountry != null && fromPort != null && toPort != null) {
                    SeaDist seadist = new SeaDist(fromCountry, fromPort, toCountry, toPort, Integer.parseInt(attributes[6]));
                    if (!seadistList.contains(seadist))
                        seadistList.add(seadist);
                }
            }
            return seadistList;
        }
    }
}
