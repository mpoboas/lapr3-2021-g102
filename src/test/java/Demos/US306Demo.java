package Demos;

import lapr.project.controller.AuthController;
import lapr.project.controller.GetWarehouseOccupancyRateController;
import lapr.project.data.ConnectionFactory;
import lapr.project.data.DatabaseConnection;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class US306Demo {
    static Scanner scan = new Scanner(System.in);
    static GetWarehouseOccupancyRateController getWarehouseOccupancyRateController = new GetWarehouseOccupancyRateController();

    public static void main(String[] args) throws IOException, SQLException {
        DatabaseConnection databaseConnection = ConnectionFactory.getInstance().getDatabaseConnection();
        System.out.println("Enter email: ");
        String email = scan.next();
        System.out.println("Enter password: ");
        String password = scan.next();
        AuthController authController = new AuthController();
        if (!authController.doLogin(email, password)) System.out.println("Invalid Login Credentials.");
        else {
            System.out.println("Enter your port manager identification number (e.g. 1): ");
            int portManagerId = scan.nextInt();
            System.out.println(getWarehouseOccupancyRateController.getEachWarehouseOccupancyRateAndContainersLeaving30Days(databaseConnection, portManagerId));
        }
    }
}
