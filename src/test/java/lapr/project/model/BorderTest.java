package lapr.project.model;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class BorderTest {

    @Test
    void getCountry1() {
        Country c1 = new Country("Europe","CY","CYP","Cyprus",0.85,"Nicosia",35.16666667,33.366667);
        Country c2 = new Country("Europe","MT","MLT","Malta",0.44,"Valletta",35.88333333,14.5);
        Border border = new Border(c1,c2);
        assertEquals(c1,border.getCountry1());
        assertNotEquals(c2,border.getCountry1());
    }

    @Test
    void getCountry2() {
        Country c1 = new Country("Europe","CY","CYP","Cyprus",0.85,"Nicosia",35.16666667,33.366667);
        Country c2 = new Country("Europe","MT","MLT","Malta",0.44,"Valletta",35.88333333,14.5);
        Border border = new Border(c1,c2);
        assertEquals(c2,border.getCountry2());
        assertNotEquals(c1,border.getCountry2());
    }

    @Test
    void testEquals() {
        Country c1 = new Country("Europe","CY","CYP","Cyprus",0.85,"Nicosia",35.16666667,33.366667);
        Country c2 = new Country("Europe","MT","MLT","Malta",0.44,"Valletta",35.88333333,14.5);
        Border border1 = new Border(c1,c2);
        assertTrue(border1.equals(border1));
        Country c3 = new Country("Europe","AM","ARM","Armenia",3.01,"Yerevan",40.16666667,44.5);
        Country c4 = new Country("Europe","ES","ESP","Spain",46.53,"Madrid",40.4,-3.683333);
        Border border2 = new Border(c3,c4);
        assertFalse(border1.equals(border2));
        Border border3 = new Border(c1,c4);
        assertFalse(border1.equals(border3));
        Border border4 = new Border(c3,c2);
        assertFalse(border1.equals(border4));
        Border border5 = new Border(c1,c2);
        assertTrue(border1.equals(border5));
        assertFalse(border1.equals(null));
        assertFalse(border1.equals(c1));

    }

    @Test
    void testHashCode() {
        Country c1 = new Country("Europe","CY","CYP","Cyprus",0.85,"Nicosia",35.16666667,33.366667);
        Country c2 = new Country("Europe","MT","MLT","Malta",0.44,"Valletta",35.88333333,14.5);
        Border border = new Border(c1,c2);
        assertEquals(Objects.hash(c1,c2),border.hashCode());
    }
}