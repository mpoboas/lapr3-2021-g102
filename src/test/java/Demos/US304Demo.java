package Demos;

import lapr.project.controller.GetAuditTrailController;
import lapr.project.data.AuditTrailDB;
import lapr.project.data.ConnectionFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class US304Demo {

    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        ConnectionFactory cf = ConnectionFactory.getInstance();
        AuditTrailDB auditTrailDB = new AuditTrailDB();
        GetAuditTrailController ctrl = new GetAuditTrailController(auditTrailDB, cf);
        String res;
        do {
            System.out.println("Audit trails for a given container of a given cargo manifest:");
            System.out.println("Insert Container Identification (e.g. LPRU5603455)");
            String containerID = scan.next();
            System.out.println("Insert Cargo Manifest (e.g. 8)");
            int cargoManifestID = scan.nextInt();

            List<String> auditTrails = new ArrayList<>();
            try {
                auditTrails = ctrl.getAuditTrails(containerID, cargoManifestID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (!auditTrails.isEmpty()) {
                for (String s : auditTrails) {
                    System.out.println(s);
                }
            } else {
                System.out.println("No audit trails found of container: " + containerID + " of cargo manifest: " + cargoManifestID);
            }

            System.out.println("Do you want to search more? (y/n)");
            res = scan.next();
        } while (res.equals("y"));
        System.out.println("Operation ended");

    }
}
