package Demos;

import lapr.project.controller.ShortestPathController;
import lapr.project.model.*;
import lapr.project.stores.PortStore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class US402Demo {
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        ShortestPathController controller;
        Company comp = new Company("Comp");
        List<Place> places = new ArrayList<>();
        ImportPorts port = new ImportPorts(new PortStore());
        port.importPortsData("data/data-shipsports/data-shipsports/data-ships&ports/bports.csv");
        comp.getFreightNetwork().buildFreightNetworkFromGivenData(ImportCountry.importCountries("data/Data-Sprint3-4/countries.csv"), ImportBorder.importBorders("data/Data-Sprint3-4/borders.csv"), port.getListOfImportedPorts(), ImportSeadist.importSeadistData("data/Data-Sprint3-4/seadists.csv"), 10);
        for(int i = 0; i < comp.getFreightNetwork().getGraph().vertices().size(); i++){
            System.out.printf("ID %d; ", i+1);
            System.out.println("| " + comp.getFreightNetwork().getGraph().vertices().get(i).getCountry() + " | " + comp.getFreightNetwork().getGraph().vertices().get(i).getContinent() + " | " +  comp.getFreightNetwork().getGraph().vertices().get(i).getLatitude() + " | " + comp.getFreightNetwork().getGraph().vertices().get(i).getLongitude() + " | "  + comp.getFreightNetwork().getGraph().vertices().get(i).getClass()+ " | ");
        }
        System.out.println("Place id?");
        int p = scan.nextInt();
        System.out.println("Number of places?");
        int n = scan.nextInt();
        for(int i = 0; i < comp.getFreightNetwork().getGraph().vertices().size(); i++){
            System.out.printf("ID %d; ", i+1);
            System.out.println("| " + comp.getFreightNetwork().getGraph().vertices().get(i).getCountry() + " | " + comp.getFreightNetwork().getGraph().vertices().get(i).getContinent() + " | " +  comp.getFreightNetwork().getGraph().vertices().get(i).getLatitude() + " | " + comp.getFreightNetwork().getGraph().vertices().get(i).getLongitude() + " | "  + comp.getFreightNetwork().getGraph().vertices().get(i).getClass()+ " | ");
        }
        if(n>0) {
            System.out.println("Write the id of the places to pass");
            for (int j = 0; j < n; j++){
                int id = scan.nextInt();
                places.add(comp.getFreightNetwork().getGraph().vertex(id-1));
            }
        }
    }
}
