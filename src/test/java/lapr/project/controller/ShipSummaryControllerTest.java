package lapr.project.controller;

import lapr.project.model.AISMessage;
import lapr.project.model.Import;
import lapr.project.model.Ship;
import lapr.project.model.Trips;
import lapr.project.stores.ShipStore;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShipSummaryControllerTest {
    ShipStore store = new ShipStore();
    Import imp = new Import(store);


    @Test
    public void testPositionalMessagesController() {
        ShipSummaryController ctrl = new ShipSummaryController(store);

        try {
            imp.importShipsData("data/data-shipsports/data-shipsports/data-ships&ports/sships.csv");
            imp.addShips();
            //System.out.println(store.getListOfShipsASCOrd());

        } catch (IOException e) {
            e.printStackTrace();
        }

        Ship testShip = ctrl.getShip("210950000");
        List<AISMessage> aisMessagesList = testShip.getAisMessagesList();

        Ship ship;
        AISMessage ais1;
        AISMessage ais2;
        AISMessage ais3;

        ship = new Ship(112,"122","test","Ship", 12, 1200, 600 , 21);

        ais1 = new AISMessage( LocalDateTime.of(2018, Month.JUNE, 26, 1, 43), 42.69577, -66.97808, -54.8,13.7, 357, 3000, 'B' );
        ship.addAISMessage(ais1);
        ais2 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 26, 2, 23), 50.71055, -66.97776, 1.4,13.5, 358, 3000, 'B' );
        ship.addAISMessage(ais2);
        ais3 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 26, 3, 59), 42.73879, -66.97726, 3.4,13.4, 357, 3000, 'B' );
        ship.addAISMessage(ais3);


        System.out.println(ship.getAisMessagesList());
        ctrl.getShipSummary(ship);
        /*String expected = "Ship's Summary{MMSI=112, Vessel Name='122', Start Base Date Time='2018-06-26T01:43', End Base Date Time='2018-06-26T03:59', Total Movement Time=2.0, Total Number of Movements=3.0, Max SOG=13.7, Max COG=3.4, Mean SOG=13.533333333333333, Mean COG=13.533333333333333, Total Distance=959.8318622227812, Delta Distance=2.583174067017685, Departure Latitude=42.69577, Departure Longitude=-66.97808, Arrival Latitude=42.73879, Arrival Longitude=-66.97726}";
        assertEquals(expected,ctrl.getShipSummary(ship));
        assertTrue(ctrl.getShipSummary(ship).equals(expected));*/
    }
}
