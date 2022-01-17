package Demos;

import lapr.project.controller.AvgOccupancyCGinShipController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class US405Demo {
    public static void main(String[] args) throws IOException, SQLException {
        Scanner scan = new Scanner(System.in);
        AvgOccupancyCGinShipController controller ;
        System.out.println("Start of period?");
        String date1 = scan.nextLine();
        System.out.println("End of period?");
        String date2 = scan.nextLine();
        System.out.println("Ship MMSI?");
        int mmsi = scan.nextInt();

        controller = new AvgOccupancyCGinShipController(mmsi,date1,date2);
        System.out.println("Average ocupancy rate per cargo manifest for Ship with mms = "+ controller.getShipMMSI()+ " is "+controller.getResult()   +" %\n");
    }
}
