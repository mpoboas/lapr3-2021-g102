package lapr.project.controller;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.NoRouteToHostException;
import java.sql.SQLException;
import java.sql.SQLRecoverableException;

import static org.junit.jupiter.api.Assertions.*;

class getNManifestsControllerTest {
    GetNManifestsController controller;
    @Test
    void getNManifests() throws SQLException, IOException {
        try {
            controller = new GetNManifestsController(3, 2020);
            System.out.println(controller.getNManifests());
        }catch(RuntimeException e){
            System.out.println("The server is not avalaible at the moment");
        }
    }

    @Test
    void getAvgContainerPerManifest() throws SQLException, IOException {
        try {
            controller = new GetNManifestsController(3, 2020);
            System.out.println(controller.getAvgContainerPerManifest());
        }catch(RuntimeException e){
            System.out.println("The server is not available at the moment");
        }
    }
}