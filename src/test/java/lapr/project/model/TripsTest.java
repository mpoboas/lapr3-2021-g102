package lapr.project.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class TripsTest {
    Trips trip ;
    AISMessage ais1;
    AISMessage ais2;
    AISMessage ais3;

    @Test
    public void getStartTest(){
        trip = new Trips();
        ais1 = new AISMessage( LocalDateTime.of(2018, Month.JUNE, 26, 2, 0), 13, 14, 21,13, 4, 3000, 'B' );
        trip.addAISMessage(ais1);
        ais2 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 27, 1, 0), 14, 15, 21,13, 4, 3000, 'B' );
        trip.addAISMessage(ais2);
        ais3 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 29, 13, 0), 16, 19, 21,13, 4, 3000, 'B' );
        trip.addAISMessage(ais3);
        assertEquals(LocalDateTime.of(2018, Month.JUNE, 26, 2, 0),trip.getStart());
    }

    @Test
    public void addAISMessage() {
        trip = new Trips();
        ais1 = new AISMessage( LocalDateTime.of(2018, Month.JUNE, 26, 2, 0), 13, 14, 21,13, 4, 3000, 'B' );
        trip.addAISMessage(ais1);
        assertTrue(trip.getAIsMessage(0) == ais1, "Should be true");


    }

    @Test
    public void endTrip() {
        trip = new Trips();
        ais1 = new AISMessage( LocalDateTime.of(2018, Month.JUNE, 26, 2, 0), 13, 14, 21,13, 4, 3000, 'B' );
        trip.addAISMessage(ais1);
        ais2 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 27, 1, 0), 14, 15, 21,13, 4, 3000, 'B' );
        trip.addAISMessage(ais2);
        ais3 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 29, 13, 0), 16, 19, 21,13, 4, 3000, 'B' );
        trip.addAISMessage(ais3);
        assertFalse(trip.getTripStatus(), "Should be false");
        trip.endTrip();
        assertTrue(trip.getTripStatus(), "Should be true");
    }


    @Test
    public void getTripDistance() {
        trip = new Trips();
        ais1 = new AISMessage( LocalDateTime.of(2018, Month.JUNE, 26, 2, 0), 13, 14, 21,13, 4, 3000, 'B' );
        trip.addAISMessage(ais1);
        ais2 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 27, 1, 0), 14, 15, 21,13, 4, 3000, 'B' );
        trip.addAISMessage(ais2);
        ais3 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 29, 13, 0), 16, 19, 21,13, 4, 3000, 'B' );
        trip.addAISMessage(ais3);
        assertEquals(344.9,trip.getTripDistance(), 0.1);
    }

    @Test
    public void getTripDuration() {
        trip = new Trips();
        ais1 = new AISMessage( LocalDateTime.of(2018, Month.JUNE, 26, 2, 0), 13, 14, 21,13, 4, 3000, 'B' );
        trip.addAISMessage(ais1);
        ais2 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 27, 1, 0), 14, 15, 21,13, 4, 3000, 'B' );
        trip.addAISMessage(ais2);
        ais3 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 29, 13, 0), 16, 19, 21,13, 4, 3000, 'B' );
        trip.addAISMessage(ais3);
        trip.endTrip();
        assertEquals(83,trip.getTripDuration(), 0.1);
    }

    @Test
    public void getEnd(){
        trip = new Trips();
        ais1 = new AISMessage( LocalDateTime.of(2018, Month.JUNE, 26, 2, 0), 13, 14, 21,13, 4, 3000, 'B' );
        trip.addAISMessage(ais1);
        ais2 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 27, 1, 0), 14, 15, 21,13, 4, 3000, 'B' );
        trip.addAISMessage(ais2);
        ais3 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 29, 13, 0), 16, 19, 21,13, 4, 3000, 'B' );
        trip.addAISMessage(ais3);
        trip.endTrip();
        assertEquals(LocalDateTime.of(2018, Month.JUNE, 29, 13, 0), trip.getEnd());

    }
    @Test
    public void testTripWithArguments(){
        trip = new Trips(1, 1232,12311, 1, new Date("01/12/2021 9:00:00"), new ArrayList<>());
        assertEquals(1, trip.getId());
        assertEquals(1232, trip.getIdPortWarehouseDeparture());
        assertEquals(12311, trip.getIdPortWarehouseArrival());
        assertEquals(1, trip.getIdVehicle());
        assertEquals(new Date("01/12/2021 9:00:00"), trip.getEndDate());

    }
}