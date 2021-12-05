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
    private ConnectionFactory cf;

    /**
     *
     * @param id ship catain id
     * @param year year that ship captain inquired
     */
    public GetNmanifestsYear(int id, int year) {
        this.year = year;
        this.idShipCaptain = id;

    }
    public void runDBCommand() throws SQLException, IOException {
        this.cf = new ConnectionFactory();
        getNmanifestsinyear();
        getAVGContainerPerManifest();

    }
        /**
         *
         * @throws SQLException sql exception
         */
    private void getNmanifestsinyear() throws SQLException {
        String sqlCommand = "SELECT  COUNT(*) FROM CARGOMANIFEST WHERE EXTRACT(YEAR FROM ELABORATIONDATE ) = "+this.year+" AND (IDCARGOMANIFEST IN (SELECT IDCARGOMANIFESTLOAD  FROM ARRIVALDEPARTURE WHERE IDTRIP IN (SELECT IDTRIP FROM CREW WHERE IDEMPLOYEE = "+this.idShipCaptain +")) OR IDCARGOMANIFEST IN (SELECT IDCARGOMANIFESTUNLOAD  FROM ARRIVALDEPARTURE WHERE IDTRIP IN (SELECT IDTRIP FROM CREW WHERE IDEMPLOYEE = "+this.idShipCaptain +")))";
        nManifestsYear = executeStatementeOnDB(sqlCommand);
    }

    /**
     *
     * @throws SQLException sql exception
     */
    private void getAVGContainerPerManifest() throws SQLException {
        String sqlCommand = "SELECT AVG(c) FROM (SELECT IDCARGOMANIFEST, COUNT(*) AS c FROM MANIFESTCONTAINER WHERE IDCARGOMANIFEST IN (SELECT IDCARGOMANIFEST FROM CARGOMANIFEST WHERE EXTRACT(YEAR FROM ELABORATIONDATE ) = "+this.year+" AND (IDCARGOMANIFEST IN (SELECT IDCARGOMANIFESTLOAD  FROM ARRIVALDEPARTURE WHERE IDTRIP IN (SELECT IDTRIP FROM CREW WHERE IDEMPLOYEE = "+this.idShipCaptain +")) OR IDCARGOMANIFEST IN (SELECT IDCARGOMANIFESTUNLOAD  FROM ARRIVALDEPARTURE WHERE IDTRIP IN (SELECT IDTRIP FROM CREW WHERE IDEMPLOYEE = "+this.idShipCaptain +")))) GROUP BY IDCARGOMANIFEST) ";

        avgContainerPerManifest = executeStatementeOnDB(sqlCommand);
    }

    /**
     *
     * @param sqlCommand command to be executed from database
     * @return result set from databse
     * @throws SQLException sql exception
     */
    private int executeStatementeOnDB(String sqlCommand) throws SQLException {
        int result;
        Connection connection = cf.getDatabaseConnection().getConnection();
        try (PreparedStatement runcommand = connection.prepareStatement(sqlCommand); ResultSet rs = runcommand.executeQuery()) {
            rs.next();
            result = rs.getInt(1);
        }
        return result;
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
