package Demos;

import lapr.project.controller.App;
import lapr.project.controller.AuthController;
import lapr.project.controller.GetShipIdleDaysController;
import lapr.project.data.ConnectionFactory;
import lapr.project.data.DatabaseConnection;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Year;
import java.util.Scanner;

public class US404Demo {
    static Scanner scan = new Scanner(System.in);
    static GetShipIdleDaysController getShipIdleDaysController = new GetShipIdleDaysController();

    public static void main(String[] args) throws IOException, SQLException {
        DatabaseConnection databaseConnection = ConnectionFactory.getInstance().getDatabaseConnection();
        System.out.println("Enter email: (e.g. sergiocosta15@lei.sem3.pt)");
        String email = scan.next();
        System.out.println("Enter password: ");
        String password = scan.next();
        AuthController authController = new AuthController();

        if (!authController.doLogin(email, password)) System.out.println("Invalid Login Credentials.");
        else System.out.println(getShipIdleDaysController.getEachShipNumberOfIdleDays(databaseConnection, App.getInstance().getCurrentUserSession().getUserName(), Year.now().getValue()));
    }
}
