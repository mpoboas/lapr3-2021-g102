package lapr.project.model;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class CountryTest {

    @Test
    void getAlpha2Code() {
        Country c1 = new Country("Europe","CY","CYP","Cyprus",0.85,"Nicosia",35.16666667,33.366667);
        assertEquals("CY",c1.getAlpha2Code());
        assertNotEquals("YC",c1.getAlpha2Code());
    }

    @Test
    void getAlpha3Code() {
        Country c1 = new Country("Europe","CY","CYP","Cyprus",0.85,"Nicosia",35.16666667,33.366667);
        assertEquals("CYP",c1.getAlpha3Code());
        assertNotEquals("PYC",c1.getAlpha3Code());
    }

    @Test
    void getContinent() {
        Country c1 = new Country("Europe","CY","CYP","Cyprus",0.85,"Nicosia",35.16666667,33.366667);
        assertEquals("Europe",c1.getContinent());
        assertNotEquals("America",c1.getContinent());
    }

    @Test
    void getCountry() {
        Country c1 = new Country("Europe","CY","CYP","Cyprus",0.85,"Nicosia",35.16666667,33.366667);
        assertEquals("Cyprus",c1.getCountry());
        assertNotEquals("Portugal",c1.getCountry());
    }

    @Test
    void getLatitude() {
        Country c1 = new Country("Europe","CY","CYP","Cyprus",0.85,"Nicosia",35.16666667,33.366667);
        assertEquals(33.366667,c1.getLatitude());
        assertNotEquals(58,c1.getLatitude());
    }

    @Test
    void getLongitude() {
        Country c1 = new Country("Europe","CY","CYP","Cyprus",0.85,"Nicosia",35.16666667,33.366667);
        assertEquals(35.16666667,c1.getLongitude());
        assertNotEquals(58,c1.getLongitude());
    }

    @Test
    void getPopulation() {
        Country c1 = new Country("Europe","CY","CYP","Cyprus",0.85,"Nicosia",35.16666667,33.366667);
        assertEquals(0.85,c1.getPopulation());
        assertNotEquals(1,c1.getPopulation());
    }

    @Test
    void getCapital() {
        Country c1 = new Country("Europe","CY","CYP","Cyprus",0.85,"Nicosia",35.16666667,33.366667);
        assertEquals("Nicosia",c1.getCapital());
        assertNotEquals("Lisboa",c1.getCapital());

    }

    @Test
    void testEqualsSameObject() {
        Country c1 = new Country("Europe","CY","CYP","Cyprus",0.85,"Nicosia",35.16666667,33.366667);
        assertTrue(c1.equals(c1));
    }

    @Test
    void testEqualsWithNull() {
        Country c1 = new Country("Europe","CY","CYP","Cyprus",0.85,"Nicosia",35.16666667,33.366667);
        assertFalse(c1.equals(null));
    }

    @Test
    void testEqualsWithOtherObject() {
        Country c1 = new Country("Europe","CY","CYP","Cyprus",0.85,"Nicosia",35.16666667,33.366667);
        assertFalse(c1.equals("Country"));
    }

    @Test
    void testNotEquals() {
        Country c1 = new Country("Europe","CY","CYP","Cyprus",0.85,"Nicosia",35.16666667,33.366667);
        Country c2 = new Country("Europe","MT","MLT","Malta",0.44,"Valletta",35.88333333,14.5);
        assertFalse(c1.equals(c2));
    }

    @Test
    void testEquals() {
        Country c1 = new Country("Europe","CY","CYP","Cyprus",0.85,"Nicosia",35.16666667,33.366667);
        Country c2 = new Country("Europe","CY","CYP","Cyprus",0.44,"Valletta",35.88333333,14.5);
        assertTrue(c1.equals(c2));
    }

    @Test
    void testEqualsExceptions() {
        Country c1 = new Country("Europe","CY","CYP","Cyprus",0.85,"Nicosia",35.16666667,33.366667);

        Country c2 = new Country("Europe","CY","MLT","Malta",0.44,"Valletta",35.88333333,14.5);
        Country c3 = new Country("Europe","MT","CYP","Malta",0.44,"Valletta",35.88333333,14.5);
        Country c4 = new Country("Europe","MT","MLT","Cyprus",0.44,"Valletta",35.88333333,14.5);

        Country c5 = new Country("Europe","CY","CYP","Malta",0.44,"Valletta",35.88333333,14.5);

        Country c6 = new Country("Europe","CY","MLT","Cyprus",0.44,"Valletta",35.88333333,14.5);
        Country c7 = new Country("Europe","MT","CYP","Cyprus",0.44,"Valletta",35.88333333,14.5);

        assertFalse(c1.equals(c2));
        assertFalse(c1.equals(c3));
        assertFalse(c1.equals(c4));
        assertFalse(c1.equals(c5));
        assertFalse(c1.equals(c6));
        assertFalse(c1.equals(c7));
    }




    @Test
    void testHashCode() {
        Country c1 = new Country("Europe","CY","CYP","Cyprus",0.85,"Nicosia",35.16666667,33.366667);
        assertEquals(Objects.hash("CY","CYP","Cyprus"),c1.hashCode());
    }
}