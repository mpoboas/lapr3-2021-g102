package lapr.project.auth.domain.store;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserStoreTest {
    UserStore store = new UserStore();

    @Test
    void create() {
        assertFalse(store.add(null));
        assertTrue(store.add(store.create("Test","test@isep.ipp.pt","testPasword")));
    }


    @Test
    void remove() {
        store.add(store.create("Test","test@isep.ipp.pt","testPasword"));
        assertFalse(store.remove(null));
        assertTrue(store.remove(store.getById("test@isep.ipp.pt").get()));

    }
}