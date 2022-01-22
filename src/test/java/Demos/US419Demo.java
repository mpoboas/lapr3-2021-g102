package Demos;

import lapr.project.controller.App;
import lapr.project.controller.AuthController;
import lapr.project.utils.Constants;
import lapr.project.utils.Pair;
import lapr.project.utils.PhysicsUtils;

import java.awt.geom.Point2D;
import java.util.Scanner;

public class US419Demo {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter email: ");
        String email = scan.next();
        System.out.println("Enter password: ");
        String password = scan.next();
        AuthController authController = new AuthController();

        if (!authController.doLogin(email, password)) System.out.println("Invalid Login Credentials.");
        else {
            String res;
            do {
                Pair<Point2D.Double, Double> unladenCenterOfMass = PhysicsUtils.calculateTheUnleadedCenterOfMass(175, 28, 8.5, Constants.SHIP_MASS, "STERN");
                System.out.println("How many containers do you want to place?");
                int numContainers = scan.nextInt();
                if (numContainers > 100)
                    System.out.println("Maximum number of containers per layer on the deck exceeded. Maximum is 100.");
                else {

                    Point2D.Double[][] containerMatrix = PhysicsUtils.whereToPositionContainersOnVessel(unladenCenterOfMass, Constants.SHIP_AVAILABLE_LENGTH, Constants.SHIP_AVAILABLE_WIDTH, Constants.CONTAINER_LENGTH, Constants.CONTAINER_WIDTH, numContainers);
                    System.out.println("X and Y axis positions: ");
                    for (int i = 0; i < 10; i++) {
                        for (int j = 0; j < 10; j++) {
                            System.out.printf("[%6.3f;%6.3f] ", containerMatrix[i][j].getX(), containerMatrix[i][j].getY());
                        }
                        System.out.println();
                    }

                    Pair<Point2D.Double, Double> totalCenterOfMass = PhysicsUtils.calculateTotalCenterOfMass(unladenCenterOfMass, containerMatrix, numContainers, Constants.SABRINA.getWeight());
                    System.out.printf("\nPrevious total centre of mass: (%.3f, %.3f, %.3f)", unladenCenterOfMass.getFirst().getX(), unladenCenterOfMass.getFirst().getY(), unladenCenterOfMass.getSecond());
                    System.out.printf("\nNew total centre of mass: (%.3f, %.3f, %.3f)\n\n", totalCenterOfMass.getFirst().getX(), totalCenterOfMass.getFirst().getY(), totalCenterOfMass.getSecond());
                }

                System.out.println("Do you want to repeat the process? (y/n)");
                res = scan.next();
                System.out.println();
            } while (res.equals("y"));
        }
    }
}
