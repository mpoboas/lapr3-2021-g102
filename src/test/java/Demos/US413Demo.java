package Demos;

import lapr.project.utils.Pair;
import lapr.project.utils.PhysicsUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class US413Demo {
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Number of trip sections");
        int nSection = scan.nextInt();
        List<Pair<Integer,Integer>> list = new ArrayList<>();
        for(int i = 0; i < nSection; i++){
            System.out.println("Section "+(i+1)+" duration");
            int duration = scan.nextInt();
            System.out.println("Section "+(i+1)+" temperature");
            int temp = scan.nextInt();
            Pair<Integer,Integer> pair = new Pair<>(duration,temp);
            list.add(pair);
        }
        System.out.println("Number of 7º containers");
        int n7DegContainers = scan.nextInt();
        System.out.println("Number of -5º containers");
        int n5DegContainers = scan.nextInt();
        List<Pair<Double,Double>> listOfEnergy = PhysicsUtils.energyToBeSupliedTocontainers(list,n5DegContainers,n7DegContainers);
        for(int i = 0; i < listOfEnergy.size(); i++){
            System.out.println("Section "+ (i+1) + "\nEnergy for -5º containers: "+ listOfEnergy.get(i).getFirst()+ " joules\nEnergy for 7º containers: "+ listOfEnergy.get(i).getSecond()+" joules");
        }



    }
}
