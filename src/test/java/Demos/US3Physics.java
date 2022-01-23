package Demos;

import lapr.project.utils.Pair;
import lapr.project.utils.PhysicsUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class US3Physics {
    static Scanner scan = new Scanner (System.in);
    public static void main(String[] args) {
        System.out.println("Container dimension = 40'");
        System.out.println();
        List<Double> conductivity = new ArrayList<>();
        List<Double> wallThicknessList = new ArrayList<>();
        String res;
        do {
            System.out.println("List of available materials");
            List<Pair<String,Double>> materials = PhysicsUtils.listOfMaterials();
            int i = 1;
            for (Pair<String,Double> p:materials) {
                System.out.println(i + " " + p.getFirst());
                i++;
            }

            System.out.println("Choose Material of the outer wall:");
            int index = scan.nextInt() - 1;

            conductivity.add(materials.get(index).getSecond());
            System.out.println("Choose the outer wall thickness (0,04):");
            double thicknessOuter = scan.nextDouble();
            wallThicknessList.add(thicknessOuter);

            System.out.println("Choose Material of the middle wall:");
            int index2 = scan.nextInt() - 1;
            conductivity.add(materials.get(index2).getSecond());
            System.out.println("Choose the outer wall thickness (0,10/0,06):");
            double thicknessMiddle = scan.nextDouble();
            wallThicknessList.add(thicknessMiddle);

            System.out.println("Choose Material of the Inner wall:");
            int index3 = scan.nextInt() - 1;
            conductivity.add(materials.get(index3).getSecond());
            System.out.println("Choose the outer wall thickness (0,06/0,10):");
            double thicknessInner = scan.nextDouble();
            wallThicknessList.add(thicknessInner);
            System.out.println();
            System.out.println("Calculated Resistance:");
            double resistencia1 = PhysicsUtils.calculateContainerThermalResistance(PhysicsUtils.CONTAINER_40_AREA,conductivity,wallThicknessList);
            System.out.println(resistencia1);

            System.out.println("Do you want to know how much energy is necessary to maintain the temperature (y/n)?");
            String res2 = scan.next();
            if (res2.equals("y")){
                System.out.println("External temperature:");
                int temp1 = scan.nextInt();
                System.out.println("Inner temperature:");
                int temp2 = scan.nextInt();
                System.out.println("Time of the voyage:");
                System.out.println("Hours:");
                int hour = scan.nextInt();
                System.out.println("Minutes:");
                int min = scan.nextInt();
                System.out.println("Seconds:");
                int sec = scan.nextInt();

                double energy = PhysicsUtils.calculateEnergyNecessary(temp1,temp2,resistencia1,hour,min,sec);
                System.out.println("Energy needed to maintain the desired temperature in the container: " +energy);
            }



            System.out.println("Do you want to know other materials resistance? (y/n)");
            res = scan.next();

        }while(res.equals("y"));





    }
}
