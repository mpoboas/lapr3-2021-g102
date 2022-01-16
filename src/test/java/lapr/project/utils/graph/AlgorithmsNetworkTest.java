package lapr.project.utils.graph;

import lapr.project.model.*;
import lapr.project.stores.FreightNetwork;
import lapr.project.stores.PortStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlgorithmsNetworkTest {
    final FreightNetwork network = new FreightNetwork();
    String fileDirectoryCountry = "data/Data-Sprint3-4/countries.csv";
    String fileDirectoryPorts = "data/data-shipsports/data-shipsports/data-ships&ports/bports.csv";
    String fileDirectoryBorders = "data/Data-Sprint3-4/borders.csv";
    String fileDirectorySeaDist = "data/Data-Sprint3-4/seadists.csv";
    ImportPorts importP = new ImportPorts(new PortStore());

    @BeforeEach
    public void setUp() {
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
    }

    @Test
    void getEfficientCycleTest(){
        LinkedList<Place> biggestCycle = network.getEfficientCycle();
        assertEquals(36, network.getGraph().key(biggestCycle.getFirst()));
    }

    @Test
    void getLessWeightedBiggestCycle(){
        ArrayList<LinkedList<Place>> setOfCycles = new ArrayList<>();
        LinkedList<Place> cycle1 = AlgorithmsNetwork.searchCycle(network.getGraph(), AlgorithmsNetwork.nearestNeighbour(network.getGraph(),network.getGraph().vertex(1)));
        LinkedList<Place> cycle2 = AlgorithmsNetwork.searchCycle(network.getGraph(), AlgorithmsNetwork.nearestNeighbour(network.getGraph(),network.getGraph().vertex(11)));
        LinkedList<Place> cycle3 = AlgorithmsNetwork.searchCycle(network.getGraph(), AlgorithmsNetwork.nearestNeighbour(network.getGraph(),network.getGraph().vertex(13)));
        setOfCycles.add(cycle1);
        setOfCycles.add(cycle2);
        setOfCycles.add(cycle3);
        LinkedList<Place> result = AlgorithmsNetwork.getLessWeightedBiggestCycle(network.getGraph(), setOfCycles);
        assertNotNull(result);
        assertNotEquals(0,result.size());
        assertEquals(cycle1,result);



    }


    @Test
    void nearestNeighbour() {
        List<Place> places = AlgorithmsNetwork.nearestNeighbour(network.getGraph(),network.getGraph().vertex(0));
        assertNotNull(places);
        assertNotEquals(0,places.size());
        assertEquals(28,places.size());
    }

    @Test
    void searchCycle() {
        LinkedList<Place> places = AlgorithmsNetwork.nearestNeighbour(network.getGraph(),network.getGraph().vertex(0));
        try {
            AlgorithmsNetwork.searchCycle(network.getGraph(),places);
        }catch (Exception ex){
            assertEquals(IllegalStateException.class, ex.getClass());
            assertEquals("Not Cycle Detected", ex.getMessage());
        }

        LinkedList<Place> places2 =AlgorithmsNetwork.searchCycle(network.getGraph(), AlgorithmsNetwork.nearestNeighbour(network.getGraph(),network.getGraph().vertex(1))) ;
        assertEquals(places2.getFirst(),places2.getLast());

        for (int i = 0; i < places2.size() - 1; i++) {
            assertNotNull(network.getGraph().edge(places2.get(i),places2.get(i+1)));
        }
    }

    @Test
    void searchLargestCycle() {
        ArrayList<LinkedList<Place>> result;
        ArrayList<LinkedList<Place>> setOfCycles = new ArrayList<>();
        LinkedList<Place> cycle1 = AlgorithmsNetwork.searchCycle(network.getGraph(), AlgorithmsNetwork.nearestNeighbour(network.getGraph(),network.getGraph().vertex(1)));
        LinkedList<Place> cycle2 = AlgorithmsNetwork.searchCycle(network.getGraph(), AlgorithmsNetwork.nearestNeighbour(network.getGraph(),network.getGraph().vertex(11)));
        LinkedList<Place> cycle3 = AlgorithmsNetwork.searchCycle(network.getGraph(), AlgorithmsNetwork.nearestNeighbour(network.getGraph(),network.getGraph().vertex(13)));
        setOfCycles.add(cycle1);
        setOfCycles.add(cycle2);
        result = AlgorithmsNetwork.searchLargestCycle(setOfCycles);
        assertNotNull(result);
        assertEquals(1,result.size());
        assertEquals(cycle2,result.get(0));
        setOfCycles.add(cycle3);
        result = AlgorithmsNetwork.searchLargestCycle(setOfCycles);
        assertEquals(2,result.size());
    }

    @Test
    void calculateTotalWeight() {
        LinkedList<Place> cycle1 = AlgorithmsNetwork.searchCycle(network.getGraph(), AlgorithmsNetwork.nearestNeighbour(network.getGraph(),network.getGraph().vertex(1)));
        double result = AlgorithmsNetwork.calculateTotalWeight(network.getGraph(), cycle1);
        assertEquals(1510.0450708880592,result);
        LinkedList<Place> path = new LinkedList<>();
        path.add(network.getGraph().vertex(64));
        path.add(network.getGraph().vertex(71)); //410
        path.add(network.getGraph().vertex(72));
        path.add(network.getGraph().vertex(88)); //171
        double result2 = AlgorithmsNetwork.calculateTotalWeight(network.getGraph(), path);
        assertEquals(747.355980962686,result2);
    }
}