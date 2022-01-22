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
    public static void main(String[] args) throws IOException, SQLException {
        Scanner scan = new Scanner(System.in);
        GetShipIdleDaysController getShipIdleDaysController = new GetShipIdleDaysController();
        DatabaseConnection databaseConnection = ConnectionFactory.getInstance().getDatabaseConnection();
        System.out.println("Enter email: (e.g. sergiocosta15@lei.sem3.pt)");
        String email = scan.next();
        System.out.println("Enter password: ");
        String password = scan.next();
        AuthController authController = new AuthController();

        if (!authController.doLogin(email, password)) System.out.println("Invalid Login Credentials.");
        else {
            String res;
            do {
                int year = -1;
                System.out.println("\nPlease choose an option: ");
                System.out.println("1 - Get the number of idle days for the current year");
                System.out.println("2 - Get the number of idle days for a given year");
                int option = scan.nextInt();

                switch (option) {
                    case 1: year = Year.now().getValue();
                        break;
                    case 2: System.out.println("Type a year: ");
                        year = scan.nextInt();
                        break;
                    default:
                        System.out.println("Invalid Option.");
                        break;
                }
                if (year != -1) System.out.println("\n" + getShipIdleDaysController.getEachShipNumberOfIdleDays(databaseConnection, App.getInstance().getCurrentUserSession().getUserName(), year));

                System.out.println("Do you want to repeat the process? (y/n)");
                res = scan.next();
                System.out.println();
            } while (res.equals("y"));
        }
    }
}
