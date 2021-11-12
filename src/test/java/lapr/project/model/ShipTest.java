package lapr.project.model;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Objects;

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
        ship = new Ship(112,"122","test","Ship", 12, 1200, 600 , 21);
        trip = new Trips();
        ship.addTrip(trip);
        ais1 = new AISMessage( LocalDateTime.of(2018, Month.JUNE, 26, 2, 0), 13, 14, 21,13, 4, 3000, 'B' );
        ship.getTrip(0).addAISMessage(ais1);
        ais2 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 27, 1, 0), 14, 15, 21,13, 4, 3000, 'B' );
        ship.getTrip(0).addAISMessage(ais2);
        ais3 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 29, 13, 0), 16, 19, 21,13, 4, 3000, 'B' );
        ship.getTrip(0).addAISMessage(ais3);
        ship.getTrip(0).endTrip();
        trip2 = new Trips();
        ship.addTrip(trip2);
        ais4 = new AISMessage( LocalDateTime.of(2019, Month.MAY, 26, 2, 0), 13, 14, 21,13, 4, 3000, 'B' );
        ship.getTrip(1).addAISMessage(ais4);
        ais5 = new AISMessage(LocalDateTime.of(2019, Month.MAY, 27, 1, 0), 14, 15, 21,13, 4, 3000, 'B' );
        ship.getTrip(1).addAISMessage(ais5);
        ais6 = new AISMessage(LocalDateTime.of(2019, Month.MAY, 29, 13, 0), 16, 19, 21,13, 4, 3000, 'B' );
        ship.getTrip(1).addAISMessage(ais6);
        ship.getTrip(1).endTrip();
        assertEquals(706.3/166,ship.getAvgSpeed(ship.getTotalDistance(), ship.getTotalTimeinSea()),0.1);
    }

    @Test
    public void numberOfMovements(){
        ship = new Ship(112,"122","test","Ship", 12, 1200, 600 , 21);
        trip = new Trips();
        ship.addTrip(trip);
        ais1 = new AISMessage( LocalDateTime.of(2018, Month.JUNE, 26, 2, 0), 13, 14, 21,13, 4, 3000, 'B' );
        ship.getTrip(0).addAISMessage(ais1);
        ship.addAISMessage(ais1);
        ais2 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 27, 1, 0), 14, 15, 21,13, 4, 3000, 'B' );
        ship.getTrip(0).addAISMessage(ais2);
        ship.addAISMessage(ais2);
        ais3 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 29, 13, 0), 16, 19, 21,13, 4, 3000, 'B' );
        ship.getTrip(0).addAISMessage(ais3);
        ship.addAISMessage(ais3);
        ship.getTrip(0).endTrip();
        trip2 = new Trips();
        ship.addTrip(trip2);
        ais4 = new AISMessage( LocalDateTime.of(2019, Month.MAY, 26, 2, 0), 13, 14, 21,13, 4, 3000, 'B' );
        ship.getTrip(1).addAISMessage(ais4);
        ship.addAISMessage(ais4);
        ais5 = new AISMessage(LocalDateTime.of(2019, Month.MAY, 27, 1, 0), 14, 15, 21,13, 4, 3000, 'B' );
        ship.getTrip(1).addAISMessage(ais5);
        ship.addAISMessage(ais5);
        ais6 = new AISMessage(LocalDateTime.of(2019, Month.MAY, 29, 13, 0), 16, 19, 21,13, 4, 3000, 'B' );
        ship.getTrip(1).addAISMessage(ais6);
        ship.addAISMessage(ais6);
        ship.getTrip(1).endTrip();
        assertEquals(6, ship.numberOfMovements());
    }


    @Test
    public void deltaDistance(){
        ship = new Ship(112,"122","test","Ship", 12, 1200, 600 , 21);
        trip = new Trips();
        ship.addTrip(trip);
        ais1 = new AISMessage( LocalDateTime.of(2018, Month.JUNE, 26, 2, 0), 13, 14, 21,13, 4, 3000, 'B' );
        ship.getTrip(0).addAISMessage(ais1);
        ship.addAISMessage(ais1);
        ais2 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 27, 1, 0), 14, 15, 21,13, 4, 3000, 'B' );
        ship.getTrip(0).addAISMessage(ais2);
        ship.addAISMessage(ais2);
        ais3 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 29, 13, 0), 16, 19, 21,13, 4, 3000, 'B' );
        ship.getTrip(0).addAISMessage(ais3);
        ship.addAISMessage(ais3);
        ship.getTrip(0).endTrip();
        trip2 = new Trips();
        ship.addTrip(trip2);
        ais4 = new AISMessage( LocalDateTime.of(2019, Month.MAY, 26, 2, 0), 13, 14, 21,13, 4, 3000, 'B' );
        ship.getTrip(1).addAISMessage(ais4);
        ship.addAISMessage(ais4);
        ais5 = new AISMessage(LocalDateTime.of(2019, Month.MAY, 27, 1, 0), 14, 15, 21,13, 4, 3000, 'B' );
        ship.getTrip(1).addAISMessage(ais5);
        ship.addAISMessage(ais5);
        ais6 = new AISMessage(LocalDateTime.of(2019, Month.MAY, 29, 13, 0), 16, 19, 21,13, 4, 3000, 'B' );
        ship.getTrip(1).addAISMessage(ais6);
        ship.addAISMessage(ais6);
        ship.getTrip(1).endTrip();
        assertEquals(349.8, ship.deltaDistance(), 0.1);
    }

    @Test
    public void getTotalDistance() {
        ship = new Ship(112,"122","test","Ship", 12, 1200, 600 , 21);
        trip = new Trips();
        ship.addTrip(trip);
        ais1 = new AISMessage( LocalDateTime.of(2018, Month.JUNE, 26, 2, 0), 13, 14, 21,13, 4, 3000, 'B' );
        ship.getTrip(0).addAISMessage(ais1);
        ais2 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 27, 1, 0), 14, 15, 21,13, 4, 3000, 'B' );
        ship.getTrip(0).addAISMessage(ais2);
        ais3 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 29, 13, 0), 16, 19, 21,13, 4, 3000, 'B' );
        ship.getTrip(0).addAISMessage(ais3);
        ship.getTrip(0).endTrip();
        trip2 = new Trips();
        ship.addTrip(trip2);
        ais4 = new AISMessage( LocalDateTime.of(2019, Month.MAY, 26, 2, 0), 13, 14, 21,13, 4, 3000, 'B' );
        ship.getTrip(1).addAISMessage(ais4);
        ais5 = new AISMessage(LocalDateTime.of(2019, Month.MAY, 27, 1, 0), 14, 15, 21,13, 4, 3000, 'B' );
        ship.getTrip(1).addAISMessage(ais5);
        ais6 = new AISMessage(LocalDateTime.of(2019, Month.MAY, 29, 13, 0), 16, 19, 21,13, 4, 3000, 'B' );
        ship.getTrip(1).addAISMessage(ais6);
        ship.getTrip(1).endTrip();
        assertEquals(706.3,ship.getTotalDistance(),0.1 );
    }

    @Test
    public void getTotalTimeinSea() {
        ship = new Ship(112,"122","test","Ship", 12, 1200, 600 , 21);
        trip = new Trips();
        ship.addTrip(trip);
        ais1 = new AISMessage( LocalDateTime.of(2018, Month.JUNE, 26, 2, 0), 13, 14, 21,13, 4, 3000, 'B' );
        ship.getTrip(0).addAISMessage(ais1);
        ais2 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 27, 1, 0), 14, 15, 21,13, 4, 3000, 'B' );
        ship.getTrip(0).addAISMessage(ais2);
        ais3 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 29, 13, 0), 16, 19, 21,13, 4, 3000, 'B' );
        ship.getTrip(0).addAISMessage(ais3);
        ship.getTrip(0).endTrip();
        trip2 = new Trips();
        ship.addTrip(trip2);
        ais4 = new AISMessage( LocalDateTime.of(2019, Month.MAY, 26, 2, 0), 13, 14, 21,13, 4, 3000, 'B' );
        ship.getTrip(1).addAISMessage(ais4);
        ais5 = new AISMessage(LocalDateTime.of(2019, Month.MAY, 27, 1, 0), 14, 15, 21,13, 4, 3000, 'B' );
        ship.getTrip(1).addAISMessage(ais5);
        ais6 = new AISMessage(LocalDateTime.of(2019, Month.MAY, 29, 13, 0), 16, 19, 21,13, 4, 3000, 'B' );
        ship.getTrip(1).addAISMessage(ais6);
        ship.getTrip(1).endTrip();
        assertEquals(166,ship.getTotalTimeinSea(),0.1 );
    }

    @Test
    public void addAISMessage() {
        ship = new Ship(112,"122","test","Ship", 12, 1200, 600 , 21);
        trip = new Trips();
        ship.addTrip(trip);
        ais1 = new AISMessage( LocalDateTime.of(2018, Month.JUNE, 26, 2, 0), 13, 14, 21,13, 4, 3000, 'B' );
        ship.getTrip(0).addAISMessage(ais1);
        ship.addAISMessage(ais1);
        assertEquals(ship.getAISM(0),ais1);
        ais2 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 27, 1, 0), 14, 15, 21,13, 4, 3000, 'B' );
        ship.getTrip(0).addAISMessage(ais2);
        ship.addAISMessage(ais2);
        ais3 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 29, 13, 0), 16, 19, 21,13, 4, 3000, 'B' );
        ship.getTrip(0).addAISMessage(ais3);
        ship.addAISMessage(ais3);
        ship.getTrip(0).endTrip();
        trip2 = new Trips();
        ship.addTrip(trip2);
        ais4 = new AISMessage( LocalDateTime.of(2019, Month.MAY, 26, 2, 0), 13, 14, 21,13, 4, 3000, 'B' );
        ship.getTrip(1).addAISMessage(ais4);
        ship.addAISMessage(ais4);
        ais5 = new AISMessage(LocalDateTime.of(2019, Month.MAY, 27, 1, 0), 14, 15, 21,13, 4, 3000, 'B' );
        ship.getTrip(1).addAISMessage(ais5);
        ship.addAISMessage(ais5);
        ais6 = new AISMessage(LocalDateTime.of(2019, Month.MAY, 29, 13, 0), 16, 19, 21,13, 4, 3000, 'B' );
        ship.getTrip(1).addAISMessage(ais6);
        ship.addAISMessage(ais6);
        ship.getTrip(1).endTrip();
        assertEquals(ship.getAISM(0),ais1);
        assertEquals(ship.getAISM(1),ais2);
        assertEquals(ship.getAISM(2),ais3);
        assertEquals(ship.getAISM(3),ais4);
        assertEquals(ship.getAISM(4),ais5);
        assertEquals(ship.getAISM(5),ais6);
    }

    @Test
    public void addTrip() {
        ship = new Ship(112,"122","test","Ship", 12, 1200, 600 , 21 );

        trip = new Trips();
        ship.addTrip(trip);
        assertEquals(trip, ship.getTrip(0));
        ais1 = new AISMessage( LocalDateTime.of(2018, Month.JUNE, 26, 2, 0), 13, 14, 21,13, 4, 3000, 'B');
        ship.getTrip(0).addAISMessage(ais1);
        ais2 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 27, 1, 0), 14, 15, 21,13, 4, 3000, 'B');
        ship.getTrip(0).addAISMessage(ais1);
        ais3 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 29, 13, 0), 16, 19, 21,13, 4, 3000, 'B');
        ship.getTrip(0).addAISMessage(ais1);
        ship.getTrip(0).endTrip();
        assertEquals(ship.getTrip(0).getTripStatus(), true);
    }

    @Test
    public void testTimeBetweenDates(){
        ship = new Ship(112,"122","test","Ship", 12, 1200, 600 , 21);
        trip = new Trips();
        ship.addTrip(trip);
        ais1 = new AISMessage( LocalDateTime.of(2018, Month.JUNE, 26, 2, 0), 13, 14, 21,13, 4, 3000, 'B' );
        ship.getTrip(0).addAISMessage(ais1);
        ship.addAISMessage(ais1);
        assertEquals(ship.getAISM(0),ais1);
        ais2 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 27, 1, 0), 14, 15, 21,13, 4, 3000, 'B' );
        ship.getTrip(0).addAISMessage(ais2);
        ship.addAISMessage(ais2);
        ais3 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 29, 13, 0), 16, 19, 21,13, 4, 3000, 'B' );
        ship.getTrip(0).addAISMessage(ais3);
        ship.addAISMessage(ais3);
        ship.getTrip(0).endTrip();
        trip2 = new Trips();
        ship.addTrip(trip2);
        ais4 = new AISMessage( LocalDateTime.of(2019, Month.MAY, 26, 2, 0), 13, 14, 21,13, 4, 3000, 'B' );
        ship.getTrip(1).addAISMessage(ais4);
        ship.addAISMessage(ais4);
        ais5 = new AISMessage(LocalDateTime.of(2019, Month.MAY, 27, 1, 0), 14, 15, 21,13, 4, 3000, 'B' );
        ship.getTrip(1).addAISMessage(ais5);
        ship.addAISMessage(ais5);
        ais6 = new AISMessage(LocalDateTime.of(2019, Month.MAY, 29, 13, 0), 16, 19, 21,13, 4, 3000, 'B' );
        ship.getTrip(1).addAISMessage(ais6);
        ship.addAISMessage(ais6);
        ship.getTrip(1).endTrip();
        assertEquals(83,ship.getTimeInSeaBetweenDates( LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0),  LocalDateTime.of(2019, Month.JANUARY, 1, 0, 0)),0.1 );
    }
    @Test
    public void testDistBetweenDates(){
        ship = new Ship(112,"122","test","Ship", 12, 1200, 600 , 21);
        trip = new Trips();
        ship.addTrip(trip);
        ais1 = new AISMessage( LocalDateTime.of(2018, Month.JUNE, 26, 2, 0), 13, 14, 21,13, 4, 3000, 'B' );
        ship.getTrip(0).addAISMessage(ais1);
        ship.addAISMessage(ais1);
        assertEquals(ship.getAISM(0),ais1);
        ais2 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 27, 1, 0), 14, 15, 21,13, 4, 3000, 'B' );
        ship.getTrip(0).addAISMessage(ais2);
        ship.addAISMessage(ais2);
        ais3 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 29, 13, 0), 16, 19, 21,13, 4, 3000, 'B' );
        ship.getTrip(0).addAISMessage(ais3);
        ship.addAISMessage(ais3);
        ship.getTrip(0).endTrip();
        trip2 = new Trips();
        ship.addTrip(trip2);
        ais4 = new AISMessage( LocalDateTime.of(2019, Month.MAY, 26, 2, 0), 13, 14, 21,13, 4, 3000, 'B' );
        ship.getTrip(1).addAISMessage(ais4);
        ship.addAISMessage(ais4);
        ais5 = new AISMessage(LocalDateTime.of(2019, Month.MAY, 27, 1, 0), 14, 15, 21,13, 4, 3000, 'B' );
        ship.getTrip(1).addAISMessage(ais5);
        ship.addAISMessage(ais5);
        ais6 = new AISMessage(LocalDateTime.of(2019, Month.MAY, 29, 13, 0), 16, 19, 21,13, 4, 3000, 'B' );
        ship.getTrip(1).addAISMessage(ais6);
        ship.addAISMessage(ais6);
        ship.getTrip(1).endTrip();
        assertEquals(353.1,ship.getDistBetweenDates( LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0),  LocalDateTime.of(2019, Month.JANUARY, 1, 0, 0)),0.1 );
    }

    @Test
    public void testEquals() {
        Ship ship1 = new Ship(112,"122","test","Ship", 12, 1200, 600 , 21);
        Ship ship2 = new Ship(113,"132","test1","Ship1", 12, 1200, 600 , 21);
        Ship ship3 = new Ship(112,"122","test","Ship", 12, 1200, 600 , 21);
        assertTrue(ship1.equals(ship1));
        assertFalse(ship1.equals(null));
        assertFalse(ship1.equals("Ship"));
        assertFalse(ship1.equals(ship2));
        assertTrue(ship1.equals(ship3));

    }

    @Test
    public void testHashCode() {
        Ship ship1 = new Ship(112,"122","test","Ship", 12, 1200, 600 , 21);
        assertEquals( Objects.hash(112, "test", "Ship"),ship1.hashCode());
    }

    @Test
    public void compareTo() {
        Ship ship1 = new Ship(112,"122","test","Ship", 12, 1200, 600 , 21);
        Ship ship2 = new Ship(113,"132","test1","Ship1", 12, 1200, 600 , 21);
        Ship ship3 = new Ship(112,"122","test","Ship", 12, 1200, 600 , 21);
        assertEquals(-1,ship1.compareTo(ship2));
        assertEquals(0,ship1.compareTo(ship3));
        assertEquals(1,ship2.compareTo(ship1));
    }
}