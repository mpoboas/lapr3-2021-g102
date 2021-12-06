package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContainerTest {

    @Test
    void getId() {
        Container c = new Container( "ID", "Refrigerated","20R5",2000,1220,1200);
        assertEquals("ID", c.getId());
        assertNotEquals("LPRU7513725", c.getId());

    }

    @Test
    void getType() {
        Container c = new Container( "ID", "Refrigerated","20R5",2000,1220,1200);
        assertEquals("Refrigerated", c.getType());
        assertNotEquals("refrigerated5", c.getType());

    }

    @Test
    void getIsoCode() {
        Container c = new Container( "ID", "Refrigerated","20R5",2000,1220,1200);
        assertEquals("20R5", c.getIsoCode());
        assertNotEquals("20NR", c.getIsoCode());

    }

    @Test
    void getTare() {
        Container c = new Container( "ID", "Refrigerated","20R5",2000,1220,1200);
        assertEquals(2000, c.getTare());
        assertNotEquals(1199, c.getGross());
    }

    @Test
    void getPayload() {
        Container c = new Container( "ID", "Refrigerated","20R5",2000,1220,1200);
        assertEquals(1220, c.getPayload());
        assertNotEquals(1199, c.getPayload());
    }

    @Test
    void getGross() {
        Container c = new Container( "ID", "Refrigerated","20R5",2000,1220,1200);
        assertEquals(1200, c.getGross());
        assertNotEquals(1199, c.getGross());

    }

    @Test
    void testToString() {
        Container c = new Container( "ID", "Refrigerated","20R5",2000,1220,1200);
        System.out.println(c);
    }
}