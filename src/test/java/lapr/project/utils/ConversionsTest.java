package lapr.project.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConversionsTest {

    @Test
    public void nauticalMilesToKm() {
        double valueInNauticalMiles = 1;

        assertEquals(1.852, Conversions.nauticalMilesToKm(1));
    }
}