package Demos;

import lapr.project.controller.BuildFreightNetworkController;
import lapr.project.data.ConnectionFactory;
import lapr.project.data.DatabaseConnection;

import java.io.IOException;
import java.util.Scanner;

public class US301Demo {
    static Scanner scan = new Scanner(System.in);
    static BuildFreightNetworkController buildFreightNetworkController = new BuildFreightNetworkController();

    public static void main(String[] args) {
        try {
            DatabaseConnection databaseConnection = ConnectionFactory.getInstance().getDatabaseConnection();
            System.out.println("Enter the number of closest ports: ");
            int numClosestPorts = scan.nextInt();
            buildFreightNetworkController.buildFreightNetworkFromDatabase(databaseConnection, numClosestPorts);

            System.out.println(buildFreightNetworkController.getFreightNetworkGraph());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
