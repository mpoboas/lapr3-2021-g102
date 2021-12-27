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
    void buildNetwork() throws IOException {
        controller = new ClossenessPlacesController(null,2);
        ImportPorts port = new ImportPorts(new PortStore());
        port.importPortsData("data/data-shipsports/LAPR3_G102 - bports (alpha2Code).csv");
        controller.getClossenessPlaces().getNetwork().buildFreightNetworkFromGivenData(ImportCountry.importCountries("data/Data-Sprint3-4/countries.csv"),ImportBorder.importBorders("data/Data-Sprint3-4/borders.csv"),port.getListOfImportedPorts(),ImportSeadist.importSeadistData("data/Data-Sprint3-4/seadists.csv"),2);
        controller.setPort(controller.getClossenessPlaces().getNetwork().getGraph().vertex(1));
        List<Pair<Place,Double>> list =  controller.getClossenessPlacesList();
        assertEquals("Guatemala" ,list.get(0).getFirst().getCountry());
        assertEquals("Belize" ,list.get(1).getFirst().getCountry());
        assertEquals(2,list.size());

    }

    @Test
    void getClossenessPlacesList() {
    }
}