package lapr.project.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @Test
    void getInstance() {
        assertNotNull(App.getInstance());
    }

    @Test
    void getCompany() {
        App app = App.getInstance();
        assertNotNull(app.getCompany());

    }


}