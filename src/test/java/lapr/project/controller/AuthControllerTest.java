package lapr.project.controller;

import lapr.project.auth.mappers.dto.UserRoleDTO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuthControllerTest {
    AuthController controller;

    @Test
    void doLogin() {
        controller = new AuthController();

        assertFalse(controller.doLogin("randomemailmail.com", "123322"));
        assertTrue(controller.doLogin("shipcaptain@lei.sem3.pt", "123456"));

    }

    @Test
    void getUserRoles() {
        controller = new AuthController();
        List<UserRoleDTO> nullList = controller.getUserRoles();
        assertNull(nullList);
        controller.doLogin("shipcaptain@lei.sem3.pt", "123456");

        assertEquals("SHIP CAPTAIN",controller.getUserRoles().get(0).getDescription());


    }

    @Test
    void doLogout() {
        controller = new AuthController();
        controller.doLogin("shipcaptain@lei.sem3.pt", "123456");
        controller.doLogout();
    }
}