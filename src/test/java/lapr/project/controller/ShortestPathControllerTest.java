package lapr.project.controller;

import lapr.project.model.*;
import lapr.project.stores.PortStore;
import lapr.project.utils.Pair;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShortestPathControllerTest {
    static Company comp = new Company("Test Company");
    static ImportPorts port = new ImportPorts(new PortStore());

    @BeforeAll
    static void init() throws IOException {
        port.importPortsData("data/data-shipsports/data-shipsports/data-ships&ports/bports.csv");
        comp.getFreightNetwork().buildFreightNetworkFromGivenData(ImportCountry.importCountries("data/Data-Sprint3-4/countries.csv"), ImportBorder.importBorders("data/Data-Sprint3-4/borders.csv"),port.getListOfImportedPorts(), ImportSeadist.importSeadistData("data/Data-Sprint3-4/seadists.csv"), 10);

    }

    @Test
    void getFull() {
        ShortestPathController controller;
        List<Place> places = new ArrayList<>();
        places.add(comp.getFreightNetwork().getGraph().vertex(135));
        places.add(comp.getFreightNetwork().getGraph().vertex(1));
        controller = new ShortestPathController(places,comp,comp.getFreightNetwork().getGraph().vertex(120),comp.getFreightNetwork().getGraph().vertex(100));
        Pair<LinkedList<Place>,Double> fullPathInfo = controller.getFull();
        assertEquals(27,fullPathInfo.getFirst().size());
        assertEquals(35371.82,fullPathInfo.getSecond(),0.1);

    }

    @Test
    void getDirect() {
        ShortestPathController controller;
        List<Place> places = new ArrayList<>();
        places.add(comp.getFreightNetwork().getGraph().vertex(135));
        places.add(comp.getFreightNetwork().getGraph().vertex(1));
        controller = new ShortestPathController(places,comp,comp.getFreightNetwork().getGraph().vertex(120),comp.getFreightNetwork().getGraph().vertex(100));
        Pair<LinkedList<Place>,Double> directPathInfo = controller.getDirect();
        assertEquals(2,directPathInfo.getFirst().size());
        assertEquals(2743.0,directPathInfo.getSecond(),0.1);
    }

    @Test
    void getSea() {
        ShortestPathController controller;
        List<Place> places = new ArrayList<>();
        places.add(comp.getFreightNetwork().getGraph().vertex(135));
        places.add(comp.getFreightNetwork().getGraph().vertex(1));
        controller = new ShortestPathController(places,comp,comp.getFreightNetwork().getGraph().vertex(120),comp.getFreightNetwork().getGraph().vertex(100));
        Pair<LinkedList<Place>,Double> seaPathInfo = controller.getSea();
        assertEquals(2,seaPathInfo.getFirst().size());
        assertEquals(2743.0,seaPathInfo.getSecond(),0.1);
    }

    @Test
    void getLand() {
        ShortestPathController controller;
        List<Place> places = new ArrayList<>();
        places.add(comp.getFreightNetwork().getGraph().vertex(135));
        places.add(comp.getFreightNetwork().getGraph().vertex(1));
        controller = new ShortestPathController(places,comp,comp.getFreightNetwork().getGraph().vertex(120),comp.getFreightNetwork().getGraph().vertex(100));
        Pair<LinkedList<Place>,Double> landPathInfo = controller.getLand();
        assertEquals(7,landPathInfo.getFirst().size());
        assertEquals(5066.74,landPathInfo.getSecond(),0.1);
    }
}