package lapr.project.utils;

import lapr.project.model.*;
import lapr.project.stores.PortStore;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FreightNetworkTest {
    String fileDirectoryCountry = "data/Data-Sprint3-4/countries.csv";
    String fileDirectoryPorts = "data/data-shipsports/data-shipsports/data-ships&ports/bports.csv";
    String fileDirectoryBorders = "data/Data-Sprint3-4/borders.csv";
    String fileDirectorySeaDist = "data/Data-Sprint3-4/seadists.csv";
    ImportPorts importP = new ImportPorts(new PortStore());

    @Test
    void buildFreightNetwork() {
        FreightNetwork network = new FreightNetwork();
        try {
            importP.importPortsData(fileDirectoryPorts);
            network.setCountries(ImportCountry.importCountries(fileDirectoryCountry));
            network.setBorders(ImportBorder.importBorders(fileDirectoryBorders));
            network.setPorts(importP.getListOfImportedPorts());
            network.setSeaDists(ImportSeadist.importSeadistData(fileDirectorySeaDist));
        } catch (IOException e) {
            e.printStackTrace();
        }
        network.buildFreightNetwork(5);
        System.out.println(network);
        assertTrue(network.getGraph().vertices().containsAll(network.getCountries()));
        assertTrue(network.getGraph().vertices().containsAll(network.getPorts()));
    }

    @Test
    void testToString() {
    }
}