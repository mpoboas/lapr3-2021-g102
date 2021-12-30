package Demos;

import lapr.project.controller.AuthController;
import lapr.project.controller.GetClientContainerRouteController;
import lapr.project.data.ConnectionFactory;
import lapr.project.data.DatabaseConnection;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class US305Demo {
    static Scanner scan = new Scanner(System.in);
    static GetClientContainerRouteController getClientContainerRouteController = new GetClientContainerRouteController();

    public static void main(String[] args) throws IOException, SQLException {
        DatabaseConnection databaseConnection = ConnectionFactory.getInstance().getDatabaseConnection();
        System.out.println("Enter email: ");
        String email = scan.next();
        System.out.println("Enter password: ");
        String password = scan.next();
        AuthController authController = new AuthController();
        if (!authController.doLogin(email, password)) System.out.println("Invalid Login Credentials.");
        else {
            System.out.println("Enter your client identification number (3): ");
            int clientId = scan.nextInt();
            System.out.println("Enter the container identification code (LPRU1628240): ");
            String containerId = scan.next();
            System.out.println(getClientContainerRouteController.getClientContainerRoute(databaseConnection, clientId, containerId));
        }
    }
}
