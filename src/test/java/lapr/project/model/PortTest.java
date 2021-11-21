package lapr.project.model;

import org.junit.jupiter.api.Test;

import java.util.Objects;

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

    @Test
    void testEquals(){
        Port port1= new Port (144,"Test Port", "Europe", "Germany", 130, 150);
        Port port2= new Port (144,"Test Port", "Europe", "Germany", 130, 150); //Copy of port 1
        Port port3= new Port (142,"Test Port", "Europe", "Germany", 85, 120);
        Port port21= new Port (144,"Test Port", "Europe", "Germany", 131, 152); //same id of port2
        Port port22= new Port (141,"Test Port", "Europe", "Germany", 130, 152); //same latitude port2
        Port port23= new Port (141,"Test Port", "Europe", "Germany", 131, 150); //same longitude port2
        Port port24= new Port (144,"Test Port", "Europe", "Germany", 130, 151); //same id and latitude
        Port port25= new Port (144,"Test Port", "Europe", "Germany", 131, 150); //same id and longitude
        Port port26= new Port (141,"Test Port", "Europe", "Germany", 130, 150); //same latitude and longitude
        assertTrue(port1.equals(port1));
        assertFalse(port1.equals(null));
        assertFalse(port1.equals("Port"));
        assertFalse(port1.equals(port3));
        assertTrue(port1.equals(port2));
        assertFalse(port2.equals(port21));
        assertFalse(port2.equals(port22));
        assertFalse(port2.equals(port23));
        assertFalse(port2.equals(port24));
        assertFalse(port2.equals(port25));
        assertFalse(port2.equals(port26));
    }

    @Test
    void testCompare(){
        Port port1= new Port (144,"Test Port", "Europe", "Germany", 130, 150);
        Port port2= new Port (144,"Test Port", "Europe", "Germany", 130, 150);
        Port port3= new Port (142,"Test Port", "Europe", "Germany", 85, 120);
        assertEquals(0,port1.compareTo(port2));
        assertEquals(2,port1.compareTo(port3));
        assertEquals(-2,port3.compareTo(port1));
    }

    @Test
    void testToString(){
        Port port1= new Port (144,"Test Port", "Europe", "Germany", 130, 150);
        String port1S = "Port{id=144, latitude=130.0, longitude=150.0}";
        assertEquals(port1S,port1.toString());

    }

}