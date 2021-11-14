package lapr.project.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PairTest {
    Pair<String, String> testPair = new Pair<>("Test1", "Test2");
    @Test
    void getFirst() {
        assertEquals("Test1", testPair.getFirst());
    }

    @Test
    void getSecond() {
        assertEquals("Test2", testPair.getSecond());
    }

    @Test
    void testEquals() {
        Pair<String, String> testPairEquals = new Pair<>("Test1", "Test2");
        assertEquals(testPair, testPairEquals);
    }

    @Test
    void testToString() {
        assertEquals("(Test1, Test2)", testPair.toString());
    }
}