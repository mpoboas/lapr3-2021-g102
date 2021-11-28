package lapr.project.controller;

import lapr.project.data.getNmanifestsYear;

import java.io.IOException;
import java.sql.SQLException;

public class GetNManifestsController {
    private final int idShipCaptain;
    private final int year;
    private final getNmanifestsYear getnManY;
    public GetNManifestsController ( int id, int year) throws IOException, SQLException {
        this.year = year;
        this.idShipCaptain = id;
        getnManY = new getNmanifestsYear(this.idShipCaptain,this.year);
    }
    public int getNManifests(){return this.getnManY.getNManifests();}
    public double getAvgContainerPerManifest(){return this.getnManY.getAvgManifests();}
}
