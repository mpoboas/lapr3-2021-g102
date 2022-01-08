package lapr.project.controller;

import lapr.project.data.AvgOccupancyPerCargoMan;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AvgOccupancyCGinShipControllerTest {
    AvgOccupancyCGinShipController controller;
    @Mock
    AvgOccupancyPerCargoMan data;
    @Test
    void setShipMMSI() {
        controller = new AvgOccupancyCGinShipController(2039203493, "2021-12-01", "2022-01-31");
        controller.setShipMMSI(13);
        assertEquals(13,controller.getShipMMSI());
    }

    @Test
    void setDate1() {
        controller = new AvgOccupancyCGinShipController(2039203493, "2021-12-01", "2022-01-31");

        controller.setDate1("2020-12-12");
        assertEquals("2020-12-12",controller.getDate1());

    }

    @Test
    void setDate2() {
        controller = new AvgOccupancyCGinShipController(2039203493, "2021-12-01", "2022-01-31");

        controller.setDate2("2023-12-12");
        assertEquals("2023-12-12",controller.getDate2());
    }

    @Test
    void getShipMMSI() {
        controller = new AvgOccupancyCGinShipController(2039203493, "2021-12-01", "2022-01-31");

        assertEquals(2039203493,controller.getShipMMSI());
    }

    @Test
    void getDate1() {
        controller = new AvgOccupancyCGinShipController(2039203493, "2021-12-01", "2022-01-31");

        assertEquals("2021-12-01",controller.getDate1());
    }

    @Test
    void getDate2() {
        controller = new AvgOccupancyCGinShipController(2039203493, "2021-12-01", "2022-01-31");

        assertEquals("2022-01-31",controller.getDate2());

    }


    @Test
    @ExtendWith(MockitoExtension.class)
    void getResult() throws SQLException, IOException {
        controller = new AvgOccupancyCGinShipController(2039203493, "2021-12-01", "2022-01-31");
        controller.setData(this.data);
        Mockito.when(this.data.getDataFromDB()).thenReturn(23.5);
        assertEquals(23.5, controller.getResult());
    }
}