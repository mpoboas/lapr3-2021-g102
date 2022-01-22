package lapr.project.controller;

import lapr.project.model.*;
import lapr.project.stores.PortStore;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

public class ColorMapControllerTest {
    ColorMapController ctrl = new ColorMapController();

    @Test
    void colorMap() throws IOException {
        ImportPorts port = new ImportPorts(new PortStore());
        Company comp = new Company("LAPR3");
        comp.getFreightNetwork().buildFreightNetworkFromGivenData(ImportCountry.importCountries("data/Data-Sprint3-4/countries.csv"),ImportBorder.importBorders("data/Data-Sprint3-4/borders.csv"),port.getListOfImportedPorts(),ImportSeadist.importSeadistData("data/Data-Sprint3-4/seadists.csv"), 1);

        ArrayList<ArrayList<Integer>> adjList;
        adjList = ctrl.graphToAdjList(comp.getFreightNetwork().getGraph());
        int numVertices = comp.getFreightNetwork().getGraph().numVertices();
        int[] coloredMap = ctrl.colorMap(numVertices, adjList);
        ctrl.printColoredMap(numVertices, coloredMap);
    }
}
