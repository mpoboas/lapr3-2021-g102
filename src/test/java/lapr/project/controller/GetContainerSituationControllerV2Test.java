package lapr.project.controller;

import lapr.project.data.ConnectionFactory;
import lapr.project.data.ContainerSituationDB;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetContainerSituationControllerV2Test {

    @Test
    void getSituation2Test1() {
        ConnectionFactory cf = mock(ConnectionFactory.class);
        ContainerSituationDB containerSituationDB = mock(ContainerSituationDB.class);
        GetContainerSituationControllerV2 ctrl = new GetContainerSituationControllerV2(containerSituationDB,cf);
        App.getInstance().doLogin("client@lei.sem3.pt", "123456");
        String containerID = "ABC123";
        try {
            when(containerSituationDB.getSituation2(containerID,App.getInstance().getCurrentUserSession().getUserName(),cf)).thenReturn("Leixões,PORT");
            assertNotNull(ctrl.getSituation2(containerID));
            assertEquals("Leixões,PORT",ctrl.getSituation2(containerID));
            App.getInstance().doLogout();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    void getSituation2Test2() {
        ConnectionFactory cf = mock(ConnectionFactory.class);
        ContainerSituationDB containerSituationDB = mock(ContainerSituationDB.class);
        GetContainerSituationControllerV2 ctrl = new GetContainerSituationControllerV2(containerSituationDB,cf);
        App.getInstance().doLogin("portmanager@lei.sem3.pt", "123456");
        String containerID = "ABC123";
        try{
            ctrl.getSituation2(containerID);
        }catch (Exception ex){
            assertEquals(IllegalStateException.class, ex.getClass());
            assertEquals("Not authorized", ex.getMessage());
        }
    }
}