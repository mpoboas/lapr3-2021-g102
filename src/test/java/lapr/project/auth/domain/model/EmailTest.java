package lapr.project.auth.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmailTest {
    Email email = new Email("shipcaptain@lei.sem3.pt");

    @Test
    void getEmail() {
        assertEquals("shipcaptain@lei.sem3.pt", this.email.getEmail());
    }

    @Test
    void testHashCode() {
        assertEquals(996541499, this.email.hashCode());
    }

    @Test
    void testEquals() {
        Email equalEmail = new Email("shipcaptain@lei.sem3.pt");
        assertEquals(this.email, equalEmail);
    }

    @Test
    void testToString() {
        assertEquals("shipcaptain@lei.sem3.pt", this.email.toString());
    }
}