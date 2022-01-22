package lapr.project.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ImportContainersTest {
    static ImportContainers importContainers = new ImportContainers();

    @BeforeAll
    static void init(){
        importContainers.importContainersData("data/data-shipsports/LAPR3_G102 - containers.csv");
    }

    @Test
    void importContainersData() {

        assertEquals(5200, importContainers.getListOfImportedContainers().get(0).getTare());
        assertEquals(4900, importContainers.getListOfImportedContainers().get(4).getTare());

    }



    @Test
    void containerAlreadyImported()  {

        assertTrue(importContainers.containerAlreadyImported(importContainers.getListOfImportedContainers().get(10).getId()));
        Container c = new Container( "ID", "Refrigerated","20R5",2000,1220,1200);
        assertFalse(importContainers.containerAlreadyImported(c.getId()));
    }

    @Test
    void getListOfImportedContainers() {

        List<Container> list = importContainers.getListOfImportedContainers();
        assertEquals("LPRU0566306", list.get(0).getId());
        assertEquals("LPRU9575857", list.get(2).getId());

    }
}