package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PortTest {

    @Test
    public void getid() {
        Port port= new Port (144,"Test Port", "Europe", "Germany", 130, 150);

        assertEquals(144, port.getid());
    }

    @Test
    public void getName() {
        Port port= new Port (144,"Test Port", "Europe", "Germany", 130, 150);

        assertEquals("Test Port", port.getName());
    }

    @Test
    public void getContinent() {
        Port port= new Port (144,"Test Port", "Europe", "Germany", 130, 150);

        assertEquals("Europe", port.getContinent());
    }

    @Test
    public void getCountry() {
        Port port= new Port (144,"Test Port", "Europe", "Germany", 130, 150);

        assertEquals("Germany", port.getCountry());
    }

    @Test
    public void getLatitude() {
        Port port= new Port (144,"Test Port", "Europe", "Germany", 130, 150);

        assertEquals(130, port.getLatitude(), 0.01);
    }

    @Test
    public void getLongitude() {
        Port port= new Port (144,"Test Port", "Europe", "Germany", 130, 150);

        assertEquals(150, port.getLongitude(), 0.01);


    }
}