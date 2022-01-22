package Demos;

import lapr.project.controller.AuthController;
import lapr.project.model.*;
import lapr.project.stores.FreightNetwork;
import lapr.project.stores.PortStore;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class US401Demo {
    static String fileDirectoryCountry = "data/Data-Sprint3-4/countries.csv";
    static String fileDirectoryPorts = "data/data-shipsports/data-shipsports/data-ships&ports/bports.csv";
    static String fileDirectoryBorders = "data/Data-Sprint3-4/borders.csv";
    static String fileDirectorySeaDist = "data/Data-Sprint3-4/seadists.csv";

    public static void main(String[] args) throws IOException, SQLException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter email: ");
        String email = scan.next();
        System.out.println("Enter password: ");
        String password = scan.next();
        AuthController authController = new AuthController();

        ImportPorts importP = new ImportPorts(new PortStore());
        FreightNetwork network = new FreightNetwork();
        List<Country> countries = ImportCountry.importCountries(fileDirectoryCountry);
        List<Border> borders = ImportBorder.importBorders(fileDirectoryBorders);
        importP.importPortsData(fileDirectoryPorts);
        List<Port> ports = importP.getListOfImportedPorts();
        List<SeaDist> seaDists = ImportSeadist.importSeadistData(fileDirectorySeaDist);
        network.buildFreightNetworkFromGivenData(countries, borders, ports, seaDists, 5);

        if (!authController.doLogin(email, password)) System.out.println("Invalid Login Credentials.");
        else {
            String res;
            do {
                System.out.println("Type the number of desired ports: ");
                int nPorts = scan.nextInt();
                List<Port> portList = network.getMostCriticalPorts(nPorts);
                if (nPorts == 1) System.out.println("\nThe most critical port is: ");
                else System.out.println("\nThe " + nPorts + " most critical ports are: ");
                for (Port p : portList) {
                    System.out.println(p.getName());
                }

                System.out.println("\nDo you want to repeat the process? (y/n)");
                res = scan.next();
                System.out.println();
            } while (res.equals("y"));
        }
    }
}