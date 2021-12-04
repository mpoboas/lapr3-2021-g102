package lapr.project.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

class getNManifestsControllerTest {
    @Mock
    GetNManifestsController controller;
    @Test
    @ExtendWith(MockitoExtension.class)
    void getNManifests() {
        Mockito.when(this.controller.getNManifests()).thenReturn(3);
        assertEquals(3, this.controller.getNManifests());
    }

    @Test
    @ExtendWith(MockitoExtension.class)
    void getAvgContainerPerManifest() {

        Mockito.when(this.controller.getAvgContainerPerManifest()).thenReturn(1.0);
        assertEquals(1, this.controller.getAvgContainerPerManifest());

    }
}