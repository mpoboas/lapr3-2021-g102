package lapr.project.utils;

import lapr.project.model.Port;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KDTreeTest {

    @Test
    void getRoot() {
        KDTree<Port> tree = new KDTree<>();
        assertNull(tree.getRoot());
        assertTrue(tree.isEmpty());
        Port p1 = new Port(1, "name1", "continent1", "country1", 30, 40);
        Port p2 = new Port(2, "name2", "continent1", "country2", 5, 25);
        Port p3 = new Port(3, "name3", "continent2", "country3", 10, 12);
        Port p4 = new Port(4, "name4", "continent2", "country4", 70, 70);
        Port p5 = new Port(5, "name5", "continent4", "country5", 50, 30);
        Port p6 = new Port(6, "name6", "continent5", "country6", 35, 45);
        List<Port> portsList = new ArrayList<>();
        portsList.add(p1);
        portsList.add(p2);
        portsList.add(p3);
        portsList.add(p4);
        portsList.add(p5);
        portsList.add(p6);
        assertEquals(0, tree.size());
        for (Port p : portsList) {
            tree.insert(p, p.getLatitude(), p.getLongitude());
        }
        assertEquals(6, tree.size());
        assertFalse(tree.isEmpty());
        assertEquals(p1, tree.getRoot().getElement());
    }

    @Test
    void insert() {
        KDTree<Port> tree = new KDTree<>();
        assertNull(tree.getRoot());
        Port p1 = new Port(1, "name1", "continent1", "country1", 30, 40);
        Port p2 = new Port(2, "name2", "continent1", "country2", 5, 25);
        Port p3 = new Port(3, "name3", "continent2", "country3", 10, 12);
        Port p4 = new Port(4, "name4", "continent2", "country4", 70, 70);
        Port p5 = new Port(5, "name5", "continent4", "country5", 50, 30);
        Port p6 = new Port(6, "name6", "continent5", "country6", 35, 45);
        List<Port> portsList = new ArrayList<>();
        portsList.add(p1);
        portsList.add(p2);
        portsList.add(p3);
        portsList.add(p4);
        portsList.add(p5);
        portsList.add(p6);
        assertEquals(0, tree.inOrder().size());
        assertEquals(-1, tree.height());
        for (Port p : portsList) {
            tree.insert(p, p.getLatitude(), p.getLongitude());
        }
        tree.insert(p1, p1.getLatitude(), p1.getLongitude());
        System.out.println(tree);
        assertEquals(6, tree.inOrder().size());

        assertEquals(3, tree.height());
        List<Port> expectedOrder = new ArrayList<>();
        expectedOrder.add(p3);
        expectedOrder.add(p2);
        expectedOrder.add(p1);
        expectedOrder.add(p6);
        expectedOrder.add(p5);
        expectedOrder.add(p4);


        assertEquals(expectedOrder, tree.inOrder(), "inOrder should be " + expectedOrder);
        System.out.println("<tree>");
        System.out.println(tree);
        System.out.println("height1=" + tree.height());
        System.out.println("------------------------------------");

    }
/*
    @Test
    void findNearestNeighbour() {
        KDTree<Port> tree = new KDTree<>();
        Port p1 = new Port(1, "name1", "continent1", "country1", 3, 9);
        Port p2 = new Port(2, "name2", "continent1", "country2", 1, 7);
        Port p3 = new Port(3, "name3", "continent2", "country3", 7, 9);
        Port p4 = new Port(4, "name4", "continent2", "country4", 2, 1);
        Port p5 = new Port(5, "name5", "continent4", "country5", 9, 6);
        Port p6 = new Port(6, "name6", "continent5", "country6", 5, 9);
        Port p7 = new Port(7, "name7", "continent5", "country7", 1, 4);
        Port p8 = new Port(8, "name8", "continent3", "country8", 7, 3);
        Port p9 = new Port(9, "name9", "continent3", "country8", 11, 6);
        Port p10 = new Port(10, "name10", "continent3", "country12", 6, 1);
        Port p11 = new Port(11, "name11", "continent3", "country10", 8, 2);
        List<Port> portsList = new ArrayList<>();
        portsList.add(p1);
        portsList.add(p2);
        portsList.add(p3);
        portsList.add(p4);
        portsList.add(p5);
        portsList.add(p6);
        portsList.add(p7);
        portsList.add(p8);
        portsList.add(p9);
        portsList.add(p10);
        portsList.add(p11);
        assertNull(tree.findNearestNeighbour(1,2));
        for (Port p : portsList) {
            tree.insert(p, p.getLatitude(), p.getLongitude());
        }
        tree.findNearestNeighbour(4,5);
        //assertEquals(p7, tree.findNearestNeighbour(4,5));
        System.out.println("<tree>");
        System.out.println(tree);
        System.out.println("height1=" + tree.height());
        System.out.println("------------------------------------");
    }*/


}