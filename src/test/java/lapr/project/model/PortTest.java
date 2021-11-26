package lapr.project.model;

import lapr.project.utils.PortByLatitude;
import lapr.project.utils.PortByLongitude;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

public class PortTest {

    @Test
    public void getid() {
        Port port= new Port (144,"Test Port", "Europe", "Germany", 150, 130);

        assertEquals(144, port.getid());
    }

    @Test
    public void getName() {
        Port port= new Port (144,"Test Port", "Europe", "Germany", 150, 130);

        assertEquals("Test Port", port.getName());
    }

    @Test
    public void getContinent() {
        Port port= new Port (144,"Test Port", "Europe", "Germany", 150, 130);

        assertEquals("Europe", port.getContinent());
    }

    @Test
    public void getCountry() {
        Port port= new Port (144,"Test Port", "Europe", "Germany", 150, 130);

        assertEquals("Germany", port.getCountry());
    }

    @Test
    public void getLatitude() {
        Port port= new Port (144,"Test Port", "Europe", "Germany", 150, 130);

        assertEquals(130, port.getLatitude(), 0.01);
    }

    @Test
    public void getLongitude() {
        Port port= new Port (144,"Test Port", "Europe", "Germany", 150, 130);

        assertEquals(150, port.getLongitude(), 0.01);
    }

    @Test
    void testEquals(){
        Port port1= new Port (144,"Test Port", "Europe", "Germany", 150, 130);
        Port port2= new Port (144,"Test Port", "Europe", "Germany", 150, 130); //Copy of port 1
        Port port3= new Port (142,"Test Port", "Europe", "Germany", 120, 85);
        Port port21= new Port (144,"Test Port", "Europe", "Germany", 152, 131); //same id of port2
        Port port22= new Port (141,"Test Port", "Europe", "Germany", 152, 130); //same latitude port2
        Port port23= new Port (141,"Test Port", "Europe", "Germany", 150, 131); //same longitude port2
        Port port24= new Port (144,"Test Port", "Europe", "Germany", 151, 130); //same id and latitude
        Port port25= new Port (144,"Test Port", "Europe", "Germany", 150, 131); //same id and longitude
        Port port26= new Port (141,"Test Port", "Europe", "Germany", 150, 130); //same latitude and longitude
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
        Port port1= new Port (144,"Test Port", "Europe", "Germany", 150, 130);
        Port port2= new Port (144,"Test Port", "Europe", "Germany", 150, 130);
        Port port3= new Port (142,"Test Port", "Europe", "Germany", 120, 85);
        assertEquals(0,port1.compareTo(port2));
        assertEquals(2,port1.compareTo(port3));
        assertEquals(-2,port3.compareTo(port1));
    }

    @Test
    void testToString(){
        Port port1= new Port (144,"Test Port", "Europe", "Germany", 150, 130);
        String port1S = "Port{id=144, latitude=130.0, longitude=150.0}";
        assertEquals(port1S,port1.toString());
    }

    @Test
    void testComparators(){
        Port p1 = new Port(1, "name1", "continent1", "country1",  3,9);
        Port p2 = new Port(2, "name2", "continent1", "country2",1, 7 );
        Port p3 = new Port(3, "name3", "continent2", "country3", 7, 9);
        Port p4 = new Port(4, "name4", "continent2", "country4", 2, 1);
        Port p5 = new Port(5, "name5", "continent4", "country5", 9, 6);
        Port p6 = new Port(6, "name6", "continent5", "country6", 5, 9);
        Port p7 = new Port(7, "name7", "continent5", "country7", 1, 4);
        Port p8 = new Port(8, "name8", "continent3", "country8", 7, 3);
        Port p9 = new Port(9, "name9", "continent3", "country8", 11, 6);
        Port p10 = new Port(10, "name10", "continent3", "country12", 6, 1);
        Port p11 = new Port(11, "name11", "continent3", "country10", 2, 1);
        Comparator<Port> comp1 = new PortByLongitude();
        Comparator<Port> comp2 = new PortByLatitude();

        assertEquals(1,comp1.compare(p1,p2)); // p1 longitude bigger than p2 longitude
        assertEquals(-1,comp1.compare(p8,p9)); // p8 longitude lesser than p9 longitude

        //Same longitude
        assertEquals(1,comp1.compare(p3,p8)); // p3 latitude bigger than p8 latitude
        assertEquals(-1,comp1.compare(p7,p2)); // p7 latitude bigger than p2 latitude

        assertEquals(0,comp1.compare(p4,p11)); // same longitude and latitude


        assertEquals(1,comp2.compare(p1,p2)); // p1 latitude bigger than p2 latitude
        assertEquals(-1,comp2.compare(p8,p9)); // p8 latitude lesser than p9 latitude

        //same latitude
        assertEquals(1,comp2.compare(p3,p6)); // p3 longitude bigger than p6 longitude
        assertEquals(-1,comp2.compare(p5,p9)); // p5 longitude bigger than p9 longitude

        assertEquals(0,comp2.compare(p4,p11)); // same latitude and longitude


    }

}