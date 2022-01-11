package lapr.project.model;

import lapr.project.data.ConnectionFactory;
import lapr.project.stores.FreightNetwork;
import lapr.project.stores.PortStore;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShortestPathTest {
    ShortestPath path;
    List<Place> places;


    @Test
    void getFullPath() throws IOException {
        FreightNetwork network = new FreightNetwork();
        places = new ArrayList<>();
        ImportPorts port = new ImportPorts(new PortStore());
        port.importPortsData("data/data-shipsports/data-shipsports/data-ships&ports/bports.csv");
        network.buildFreightNetworkFromGivenData(ImportCountry.importCountries("data/Data-Sprint3-4/countries.csv"), ImportBorder.importBorders("data/Data-Sprint3-4/borders.csv"),port.getListOfImportedPorts(), ImportSeadist.importSeadistData("data/Data-Sprint3-4/seadists.csv"), 10);
        places.add(network.getGraph().vertex(135));
        places.add(network.getGraph().vertex(1));
        path = new ShortestPath(network.getGraph().vertex(140), network.getGraph().vertex(0),places,network);
        LinkedList<Place> fPath = path.getFullPath();
        assertEquals(294,fPath.size());
        assertEquals(network.getGraph().vertex(140),fPath.get(0));
        assertEquals(network.getGraph().vertex(0),fPath.get(293));
    }
}