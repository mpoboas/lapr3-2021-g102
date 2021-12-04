package lapr.project.controller;

import lapr.project.data.ConnectionFactory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

import static org.mockito.Mockito.mock;

class getNManifestsControllerTest {
    GetNManifestsController controller;

    @Test
    void getNManifests() throws SQLException, IOException {
        ConnectionFactory cf = mock(ConnectionFactory.class);

        try {
            controller = new GetNManifestsController(cf,3, 2020);
            System.out.println(controller.getNManifests());
        }catch(RuntimeException e){
            System.out.println("The server is not avalaible at the moment");
        }
    }
    @Test
    void getAvgContainerPerManifest() throws SQLException, IOException {
        ConnectionFactory cf = mock(ConnectionFactory.class);

        try {
            controller = new GetNManifestsController(cf,3, 2020);
            System.out.println(controller.getAvgContainerPerManifest());
        }catch(RuntimeException e){
            System.out.println("The server is not available at the moment");
        }
    }
}