package Demos;

import lapr.project.controller.PortReportController;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Scanner;

public class US310Demo {

    public static void main(String[] args) throws SQLException, IOException {
        Scanner scan = new Scanner(System.in);
        System.out.println("US310 demo \nPort report for a given month\n");
        System.out.println("Port id?");
        int idPort = scan.nextInt();
        System.out.println("Year?");
        int year = scan.nextInt();
        System.out.println("Month(1-12)?");
        int month = scan.nextInt();
        PortReportController controller = new PortReportController(year,month,idPort);
        controller.getDataAndGenerateReport();
        try (PrintWriter out = new PrintWriter("Port Report.txt")) {
            out.println(controller.getReport());
        }
    }
}
