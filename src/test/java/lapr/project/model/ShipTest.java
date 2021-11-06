package lapr.project.model;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.*;

public class ShipTest {
    Trips trip;
    AISMessage ais1;
    AISMessage ais2;
    AISMessage ais3;
    Trips trip2;
    AISMessage ais4;
    AISMessage ais5;
    AISMessage ais6;
    Ship ship;

    @Test
    public void getAvgSpeed() {
        ship = new Ship(112,"122","test","Ship", 12, 1200, 600 , 21, 3000, 'B' );
        trip = new Trips();
        ship.addTrip(trip);
        ais1 = new AISMessage( LocalDateTime.of(2018, Month.JUNE, 26, 2, 0), 13, 14, 21,13, 4);
        ship.getTrip(0).addAISMessage(ais1);
        ais2 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 27, 1, 0), 14, 15, 21,13, 4);
        ship.getTrip(0).addAISMessage(ais2);
        ais3 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 29, 13, 0), 16, 19, 21,13, 4);
        ship.getTrip(0).addAISMessage(ais3);
        ship.getTrip(0).endTrip();
        trip2 = new Trips();
        ship.addTrip(trip2);
        ais4 = new AISMessage( LocalDateTime.of(2019, Month.MAY, 26, 2, 0), 13, 14, 21,13, 4);
        ship.getTrip(1).addAISMessage(ais4);
        ais5 = new AISMessage(LocalDateTime.of(2019, Month.MAY, 27, 1, 0), 14, 15, 21,13, 4);
        ship.getTrip(1).addAISMessage(ais5);
        ais6 = new AISMessage(LocalDateTime.of(2019, Month.MAY, 29, 13, 0), 16, 19, 21,13, 4);
        ship.getTrip(1).addAISMessage(ais6);
        ship.getTrip(1).endTrip();
        assertEquals(706.3/166,ship.getAvgSpeed(),0.1);
    }

    @Test
    public void getTotalDistance() {
        ship = new Ship(112,"122","test","Ship", 12, 1200, 600 , 21, 3000, 'B' );
        trip = new Trips();
        ship.addTrip(trip);
        ais1 = new AISMessage( LocalDateTime.of(2018, Month.JUNE, 26, 2, 0), 13, 14, 21,13, 4);
        ship.getTrip(0).addAISMessage(ais1);
        ais2 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 27, 1, 0), 14, 15, 21,13, 4);
        ship.getTrip(0).addAISMessage(ais2);
        ais3 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 29, 13, 0), 16, 19, 21,13, 4);
        ship.getTrip(0).addAISMessage(ais3);
        ship.getTrip(0).endTrip();
        trip2 = new Trips();
        ship.addTrip(trip2);
        ais4 = new AISMessage( LocalDateTime.of(2019, Month.MAY, 26, 2, 0), 13, 14, 21,13, 4);
        ship.getTrip(1).addAISMessage(ais4);
        ais5 = new AISMessage(LocalDateTime.of(2019, Month.MAY, 27, 1, 0), 14, 15, 21,13, 4);
        ship.getTrip(1).addAISMessage(ais5);
        ais6 = new AISMessage(LocalDateTime.of(2019, Month.MAY, 29, 13, 0), 16, 19, 21,13, 4);
        ship.getTrip(1).addAISMessage(ais6);
        ship.getTrip(1).endTrip();
        assertEquals(706.3,ship.getTotalDistance(),0.1 );
    }

    @Test
    public void getTotalTimeinSea() {
        ship = new Ship(112,"122","test","Ship", 12, 1200, 600 , 21, 3000, 'B' );
        trip = new Trips();
        ship.addTrip(trip);
        ais1 = new AISMessage( LocalDateTime.of(2018, Month.JUNE, 26, 2, 0), 13, 14, 21,13, 4);
        ship.getTrip(0).addAISMessage(ais1);
        ais2 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 27, 1, 0), 14, 15, 21,13, 4);
        ship.getTrip(0).addAISMessage(ais2);
        ais3 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 29, 13, 0), 16, 19, 21,13, 4);
        ship.getTrip(0).addAISMessage(ais3);
        ship.getTrip(0).endTrip();
        trip2 = new Trips();
        ship.addTrip(trip2);
        ais4 = new AISMessage( LocalDateTime.of(2019, Month.MAY, 26, 2, 0), 13, 14, 21,13, 4);
        ship.getTrip(1).addAISMessage(ais4);
        ais5 = new AISMessage(LocalDateTime.of(2019, Month.MAY, 27, 1, 0), 14, 15, 21,13, 4);
        ship.getTrip(1).addAISMessage(ais5);
        ais6 = new AISMessage(LocalDateTime.of(2019, Month.MAY, 29, 13, 0), 16, 19, 21,13, 4);
        ship.getTrip(1).addAISMessage(ais6);
        ship.getTrip(1).endTrip();
        assertEquals(166,ship.getTotalTimeinSea(),0.1 );
    }

    @Test
    public void addAISMessage() {
    }

    @Test
    public void addTrip() {
        ship = new Ship(112,"122","test","Ship", 12, 1200, 600 , 21, 3000, 'B' );

        trip = new Trips();
        ship.addTrip(trip);
        assertEquals(trip, ship.getTrip(0));
        ais1 = new AISMessage( LocalDateTime.of(2018, Month.JUNE, 26, 2, 0), 13, 14, 21,13, 4);
        ship.getTrip(0).addAISMessage(ais1);
        ais2 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 27, 1, 0), 14, 15, 21,13, 4);
        ship.getTrip(0).addAISMessage(ais1);
        ais3 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 29, 13, 0), 16, 19, 21,13, 4);
        ship.getTrip(0).addAISMessage(ais1);
        ship.getTrip(0).endTrip();
        assertEquals(ship.getTrip(0).getTripStatus(), true);
    }

    @Test
    public void testEquals() {
    }

    @Test
    public void testHashCode() {
    }

    @Test
    public void compareTo() {
    }
}