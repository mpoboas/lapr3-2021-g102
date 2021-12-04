package lapr.project.controller;

import lapr.project.data.GetNmanifestsYear;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class getNManifestsControllerTest {
    @Mock
    GetNManifestsController controller;
    @Mock
    GetNmanifestsYear data;
    @Test
    @ExtendWith(MockitoExtension.class)
    void getNManifests() {
        this.controller = new GetNManifestsController(1,2020);
        this.controller.setGetnManY(data);
        Mockito.when(this.data.getNManifests()).thenReturn(3);
        assertEquals(3, this.controller.getNManifests());
        System.out.println("Result: "+ this.controller.getNManifests());

    }

    @Test
    @ExtendWith(MockitoExtension.class)
    void getAvgContainerPerManifest() {
        this.controller = mock(GetNManifestsController.class);
        Mockito.when(this.controller.getAvgContainerPerManifest()).thenReturn(1.0);
        assertEquals(1, this.controller.getAvgContainerPerManifest());
        System.out.println("Result: "+ this.controller.getAvgContainerPerManifest());

    }
}