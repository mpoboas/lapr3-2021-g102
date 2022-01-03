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
            System.out.println("Choose the outer wall thickness:");
            double thicknessOuter = scan.nextDouble();
            wallThicknessList.add(thicknessOuter);

            System.out.println("Choose Material of the middle wall:");
            int index2 = scan.nextInt() - 1;
            conductivity.add(materials.get(index2).getSecond());
            System.out.println("Choose the outer wall thickness:");
            double thicknessMiddle = scan.nextDouble();
            wallThicknessList.add(thicknessMiddle);

            System.out.println("Choose Material of the Inner wall:");
            int index3 = scan.nextInt() - 1;
            conductivity.add(materials.get(index3).getSecond());
            System.out.println("Choose the outer wall thickness:");
            double thicknessInner = scan.nextDouble();
            wallThicknessList.add(thicknessInner);
            System.out.println();
            System.out.println("Calculated Resistance:");
            double resistencia1 = PhysicsUtils.calculateContainerThermalResistance(PhysicsUtils.CONTAINER_40_AREA,conductivity,wallThicknessList);
            System.out.println(resistencia1);

            System.out.println("Do you want to know other materials resistance? (y/n)");
            res = scan.next();

        }while(res.equals("y"));





    }
}
