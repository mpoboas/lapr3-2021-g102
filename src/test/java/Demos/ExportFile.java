package Demos;

import lapr.project.data.*;

import java.io.IOException;
import java.util.Scanner;

public class ExportFile  {
    static UploadFileToDatabase upload;
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        int i;
        String filedirectory;
        System.out.println("File Firectory?");
        filedirectory = scan.nextLine();
        System.out.println("Press:\n1-Upload Ships\n2-Upload Trips\n3-Upload Country\n4-Upload Container\n5-Upload Cargo Manifest\n6-Upload SeadList\n7-Upload Border\n8-Upload Ports\n");
        i = scan.nextInt();

        if(i==1)
            uploadShip();

        if(i==2)
            uploadTrip();

        if(i==3)
            uploadCountry();

        if(i==4)
            uploadContainer();

        if(i==5)
            uploadCargoManifest();

        if(i==6)
            uploadSeadList();

        if(i==7)
            uploadBorder();

        if(i==8)
            uploadPorts();

       uploadToDB(filedirectory);

    }
    private static void uploadShip() {
        upload = new UploadShip();
    }
    private static void uploadTrip() {
        upload = new UploadTrips();
    }
    private static void uploadCountry() {
        upload = new UploadCountry();
    }
    private static void uploadContainer() {
        upload = new UploadContainers();
    }
    private static void uploadCargoManifest() {
        upload = new UploadCargoManifests();
    }
    private static void uploadSeadList() {
        upload = new UploadSeadist();

    }
    private static void uploadBorder() {
        upload = new UploadBorder();

    }
    private static void uploadPorts() {
        upload = new UploadPorts();
    }
    private static void uploadToDB(String filedirectory) throws IOException {
        upload.UploadFile(filedirectory);
        upload.importFile();
        upload.importToDatabase();

    }

}
