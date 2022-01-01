package lapr.project.auth.domain.store;

import lapr.project.auth.domain.model.UserRole;
import lapr.project.utils.Constants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRoleStoreTest {
    UserRoleStore store;

    @Test
    void create() {
        store = new UserRoleStore();
        assertTrue(store.add(store.create("C", Constants.U_ROLE_CLIENT)));
        assertFalse(store.add(null));
    }


    @Test
    void remove() {
        store = new UserRoleStore();
        store.add(new UserRole("C", Constants.U_ROLE_CLIENT));
        assertFalse(store.remove(null));
        assertTrue(store.remove(store.getById("C").get()));
    }
}