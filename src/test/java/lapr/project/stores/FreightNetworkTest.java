package lapr.project.stores;

import lapr.project.model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

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
            List<Country> countries = ImportCountry.importCountries(fileDirectoryCountry);
            List<Border> borders = ImportBorder.importBorders(fileDirectoryBorders);
            List<SeaDist> seaDists = ImportSeadist.importSeadistData(fileDirectorySeaDist);
            importP.importPortsData(fileDirectoryPorts);
            List<Port> ports = importP.getListOfImportedPorts();
            network.buildFreightNetworkFromGivenData(countries, borders, ports, seaDists, 5);
            System.out.println(network);
            //assertTrue(network.getGraph().vertices().containsAll());
            //assertTrue(network.getGraph().vertices().containsAll(network.getPorts()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}