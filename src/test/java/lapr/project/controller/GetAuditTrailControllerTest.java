package lapr.project.controller;

import lapr.project.data.AuditTrailDB;
import lapr.project.data.ConnectionFactory;
import org.junit.jupiter.api.Test;

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
}