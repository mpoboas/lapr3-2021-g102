package Demos;

import lapr.project.controller.App;
import lapr.project.controller.GetContainerSituationControllerV2;
import lapr.project.data.ConnectionFactory;
import lapr.project.data.ContainerSituationDB;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class US312Demo {

    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Container Situation");
        System.out.println("Login:");
        System.out.println("Enter email: ");
        String email = scan.next();
        System.out.println("Enter password: ");
        String password = scan.next();
        ConnectionFactory cf = ConnectionFactory.getInstance();
        ContainerSituationDB db = new ContainerSituationDB();
        GetContainerSituationControllerV2 ctrl = new GetContainerSituationControllerV2(db, cf);
        boolean log = App.getInstance().doLogin(email, password);
        if (!log) System.out.println("Invalid Login Credentials.");
        else {
            String res;
            do {

                System.out.println("Insert the Container Code to know the current situation:");
                System.out.println("Examples: LPRU1628240 || LPRU9014362");
                String code = scan.next();
                String situation;
                try {
                    situation = ctrl.getSituation2(code.trim());
                    System.out.println(situation);
                } catch (SQLException ex1) {
                    switch (ex1.getErrorCode()) {
                        case 20010:
                            System.out.println("Invalid Container id");
                            break;
                        case 20011:
                            System.out.println("Container is not leased by client");
                            break;
                        case 20009:
                            System.out.println("Container is not leased at the moment");
                            break;
                    }
                } catch (IllegalStateException ex2) {
                    System.out.println(ex2.getMessage());
                }

                System.out.println("Do you want to search more? (y/n)");
                res = scan.next();

            } while (res.equals("y"));
            System.out.println("Operation ended");

        }
    }
}
