package lapr.project.controller;

import lapr.project.model.AISMessage;
import lapr.project.model.Import;
import lapr.project.model.Ship;
import lapr.project.stores.ShipStore;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class PositionalMessagesControllerTest {
    ShipStore store = new ShipStore();
    Import imp = new Import(store);


    @Test
    public void testPositionalMessagesController() {
        PositionalMessagesController ctrl = new PositionalMessagesController(store);

        try {
            imp.importShipsData("data/data-shipsports/data-shipsports/data-ships&ports/sships.csv");
            imp.addShips();
            //System.out.println(store.getListOfShipsASCOrd());

        } catch (IOException e) {
            e.printStackTrace();
        }

        Ship testShip = ctrl.getShip("210950000");
        List<AISMessage> aisMessagesList = testShip.getAisMessagesList();
        List<Ship> list = imp.getListOfImportedShips();

        //GET SHIP
        assertEquals(testShip, ctrl.getShip("210950000"));
        assertEquals(testShip, ctrl.getShip("IMO9395044"));
        assertEquals(testShip, ctrl.getShip("C4SQ2"));

        //AT DATE
        List<AISMessage> atDate = new ArrayList<>();
        LocalDateTime date = LocalDateTime.of(2020, Month.DECEMBER, 31, 2, 0);
        for (AISMessage msg : testShip.getAisMessagesList()) {
            if (msg.getBaseDateTime().getDayOfMonth() == date.getDayOfMonth() && msg.getBaseDateTime().getMonthValue() == date.getMonthValue())
                atDate.add(msg);
        }

        assertEquals(atDate, ctrl.getPositionalMessagesAtDate(testShip, LocalDateTime.of(2020, Month.DECEMBER, 31, 2, 0)));

        //BETWEEN TWO DATES
        List<AISMessage> betweenTwoDates = new ArrayList<>();
        LocalDateTime startDateTime = LocalDateTime.of(2020, Month.DECEMBER, 31, 11, 59);
        LocalDateTime endDateTime = LocalDateTime.of(2020, Month.DECEMBER, 31, 22, 59);
        for (AISMessage msg : testShip.getAisMessagesList()) {
            if (msg.getBaseDateTime().isAfter(startDateTime) && msg.getBaseDateTime().isBefore(endDateTime))
                betweenTwoDates.add(msg);
        }

        assertEquals(betweenTwoDates, ctrl.getPositionalMessagesBetweenTwoDates(testShip, LocalDateTime.of(2020, Month.DECEMBER, 31, 11, 59), LocalDateTime.of(2020, Month.DECEMBER, 31, 22, 59)));
    }
}
