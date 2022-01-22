package Demos;

import lapr.project.controller.GetUnladenCenterOfMassController;
import lapr.project.data.ConnectionFactory;
import lapr.project.data.ShipStoreDB;
import lapr.project.utils.Constants;
import lapr.project.utils.Pair;
import lapr.project.utils.PhysicsUtils;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class US418Demo {
    public static void main(String[] args) throws IOException, SQLException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Determination of the unleaded center of mass of a vessel:");
        System.out.println();
        System.out.println("Select an option:");
        System.out.println("0 - Specific ship");
        System.out.println("1 - Insert total length, width, height and mass of the vessel");
        System.out.println("2 - Insert length, width, height of the vessel and of the control bridge");
        System.out.println("3 - Insert all the information of all the sections");
        double length, width, height, mass, bridgeDistance;
        String bridgePosition;
        Pair<Point2D.Double, Double> cm = new Pair<>(new Point2D.Double(0, 0), 0.0);

        int option = scan.nextInt();

        switch (option) {
            case 0:
                GetUnladenCenterOfMassController ctrl = new GetUnladenCenterOfMassController(new ShipStoreDB(), ConnectionFactory.getInstance());
                System.out.println("1 - Search for mmsi");
                System.out.println("2 - Select a ship");
                bridgePosition = "STERN";
                int option2 = scan.nextInt();
                switch (option2) {
                    case 1:
                        System.out.println("Insert Ship MMSI:");
                        int mmsi = scan.nextInt();
                        System.out.println("Insert Position of the control Bridge (STERN|MIDDLE|BOW):");
                        bridgePosition = scan.next();
                        ctrl.getShipInfo(mmsi);
                        break;
                    case 2:
                        System.out.println("Select a Ship");
                        System.out.println("1 - CASON_J_CALLAWAY");
                        System.out.println("2 - EVER_ACE");
                        int option3 = scan.nextInt();
                        if (option3 == 1)
                            ctrl.getShipInfo(Constants.CASON_J_CALLAWAY);
                        if (option3 == 2)
                            ctrl.getShipInfo(Constants.EVER_ACE);
                        System.out.println("Insert Position of the control Bridge (STERN|MIDDLE|BOW):");
                        bridgePosition = scan.next();
                        break;
                }
                try {
                    cm = ctrl.getCenterOfMassOfAShip(bridgePosition);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    break;
                }
                break;
            case 1:
                System.out.println("Insert total length of the vessel: ");
                length = scan.nextDouble();
                System.out.println("Insert total width of the vessel: ");
                width = scan.nextDouble();
                System.out.println("Insert total height of the vessel: ");
                height = scan.nextDouble();
                System.out.println("Insert total mass in tons of the vessel: ");
                mass = scan.nextDouble();
                System.out.println("Where is the control bridge located? (STERN|MIDDLE|BOW)");
                bridgePosition = scan.next();
                cm = PhysicsUtils.calculateTheUnleadedCenterOfMass(length, width, height, mass * 1000, bridgePosition);

                break;
            case 2:
                System.out.println("Insert length of the vessel: ");
                length = scan.nextDouble();
                System.out.println("Insert width of the vessel: ");
                width = scan.nextDouble();
                System.out.println("Insert height of the vessel: ");
                height = scan.nextDouble();
                System.out.println("Insert total mass in tons of the vessel: ");
                mass = scan.nextDouble();

                System.out.println("Insert length of the control Bridge: ");
                double lengthB = scan.nextDouble();
                System.out.println("Insert width of the control Bridge: ");
                double widthB = scan.nextDouble();
                System.out.println("Insert height of the control Bridge: ");
                double heightB = scan.nextDouble();
                System.out.println("Where is the control bridge located? (STERN|MIDDLE|BOW)");
                bridgePosition = scan.next();
                switch (bridgePosition) {
                    case "STERN":
                    default:
                        bridgeDistance = length * PhysicsUtils.SECTION_3_LENGTH_PERCENT_DISTANCE_STERN;
                        break;
                    case "MIDDLE":
                        bridgeDistance = length * PhysicsUtils.SECTION_3_LENGTH_PERCENT_DISTANCE_MIDDLE;
                        break;
                    case "BOW":
                        bridgeDistance = length * PhysicsUtils.SECTION_3_LENGTH_PERCENT_DISTANCE_BOW;
                        break;
                }
                cm = PhysicsUtils.calculateTheUnleadedCenterOfMassWithBridgeINFO(length, width, height, mass * 1000, lengthB, widthB, heightB, bridgeDistance);
                break;
            case 3:
                System.out.println("Insert length of the section 1: ");
                double length1 = scan.nextDouble();
                System.out.println("Insert width of the section 1: ");
                double width1 = scan.nextDouble();
                System.out.println("Insert height of the section 1: ");
                double height1 = scan.nextDouble();
                System.out.println("Insert mass of the section 1: ");
                double mass1 = scan.nextDouble();

                System.out.println("Insert length of the section 2: ");
                double length2 = scan.nextDouble();
                System.out.println("Insert width of the section 2: ");
                double width2 = scan.nextDouble();
                System.out.println("Insert height of the section 2: ");
                double height2 = scan.nextDouble();
                System.out.println("Insert mass of the section 2: ");
                double mass2 = scan.nextDouble();

                System.out.println("Insert length of the section 3 (control Bridge): ");
                double length3 = scan.nextDouble();
                System.out.println("Insert width of the section 3 (control Bridge): ");
                double width3 = scan.nextDouble();
                System.out.println("Insert height of the section 3 (control Bridge): ");
                double height3 = scan.nextDouble();
                System.out.println("Insert mass of the section 3 (control Bridge): ");
                double mass3 = scan.nextDouble();

                System.out.println("Distance of the control bridge from the stern");
                bridgeDistance = scan.nextDouble();

                cm = PhysicsUtils.calculateTheUnleadedCenterOfMassWithAllParameters(length1, width1, height1, mass1,
                        length2, width2, height2, mass2, length3, width3, height3, mass3, mass1 + mass2 + mass3, bridgeDistance);
                break;
        }
        System.out.println("Center of mass = ( " + cm.getFirst().x + " ; " + cm.getFirst().y + " ; " + cm.getSecond() + " )");
        System.out.printf("Center of mass = ( %.2f ; %.2f ; %.2f) %n", cm.getFirst().x, cm.getFirst().y, cm.getSecond());

    }
}
