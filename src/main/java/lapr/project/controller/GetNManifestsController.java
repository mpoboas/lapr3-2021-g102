package lapr.project.controller;

import lapr.project.data.GetNmanifestsYear;

import java.io.IOException;
import java.sql.SQLException;

public class GetNManifestsController {
    private final int idShipCaptain;
    private final int year;
    private GetNmanifestsYear getnManY;

    /**
     *
     * @param id ship captain id
     * @param year year inquired by captain
     * @throws IOException io exception
     * @throws SQLException sql exception
     */
    public GetNManifestsController ( int id, int year){
        this.year = year;
        this.idShipCaptain = id;
        getnManY = new GetNmanifestsYear(this.idShipCaptain,this.year);
    }
    public void doDBQuery() throws SQLException, IOException {
        getnManY.runDBCommand();
    }
    public void setGetnManY(GetNmanifestsYear data){
        this.getnManY = data;
    }
    /**
     *
     * @return number of manifests done by captain in a certain year
     */
    public int getNManifests(){return this.getnManY.getNManifests();}

    /**
     *
     * @return average container per cargo manifest
     */
    public double getAvgContainerPerManifest(){return this.getnManY.getAvgManifests();}
}
