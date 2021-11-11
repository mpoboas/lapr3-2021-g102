package lapr.project.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class PortTest {
    Port port= new Port (144,"Test Port", "Europe", "Germany", 130, 150);

    @Test
    public void getid() {
        assertEquals(144, port.getid());
    }

    @Test
    public void getName() {
        assertEquals("Test Port", port.getName());
    }

    @Test
    public void getContinent() {
        assertEquals("Europe", port.getContinent());
    }

    @Test
    public void getCountry() {
        assertEquals("Germany", port.getCountry());
    }

    @Test
    public void getLatitude() {
        assertEquals(130, port.getLatitude(), 0.01);
    }

    @Test
    public void getLongitude() {
        assertEquals(150, port.getLongitude(), 0.01);


    }
}