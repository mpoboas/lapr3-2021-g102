package lapr.project.controller;

import lapr.project.model.Import;
import lapr.project.stores.ShipStore;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        assertEquals("Ship{MMSI=212351000, vesselName='HYUNDAI SINGAPORE', IMO='IMO9305685', callSign='5BZP3', vesselType=70, length=303, width=40, draft=14.5}\n" +
                "[AISMessage{baseDateTime=2020-12-31T23:03, latitude=55.09307, longitude=-167.63625, COG=3.5, SOG=-61.6, heading=232, cargo=79, transceiver class=B}\n" +
                "]",ctrl.searchShip("212351000").toString());
        assertEquals("Ship{MMSI=636092932, vesselName='MSC ILONA', IMO='IMO9225641', callSign='D5VK6', vesselType=79, length=299, width=40, draft=11.8}\n" +
                "[AISMessage{baseDateTime=2020-12-31T12:06, latitude=26.52605, longitude=-78.76731, COG=0.0, SOG=-189.6, heading=220, cargo=79, transceiver class=A}\n" +
                ", AISMessage{baseDateTime=2020-12-31T14:32, latitude=26.526, longitude=-78.76736, COG=0.0, SOG=-190.6, heading=219, cargo=79, transceiver class=A}\n" +
                ", AISMessage{baseDateTime=2020-12-31T18:49, latitude=26.52601, longitude=-78.76733, COG=0.0, SOG=-190.6, heading=219, cargo=79, transceiver class=A}\n" +
                ", AISMessage{baseDateTime=2020-12-31T19:01, latitude=26.52603, longitude=-78.76731, COG=0.0, SOG=-190.6, heading=219, cargo=79, transceiver class=A}\n" +
                ", AISMessage{baseDateTime=2020-12-31T21:20, latitude=26.52601, longitude=-78.76737, COG=0.0, SOG=-190.6, heading=219, cargo=79, transceiver class=A}\n" +
                "]",ctrl.searchShip("IMO9225641").toString());
        assertEquals("Ship{MMSI=636091400, vesselName='RHL AGILITAS', IMO='IMO9373486', callSign='A8ND5', vesselType=70, length=176, width=27, draft=10.9}\n" +
                "[AISMessage{baseDateTime=2020-12-31T18:26, latitude=23.07187, longitude=-83.53367, COG=15.9, SOG=66.2, heading=68, cargo=71, transceiver class=B}\n" +
                "]",ctrl.searchShip("A8ND5").toString());

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