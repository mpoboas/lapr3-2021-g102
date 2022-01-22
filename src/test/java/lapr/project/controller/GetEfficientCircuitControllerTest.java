package lapr.project.controller;

import lapr.project.model.*;
import lapr.project.stores.FreightNetwork;
import lapr.project.stores.PortStore;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GetEfficientCircuitControllerTest {
    String fileDirectoryCountry = "data/Data-Sprint3-4/countries.csv";
    String fileDirectoryPorts = "data/data-shipsports/data-shipsports/data-ships&ports/bports.csv";
    String fileDirectoryBorders = "data/Data-Sprint3-4/borders.csv";
    String fileDirectorySeaDist = "data/Data-Sprint3-4/seadists.csv";
    ImportPorts importP = new ImportPorts(new PortStore());

    @Test
    void getEfficientCycle() {
        FreightNetwork network = new FreightNetwork();
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

        Company comp = new Company("Company");
        comp.setFreightNetwork(network);
        App.getInstance().doLogin("trafficmanager@lei.sem3.pt","123456");

        GetEfficientCircuitController ctrl = new GetEfficientCircuitController(comp);

        LinkedList<Place> biggestCycle = ctrl.getEfficientCycle();
        assertEquals(network.getEfficientCycle(),ctrl.getEfficientCycle());
        assertEquals(36, network.getGraph().key(biggestCycle.getFirst()));
        App.getInstance().doLogout();
    }

    @Test
    void getEfficientCycle2() {
        try {
            GetEfficientCircuitController cTrl = new GetEfficientCircuitController();
            App.getInstance().doLogin("fleetmanager@lei.sem3.pt", "123456");
            cTrl.getEfficientCycle();
        }catch (Exception ex){
            assertEquals(IllegalStateException.class,ex.getClass());
            assertEquals("Not Authorized", ex.getMessage());
        }
    }
}