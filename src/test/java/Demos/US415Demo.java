package Demos;

import lapr.project.controller.AuthController;
import lapr.project.model.CargoManifest;
import lapr.project.model.ImportCargoManifest;
import lapr.project.utils.Constants;
import lapr.project.utils.Pair;
import lapr.project.utils.PhysicsUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class US415Demo {
    public static void main(String[] args) throws IOException {
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
                System.out.println();
                List<Pair<Integer, Double>> tripSections = new ArrayList<>();
                String res1;
                do {
                    System.out.println("Type the section duration in seconds: ");
                    int duration = scan.nextInt();
                    System.out.println("Type the section temperature: ");
                    double temperature = scan.nextDouble();
                    tripSections.add(new Pair<>(duration, temperature));

                    System.out.println("\nDo you want to type values for another trip section? (y/n)");
                    res1 = scan.next();
                    System.out.println();
                } while (res1.equals("y"));

                ImportCargoManifest importCargoManifest = new ImportCargoManifest();
                importCargoManifest.importCargoManifestData("data/data-shipsports/LAPR3_G102 - cargoManifests.csv");
                CargoManifest cargoManifest = importCargoManifest.getListOfImportedCargoManifests().get(0);
                List<Pair<Double, Double>> totalEnergyPerSection = PhysicsUtils.energyToBeSupliedTocontainersDepOnPos(cargoManifest, tripSections);
                int numAuxEquipmentUnits = PhysicsUtils.getNumberOfAuxEquipmentUnits(tripSections, totalEnergyPerSection, Constants.AUX_EQUIPMENT_MAX_POWER);

                System.out.println("Number of auxiliary power equipment for the trip: " + numAuxEquipmentUnits);
                System.out.println("\nDo you want to repeat the process? (y/n)");
                res = scan.next();
                System.out.println();
            } while (res.equals("y"));
        }
    }
}
