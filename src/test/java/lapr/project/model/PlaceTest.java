package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlaceTest {

    @Test
    void getContinent() {
        Place place = new Place("Europa","Portugal",21.00,33.05);
        assertEquals("Europa",place.getContinent());
    }

    @Test
    void getCountry() {
        Place place = new Place("Europa","Portugal",21.00,33.05);
        assertEquals("Portugal",place.getCountry());
    }

    @Test
    void getLatitude() {
        Place place = new Place("Europa","Portugal",21.00,33.05);
        assertEquals(33.05,place.getLatitude());
    }

    @Test
    void getLongitude() {
        Place place = new Place("Europa","Portugal",21.00,33.05);
        assertEquals(21.00,place.getLongitude());
    }
}