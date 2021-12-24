package lapr.project.controller;

import lapr.project.data.DatabaseConnection;
import lapr.project.stores.FreightNetwork;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BuildFreightNetworkControllerTest {
    DatabaseConnection databaseConnection = mock(DatabaseConnection.class);
    BuildFreightNetworkController buildFreightNetworkController;
    FreightNetwork freightNetwork = mock(FreightNetwork.class);

    @Test
    void getFreightNetworkGraph() {
        this.buildFreightNetworkController = new BuildFreightNetworkController();
        assertNotNull(buildFreightNetworkController.getFreightNetworkGraph());
    }

    /*@Test
    void buildFreightNetworkFromDatabase() {
        this.buildFreightNetworkController = new BuildFreightNetworkController();
        doNothing().when(this.freightNetwork).buildFreightNetwork(5);
        this.buildFreightNetworkController.buildFreightNetworkFromDatabase(this.databaseConnection, 5);
    }*/
}