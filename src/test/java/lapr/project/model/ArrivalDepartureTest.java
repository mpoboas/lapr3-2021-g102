package lapr.project.model;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class ArrivalDepartureTest {
    ArrivalDeparture ad;

    @Test
    void getIdTrip() {
        ad = new ArrivalDeparture(1,1231,12321,new Timestamp(123432), new Timestamp(123441233), 1232,12111);
        assertEquals(1,ad.getIdTrip());
    }

    @Test
    void getPortWareDeparture() {
        ad = new ArrivalDeparture(1,1231,12321,new Timestamp(123432), new Timestamp(123441233), 1232,12111);
        assertEquals(1231,ad.getPortWareDeparture());
    }

    @Test
    void getPortWarehouseArrival() {
        ad = new ArrivalDeparture(1,1231,12321,new Timestamp(123432), new Timestamp(123441233), 1232,12111);
        assertEquals(12321, ad.getPortWarehouseArrival());
    }

    @Test
    void getArrivalDate() {
        ad = new ArrivalDeparture(1,1231,12321,new Timestamp(123432), new Timestamp(123441233), 1232,12111);
        assertTrue(ad.getDepartureDate().equals(new Timestamp(123432)));
    }

    @Test
    void getDepartureDate() {
        ad = new ArrivalDeparture(1,1231,12321,new Timestamp(123432), new Timestamp(123441233), 1232,12111);
        assertTrue(ad.getArrivalDate().equals(new Timestamp(123441233)));

    }

    @Test
    void getCargoManifestIdLoad() {
        ad = new ArrivalDeparture(1,1231,12321,new Timestamp(123432), new Timestamp(123441233), 1232,12111);
        assertEquals(1232, ad.getCargoManifestIdLoad());

    }

    @Test
    void getCargoManifestIdUnload() {
        ad = new ArrivalDeparture(1,1231,12321,new Timestamp(123432), new Timestamp(123441233), 1232,12111);
        assertEquals(12111, ad.getCargoManifestIdUnload());

    }
}