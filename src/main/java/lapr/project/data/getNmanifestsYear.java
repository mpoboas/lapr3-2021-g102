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
        String sqlCommand = "SELECT  COUNT(*) FROM CargoManifest";//ID == ID SHIPCAPTAIN AND ARRIVALPORTWAREUSE IN (SELECT * FROM ARRIVAL DEPARTURE WHERE ARRIVALDEPARTUREDATE LIKE YEAR*
        ResultSet s = executeStatementeOnDB(sqlCommand);
        s.next();
        NManifestsYear = s.getInt("COUNT(*)");
    }
    private void getAVGContainerPerManifest() throws SQLException {
        String sqlCommand = "SELECT  COUNT(*) FROM Container ";
        ResultSet s = executeStatementeOnDB(sqlCommand);
        s.next();
        AvgContainerPerManifest = (double)s.getInt("COUNT(*)")/NManifestsYear;
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
