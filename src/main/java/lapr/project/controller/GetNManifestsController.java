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

    /**
     * Method to do database query
     * @throws SQLException exception
     * @throws IOException exception
     */
    public void doDBQuery() throws SQLException, IOException {
        getnManY.runDBCommand();
    }

    /**
     * method to set data class, mostly used in tests
     * @param data new data class
     */
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
