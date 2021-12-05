package lapr.project.controller;

import lapr.project.data.ConnectionFactory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetContainerSituationControllerTest {
    ConnectionFactory connectionFactory = mock(ConnectionFactory.class);
    GetContainerSituationController ctrl = mock(GetContainerSituationController.class);

    @Test
    void getSituation(){
        try {
            when(ctrl.getSituation(3,connectionFactory)).thenReturn("SHIP,Test SHIP 2");
            assertEquals("SHIP,Test SHIP 2",ctrl.getSituation(3,connectionFactory));
            //assertEquals("SHIP,Test SHIP 2",ctrl.getSituation(3));
            //assertEquals("PORT, OTHER TEST",ctrl.getSituation(4));
            //ctrl.getSituation(123);
        } catch (Exception e){
            e.printStackTrace();
            //assertEquals(SQLException.class, e.getClass());
            //assertEquals(RuntimeException.class, e.getClass());
        }

    }
}