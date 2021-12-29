package Demos;

import lapr.project.controller.ClossenessPlacesController;
import lapr.project.model.*;
import lapr.project.stores.PortStore;

import java.io.IOException;
import java.util.Scanner;

public class US303Demo {
    static Scanner scan = new Scanner(System.in);
    static ImportPorts port = new ImportPorts(new PortStore());


    public static void main(String[] args) throws IOException {
        ClossenessPlacesController controller;
        Company comp = new Company("Company");
        port.importPortsData("data/data-shipsports/LAPR3_G102 - bports (alpha2Code).csv");
        comp.getFreightNetwork().buildFreightNetworkFromGivenData(ImportCountry.importCountries("data/Data-Sprint3-4/countries.csv"), ImportBorder.importBorders("data/Data-Sprint3-4/borders.csv"),port.getListOfImportedPorts(), ImportSeadist.importSeadistData("data/Data-Sprint3-4/seadists.csv"), 1);
        for(int i = 0; i < comp.getFreightNetwork().getGraph().vertices().size(); i++){
            System.out.printf("ID %d; ", i+1);
            System.out.println("| " + comp.getFreightNetwork().getGraph().vertices().get(i).getCountry() + " | " + comp.getFreightNetwork().getGraph().vertices().get(i).getContinent() + " | " +  comp.getFreightNetwork().getGraph().vertices().get(i).getLatitude() + " | " + comp.getFreightNetwork().getGraph().vertices().get(i).getLongitude() + " | "  + comp.getFreightNetwork().getGraph().vertices().get(i).getClass()+ " | ");
        }
        System.out.println("Place id?");
        int p = scan.nextInt();
        System.out.println("Number of places?");
        int n = scan.nextInt();
        controller = new ClossenessPlacesController(comp, comp.getFreightNetwork().getGraph().vertex(p-1), n);
        System.out.println("Places found!");
        System.out.println("| Country | Continent | Latitude | Longitude | Distance to Port (km) | Type |");
        for(int i = 0; i < controller.getClosestPlacesList().size(); i++){
            System.out.println("| " + controller.getClosestPlacesList().get(i).getFirst().getCountry() + " | " + controller.getClosestPlacesList().get(i).getFirst().getContinent() + " | " +  controller.getClosestPlacesList().get(i).getFirst().getLatitude() + " | " + controller.getClosestPlacesList().get(i).getFirst().getLongitude() + " | " + controller.getClosestPlacesList().get(i).getSecond()+ " | " + controller.getClosestPlacesList().get(i).getFirst().getClass()+ " | ");
        }

    }
}
