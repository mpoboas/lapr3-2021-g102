package Demos;

import lapr.project.controller.ListShipsController;
import lapr.project.model.Import;
import lapr.project.stores.ShipStore;

import java.io.IOException;
import java.util.Scanner;

public class US105Demo {
    static Scanner scan = new Scanner (System.in);
    public static void main(String[] args) throws IOException {
        System.out.println("Is order ascending ?(type true if yes false if no)");
        boolean asc = scan.nextBoolean();
        System.out.println("What is the criteria?(1 for number of movements, 0 for distance traveled");
        int criteria = scan.nextInt();
        while(criteria>1 ||criteria<0){
            System.out.println("Invalid Input! Try Again!");
            System.out.println("What is the criteria?(1 for number of movements, 0 for distance traveled");
            criteria = scan.nextInt();
        }
        String directory;
        System.out.println("File name or file directory?");
        directory = scan.next();
        ShipStore store = new ShipStore();
        Import im = new Import(store);
        im.importShipsData(directory);
        im.addShips();
        store = im.getShipStore();
        ListShipsController cont = new ListShipsController(asc,criteria, store);
        System.out.println(cont);
    }
}
