package lapr.project.controller;

import lapr.project.mappers.dto.ShipDTO;
import lapr.project.model.Import;
import lapr.project.stores.ShipStore;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;

class SearchShipControllerTest {
    ShipStore store = new ShipStore();
    Import imp = new Import(store);

    @Test
    void searchShip() {
        SearchShipController ctrl = new SearchShipController(store);
        try {
            imp.importShipsData("data/data-shipsports/data-shipsports/data-ships&ports/sships.csv");
            imp.addShips();
            //System.out.println(store.getListOfShipsASCOrd());

        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue(ctrl.searchShip("212351000"));
        assertTrue(ctrl.searchShip("IMO9395044"));
        assertTrue(ctrl.searchShip("WDG5171"));

        try {
            ctrl.searchShip("123");
        }catch (Exception e){
            assertEquals("The ship with the MMSI: 123 was not found",e.getMessage());
        }
        try {
            ctrl.searchShip("IMO1223");
        }catch (Exception e){
            assertEquals("The ship with the IMO: IMO1223 was not found",e.getMessage());
        }
        try {
            ctrl.searchShip("I1223");
        }catch (Exception e){
            assertEquals("The ship with the Call Sign: I1223 was not found",e.getMessage());
        }
        try {
            ctrl.searchShip("IM1223");
        }catch (Exception e){
            assertEquals("The ship with the Call Sign: IM1223 was not found",e.getMessage());
        }
        try {
            ctrl.searchShip("PQO1223");
        }catch (Exception e){
            assertEquals("The ship with the Call Sign: PQO1223 was not found",e.getMessage());
        }
        try {
            ctrl.searchShip("JMO1223");
        }catch (Exception e){
            assertEquals("The ship with the Call Sign: JMO1223 was not found",e.getMessage());
        }
        try {
            ctrl.searchShip("OMI1223");
        }catch (Exception e){
            assertEquals("The ship with the Call Sign: OMI1223 was not found",e.getMessage());
        }
        try {
            ctrl.searchShip("AB123");
        }catch (Exception e){
            assertEquals("The ship with the Call Sign: AB123 was not found",e.getMessage());
        }
    }
}