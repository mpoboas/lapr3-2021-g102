package lapr.project.controller;

import lapr.project.data.GetNmanifestsYear;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class getNManifestsControllerTest {
    @Mock
    GetNManifestsController controller;
    @Mock
    GetNmanifestsYear data;
    @Test
    @ExtendWith(MockitoExtension.class)
    void getNManifests() throws SQLException, IOException {
        this.controller = new GetNManifestsController(1,2020);
        this.controller.setGetnManY(data);
        Mockito.when(this.data.getNManifests()).thenReturn(3);
        assertEquals(3, this.controller.getNManifests());
        controller.doDBQuery();
        System.out.println("Result: "+ this.controller.getNManifests());

    }

    @Test
    @ExtendWith(MockitoExtension.class)
    void getAvgContainerPerManifest() throws SQLException, IOException {
        this.controller = new GetNManifestsController(1,2020);
        this.controller.setGetnManY(data);
        Mockito.when(this.data.getAvgManifests()).thenReturn(1.0);
        assertEquals(1.0, this.controller.getAvgContainerPerManifest());
        controller.doDBQuery();
        System.out.println("Result: "+ this.controller.getAvgContainerPerManifest());

    }
}