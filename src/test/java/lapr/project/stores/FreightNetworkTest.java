package lapr.project.stores;

import lapr.project.model.*;
import lapr.project.utils.Pair;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FreightNetworkTest {
    String fileDirectoryCountry = "data/Data-Sprint3-4/countries.csv";
    String fileDirectoryPorts = "data/data-shipsports/data-shipsports/data-ships&ports/bports.csv";
    String fileDirectoryBorders = "data/Data-Sprint3-4/borders.csv";
    String fileDirectorySeaDist = "data/Data-Sprint3-4/seadists.csv";
    ImportPorts importP = new ImportPorts(new PortStore());
    FreightNetwork network = new FreightNetwork();

    @Test
    void buildFreightNetwork() {
        try {
            List<Country> countries = ImportCountry.importCountries(fileDirectoryCountry);
            List<Border> borders = ImportBorder.importBorders(fileDirectoryBorders);
            List<SeaDist> seaDists = ImportSeadist.importSeadistData(fileDirectorySeaDist);
            importP.importPortsData(fileDirectoryPorts);
            List<Port> ports = importP.getListOfImportedPorts();
            network.buildFreightNetworkFromGivenData(countries, borders, ports, seaDists, 5);
            System.out.println(network);
            assertTrue(network.getGraph().vertices().containsAll(countries));
            assertTrue(network.getGraph().vertices().containsAll(ports));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void shortestPaths() {
        try {
            List<Country> countries = ImportCountry.importCountries(fileDirectoryCountry);
            List<Border> borders = ImportBorder.importBorders(fileDirectoryBorders);
            List<SeaDist> seaDists = ImportSeadist.importSeadistData(fileDirectorySeaDist);
            importP.importPortsData(fileDirectoryPorts);
            List<Port> ports = importP.getListOfImportedPorts();
            network.buildFreightNetworkFromGivenData(countries, borders, ports, seaDists, 5);
        } catch (IOException e) {
            e.printStackTrace();
        }

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