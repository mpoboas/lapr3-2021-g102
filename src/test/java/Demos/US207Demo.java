package Demos;

import lapr.project.controller.GetNManifestsController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class US207Demo {
    static Scanner scan = new Scanner(System.in);
    static GetNManifestsController controller;

    public static void main(String[] args) throws IOException, SQLException {
        System.out.println("Your Id?");
        int id = scan.nextInt();
        System.out.println("List number of cargo manifests transported in year and average container per cargo manifest");
        System.out.println("Which year is required?");
        int year = scan.nextInt();
        controller = new GetNManifestsController(id, year);
        controller.doDBQuery();
        System.out.println("Data from year " + year);
        System.out.println("|\tNº of cargo manifests\t|\tAvg containers per cargo manifest\t|");
        System.out.println("|\t\t" + controller.getNManifests() + "\t\t|\t\t" + controller.getAvgContainerPerManifest() + "\t\t|");
    }
}

