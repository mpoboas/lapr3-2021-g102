package Demos;

import lapr.project.controller.AuthController;
import lapr.project.controller.GetShipOccupancyRateController;
import lapr.project.data.ConnectionFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class US208209Demo {
    static Scanner scan = new Scanner(System.in);
    static GetShipOccupancyRateController controller;

    public static void main(String[] args) throws IOException, SQLException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        System.out.println("Enter email: ");
        String email = scan.next();
        System.out.println("Enter password: ");
        String password = scan.next();
        AuthController authController = new AuthController();
        if (!authController.doLogin(email, password)) System.out.println("Invalid Login Credentials.");
        else {
            double shipOccupancyRate = 0;
            System.out.println("Enter Ship MMSI: ");
            int shipMMSI = scan.nextInt();
            System.out.println("In which format do you want to enter the cargo manifest? ");
            System.out.println("1 - Id");
            System.out.println("2 - Date and Time");
            int choice = scan.nextInt();
            controller = new GetShipOccupancyRateController();

            switch (choice) {
                case 1:
                    System.out.println("Enter id: ");
                    int cargoManifestId = scan.nextInt();
                    shipOccupancyRate = controller.getShipOccupancyRateById(connectionFactory, shipMMSI, cargoManifestId);
                    break;
                case 2:
                    System.out.println("Enter date and time: ");
                    String localDateTimeStr = scan.next();
                    shipOccupancyRate = controller.getShipOccupancyRateByDateTime(connectionFactory, shipMMSI, localDateTimeStr);
                    break;
                default:
                    System.out.println("Invalid format.");
            }

            System.out.println("Ship Id: " + controller.getShipId());
            System.out.println("Cargo Manifest Id: " + controller.getCargoManifestId());
            System.out.println("Ship Occupancy Rate: " + shipOccupancyRate);
        }
    }
}
