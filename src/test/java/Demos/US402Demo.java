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
        int pInit = scan.nextInt();
        System.out.println("Place destiny id?");
        int pDest = scan.nextInt();
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
        System.out.println("LOADING...");
        controller = new ShortestPathController(places,comp,comp.getFreightNetwork().getGraph().vertex(pInit-1),comp.getFreightNetwork().getGraph().vertex(pDest-1));
        List <Place> routeLand =  controller.getLandPath();
        List <Place> routeSea =  controller.getSeaPath();
        List <Place> routeDirect =  controller.getDirectPath();
        List <Place> routeFull =  controller.getFullPath();
        System.out.println("Land Path");
        if(routeLand.size()==0){
            System.out.println("NOT POSSIBLE");
        }else{
            System.out.println("Distance = " + controller.getLandPathDist() + " km.");
            System.out.println("Passes by :");
            for (Place place : routeLand) {
                System.out.println("| " + place.getCountry() + " | " + place.getContinent() + " | " + place.getLatitude() + " | " + place.getLongitude() + " | " + place.getClass() + " | ");
            }
        }
        System.out.println("Sea Path");
        if(routeSea.size()==0){
            System.out.println("NOT POSSIBLE");
        }else{
            System.out.println("Distance = " + controller.getSeaPathDist() + " km.");
            System.out.println("Passes by :");
            for (Place place : routeSea) {
                System.out.println("| " + place.getCountry() + " | " + place.getContinent() + " | " + place.getLatitude() + " | " + place.getLongitude() + " | " + place.getClass() + " | ");
            }
        }
        System.out.println("Direct Path");
        System.out.println("Distance = " + controller.getDirectPathDist() + " km.");
        System.out.println("Passes by :");
        for (Place value : routeDirect) {
            System.out.println("| " + value.getCountry() + " | " + value.getContinent() + " | " + value.getLatitude() + " | " + value.getLongitude() + " | " + value.getClass() + " | ");
        }

        System.out.println("Full Path");
        System.out.println("Distance = " + controller.getFullPathDist() + " km.");
        System.out.println("Passes by :");
        for (Place place : routeFull) {
            System.out.println("| " + place.getCountry() + " | " + place.getContinent() + " | " + place.getLatitude() + " | " + place.getLongitude() + " | " + place.getClass() + " | ");
        }





    }
}
