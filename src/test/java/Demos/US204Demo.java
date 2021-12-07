package Demos;

import lapr.project.controller.GetContainerSituationController;
import lapr.project.data.ConnectionFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class US204Demo {
    static Scanner scan = new Scanner(System.in);
    static GetContainerSituationController ctrl = new GetContainerSituationController();

    public static void main(String[] args) {
        String res;
        do {
            System.out.println("Insert the Container Code to know the current situation:");
            int code = scan.nextInt();
            String situation;
            try {
                situation = ctrl.getSituation(code, ConnectionFactory.getInstance());
                System.out.println(situation);
            } catch (SQLException | IOException e) {
                System.out.println("Container with id "+ code+ " not found!");
            }
            System.out.println("Do you want to search more? (y/n)");
            res = scan.next();
        }while(res.equals("y"));
        System.out.println("Operation ended");



    }
}
