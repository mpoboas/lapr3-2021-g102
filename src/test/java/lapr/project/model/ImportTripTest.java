package lapr.project.model;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ImportTripTest {
    ImportTrip imports = new ImportTrip();

    @Test
    void importContainersData() {
        imports.importTripsData("data/data-shipsports/LAPR3_G102 - trip.csv");
        assertEquals(1, imports.getListOfImportedTrips().get(0).getId());
        assertEquals(2, imports.getListOfImportedTrips().size());
    }

    @Test
    void containerAlreadyImported() {
        imports.importTripsData("data/data-shipsports/LAPR3_G102 - trip.csv");
        assertEquals(1, imports.getListOfImportedTrips().get(0).getId());
        assertEquals(2, imports.getListOfImportedTrips().size());
        assertTrue(imports.tripAlreadyImported("1"));
        assertFalse(imports.tripAlreadyImported("3"));

    }

    @Test
    void getListOfImportedContainers() {
        imports.importTripsData("data/data-shipsports/LAPR3_G102 - trip.csv");
        assertEquals(1, imports.getListOfImportedTrips().get(0).getId());
        assertEquals(2, imports.getListOfImportedTrips().size());
        assertEquals(1, imports.getListOfImportedTrips().get(0).getId());
        assertEquals(new Date("01/12/2021 9:00:00"), imports.getListOfImportedTrips().get(0).getEndDate());
    }
}