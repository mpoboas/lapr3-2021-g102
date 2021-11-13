package Demos;

import lapr.project.controller.ListShipsController;
import lapr.project.model.Import;
import lapr.project.stores.ShipStore;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class US106Demo {
    static Scanner scan = new Scanner (System.in);

    public static void main(String[] args) throws IOException {
        System.out.println("How many ships are to be listed?");
        int nShips = scan.nextInt();
        while(nShips<0){
            System.out.println("Invalid Input! Try Again!");
            System.out.println("How many ships are to be listed?");
            nShips = scan.nextInt();
        }
        System.out.println("Start Date?(Format:yyyy-mm-ddThh:mm:ss");
        LocalDateTime start = LocalDateTime.parse(scan.next());
        System.out.println("End Date?(Format:yyyy-mm-ddThh:mm:ss");
        LocalDateTime end = LocalDateTime.parse(scan.next());
        String directory;
        System.out.println("File name or file directory?");
        directory = scan.next();
        ShipStore store = new ShipStore();
        Import im = new Import(store);
        im.importShipsData(directory);
        im.addShips();
        store = im.getShipStore();
        ListShipsController cont = new ListShipsController(nShips, start , end,store);
        System.out.println(cont);
    }
}
