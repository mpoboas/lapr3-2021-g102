package lapr.project.controller;

import lapr.project.data.DatabaseConnection;
import lapr.project.utils.FreightNetwork;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BuildFreightNetworkControllerTest {
    DatabaseConnection databaseConnection = mock(DatabaseConnection.class);
    BuildFreightNetworkController buildFreightNetworkController;
    FreightNetwork freightNetwork = mock(FreightNetwork.class);

    @Test
    void getFreightStore() {
        this.buildFreightNetworkController = new BuildFreightNetworkController();
        assertNotNull(buildFreightNetworkController.getFreightStore());
    }

    /*@Test
    void buildFreightNetworkFromDatabase() {
        this.buildFreightNetworkController = new BuildFreightNetworkController();
        doNothing().when(this.freightNetwork).buildFreightNetwork(5);
        this.buildFreightNetworkController.buildFreightNetworkFromDatabase(this.databaseConnection, 5);
    }*/
}