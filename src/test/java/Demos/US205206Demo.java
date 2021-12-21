package Demos;

import lapr.project.controller.GetContainersToBeLoadedOffloadedController;
import lapr.project.data.ConnectionFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class US205206Demo {
    static Scanner scan = new Scanner(System.in);
    static GetContainersToBeLoadedOffloadedController ctrl = new GetContainersToBeLoadedOffloadedController();
    static ConnectionFactory connection;

    static {
        try {
            connection = new ConnectionFactory();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        System.out.println("Enter a Vehicle ID:");
        int idShip = scan.nextInt();
        System.out.println("\nContainers to be loaded in the next port:");
        List<String> loaded = ctrl.getContainersToBeLoaded(idShip, Timestamp.valueOf(LocalDateTime.now()), connection);
        System.out.println(loaded);

        System.out.println("\nContainers to be offloaded in the next port:");
        List<String> offloaded = ctrl.getContainersToBeOffloaded(idShip, Timestamp.valueOf(LocalDateTime.now()), connection);
        System.out.println(offloaded);
    }
}
