package lapr.project.controller;

import lapr.project.data.DatabaseConnection;
import lapr.project.data.PortStoreDB;
import lapr.project.model.Company;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetWarehouseOccupancyRateControllerTest {
    GetWarehouseOccupancyRateController getWarehouseOccupancyRateController = new GetWarehouseOccupancyRateController();
    DatabaseConnection databaseConnection = mock(DatabaseConnection.class);
    PortStoreDB portStoreDB = mock(PortStoreDB.class);
    Company company = App.getInstance().getCompany();

    @Test
    void getEachWarehouseOccupancyRateAndContainersLeaving30Days() {
        try {
            this.company.setPortStoreDB(this.portStoreDB);
            when(this.portStoreDB.getEachWarehouseOccupancyRateAndContainersLeaving30Days(this.databaseConnection, 1)).thenReturn("Pass");

            assertEquals("Pass", this.getWarehouseOccupancyRateController.getEachWarehouseOccupancyRateAndContainersLeaving30Days(this.databaseConnection, 1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}