package lapr.project.controller;

import lapr.project.model.*;
import lapr.project.stores.PortStore;
import lapr.project.utils.Pair;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClossenessPlacesControllerTest {
    ClossenessPlacesController controller;

    @Test
    void testColssenessPlaces() throws IOException {
        ImportPorts port = new ImportPorts(new PortStore());
        Company comp = new Company("Company");
        comp.getFreightNetwork().buildFreightNetworkFromGivenData(ImportCountry.importCountries("data/Data-Sprint3-4/countries.csv"),ImportBorder.importBorders("data/Data-Sprint3-4/borders.csv"),port.getListOfImportedPorts(),ImportSeadist.importSeadistData("data/Data-Sprint3-4/seadists.csv"), 1);
        controller = new ClossenessPlacesController(comp ,null,2);
        port.importPortsData("data/data-shipsports/data-shipsports/data-ships&ports/bports.csv");
        controller.getClossenessPlaces().getNetwork().buildFreightNetworkFromGivenData(ImportCountry.importCountries("data/Data-Sprint3-4/countries.csv"),ImportBorder.importBorders("data/Data-Sprint3-4/borders.csv"),port.getListOfImportedPorts(),ImportSeadist.importSeadistData("data/Data-Sprint3-4/seadists.csv"),2);
        controller.setPort(controller.getClossenessPlaces().getNetwork().getGraph().vertex(1));
        List<Pair<Place,Double>> list =  controller.getClosestPlacesList();
        assertEquals("Mexico" ,list.get(0).getFirst().getCountry());
        assertEquals("Mexico" ,list.get(1).getFirst().getCountry());
        assertEquals("lapr.project.model.Port" ,list.get(1).getFirst().getClass().getName());

        assertEquals(2,list.size());

    }

    @Test
    void getClossenessPlacesList() throws IOException {
        ImportPorts port = new ImportPorts(new PortStore());
        Company comp = new Company("Company");
        comp.getFreightNetwork().buildFreightNetworkFromGivenData(ImportCountry.importCountries("data/Data-Sprint3-4/countries.csv"),ImportBorder.importBorders("data/Data-Sprint3-4/borders.csv"),port.getListOfImportedPorts(),ImportSeadist.importSeadistData("data/Data-Sprint3-4/seadists.csv"), 1);
        controller = new ClossenessPlacesController(comp ,null,2);
        port.importPortsData("data/data-shipsports/data-shipsports/data-ships&ports/bports.csv");
        controller.getClossenessPlaces().getNetwork().buildFreightNetworkFromGivenData(ImportCountry.importCountries("data/Data-Sprint3-4/countries.csv"),ImportBorder.importBorders("data/Data-Sprint3-4/borders.csv"),port.getListOfImportedPorts(),ImportSeadist.importSeadistData("data/Data-Sprint3-4/seadists.csv"),2);
        controller.setPort(controller.getClossenessPlaces().getNetwork().getGraph().vertex(1));
        controller.setnPlaces(50);
        List<Pair<Place,Double>> list =  controller.getClosestPlacesList();
        for (Pair<Place, Double> placeDoublePair : list) {
            assertNotNull(placeDoublePair.getSecond());
        }
        assertEquals("Mexico" ,list.get(0).getFirst().getCountry());
        assertEquals("Belize" ,list.get(2).getFirst().getCountry());
        assertEquals(7967.640878074627,list.get(20).getSecond().doubleValue());
        assertEquals(50,list.size());
        assertFalse(controller.getClosestPlacesList().isEmpty());

    }

}