package lapr.project.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


class AISMessageTest {

    @Test
    void createAndAttributes(){
        AISMessage ais1 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 26, 2, 0), 13, 14, 21, 13, 4, 3000, 'B');
        //AISMessage ais2 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 27, 1, 0), 14, 15, 21, 13, 4, 3000, 'B');
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy H:mm");
        LocalDateTime baseDateTime = LocalDateTime.parse("26/06/2018 2:00", dtf);
        assertEquals(baseDateTime,ais1.getBaseDateTime());
        assertEquals(13.0,ais1.getCoordinates().getLatitude(),0);
        assertEquals(14.0,ais1.getCoordinates().getLongitude(),0);
        assertEquals(21.0,ais1.getCOG(),0);
        assertEquals(13.0,ais1.getSOG(),0);
        assertEquals(4,ais1.getHeading());
        assertEquals(3000,ais1.getCargo());
        assertEquals('B',ais1.getTransceiverClass());
    }

    @Test
    void testEquals() {
        AISMessage ais1 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 26, 2, 0), 13, 14, 21, 13, 4, 3000, 'B');
        AISMessage ais2 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 27, 1, 0), 14, 15, 21, 13, 4, 3000, 'B');
        AISMessage ais3 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 26, 2, 0), 13, 14, 21, 13, 4, 3000, 'B');
        assertTrue(ais1.equals(ais1));
        assertFalse(ais1.equals(null));
        assertFalse(ais1.equals("AisMessage"));
        assertFalse(ais1.equals(ais2));
        assertTrue(ais1.equals(ais3));
    }

    @Test
    void testHashCode() {
        AISMessage ais1 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 26, 2, 0), 13, 14, 21, 13, 4, 3000, 'B');
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy H:mm");
        LocalDateTime baseDateTime = LocalDateTime.parse("26/06/2018 2:00", dtf);
        assertEquals(Objects.hash(baseDateTime),ais1.hashCode());

    }

    @Test
    void compareTo() {
        AISMessage ais1 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 26, 2, 0), 13, 14, 21, 13, 4, 3000, 'B');
        AISMessage ais2 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 27, 1, 0), 14, 15, 21, 13, 4, 3000, 'B');
        AISMessage ais3 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 26, 2, 0), 13, 14, 21, 13, 4, 3000, 'B');
        assertEquals(-1,ais1.compareTo(ais2));
        assertEquals(0,ais1.compareTo(ais3));
        assertEquals(1,ais2.compareTo(ais1));
    }

    @Test
    void testToString() {
        AISMessage ais1 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 26, 2, 0), 13, 14, 21, 13, 4, 3000, 'B');
        assertEquals("AISMessage{baseDateTime=2018-06-26T02:00, latitude=13.0, longitude=14.0, COG=21.0, SOG=13.0, heading=4}\n",ais1.toString());
    }
}