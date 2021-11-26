package Demos;

import lapr.project.controller.getNManifestsController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class US207Demo {
    static Scanner scan = new Scanner(System.in);
    static getNManifestsController controller;
    public static void main(String[] args) throws IOException, SQLException {
        int id = 12;
        System.out.println("List number of cargo manifests transported in year and average container per cargo manifest");
        System.out.println("Which year is requires?");
        int year = scan.nextInt();
        controller = new getNManifestsController(id,year);
        System.out.println("Data from year " + year);
        System.out.println("|\tNº of cargo manifests\t|\tAvg containers per cargo manifest\t|");
        System.out.println("|\t\t"+controller.getNManifests()+"\t\t|\t\t"+controller.getAvgContainerPerManifest()+"\t\t|");
    }
}