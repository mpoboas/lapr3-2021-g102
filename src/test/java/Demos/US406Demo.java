package Demos;

import lapr.project.controller.App;
import lapr.project.controller.GetVoyagesBelowThresholdController;
import lapr.project.data.ConnectionFactory;
import lapr.project.data.TripStore;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class US406Demo {

    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Voyages Bellow a certain Threshold");
        System.out.println("Login:");
        System.out.println("Enter email: ");
        String email = scan.next();
        System.out.println("Enter password: ");
        String password = scan.next();
        ConnectionFactory cf = ConnectionFactory.getInstance();
        TripStore db = new TripStore();
        GetVoyagesBelowThresholdController ctrl = new GetVoyagesBelowThresholdController(db, cf);
        boolean log = App.getInstance().doLogin(email, password);
        if (!log) System.out.println("Invalid Login Credentials.");
        else {
            String res;
            do {

                System.out.println("Insert the Ship Identifier MMSI to know the voyages");
                System.out.println("Examples: 316005667 || 255806169");
                int mmsi = scan.nextInt();

                String res2;
                System.out.println("Do you want a specific threshold? (y/n)");
                res2 = scan.next();
                double threshold;
                if (res2.equals("y")) {
                    System.out.println("Insert threshold:");
                    threshold = scan.nextDouble();
                } else threshold = 66;

                try {
                    List<String> result = ctrl.getShipVoyagesBellowThreshold(mmsi, threshold);
                    if (result.size() == 0)
                        System.out.println("There are no voyages of the ship with mmsi:" + mmsi + " bellow the threshold");
                    else {
                        for (String s : result) {
                            System.out.println(s);
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                    System.out.println("An error occured");
                }


                System.out.println("Do you want to search more? (y/n)");
                res = scan.next();

            } while (res.equals("y"));
            System.out.println("Operation ended");

        }
    }
}
