package Demos;

import lapr.project.controller.App;
import lapr.project.controller.BuildFreightNetworkController;
import lapr.project.controller.GetEfficientCircuitController;
import lapr.project.data.ConnectionFactory;
import lapr.project.model.Place;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class US403Demo {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Most Efficient Cycle ");
        System.out.println("Login (trafficmanager@lei.sem3.pt):");
        System.out.println("Enter email: ");
        String email = scan.next();
        System.out.println("Enter password: ");
        String password = scan.next();
        boolean log = App.getInstance().doLogin(email, password);
        if (!log) System.out.println("Invalid Login Credentials.");
        else {
            BuildFreightNetworkController ctrl1 = new BuildFreightNetworkController();
            System.out.println("How many ports international ports to connect? ");
            int number = scan.nextInt();
            try {
                ctrl1.buildFreightNetworkFromDatabase(ConnectionFactory.getInstance().getDatabaseConnection(), number);
            } catch (IOException e) {
                e.printStackTrace();
            }
            GetEfficientCircuitController ctrl2 = new GetEfficientCircuitController();
            LinkedList<Place> result = ctrl2.getEfficientCycle();
            System.out.println("The most efficient cycle passes through :" + (result.size() - 1) + " different places");
            System.out.println();
            for (Place place: result) {
                System.out.println(place);
            }

        }
        System.out.println();
        System.out.println("Operation ended");
    }
}
