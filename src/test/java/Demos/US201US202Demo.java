package Demos;

import lapr.project.controller.App;
import lapr.project.controller.FindClosestPortController;
import lapr.project.controller.ImportPortsController;
import lapr.project.controller.ImportShipsController;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class US201US202Demo {
    static Scanner scan = new Scanner(System.in);
    static String directoryShips = "data/data-shipsports/data-shipsports/data-ships&ports/sships.csv";
    static String directoryPorts = "data/data-shipsports/data-shipsports/data-ships&ports/sports.csv";
    static ImportShipsController ctrlShip = new ImportShipsController();
    static ImportPortsController ctrlPort = new ImportPortsController();
    static FindClosestPortController ctrlFind = new FindClosestPortController();

    public static void main(String[] args) throws IOException {
        ctrlShip.importShipData(directoryShips);
        ctrlShip.addShips();
        ctrlPort.importPortsData(directoryPorts);
        ctrlPort.addImportedPorts();
        String res;
        do {
            System.out.println("To find the closest port of a ship, insert its call sign and a date");
            System.out.println("Insert Call Sign (Example:FLSU):");
            String callSign = scan.next();
            System.out.println("Insert Date (Example:2020-12-31T00:00:00 :");
            LocalDateTime date = LocalDateTime.parse(scan.next());
            System.out.println("Closest Port:");
            System.out.println(ctrlFind.findClosestPort(callSign,date));
            System.out.println("Find closest port of other ship? (y or n)");
            res = scan.next();
        }while(res.equals("y"));
        System.out.println("Operation ended");

    }
}
