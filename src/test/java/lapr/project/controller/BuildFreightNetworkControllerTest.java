package lapr.project.controller;

import lapr.project.data.DatabaseConnection;
import lapr.project.model.Company;
import lapr.project.stores.FreightNetwork;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

class BuildFreightNetworkControllerTest {
    DatabaseConnection databaseConnection = mock(DatabaseConnection.class);
    Company company = App.getInstance().getCompany();
    FreightNetwork freightNetwork = mock(FreightNetwork.class);
    BuildFreightNetworkController buildFreightNetworkController;

    @Test
    void getFreightNetworkGraph() {
        this.buildFreightNetworkController = new BuildFreightNetworkController();
        assertNotNull(this.buildFreightNetworkController.getFreightNetworkGraph());
    }

    @Test
    void buildFreightNetworkFromDatabase() {
        this.buildFreightNetworkController = new BuildFreightNetworkController();
        this.company.setFreightNetwork(this.freightNetwork);
        doNothing().when(this.freightNetwork).buildFreightNetworkFromDatabase(this.databaseConnection, 5);
        this.buildFreightNetworkController.buildFreightNetworkFromDatabase(this.databaseConnection, 5);
    }
}