package lapr.project.model;

import lapr.project.stores.PortStore;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ImportPortsTest {

    String file1 = "data/data-shipsports/data-shipsports/data-ships&ports/sports.csv";
    String file2 = "data/data-shipsports/data-shipsports/data-ships&ports/bports.csv";

    @Test
    void importPortsData() {
        PortStore store = new PortStore();
        ImportPorts imp = new ImportPorts(store);
        try {
            imp.importPortsData("File");
        } catch (IOException e) {
            assertEquals(FileNotFoundException.class, e.getClass());
        }
        try {
            imp.importPortsData(file1);
            assertNotEquals(0, imp.getListOfImportedPorts().size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void portAlreadyImported() {
        PortStore store = new PortStore();
        ImportPorts imp = new ImportPorts(store);
        try {
            imp.importPortsData(file1);
            assertTrue(imp.portAlreadyImported("20351"));
            assertFalse(imp.portAlreadyImported("11111"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    void getListOfImportedPorts() {
        PortStore store = new PortStore();
        ImportPorts imp = new ImportPorts(store);
        Port p1 = new Port(29002, "Liverpool", "Europe", "United Kingdom", -3.033333333, 53.46666667);
        Port p2 = new Port(14635, "Los Angeles", "America", "United States", -118.2666667, 33.71666667);
        Port p3 = new Port(25007, "New Jersey", "America", "United States", -74.16666667, 40.66666667);
        Port p4 = new Port(20301, "Rio Grande", "America", "Brazil", -52.06666667, -32.06666667);
        Port p5 = new Port(20351, "Salvador", "America", "Brazil", -38.51666667, -12.96666667);
        Port p6 = new Port(27248, "Santos", "America", "Brazil", -46.31666667, -23.93333333);
        Port p7 = new Port(22226, "Halifax", "America", "Canada", -63.56666667, 44.65);
        Port p8 = new Port(25350, "Vancouver", "America", "Canada", -123.1166667, 49.28333333);
        Port p9 = new Port(27792, "San Vicente", "America", "Chile", -73.15, -36.73333333);
        Port p10 = new Port(28082, "Valparaiso", "America", "Chile", -71.63333333, -33.01666667);
        Port p11 = new Port(28261, "Buenaventura", "America", "Colombia", -77.05, 3.916666667);
        Port p12 = new Port(28313, "Cartagena", "America", "Colombia", -75.53333333, 10.41666667);
        Port p13 = new Port(18012, "Brest", "Europe", "France", -4.5, 48.4);
        Port p14 = new Port(18326, "Dunkirk", "Europe", "France", 2.366666667, 51.05);
        Port p15 = new Port(18476, "Ponta Delgada", "Europe", "Portugal", -25.66666667, 37.73333333);
        Port p16 = new Port(23428, "Funchal", "Europe", "Portugal", -16.91666667, 32.65);
        Port p17 = new Port(13012, "Leixoes", "Europe", "Portugal", -8.7, 41.18333333);
        Port p18 = new Port(13390, "Setubal", "Europe", "Portugal", -8.916666667, 38.5);
        Port p19 = new Port(17386, "Barcelona", "Europe", "Spain", 2.166666667, 41.33333333);
        Port p20 = new Port(18937, "Valencia", "Europe", "Spain", -0.3, 39.45);
        Port p21 = new Port(30045, "Callao", "America", "Peru", -77.16666667, -12.05);
        Port p22 = new Port(10860, "Matarani", "America", "Peru", -72.1, -17);
        List<Port> expected = new ArrayList<>();
        expected.add(p1);expected.add(p2);expected.add(p3);expected.add(p4);expected.add(p5);
        expected.add(p6);expected.add(p7);expected.add(p8);expected.add(p9);expected.add(p10);
        expected.add(p11);expected.add(p12);expected.add(p13);expected.add(p14);expected.add(p15);
        expected.add(p16);expected.add(p17);expected.add(p18);expected.add(p19);expected.add(p20);
        expected.add(p21);expected.add(p22);
        try {
            imp.importPortsData(file1);
            assertEquals(expected,imp.getListOfImportedPorts());
            imp.importPortsData(file2);
            assertNotEquals(expected,imp.getListOfImportedPorts());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void addPortsTest(){
        PortStore store = new PortStore();
        ImportPorts imp = new ImportPorts(store);
        try {
            imp.importPortsData(file1);
            imp.addPorts();
            assertEquals(0,imp.getListOfImportedPorts().size());
            assertNotEquals(0,store.getPortTree().inOrder().size());
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(store,imp.getStore());

    }
}