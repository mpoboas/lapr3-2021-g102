package lapr.project.utils;

import lapr.project.model.AISMessage;
import lapr.project.model.Ship;
import lapr.project.model.Trips;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SortByMMSITravDistanceTest {

    @Test
    void compare() {
        Ship ex1 = new Ship(112, "122", "test", "Ship", 12, 1200, 600, 21);
        Ship ex2 = new Ship(110, "123", "test1", "Ship1", 12, 1200, 600, 21);
        Ship ex3 = new Ship(122, "124", "test2", "Ship2", 12, 1200, 600, 21);

        Trips exTrip1 = new Trips();
        ex1.addTrip(exTrip1);
        Trips exTrip2 = new Trips();
        ex2.addTrip(exTrip2);
        Trips exTrip3 = new Trips();
        ex3.addTrip(exTrip3);

        AISMessage exAis1 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 26, 2, 0), 13, 14, 21, 13, 4, 3000, 'B');
        AISMessage exAis2 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 27, 1, 0), 14, 15, 21, 13, 4, 3000, 'B');
        AISMessage exAis3 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 29, 13, 0), 16, 19, 21, 13, 4, 3000, 'B');
        ex1.getTrip(0).addAISMessage(exAis1);
        ex1.getTrip(0).addAISMessage(exAis2);
        ex1.addAISMessage(exAis1);
        ex1.addAISMessage(exAis2);
        ex2.getTrip(0).addAISMessage(exAis2);
        ex2.getTrip(0).addAISMessage(exAis3);
        ex2.addAISMessage(exAis2);
        ex2.addAISMessage(exAis2);
        ex3.getTrip(0).addAISMessage(exAis3);
        ex3.getTrip(0).addAISMessage(exAis1);
        ex3.addAISMessage(exAis3);
        ex3.addAISMessage(exAis1);

        List<Pair<Ship, Ship>> listOfPairs = new ArrayList<>();
        listOfPairs.add(new ImmutablePair<>(ex1, ex2));
        listOfPairs.add(new ImmutablePair<>(ex1, ex3));
        listOfPairs.add(new ImmutablePair<>(ex2, ex3));

        listOfPairs.sort(new SortByMMSITravDistance());
        List<Pair<Ship, Ship>> expected = new ArrayList<>();
        expected.add(new ImmutablePair<>(ex2, ex3));
        expected.add(new ImmutablePair<>(ex1, ex2));
        expected.add(new ImmutablePair<>(ex1, ex3));
        assertEquals(expected, listOfPairs);
    }
}