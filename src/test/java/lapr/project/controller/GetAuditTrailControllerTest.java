package lapr.project.controller;

import lapr.project.data.AuditTrailDB;
import lapr.project.data.ConnectionFactory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetAuditTrailControllerTest {

    @Test
    void getAuditTrails() {
        ConnectionFactory cf = mock(ConnectionFactory.class);
        AuditTrailDB auditTrailDB = mock(AuditTrailDB.class);
        GetAuditTrailController ctrl = new GetAuditTrailController(auditTrailDB,cf);
        String containerID = "ABC123";
        int cargoManifestID = 123;
        List<String> list = new ArrayList<>();
        list.add("Audit1");
        try {
            when(auditTrailDB.getAuditTrails(containerID,cargoManifestID,cf)).thenReturn(list);
            assertNotNull(ctrl.getAuditTrails(containerID,cargoManifestID));
            assertEquals(list,ctrl.getAuditTrails(containerID,cargoManifestID));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
/*
    @Test
    void getAuditTrails2() throws IOException {
        ConnectionFactory cf = ConnectionFactory.getInstance();
        AuditTrailDB auditTrailDB = new AuditTrailDB();
        GetAuditTrailController ctrl = new GetAuditTrailController(auditTrailDB,cf);
        String idContainer ="LPRU5603455";
        int idCargoManifest=8;
        try{
            List<String> res = ctrl.getAuditTrails(idContainer,idCargoManifest);
        for (String s: res) {
            System.out.println(s);
        }
        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
    }*/
}