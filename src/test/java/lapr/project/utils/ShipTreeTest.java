package lapr.project.utils;

import lapr.project.model.Import;
import lapr.project.model.Ship;
import lapr.project.stores.ShipStore;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShipTreeTest {

    @Test
    void insert() {
        Ship ship1 = new Ship(112, "112", "IMOtest9", "Ship55", 12, 1200, 600, 21);
        Ship ship2 = new Ship(113, "122", "IMOtest1", "Ship89", 12, 1200, 600, 21);
        Ship ship3 = new Ship(114, "132", "IMOtest4", "Ship01", 12, 1200, 600, 21);
        Ship ship4 = new Ship(110, "142", "IMOtest3", "Ship63", 12, 1200, 600, 21);
        Ship ship5 = new Ship(120, "152", "IMOtest8", "Ship47", 12, 1200, 600, 21);
        Ship ship6 = new Ship(118, "162", "IMOtest5", "Ship99", 12, 1200, 600, 21);
        List<Ship> arr = new ArrayList<>();
        arr.add(ship1);
        arr.add(ship2);
        arr.add(ship3);
        arr.add(ship4);
        arr.add(ship5);
        arr.add(ship6);
        //test Simple right rotation
        //Testing Ship tree by IMO
        ShipTree instance = new ShipTree("IMO");

        List<Ship> order1 = new ArrayList<>();
        order1.add(ship2);
        order1.add(ship4);
        order1.add(ship3);
        order1.add(ship6);
        order1.add(ship5);
        order1.add(ship1);
        for (Ship s : arr) {
            instance.insert(s);
        }
        instance.insert(ship1);

        assertEquals(order1, instance.inOrder(), "inOrder should be " + order1.toString());
        //System.out.println("<instance 1>");
        //System.out.println(instance);
        //System.out.println("height1=" + instance.height());
        //System.out.println("------------------------------------");

        //Testing Ship tree by CallSign
        ShipTree instance1 = new ShipTree("Call Sign");

        List<Ship> order2 = new ArrayList<>();
        order2.add(ship3);
        order2.add(ship5);
        order2.add(ship1);
        order2.add(ship4);
        order2.add(ship2);
        order2.add(ship6);
        for (Ship s : arr) {
            instance1.insert(s);
        }
        assertEquals(order2, instance1.inOrder(), "inOrder should be " + order2.toString());
        //System.out.println("<instance 1.2>");
        //System.out.println(instance1);
        //System.out.println("height1.2=" + instance1.height());
        //System.out.println("------------------------------------");

        //test Simple left rotation
        ShipTree instance2 = new ShipTree("IMO");
        //AVL<Integer> instance2 = new AVL();
        ship1 = new Ship(112, "112", "IMOtest08", "Ship55", 12, 1200, 600, 21);
        ship2 = new Ship(113, "122", "IMOtest04", "Ship89", 12, 1200, 600, 21);
        ship3 = new Ship(114, "132", "IMOtest10", "Ship01", 12, 1200, 600, 21);
        ship4 = new Ship(110, "142", "IMOtest09", "Ship63", 12, 1200, 600, 21);
        ship5 = new Ship(120, "152", "IMOtest15", "Ship47", 12, 1200, 600, 21);
        ship6 = new Ship(118, "162", "IMOtest12", "Ship99", 12, 1200, 600, 21);
        arr = new ArrayList<>();
        arr.add(ship1);
        arr.add(ship2);
        arr.add(ship3);
        arr.add(ship4);
        arr.add(ship5);
        arr.add(ship6);
        order1 = new ArrayList<>();
        order1.add(ship2);
        order1.add(ship1);
        order1.add(ship4);
        order1.add(ship3);
        order1.add(ship6);
        order1.add(ship5);

        for (Ship s : arr) {
            instance2.insert(s);
        }



        assertEquals(order1, instance2.inOrder(), "inOrder should be " + order1.toString());
        assertEquals(instance2.height(), 2);


        //test double rotation
        //AVL<Integer> instance3 = new AVL();
        ShipTree instance3 = new ShipTree("IMO");
        int arr3[] = {8, 4, 10, 2, 6, 5};
        ship1 = new Ship(112, "112", "IMOtest08", "Ship55", 12, 1200, 600, 21);
        ship2 = new Ship(113, "122", "IMOtest04", "Ship89", 12, 1200, 600, 21);
        ship3 = new Ship(114, "132", "IMOtest10", "Ship01", 12, 1200, 600, 21);
        ship4 = new Ship(110, "142", "IMOtest02", "Ship63", 12, 1200, 600, 21);
        ship5 = new Ship(120, "152", "IMOtest06", "Ship47", 12, 1200, 600, 21);
        ship6 = new Ship(118, "162", "IMOtest05", "Ship99", 12, 1200, 600, 21);
        arr = new ArrayList<>();
        arr.add(ship1);
        arr.add(ship2);
        arr.add(ship3);
        arr.add(ship4);
        arr.add(ship5);
        arr.add(ship6);

        order1 = new ArrayList<>();
        order1.add(ship4);
        order1.add(ship2);
        order1.add(ship6);
        order1.add(ship5);
        order1.add(ship1);
        order1.add(ship3);

        for (Ship s : arr) {
            instance3.insert(s);
        }

        //System.out.println("<instance 3>");
        //System.out.println(instance3);
        //System.out.println("height3=" + instance3.height());
        assertEquals(order1, instance3.inOrder(), "inOrder should be " + order1.toString());
        assertEquals(instance3.height(), 2);
        //System.out.println("------------------------------------");
    }

    @Test
    void getShip() {
        Ship ship1 = new Ship(112, "112", "IMOtest9", "Ship55", 12, 1200, 600, 21);
        Ship ship2 = new Ship(113, "122", "IMOtest1", "Ship89", 12, 1200, 600, 21);
        Ship ship3 = new Ship(114, "132", "IMOtest4", "Ship01", 12, 1200, 600, 21);
        Ship ship4 = new Ship(110, "142", "IMOtest3", "Ship63", 12, 1200, 600, 21);
        Ship ship5 = new Ship(120, "152", "IMOtest8", "Ship47", 12, 1200, 600, 21);
        Ship ship6 = new Ship(118, "162", "IMOtest5", "Ship99", 12, 1200, 600, 21);
        List<Ship> arr = new ArrayList<>();
        arr.add(ship1);
        arr.add(ship2);
        arr.add(ship3);
        arr.add(ship4);
        arr.add(ship5);
        arr.add(ship6);
        ShipTree instance1 = new ShipTree("MMSI");
        for (Ship s : arr) {
            instance1.insert(s);
        }
        assertEquals(ship1, instance1.getShip(ship1));
        assertNull(instance1.getShip(new Ship(100, "1000", "IMOtest666", "Ship58", 12, 1200, 600, 21)));

    }

    @Test
    void remove() {
        Ship ship1 = new Ship(112, "112", "IMOtest9", "Ship55", 12, 1200, 600, 21);
        Ship ship2 = new Ship(113, "122", "IMOtest1", "Ship89", 12, 1200, 600, 21);
        Ship ship3 = new Ship(114, "132", "IMOtest4", "Ship01", 12, 1200, 600, 21);
        Ship ship4 = new Ship(110, "142", "IMOtest3", "Ship63", 12, 1200, 600, 21);
        Ship ship5 = new Ship(120, "152", "IMOtest8", "Ship47", 12, 1200, 600, 21);
        Ship ship6 = new Ship(118, "162", "IMOtest5", "Ship99", 12, 1200, 600, 21);
        List<Ship> arr = new ArrayList<>();
        arr.add(ship1);
        arr.add(ship2);
        arr.add(ship3);
        arr.add(ship4);
        arr.add(ship5);
        arr.add(ship6);
        List<Ship> order1 = new ArrayList<>();
        order1.add(ship2);
        order1.add(ship4);
        order1.add(ship3);
        order1.add(ship6);
        order1.add(ship5);
        order1.add(ship1);


        ShipTree instance;

        instance = new ShipTree("IMO");
        instance.remove(ship1);
        for (Ship s : arr) {
            instance.insert(s);
        }


        //no rotations needed
        instance.remove(ship4);
        order1.remove(ship4);
        assertEquals(order1, instance.inOrder(), "inOrder should be " + order1.toString());
        assertEquals(instance.height(), 2);

        //test Simple left rotation
        instance.remove(ship2);
        order1.remove(ship2);
        assertEquals(order1, instance.inOrder(), "inOrder should be " + order1.toString());
        assertEquals(instance.height(), 2);

        instance.remove(ship1);
        order1.remove(ship1);
        assertEquals(order1, instance.inOrder(), "inOrder should be " + order1.toString());
        assertEquals(instance.height(), 1);

        instance.remove(ship6);
        order1.remove(ship6);
        assertEquals(order1, instance.inOrder(), "inOrder should be " + order1.toString());
        assertEquals(1, instance.height());

        instance.remove(ship3);
        order1.remove(ship3);
        assertEquals(order1, instance.inOrder(), "inOrder should be " + order1.toString());
        assertEquals(0, instance.height());

        instance.remove(ship5);
        order1.remove(ship5);
        assertEquals(order1, instance.inOrder(), "inOrder should be " + order1.toString());
        assertEquals(-1, instance.height());

    }

    @Test
    void testEquals() {
        Ship ship1 = new Ship(112, "112", "IMOtest9", "Ship55", 12, 1200, 600, 21);
        Ship ship2 = new Ship(113, "122", "IMOtest1", "Ship89", 12, 1200, 600, 21);
        ShipTree instance = new ShipTree("IMO");
        instance.insert(ship1);
        instance.insert(ship2);
        ShipTree instance2 = new ShipTree("IMO");
        instance2.insert(ship1);
        instance2.insert(ship2);
        assertTrue(new ShipTree("MMSI").equals(new ShipTree("MMSI")), "equals! ");
        assertTrue(instance.equals(instance), "equals! ");
        assertTrue(instance.equals(instance2), "equals! ");
        instance2.remove(ship1);
        assertFalse(instance.equals(instance2), "equals! ");
        assertFalse(instance.equals(null), "equals! ");
        assertFalse(instance.equals("TREE"), "equals! ");
        assertFalse(instance.equals(new ShipTree("MMSI")), "equals! ");
        assertFalse(new ShipTree("MMSI").equals(instance), "equals! ");
    }

    @Test
    void testSearch() {
        Ship ship1 = new Ship(112, "112", "IMOtest9", "Ship55", 12, 1200, 600, 21);
        Ship ship2 = new Ship(113, "122", "IMOtest1", "Ship89", 12, 1200, 600, 21);
        Ship ship3 = new Ship(114, "132", "IMOtest4", "Ship01", 12, 1200, 600, 21);
        Ship ship4 = new Ship(110, "142", "IMOtest3", "Ship63", 12, 1200, 600, 21);
        Ship ship5 = new Ship(120, "152", "IMOtest8", "Ship47", 12, 1200, 600, 21);
        Ship ship6 = new Ship(118, "162", "IMOtest5", "Ship99", 12, 1200, 600, 21);
        List<Ship> arr = new ArrayList<>();
        arr.add(ship1);
        arr.add(ship2);
        arr.add(ship3);
        arr.add(ship4);
        arr.add(ship5);
        arr.add(ship6);
        ShipTree instance1 = new ShipTree("MMSI");
        ShipTree instance2 = new ShipTree("IMO");
        ShipTree instance3 = new ShipTree("Call Sign");
        for (Ship s : arr) {
            instance1.insert(s);
            instance2.insert(s);
            instance3.insert(s);
        }

        assertEquals(ship2, instance1.searchShipByMMSI(113));
        assertEquals(ship3, instance2.searchShipByIMO("IMOtest4"));
        assertEquals(ship6, instance3.searchShipByCallSign("Ship99"));
        assertNull(instance1.searchShipByMMSI(100));
        assertNull(instance2.searchShipByIMO("IMOtest10"));
        assertNull(instance3.searchShipByCallSign("Ship11"));
    }

    @Test
    void getCloseCoordsShips() {
        Import imp = new Import(new ShipStore());
        String file = "data/data-shipsports/data-shipsports/data-ships&ports/sships.csv";

        try {
            imp.importShipsData(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        imp.addShips();

        List<Pair<Ship, Ship>> pairOfShipsList = imp.getShipStore().getShipBSTMMSI().getCloseCoordsShips();

        for (int i = 0; i < pairOfShipsList.size(); i++) {
            for (int j = i + 1; j < pairOfShipsList.size(); j++) {
                if (pairOfShipsList.get(i).getFirst() == pairOfShipsList.get(j).getSecond())
                    pairOfShipsList.remove(pairOfShipsList.get(j));
            }
        }

        assertNotNull(pairOfShipsList);
        assertEquals(0, pairOfShipsList.size());
    }
}