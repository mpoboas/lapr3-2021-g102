package lapr.project.model;

import lapr.project.stores.FreightNetwork;
import lapr.project.stores.PortStore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShortestPathTest {
    ShortestPath path;
    List<Place> places = new ArrayList<>();
    static FreightNetwork network = new FreightNetwork();
    static ImportPorts port = new ImportPorts(new PortStore());

    @BeforeAll
    static void init() throws IOException {
        port.importPortsData("data/data-shipsports/data-shipsports/data-ships&ports/bports.csv");
        network.buildFreightNetworkFromGivenData(ImportCountry.importCountries("data/Data-Sprint3-4/countries.csv"), ImportBorder.importBorders("data/Data-Sprint3-4/borders.csv"),port.getListOfImportedPorts(), ImportSeadist.importSeadistData("data/Data-Sprint3-4/seadists.csv"), 10);

    }
    @Test
    void getFullPath() {
        places.add(network.getGraph().vertex(135));
        places.add(network.getGraph().vertex(1));
        path = new ShortestPath(network.getGraph().vertex(140), network.getGraph().vertex(0), places, network);
        LinkedList<Place> fPath = path.getpathNplaces();
        assertEquals(path.getpathNplaces().size(), fPath.size());
        assertEquals(29802,path.getnPlacesDistance(),0.3);
    }
    @Test
    void getNplacesPath()  {
        places.add(network.getGraph().vertex(12));

        places.add(network.getGraph().vertex(135));
        places.add(network.getGraph().vertex(1));
        places.add(network.getGraph().vertex(14));

        path = new ShortestPath(network.getGraph().vertex(140), network.getGraph().vertex(0), places, network);
        LinkedList<Place> fPath = path.getpathNplaces();
        assertEquals(path.getpathNplaces().size(), fPath.size());

        assertEquals(38919,path.getnPlacesDistance(),0.01);
    }
    @Test
    void getFullPath2()  {
        places.add(network.getGraph().vertex(12));



        path = new ShortestPath(network.getGraph().vertex(140), network.getGraph().vertex(0), places, network);
        LinkedList<Place> fPath = path.getpathNplaces();
        assertEquals(path.getpathNplaces().size(), fPath.size());

        assertEquals(24814.6,path.getnPlacesDistance(),0.1);
    }
    @Test
    void getFullPath3 () {



        path = new ShortestPath(network.getGraph().vertex(140), network.getGraph().vertex(0), places, network);
        LinkedList<Place> fPath = path.getpathNplaces();
        assertEquals(path.getpathNplaces().size(), fPath.size());

        assertEquals(5584.2,path.getnPlacesDistance(),0.1);
    }

    @Test
    void getLandPath()  {
        places.add(network.getGraph().vertex(135));
        places.add(network.getGraph().vertex(1));
        path = new ShortestPath(network.getGraph().vertex(135), network.getGraph().vertex(0), places, network);
        LinkedList<Place> fPath = path.getLandPath();
        assertEquals(path.getLandPath().size(), fPath.size());
        assertEquals(0,path.getLandPathDistance(),0.3);
    }

    @Test
    void getLandPath2(){
        places.add(network.getGraph().vertex(135));
        places.add(network.getGraph().vertex(100));
        path = new ShortestPath(network.getGraph().vertex(34), network.getGraph().vertex(56), places, network);

        LinkedList<Place> fPath = path.getLandPath();
        assertEquals(path.getLandPath().size(), fPath.size());
        assertEquals(3054,path.getLandPathDistance(),0.3);
    }
    @Test
    void getDirectPath() {
        places.add(network.getGraph().vertex(135));
        places.add(network.getGraph().vertex(1));
        path = new ShortestPath(network.getGraph().vertex(140), network.getGraph().vertex(0),places,network);
        LinkedList<Place> fPath = path.getDirectPath();
        assertEquals(path.getDirectPath().size(),fPath.size());
        assertEquals(5584,path.getDirectPathDistance(),0.3);


    }
    @Test
    void getSeaPath() {
       places.add(network.getGraph().vertex(135));
        places.add(network.getGraph().vertex(1));
        path = new ShortestPath(network.getGraph().vertex(140), network.getGraph().vertex(0),places,network);
        LinkedList<Place> fPath = path.getSeaPath();
        assertEquals(path.getSeaPath().size(),fPath.size());
        assertEquals(0,path.getSeaPathDistance());
    }
    @Test
    void getSeaPath2()  {
        places.add(network.getGraph().vertex(135));
        places.add(network.getGraph().vertex(143));
        path = new ShortestPath(network.getGraph().vertex(140), network.getGraph().vertex(130),places,network);
        LinkedList<Place> fPath = path.getSeaPath();
        assertEquals(path.getSeaPath().size(),fPath.size());
        assertEquals(9835.8,path.getSeaPathDistance(),0.01);
    }
}