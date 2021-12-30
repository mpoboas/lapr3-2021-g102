package lapr.project.model;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class SeaDistTest {

    @Test
    void getFrom_country() {
        Country fromC = new Country("Europe","DK","DNK","Denmark",5.75,"Copenhagen",12.583333,55.66666667);
        Country toC = new Country("Europe","TR","TUR","Turkey",79.81,"Ankara",32.866667,39.93333333);
        Port fromP = new Port(10358,"Aarhus","Europe","Denmark",10.21666667,56.15,1222);
        Port toP = new Port(246265,"Ambarli","Europe","Turkey",28.53333333,40.98333333,1222);
        SeaDist s1 = new SeaDist(fromC,fromP,toC,toP,3673);
        assertEquals(fromC,s1.getFrom_country());
    }

    @Test
    void getFrom_port() {
        Country fromC = new Country("Europe","DK","DNK","Denmark",5.75,"Copenhagen",12.583333,55.66666667);
        Country toC = new Country("Europe","TR","TUR","Turkey",79.81,"Ankara",32.866667,39.93333333);
        Port fromP = new Port(10358,"Aarhus","Europe","Denmark",10.21666667,56.15,1222);
        Port toP = new Port(246265,"Ambarli","Europe","Turkey",28.53333333,40.98333333,1222);
        SeaDist s1 = new SeaDist(fromC,fromP,toC,toP,3673);
        assertEquals(fromP,s1.getFrom_port());
    }

    @Test
    void getTo_port() {
        Country fromC = new Country("Europe","DK","DNK","Denmark",5.75,"Copenhagen",12.583333,55.66666667);
        Country toC = new Country("Europe","TR","TUR","Turkey",79.81,"Ankara",32.866667,39.93333333);
        Port fromP = new Port(10358,"Aarhus","Europe","Denmark",10.21666667,56.15,1222);
        Port toP = new Port(246265,"Ambarli","Europe","Turkey",28.53333333,40.98333333,1222);
        SeaDist s1 = new SeaDist(fromC,fromP,toC,toP,3673);
        assertEquals(toP,s1.getTo_port());
    }

    @Test
    void getTo_country() {
        Country fromC = new Country("Europe","DK","DNK","Denmark",5.75,"Copenhagen",12.583333,55.66666667);
        Country toC = new Country("Europe","TR","TUR","Turkey",79.81,"Ankara",32.866667,39.93333333);
        Port fromP = new Port(10358,"Aarhus","Europe","Denmark",10.21666667,56.15,1222);
        Port toP = new Port(246265,"Ambarli","Europe","Turkey",28.53333333,40.98333333,1222);
        SeaDist s1 = new SeaDist(fromC,fromP,toC,toP,3673);
        assertEquals(toC,s1.getTo_country());
    }

    @Test
    void getSeaDistance() {
        Country fromC = new Country("Europe","DK","DNK","Denmark",5.75,"Copenhagen",12.583333,55.66666667);
        Country toC = new Country("Europe","TR","TUR","Turkey",79.81,"Ankara",32.866667,39.93333333);
        Port fromP = new Port(10358,"Aarhus","Europe","Denmark",10.21666667,56.15,1222);
        Port toP = new Port(246265,"Ambarli","Europe","Turkey",28.53333333,40.98333333,1222);
        SeaDist s1 = new SeaDist(fromC,fromP,toC,toP,3673);
        assertEquals(3673,s1.getSeaDistance());
    }

    @Test
    void compareTo() {
        SeaDist s1 = new SeaDist(null,null,null,null,3673);
        SeaDist s2 = new SeaDist(null,null,null,null,3673);
        SeaDist s3 = new SeaDist(null,null,null,null,3670);
        SeaDist s4 = new SeaDist(null,null,null,null,3680);
        assertEquals(0,s1.compareTo(s2));
        assertEquals(1,s1.compareTo(s3));
        assertEquals(-1,s1.compareTo(s4));
    }

    @Test
    void testEquals() {
        Country fromC = new Country("Europe","DK","DNK","Denmark",5.75,"Copenhagen",12.583333,55.66666667);
        Country toC = new Country("Europe","TR","TUR","Turkey",79.81,"Ankara",32.866667,39.93333333);
        Port fromP = new Port(10358,"Aarhus","Europe","Denmark",10.21666667,56.15,1222);
        Port toP = new Port(246265,"Ambarli","Europe","Turkey",28.53333333,40.98333333,1222);

        Port p1 = new Port(29002, "Liverpool", "Europe", "United Kingdom", -3.033333333, 53.46666667,1222);
        Port p2 = new Port(14635, "Los Angeles", "America", "United States", -118.2666667, 33.71666667,1222);
        Port p3 = new Port(25007, "New Jersey", "America", "United States", -74.16666667, 40.66666667,1222);

        SeaDist s1 = new SeaDist(fromC,fromP,toC,toP,3673);
        SeaDist s2 = new SeaDist(fromC,fromP,toC,toP,3673);
        assertTrue(s1.equals(s1));
        assertTrue(s1.equals(s2));
        assertFalse(s1.equals(null));

        SeaDist s3 = new SeaDist(fromC,p1,toC,p2,3673);
        assertFalse(s1.equals(s3));
        SeaDist s4 = new SeaDist(fromC,fromP,toC,p2,3673);
        assertFalse(s1.equals(s4));
        SeaDist s5 = new SeaDist(fromC,p3,toC,toP,3673);
        assertFalse(s1.equals(s5));
        assertFalse(s1.equals("Seadist"));

    }

    @Test
    void testHashCode() {
        Country fromC = new Country("Europe","DK","DNK","Denmark",5.75,"Copenhagen",12.583333,55.66666667);
        Country toC = new Country("Europe","TR","TUR","Turkey",79.81,"Ankara",32.866667,39.93333333);
        Port fromP = new Port(10358,"Aarhus","Europe","Denmark",10.21666667,56.15,1222);
        Port toP = new Port(246265,"Ambarli","Europe","Turkey",28.53333333,40.98333333,1222);
        SeaDist s1 = new SeaDist(fromC,fromP,toC,toP,3673);

        assertEquals(Objects.hash(fromP,toP),s1.hashCode());
    }
}