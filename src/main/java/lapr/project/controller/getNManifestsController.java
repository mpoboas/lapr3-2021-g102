package lapr.project.controller;

import lapr.project.data.getNmanifestsYear;

import java.io.IOException;
import java.sql.SQLException;

public class getNManifestsController {
    private int idShipCaptain;
    private int year;
    private getNmanifestsYear getnManY;
    public getNManifestsController ( int id, int year) throws IOException, SQLException {
        this.year = year;
        this.idShipCaptain = id;
        getnManY = new getNmanifestsYear(this.idShipCaptain,this.year);
    }
    public int getNManifests(){return this.getnManY.getNManifests();}
    public double getAvgContainerPerManifest(){return this.getnManY.getAvgManifests();}
}
