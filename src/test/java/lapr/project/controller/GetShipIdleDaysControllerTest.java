package lapr.project.controller;

import lapr.project.data.DatabaseConnection;
import lapr.project.data.ShipStoreDB;
import lapr.project.model.Company;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetShipIdleDaysControllerTest {
    GetShipIdleDaysController getShipIdleDaysController = new GetShipIdleDaysController();
    DatabaseConnection databaseConnection = mock(DatabaseConnection.class);
    ShipStoreDB shipStoreDB = mock(ShipStoreDB.class);
    Company company = App.getInstance().getCompany();
    AuthController authController = new AuthController();

    @Test
    void getEachShipNumberOfIdleDays() {
        try {
            authController.doLogin("fleetmanager@lei.sem3.pt", "123456");
            this.company.setShipStoreDB(this.shipStoreDB);
            when(this.shipStoreDB.getEachShipNumberOfIdleDays(this.databaseConnection, "FleetManager", 2021)).thenReturn("Pass");
            assertEquals("Pass", this.getShipIdleDaysController.getEachShipNumberOfIdleDays(this.databaseConnection, "FleetManager", 2021));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}