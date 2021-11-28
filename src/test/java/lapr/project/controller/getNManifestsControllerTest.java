package lapr.project.controller;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class getNManifestsControllerTest {
    getNManifestsController controller;

    @Test
    void getNManifests() throws SQLException, IOException {
        controller = new getNManifestsController(3, 2020);
        System.out.println(controller.getNManifests());
    }

    @Test
    void getAvgContainerPerManifest() throws SQLException, IOException {
        controller = new getNManifestsController(3, 2020);
        System.out.println(controller.getAvgContainerPerManifest());
    }
}