package lapr.project.controller;

import lapr.project.data.ConnectionFactory;
import lapr.project.data.TripStore;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetVoyagesBelowThresholdControllerTest {

    @Test
    void getShipVoyagesBellowThreshold() {
        ConnectionFactory cf = mock(ConnectionFactory.class);
        TripStore tripStore = mock(TripStore.class);
        GetVoyagesBelowThresholdController ctrl = new GetVoyagesBelowThresholdController(tripStore,cf);
        App.getInstance().doLogin("fleetmanager@lei.sem3.pt", "123456");
        int mmsi = 111111111;
        double threshold = 66;
        try {
            List<String> result = new ArrayList<>();
            result.add("Start Date: 5/1/2021 | Start Place ID: 123 | Start Place Name: Leixões | End Date: 6/1/2021 | End Place ID: 321 | End Place Name: seõxieL");
            when(tripStore.getShipVoyagesBelowThreshold(mmsi,threshold,cf)).thenReturn(result);
            assertNotNull(ctrl.getShipVoyagesBellowThreshold(mmsi,threshold));
            assertEquals(result,ctrl.getShipVoyagesBellowThreshold(mmsi,threshold));
            App.getInstance().doLogout();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    void getShipVoyagesBellowThreshold2() {
        ConnectionFactory cf = mock(ConnectionFactory.class);
        TripStore tripStore = mock(TripStore.class);
        GetVoyagesBelowThresholdController ctrl = new GetVoyagesBelowThresholdController(tripStore,cf);
        App.getInstance().doLogin("portmanager@lei.sem3.pt", "123456");
        int mmsi = 11111111;
        double threshold = 66;
        try{
            ctrl.getShipVoyagesBellowThreshold(mmsi,threshold);
        }catch (Exception ex){
            assertEquals(IllegalStateException.class, ex.getClass());
            assertEquals("Not authorized", ex.getMessage());
        }
    }
}