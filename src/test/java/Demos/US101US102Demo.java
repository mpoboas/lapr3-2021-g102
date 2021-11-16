package Demos;

import lapr.project.controller.SearchShipController;
import lapr.project.model.Import;
import lapr.project.stores.ShipStore;

import java.io.IOException;
import java.util.Scanner;

public class US101US102Demo {
    static Scanner scan = new Scanner (System.in);
    static String  directory = "data/data-shipsports/data-shipsports/data-ships&ports/sships.csv";
    public static void main(String[] args) throws IOException {
        ShipStore store = new ShipStore();
        Import im = new Import(store);
        im.importShipsData(directory);
        im.addShips();
        String res;
        do {
            System.out.println("Insert the code that refers the Ship (MMSI,IMO or CallSign):");
            System.out.println("Examples:");
            System.out.println("212180000");
            System.out.println("IMO9192387");
            System.out.println("LATO7");
            String code = scan.next();
            SearchShipController ctrl = new SearchShipController(store);
            try {
                System.out.println(ctrl.searchShip(code));
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            System.out.println("Search another? (y or n)");
            res = scan.next();
        }while (res.equals("y"));
        System.out.println("Search ended");





    }
}
