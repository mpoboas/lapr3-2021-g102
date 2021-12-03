package lapr.project.auth.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordTest {
    Password password = new Password("123456");

    @Test
    void checkPassword() {
        this.password.checkPassword("123456");
    }

    @Test
    void testHashCode() {
        assertNotEquals("", this.password.hashCode());
    }

    @Test
    void testEquals() {
        Password equalPassword = new Password("123456");
        this.password.equals(equalPassword);
    }
}