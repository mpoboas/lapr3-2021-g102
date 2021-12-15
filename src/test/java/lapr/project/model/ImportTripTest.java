package lapr.project.model;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ImportTripTest {
    ImportTrip imports = new ImportTrip();

    @Test
    void importContainersData() {
        imports.importContainersData("data/data-shipsports/LAPR3_G102 - trip.csv");
        assertEquals(1, imports.getListOfImportedContainers().get(0).getId());
        assertEquals(1, imports.getListOfImportedContainers().size());
    }

    @Test
    void containerAlreadyImported() {
        imports.importContainersData("data/data-shipsports/LAPR3_G102 - trip.csv");
        assertEquals(1, imports.getListOfImportedContainers().get(0).getId());
        assertEquals(1, imports.getListOfImportedContainers().size());
    }

    @Test
    void getListOfImportedContainers() {
        imports.importContainersData("data/data-shipsports/LAPR3_G102 - trip.csv");
        assertEquals(1, imports.getListOfImportedContainers().get(0).getId());
        assertEquals(1, imports.getListOfImportedContainers().size());
        imports.importContainersData("data/data-shipsports/LAPR3_G102 - trip.csv");
        assertEquals(1, imports.getListOfImportedContainers().get(0).getId());
        assertEquals(new Date("01/12/2021 9:00:00"), imports.getListOfImportedContainers().get(0).getEndDate());
    }
}