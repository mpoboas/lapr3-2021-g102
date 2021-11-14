package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompanyTest {

    @Test
    void getShipStore() {
        Company comp = new Company("Company");
        assertNotNull(comp.getShipStore());
    }
}