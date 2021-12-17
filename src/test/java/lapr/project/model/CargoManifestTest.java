package lapr.project.model;

import lapr.project.utils.Pair;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CargoManifestTest {
    ImportContainers importContainers;


    @Test
    void getId() {
        CargoManifest cm = new CargoManifest(12, new Date("12/12/2021"), "load", null);
        assertEquals(12, cm.getId());
        assertNotEquals(13, cm.getId());

    }

    @Test
    void getDate() {
        CargoManifest cm = new CargoManifest(12, new Date("12/12/2021"), "load", null);
        assertEquals(new Date("12/12/2021"), cm.getDate());
        assertNotEquals(new Date("12/2/2021"), cm.getDate());
    }

    @Test
    void getType() {
        CargoManifest cm = new CargoManifest(12, new Date("12/12/2021"), "load", null);
        assertTrue(cm.getType().equalsIgnoreCase("load"));
        assertFalse(cm.getType().equalsIgnoreCase("unload"));

    }




    @Test
    void addContainers() {
        CargoManifest cm = new CargoManifest(12, new Date("12/12/2021"), "load", new ArrayList<>());
        importContainers = new ImportContainers();
        importContainers.importContainersData("data/LAPR3_G102 - containers.csv");
        List<Pair<Container, CargoManifest.ContainerPosition>> l = new ArrayList<>();
        for(int i = 0 ; i< importContainers.getListOfImportedContainers().size(); i++){
            l.add(new Pair<>(importContainers.getListOfImportedContainers().get(i), new CargoManifest.ContainerPosition(i, i+1,i*2)) );
        }
        cm.addContainers(l);
        assertEquals(importContainers.getListOfImportedContainers().get(0), cm.getContainerList().get(0).getFirst());
        assertEquals(importContainers.getListOfImportedContainers().get(importContainers.getListOfImportedContainers().size() -1), cm.getContainerList().get(cm.getContainerList().size() -1).getFirst());
        assertTrue( cm.getContainerList().get(0).getSecond().equals(new CargoManifest.ContainerPosition(0,1,0)));
        assertFalse( cm.getContainerList().get(0).getSecond().equals(new CargoManifest.ContainerPosition(1,1,0)));
        assertFalse( cm.getContainerList().get(0).getSecond().equals(new CargoManifest.ContainerPosition(0,0,0)));
        assertFalse( cm.getContainerList().get(0).getSecond().equals(new CargoManifest.ContainerPosition(0,1,1)));
        assertFalse( cm.getContainerList().get(0).getSecond().equals(cm));
        Container c = new Container( "ID", "Refrigerated","20R5",2000,1220,1200);
        Pair<Container, CargoManifest.ContainerPosition> p = new Pair<Container, CargoManifest.ContainerPosition>(c, new CargoManifest.ContainerPosition(0,0,0));
        cm.addContainer(p);
        assertEquals(c, cm.getContainerList().get(cm.getContainerList().size() -1).getFirst());
        assertFalse(cm.equals(cm.getContainerList().get(0).getSecond()));
        assertTrue(cm.equals(new CargoManifest(12, null, null , new ArrayList<>())));
        assertFalse(cm.equals(new CargoManifest(2, null, null , new ArrayList<>())));


    }

    @Test
    void testToString() {
        CargoManifest cm = new CargoManifest(12, new Date("12/12/2021"), "load", new ArrayList<>());
        importContainers = new ImportContainers();
        importContainers.importContainersData("data/LAPR3_G102 - containers.csv");
        List<Pair<Container, CargoManifest.ContainerPosition>> l = new ArrayList<>();
        for(int i = 0 ; i< importContainers.getListOfImportedContainers().size(); i++){
            l.add(new Pair<>(importContainers.getListOfImportedContainers().get(i), new CargoManifest.ContainerPosition(i, i+1,i*2)) );
        }
        cm.addContainers(l);
        System.out.println(cm);

    }
}