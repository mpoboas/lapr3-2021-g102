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
        network.buildFreightNetworkFromGivenData(ImportCountry.importCountries("data/Data-Sprint3-4/countries.csv"), ImportBorder.importBorders("data/Data-Sprint3-4/borders.csv"), port.getListOfImportedPorts(), ImportSeadist.importSeadistData("data/Data-Sprint3-4/seadists.csv"), 10);
        places.add(network.getGraph().vertex(135));
        places.add(network.getGraph().vertex(1));
        path = new ShortestPath(network.getGraph().vertex(140), network.getGraph().vertex(0), places, network);
        LinkedList<Place> fPath = path.getpathNplaces();
        assertEquals(path.getpathNplaces().size(), fPath.size());
        assertEquals(network.getGraph().vertex(140), fPath.get(0));
        assertEquals(network.getGraph().vertex(0), fPath.get(fPath.size()-1));
        assertEquals(29802,path.getnPlacesDistance(),0.3);
    }
    @Test
    void getNplacesPath() throws IOException {
        FreightNetwork network = new FreightNetwork();
        places = new ArrayList<>();
        ImportPorts port = new ImportPorts(new PortStore());
        port.importPortsData("data/data-shipsports/data-shipsports/data-ships&ports/bports.csv");
        network.buildFreightNetworkFromGivenData(ImportCountry.importCountries("data/Data-Sprint3-4/countries.csv"), ImportBorder.importBorders("data/Data-Sprint3-4/borders.csv"), port.getListOfImportedPorts(), ImportSeadist.importSeadistData("data/Data-Sprint3-4/seadists.csv"), 10);
        places.add(network.getGraph().vertex(135));
        places.add(network.getGraph().vertex(1));
        places.add(network.getGraph().vertex(12));
        places.add(network.getGraph().vertex(14));
        places.add(network.getGraph().vertex(5));
        places.add(network.getGraph().vertex(51));
        places.add(network.getGraph().vertex(15));
        places.add(network.getGraph().vertex(17));
        places.add(network.getGraph().vertex(59));
        places.add(network.getGraph().vertex(10));
        places.add(network.getGraph().vertex(56));
        places.add(network.getGraph().vertex(11));
        places.add(network.getGraph().vertex(50));
        places.add(network.getGraph().vertex(60));
        places.add(network.getGraph().vertex(135));
        places.add(network.getGraph().vertex(121));
        places.add(network.getGraph().vertex(131));
        places.add(network.getGraph().vertex(111));
        places.add(network.getGraph().vertex(112));
        places.add(network.getGraph().vertex(75));
        places.add(network.getGraph().vertex(58));
        places.add(network.getGraph().vertex(43));
        places.add(network.getGraph().vertex(52));
        places.add(network.getGraph().vertex(9));

        path = new ShortestPath(network.getGraph().vertex(140), network.getGraph().vertex(0), places, network);
        LinkedList<Place> fPath = path.getpathNplaces();
        assertEquals(path.getpathNplaces().size(), fPath.size());
        assertEquals(network.getGraph().vertex(140), fPath.get(0));
        assertEquals(network.getGraph().vertex(0), fPath.get(fPath.size()-1));
        assertEquals(321664.9,path.getnPlacesDistance(),0.01);
    }

    @Test
    void getLandPath() throws IOException {
        FreightNetwork network = new FreightNetwork();
        places = new ArrayList<>();
        ImportPorts port = new ImportPorts(new PortStore());
        port.importPortsData("data/data-shipsports/data-shipsports/data-ships&ports/bports.csv");
        network.buildFreightNetworkFromGivenData(ImportCountry.importCountries("data/Data-Sprint3-4/countries.csv"), ImportBorder.importBorders("data/Data-Sprint3-4/borders.csv"), port.getListOfImportedPorts(), ImportSeadist.importSeadistData("data/Data-Sprint3-4/seadists.csv"), 10);
        places.add(network.getGraph().vertex(135));
        places.add(network.getGraph().vertex(1));
        path = new ShortestPath(network.getGraph().vertex(140), network.getGraph().vertex(0), places, network);
        LinkedList<Place> fPath = path.getLandPath();
        path.getDirectPath();
        assertEquals(path.getLandPath().size(), fPath.size());
        assertEquals(5584,path.getLandPathDistance(),0.3);
    }
    @Test
    void getDirectPath() throws IOException {
        FreightNetwork network = new FreightNetwork();
        places = new ArrayList<>();
        ImportPorts port = new ImportPorts(new PortStore());
        port.importPortsData("data/data-shipsports/data-shipsports/data-ships&ports/bports.csv");
        network.buildFreightNetworkFromGivenData(ImportCountry.importCountries("data/Data-Sprint3-4/countries.csv"), ImportBorder.importBorders("data/Data-Sprint3-4/borders.csv"),port.getListOfImportedPorts(), ImportSeadist.importSeadistData("data/Data-Sprint3-4/seadists.csv"), 10);
        places.add(network.getGraph().vertex(135));
        places.add(network.getGraph().vertex(1));
        path = new ShortestPath(network.getGraph().vertex(140), network.getGraph().vertex(0),places,network);
        LinkedList<Place> fPath = path.getDirectPath();
        assertEquals(path.getDirectPath().size(),fPath.size());
        assertEquals(5584,path.getDirectPathDistance(),0.3);


    }
    @Test
    void getSeaPath() throws IOException {
        FreightNetwork network = new FreightNetwork();
        places = new ArrayList<>();
        ImportPorts port = new ImportPorts(new PortStore());
        port.importPortsData("data/data-shipsports/data-shipsports/data-ships&ports/bports.csv");
        network.buildFreightNetworkFromGivenData(ImportCountry.importCountries("data/Data-Sprint3-4/countries.csv"), ImportBorder.importBorders("data/Data-Sprint3-4/borders.csv"),port.getListOfImportedPorts(), ImportSeadist.importSeadistData("data/Data-Sprint3-4/seadists.csv"), 10);
        places.add(network.getGraph().vertex(135));
        places.add(network.getGraph().vertex(1));
        path = new ShortestPath(network.getGraph().vertex(140), network.getGraph().vertex(0),places,network);
        LinkedList<Place> fPath = path.getSeaPath();
        assertEquals(path.getSeaPath().size(),fPath.size());
        assertEquals(0,path.getSeaPathDistance());
    }
}