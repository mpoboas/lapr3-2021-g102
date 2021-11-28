package lapr.project.data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class getNmanifestsYear {
    private final int IdShipCaptain;
    private final int year;
    private int NManifestsYear;
    private double AvgContainerPerManifest;
    private final ConnectionFactory cf;
    public getNmanifestsYear(int id, int year) throws IOException, SQLException {
        this.year = year;
        this.IdShipCaptain = id;
        cf = new ConnectionFactory();
        getNmanifestsinyear();
        getAVGContainerPerManifest();
    }
    private void getNmanifestsinyear() throws SQLException {
        String sqlCommand = "SELECT  COUNT(*) FROM CARGOMANIFEST WHERE ELABORATIONDATE > TO_TIMESTAMP('01-01-"+this.year +" 00:00', 'DD-MM-YYYY HH24:mi') AND ELABORATIONDATE < TO_TIMESTAMP('31-12-"+this.year +" 23:59', 'DD-MM-YYYY HH24:mi') AND IDTRIP IN (SELECT IDTRIP FROM CREW WHERE IDEMPLOYEE = "+this.IdShipCaptain +")";
        ResultSet s = executeStatementeOnDB(sqlCommand);
        s.next();
        NManifestsYear = s.getInt("COUNT(*)");
    }
    private void getAVGContainerPerManifest() throws SQLException {
        String sqlCommand = "SELECT AVG(c) FROM (SELECT IDCARGOMANIFEST, COUNT(*) AS c FROM MANIFESTCONTAINER WHERE IDCARGOMANIFEST IN (SELECT IDCARGOMANIFEST FROM CARGOMANIFEST WHERE ELABORATIONDATE > TO_TIMESTAMP('01-01-"+this.year +" 00:00', 'DD-MM-YYYY HH24:mi') AND  ELABORATIONDATE < TO_TIMESTAMP('31-12-"+this.year +" 23:59', 'DD-MM-YYYY HH24:mi') and idtrip in ( select IDTRIP FROM CREW WHERE IDEMPLOYEE = "+this.IdShipCaptain +")) GROUP BY idcargomanifest) ";
        ResultSet s = executeStatementeOnDB(sqlCommand);
        s.next();
        AvgContainerPerManifest = s.getInt("AVG(c)");
    }
    private ResultSet executeStatementeOnDB(String sqlCommand) throws SQLException {
        Connection connection = cf.getDatabaseConnection().getConnection();
        PreparedStatement runcommand = connection.prepareStatement(sqlCommand);
        return runcommand.executeQuery();
    }
    public int getNManifests(){
        return this.NManifestsYear;
    }
    public double getAvgManifests(){
        return this.AvgContainerPerManifest;
    }
}
