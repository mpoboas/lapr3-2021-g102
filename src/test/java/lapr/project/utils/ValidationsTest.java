package lapr.project.utils;

import lapr.project.model.AISMessage;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidationsTest {

    @Test
    void validateAISMessageForCalculations() {
        AISMessage ais1 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 26, 2, 0), 13, 14, 21, 13, 4, 3000, 'B');
        assertTrue(Validations.validateAISMessageForCalculations(ais1));
        AISMessage aisLat91 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 26, 2, 0), 91, 14, 21, 13, 4, 3000, 'B');
        assertFalse(Validations.validateAISMessageForCalculations(aisLat91));
        AISMessage aisLong181 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 26, 2, 0), 13, 181, 21, 13, 4, 3000, 'B');
        assertFalse(Validations.validateAISMessageForCalculations(aisLong181));
        AISMessage aisHeading511 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 26, 2, 0), 13, 14, 21, 13, 511, 3000, 'B');
        assertFalse(Validations.validateAISMessageForCalculations(aisHeading511));
    }
}