package lapr.project.controller;

import lapr.project.data.ContainerStore;
import lapr.project.data.DatabaseConnection;
import lapr.project.model.Company;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetClientContainerRouteControllerTest {
    GetClientContainerRouteController getClientContainerRouteController = new GetClientContainerRouteController();
    DatabaseConnection databaseConnection = mock(DatabaseConnection.class);
    ContainerStore containerStore = mock(ContainerStore.class);
    Company company = App.getInstance().getCompany();

    @Test
    void getClientContainerRoute() {
        try {
            this.company.setContainerStore(this.containerStore);
            when(this.containerStore.getClientContainerRoute(this.databaseConnection, 5, "pass")).thenReturn("Pass");

            assertEquals("Pass", this.getClientContainerRouteController.getClientContainerRoute(this.databaseConnection, 5, "pass"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}