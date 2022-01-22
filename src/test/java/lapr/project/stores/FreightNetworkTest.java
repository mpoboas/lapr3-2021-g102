package lapr.project.stores;

import lapr.project.model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FreightNetworkTest {
    static String fileDirectoryCountry = "data/Data-Sprint3-4/countries.csv";
    static String fileDirectoryPorts = "data/data-shipsports/data-shipsports/data-ships&ports/bports.csv";
    static String fileDirectoryBorders = "data/Data-Sprint3-4/borders.csv";
    static String fileDirectorySeaDist = "data/Data-Sprint3-4/seadists.csv";
    static ImportPorts importP = new ImportPorts(new PortStore());
    static FreightNetwork network = new FreightNetwork();
    static List<Country> countries ;
    static List<Port> ports;
    @BeforeAll
    static void init(){
        try {
             countries = ImportCountry.importCountries(fileDirectoryCountry);
            List<Border> borders = ImportBorder.importBorders(fileDirectoryBorders);
            List<SeaDist> seaDists = ImportSeadist.importSeadistData(fileDirectorySeaDist);
            importP.importPortsData(fileDirectoryPorts);
             ports = importP.getListOfImportedPorts();
            network.buildFreightNetworkFromGivenData(countries, borders, ports, seaDists, 5);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    void buildFreightNetwork() {
        assertTrue(network.getGraph().vertices().containsAll(countries));
        assertTrue(network.getGraph().vertices().containsAll(ports));
    }

    @Test
    void shortestPaths() {
        List<String> expectedList = new ArrayList<>();
        expectedList.add("Funchal");
        expectedList.add("Mersin");
        expectedList.add("Salvador");
        expectedList.add("Hamburg");
        expectedList.add("Bergen");
        List<Port> portList = network.getMostCriticalPorts(5);
        assertEquals(expectedList.size(), portList.size());
        for (int i = 0; i < portList.size(); i++) {
            assertEquals(portList.get(i).getName(), expectedList.get(i));
        }
    }
}