package lapr.project.controller;

import lapr.project.data.ConnectionFactory;
import lapr.project.data.ShipStoreDB;
import lapr.project.model.Ship;
import lapr.project.utils.Constants;
import lapr.project.utils.Pair;
import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetUnladenCenterOfMassControllerTest {

    @Test
    void getCenterOfMassOfAShip(){
        ConnectionFactory cf = mock(ConnectionFactory.class);
        ShipStoreDB storeDB = mock(ShipStoreDB.class);
        GetUnladenCenterOfMassController ctrl = new GetUnladenCenterOfMassController(storeDB,cf);
        int mmsi = 366971340;
        try {
            when(storeDB.getShipByMMSI(cf.getDatabaseConnection(), mmsi)).thenReturn(Constants.CASON_J_CALLAWAY);
            assertTrue(ctrl.getShipInfo(mmsi));
            Pair<Point2D.Double,Double> result = ctrl.getCenterOfMassOfAShip("BOW");
            assertEquals(0,result.getFirst().x);
            assertEquals(110.227125,result.getFirst().y);
            assertEquals(2.72,result.getSecond());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ctrl = new GetUnladenCenterOfMassController(storeDB,cf);
        assertTrue(ctrl.getShipInfo(Constants.CASON_J_CALLAWAY));
        Pair<Point2D.Double,Double> result = ctrl.getCenterOfMassOfAShip("BOW");
        assertEquals(0,result.getFirst().x);
        assertEquals(110.227125,result.getFirst().y);
        assertEquals(2.72,result.getSecond());

        ctrl = new GetUnladenCenterOfMassController(storeDB,cf);
        assertTrue(ctrl.getShipInfo(new Ship(123,"SHIP","SHIPIMO","CALLSIGN",0,400,62,13.5)));
        try{
            ctrl.getCenterOfMassOfAShip("BOW");
        }catch (Exception ex){
            assertEquals(IllegalStateException.class,ex.getClass());
            assertEquals("The ship does not have the necessary info",ex.getMessage());
        }
    }
}