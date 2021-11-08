package lapr.project.stores;

import lapr.project.model.AISMessage;
import lapr.project.model.Ship;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShipStoreTest {

    @Test
    void createShip() {
        ShipStore store = new ShipStore();
        Ship ex1 =  new Ship(112,"122","test","Ship", 12, 1200, 600 , 21);
        Ship ex2 = new Ship(113,"122","test","Ship", 12, 1200, 600 , 21);
        Ship res = store.createShip(112,"122","test","Ship", 12, 1200, 600 , 21);

        assertEquals(ex1,res);
    }

    @Test
    void validateShip() {
        ShipStore store = new ShipStore();
        Ship ex1 =  new Ship(112,"122","test","Ship", 12, 1200, 600 , 21);
        assertTrue(store.validateShip(ex1));
        store.addShip(ex1);
        assertFalse(store.validateShip(null));
    }

    @Test
    void addShip() {
        ShipStore store = new ShipStore();
        Ship ex1 =  new Ship(112,"122","test","Ship", 12, 1200, 600 , 21);
        //Ship ex2 =  new Ship(110,"122","test","Ship", 12, 1200, 600 , 21);
        //Ship ex3 =  new Ship(122,"122","test","Ship", 12, 1200, 600 , 21);
        assertTrue(store.addShip(ex1));
        //assertTrue(store.addShip(ex2));
        //assertTrue(store.addShip(ex3));
        //assertFalse(store.addShip(ex1));
        ex1.addAISMessage( new AISMessage( LocalDateTime.of(2018, Month.JUNE, 26, 2, 0), 13, 14, 21,13, 4, 3000, 'B' ));
        //store.addShip(ex1);
        //assertTrue(store.addShip(ex1));
    }

    @Test
    void getListOfShipsASCOrd() {
        /*
        ShipStore store = new ShipStore();
        Ship ex1 =  new Ship(112,"122","test","Ship", 12, 1200, 600 , 21);
        Ship ex2 =  new Ship(110,"122","test","Ship", 12, 1200, 600 , 21);
        Ship ex3 =  new Ship(122,"122","test","Ship", 12, 1200, 600 , 21);
        store.addShip(ex1);
        store.addShip(ex2);
        store.addShip(ex3);
        List<Ship> expected = new ArrayList<>();
        expected.add(ex2);
        expected.add(ex1);
        expected.add(ex3);
        assertEquals(expected,store.getListOfShipsASCOrd());*/

    }

    @Test
    void getShipDuplicated() {
        ShipStore store = new ShipStore();
        Ship ex1 =  new Ship(112,"122","test","Ship", 12, 1200, 600 , 21);
        store.addShip(ex1);
        Ship res = store.getShipDuplicated(ex1);
        assertEquals(res,ex1);

    }

    @Test
    void getShipByMMSI() {
        ShipStore store = new ShipStore();
        Ship ex1 =  new Ship(112,"122","test","Ship", 12, 1200, 600 , 21);
        store.addShip(ex1);
        Ship res = store.getShipByMMSI(112);
        assertEquals(res,ex1);

        try {
            store.getShipByMMSI(111);
        } catch (Exception e){
            assertEquals("The ship with the MMSI: 111 was not found", e.getMessage());
        }
    }

    @Test
    void getShipByIMO() {
        ShipStore store = new ShipStore();
        Ship ex1 =  new Ship(112,"122","test","Ship", 12, 1200, 600 , 21);
        store.addShip(ex1);
        Ship res = store.getShipByIMO("test");;
        assertEquals(res,ex1);

        try {
            store.getShipByIMO("test2");
        } catch (Exception e){
            assertEquals("The ship with the IMO: test2 was not found", e.getMessage());
        }
    }

    @Test
    void getShipByCallSign() {
        ShipStore store = new ShipStore();
        Ship ex1 =  new Ship(112,"122","test","Ship", 12, 1200, 600 , 21);
        store.addShip(ex1);
        Ship res = store.getShipByCallSign("Ship");;
        assertEquals(res,ex1);

        try {
            store.getShipByCallSign("Ship2");
        } catch (Exception e){
            assertEquals("The ship with the Call Sign: Ship2 was not found", e.getMessage());
        }
    }
}