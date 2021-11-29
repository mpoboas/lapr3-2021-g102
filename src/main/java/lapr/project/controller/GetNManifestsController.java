package lapr.project.controller;

import lapr.project.data.GetNmanifestsYear;

import java.io.IOException;
import java.sql.SQLException;

public class GetNManifestsController {
    private final int idShipCaptain;
    private final int year;
    private final GetNmanifestsYear getnManY;

    /**
     *
     * @param id ship captain id
     * @param year year inquired by captain
     * @throws IOException io exception
     * @throws SQLException sql exception
     */
    public GetNManifestsController ( int id, int year) throws IOException, SQLException {
        this.year = year;
        this.idShipCaptain = id;
        getnManY = new GetNmanifestsYear(this.idShipCaptain,this.year);
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
