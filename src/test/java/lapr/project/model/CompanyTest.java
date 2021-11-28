package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompanyTest {

    @Test
    void getShipStoreTest() {
        Company comp = new Company("Company");
        assertNotNull(comp.getShipStore());
    }

    @Test
    void getPortStoreTest(){
        Company comp = new Company("Company");
        assertNotNull(comp.getPortStore());
    }
}