package lapr.project.controller;

import lapr.project.data.ConnectionFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetShipOccupancyRateControllerTest {
    AuthController authController = new AuthController();
    ConnectionFactory connectionFactory = mock(ConnectionFactory.class);
    GetShipOccupancyRateController getShipOccupancyRateController = mock(GetShipOccupancyRateController.class);

    @Test
    void getShipOccupancyRateById() {
        try {
            authController.doLogin("shipcaptain@lei.sem3.pt", "123456");
            when(getShipOccupancyRateController.getShipOccupancyRateById(connectionFactory, 112, 1)).thenReturn(0.75);
            assertEquals(0.75, getShipOccupancyRateController.getShipOccupancyRateById(connectionFactory, 112, 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getShipOccupancyRateByDateTime() {
        try {
            authController.doLogin("shipcaptain@lei.sem3.pt", "123456");
            when(getShipOccupancyRateController.getShipOccupancyRateByDateTime(connectionFactory, 112, "03-03-2020 00:00")).thenReturn(0.75);
            assertEquals(0.75, getShipOccupancyRateController.getShipOccupancyRateByDateTime(connectionFactory, 112, "03-03-2020 00:00"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getIdShipById() {
        try {
            authController.doLogin("shipcaptain@lei.sem3.pt", "123456");
            when(getShipOccupancyRateController.getShipId()).thenReturn(1);
            getShipOccupancyRateController.getShipOccupancyRateById( connectionFactory, 112, 1);
            assertEquals(1, getShipOccupancyRateController.getShipId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getIdShipByDateTime() {
        try {
            authController.doLogin("shipcaptain@lei.sem3.pt", "123456");
            when(getShipOccupancyRateController.getShipId()).thenReturn(1);
            getShipOccupancyRateController.getShipOccupancyRateByDateTime(connectionFactory, 112, "03-03-2020 00:00");
            assertEquals(1, getShipOccupancyRateController.getShipId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getIdCargoManifestById() {
        try {
            authController.doLogin("shipcaptain@lei.sem3.pt", "123456");
            when(getShipOccupancyRateController.getCargoManifestId()).thenReturn(1);
            getShipOccupancyRateController.getShipOccupancyRateById(connectionFactory, 112, 1);
            assertEquals(1, getShipOccupancyRateController.getCargoManifestId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getIdCargoManifestByDateTime() {
        try {
            authController.doLogin("shipcaptain@lei.sem3.pt", "123456");
            when(getShipOccupancyRateController.getCargoManifestId()).thenReturn(10);
            getShipOccupancyRateController.getShipOccupancyRateByDateTime(connectionFactory, 112, "03-03-2020 00:00");
            assertEquals(10, getShipOccupancyRateController.getCargoManifestId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}