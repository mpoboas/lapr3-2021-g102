package lapr.project.model;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ImportCargoManifestTest {
    ImportCargoManifest imports;

    @Test
    void importCargoManifestData() throws IOException {
        imports = new ImportCargoManifest();
        imports.importCargoManifestData("data/data-shipsports/LAPR3_G102 - cargoManifests.csv");
        assertEquals(1, imports.getListOfImportedCargoManifests().get(0).getId() );
        assertEquals("LPRU1628240", imports.getListOfImportedCargoManifests().get(0).getContainerList().get(0).getFirst().getId() );
        assertEquals("LPRU5977032", imports.getListOfImportedCargoManifests().get(0).getContainerList().get(1).getFirst().getId() );
        assertTrue(imports.getListOfImportedCargoManifests().get(0).getContainerList().get(0).getSecond().equals(new CargoManifest.ContainerPosition(11,1,1)) );
    }

    @Test
    void cargoManifestAlreadyImported() throws IOException {
        imports = new ImportCargoManifest();
        imports.importCargoManifestData("data/data-shipsports/LAPR3_G102 - cargoManifests.csv");
        assertTrue(imports.cargoManifestAlreadyImported("6"));
        assertFalse(imports.cargoManifestAlreadyImported("100000"));
    }

    @Test
    void getCargoManifestByID() throws IOException {
        imports = new ImportCargoManifest();
        imports.importCargoManifestData("data/data-shipsports/LAPR3_G102 - cargoManifests.csv");


    }


    @Test
    void getListOfImportedCargoManifests() throws IOException {
        imports = new ImportCargoManifest();
        imports.importCargoManifestData("data/data-shipsports/LAPR3_G102 - cargoManifests.csv");
        List<CargoManifest> list = imports.getListOfImportedCargoManifests();
        assertEquals(18, list.size());
    }
}