package lapr.project.data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetNmanifestsYear {
    private final int idShipCaptain;
    private final int year;
    private int nManifestsYear;
    private double avgContainerPerManifest;
    private final ConnectionFactory cf;

    /**
     *
     * @param id ship catain id
     * @param year year that ship captain inquired
     * @throws IOException ioexception
     * @throws SQLException sqlexception
     */
    public GetNmanifestsYear(int id, int year) throws IOException, SQLException {
        this.year = year;
        this.idShipCaptain = id;
        cf = new ConnectionFactory();
        getNmanifestsinyear();
        getAVGContainerPerManifest();
    }

    /**
     *
     * @throws SQLException sql exception
     */
    private void getNmanifestsinyear() throws SQLException {
        String sqlCommand = "SELECT  COUNT(*) FROM CARGOMANIFEST WHERE ELABORATIONDATE > TO_TIMESTAMP('01-01-"+this.year +" 00:00', 'DD-MM-YYYY HH24:mi') AND ELABORATIONDATE < TO_TIMESTAMP('31-12-"+this.year +" 23:59', 'DD-MM-YYYY HH24:mi') AND IDCARGOMANIFEST IN (SELECT IDCARGOMANIFESTLOAD  FROM ARRIVALDEPARTURE WHERE IDTRIP IN (SELECT IDTRIP FROM CREW WHERE IDEMPLOYEE = "+this.idShipCaptain +")) OR IDCARGOMANIFEST IN (SELECT IDCARGOMANIFESTUNLOAD  FROM ARRIVALDEPARTURE WHERE IDTRIP IN (SELECT IDTRIP FROM CREW WHERE IDEMPLOYEE = "+this.idShipCaptain +"))";
        ResultSet s = executeStatementeOnDB(sqlCommand);
        s.next();
        nManifestsYear = s.getInt("COUNT(*)");
    }

    /**
     *
     * @throws SQLException sql exception
     */
    private void getAVGContainerPerManifest() throws SQLException {
        String sqlCommand = "SELECT AVG(c) FROM (SELECT IDCARGOMANIFEST, COUNT(*) AS c FROM MANIFESTCONTAINER WHERE IDCARGOMANIFEST IN (SELECT IDCARGOMANIFEST FROM CARGOMANIFEST WHERE ELABORATIONDATE > TO_TIMESTAMP('01-01-"+this.year +" 00:00', 'DD-MM-YYYY HH24:mi') AND ELABORATIONDATE < TO_TIMESTAMP('31-12-"+this.year +" 23:59', 'DD-MM-YYYY HH24:mi') AND IDCARGOMANIFEST IN (SELECT IDCARGOMANIFESTLOAD  FROM ARRIVALDEPARTURE WHERE IDTRIP IN (SELECT IDTRIP FROM CREW WHERE IDEMPLOYEE = "+this.idShipCaptain +")) OR IDCARGOMANIFEST IN (SELECT IDCARGOMANIFESTUNLOAD  FROM ARRIVALDEPARTURE WHERE IDTRIP IN (SELECT IDTRIP FROM CREW WHERE IDEMPLOYEE = "+this.idShipCaptain +"))) GROUP BY IDCARGOMANIFEST) ";
        ResultSet s = executeStatementeOnDB(sqlCommand);
        s.next();
        avgContainerPerManifest = s.getInt("AVG(c)");
    }

    /**
     *
     * @param sqlCommand command to be executed from database
     * @return result set from databse
     * @throws SQLException sql exception
     */
    private ResultSet executeStatementeOnDB(String sqlCommand) throws SQLException {
        Connection connection = cf.getDatabaseConnection().getConnection();
        PreparedStatement runcommand = connection.prepareStatement(sqlCommand);
        return runcommand.executeQuery();
    }

    /**
     *
     * @return number of manifets per x year by captain
     */
    public int getNManifests(){
        return this.nManifestsYear;
    }

    /**
     *
     * @return average container per cargo manifest
     */
    public double getAvgManifests(){
        return this.avgContainerPerManifest;
    }
}
