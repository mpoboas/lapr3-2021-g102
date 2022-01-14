package lapr.project.data;

import lapr.project.model.CargoManifest;
import lapr.project.model.Container;
import lapr.project.model.Trips;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TripStore implements Persistable{
    @Override
    public boolean save(DatabaseConnection databaseConnection, Object object) {
        Trips trips = (Trips) object;
        boolean returnValue = false;
        try {
            saveTripToDatabase(databaseConnection, trips);
            returnValue = true;
        } catch (SQLException ex) {
            Logger.getLogger(TripStore.class.getName())
                    .log(Level.SEVERE, null, ex);
            databaseConnection.registerError(ex);
            returnValue = false;
        }
        return returnValue;
    }
    @Override
    public boolean delete(DatabaseConnection databaseConnection,
                          Object object) {
        boolean returnValue = false;
        Connection connection = databaseConnection.getConnection();
        Trips trip = (Trips) object;
        try {
            String sqlCommand = "delete from trip where idtrip = ?";
            try (PreparedStatement deleteTripPreparedStatement = connection.prepareStatement(
                    sqlCommand)) {
                deleteTripPreparedStatement.setInt(1, trip.getId());
                deleteTripPreparedStatement.executeUpdate();
            }
            returnValue = true;
        } catch (SQLException exception) {
            Logger.getLogger(TripStore.class.getName())
                    .log(Level.SEVERE, null, exception);
            databaseConnection
                    .registerError(exception);
            returnValue = false;
        }
        return returnValue;
    }
    private void saveTripToDatabase(DatabaseConnection databaseConnection,
                                         Trips trip) throws SQLException {

        if (isTripOnDatabase(databaseConnection, trip)) {
            updateTripOnDatabase(databaseConnection, trip);
        } else {
            insertTripOnDatabase(databaseConnection, trip);
            saveArrivalDeparture(databaseConnection,trip);
        }

    }
    private boolean isTripOnDatabase(DatabaseConnection databaseConnection,
                                          Trips trip) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        boolean isTripOnDatabase = false;

        String sqlCommand = "select * from trip where idtrip = ?";

        try(PreparedStatement getTripPreparedStatement =
                    connection.prepareStatement(sqlCommand)) {

            getTripPreparedStatement.setInt(1, trip.getId());

            try (ResultSet tripResultSet = getTripPreparedStatement.executeQuery()) {

                if (tripResultSet.next()) {
                    isTripOnDatabase = true;
                } else {

                    isTripOnDatabase = false;
                }
            }
        }
        return isTripOnDatabase;
    }
    private void insertTripOnDatabase(DatabaseConnection databaseConnection,
                                  Trips trip) throws SQLException {
        String sqlCommand =
                "insert into trip(idportwarehousedeparture, idportwarehousearrival, idvehicle, enddate, idtrip) values (?, ?, ?, ?, ?)";

        executeTripStatementOnDatabase(databaseConnection, trip,
                sqlCommand);
    }
    private void updateTripOnDatabase(DatabaseConnection databaseConnection,
                                           Trips trip) throws SQLException {
        String sqlCommand =
                "update trip set idportwarehousedeparture = ? , idportwarehousearrival = ?, idvehicle = ? , enddate = ? where idtrip = ?";

        executeTripStatementOnDatabase(databaseConnection, trip,
                sqlCommand);
    }
    private void executeTripStatementOnDatabase(
            DatabaseConnection databaseConnection,
            Trips trip, String sqlCommand) throws SQLException {

        Connection connection = databaseConnection.getConnection();

        try(PreparedStatement saveContainerPreparedStatement =
                    connection.prepareStatement(
                            sqlCommand)) {
            saveContainerPreparedStatement.setInt(1, trip.getIdPortWarehouseDeparture());
            saveContainerPreparedStatement.setInt(2, trip.getIdPortWarehouseArrival());
            saveContainerPreparedStatement.setInt(3, trip.getIdVehicle());
            saveContainerPreparedStatement.setDate(4, new Date(trip.getEndDate().getTime()));
            saveContainerPreparedStatement.setInt(5, trip.getId());
            saveContainerPreparedStatement.executeUpdate();
        }
    }

    private void saveArrivalDeparture(DatabaseConnection databaseConnection, Trips t) throws SQLException {
        String sqlCommand = "Insert into arrivaldeparture(idtrip, idportwarehousedeparture, idportwarehousearrival, departuredate, arrivaldate, estimatedarrivaldate, idcargomanifestload, idcargomanifestunload) values ( ?, ?, ? ,? ,?, ?, ?, ? )";
        executeADstatementdOnDatabase(databaseConnection,t , sqlCommand);
    }
    private void executeADstatementdOnDatabase(DatabaseConnection databaseConnection,
                                               Trips t, String sqlCommand) throws SQLException{
        Connection connection = databaseConnection.getConnection();
        try(PreparedStatement saveADPreparedStatement =
                    connection.prepareStatement(
                            sqlCommand)) {
            for(int i = 0; i< t.getADLIst().size(); i++){
                saveADPreparedStatement.setInt(1, t.getADLIst().get(i).getIdTrip());
                saveADPreparedStatement.setInt(2, t.getADLIst().get(i).getPortWareDeparture());
                saveADPreparedStatement.setInt(3, t.getADLIst().get(i).getPortWarehouseArrival());
                saveADPreparedStatement.setTimestamp(4, t.getADLIst().get(i).getDepartureDate());
                saveADPreparedStatement.setTimestamp(5, t.getADLIst().get(i).getArrivalDate());
                saveADPreparedStatement.setTimestamp(6, t.getADLIst().get(i).getEstimatedArrivalDate());
                saveADPreparedStatement.setInt(7, t.getADLIst().get(i).getCargoManifestIdLoad());
                saveADPreparedStatement.setInt(8, t.getADLIst().get(i).getCargoManifestIdUnload());
                saveADPreparedStatement.executeUpdate();
            }
        }
    }

    /**
     * Gets the list of voyages of a given ship that the occupancy rate is below a threshold
     * @param shipMMSI the ship identification
     * @param cf connection
     * @return the list of voyages
     * @throws SQLException
     */
    public List<String> getShipVoyagesBelowThreshold(int shipMMSI,double threshold, ConnectionFactory cf) throws SQLException{
        List<String> list = new ArrayList<>();
        Connection conn = cf.getDatabaseConnection().getConnection();
        try(CallableStatement statement = conn.prepareCall("{ ? = call fnc_get_voyages_below_threshold(?,?)}")){
            statement.registerOutParameter(1,Types.REF_CURSOR);
            statement.setInt(2,shipMMSI);
            statement.setDouble(3,threshold);
            statement.execute();
            ResultSet rs = statement.getObject(1,ResultSet.class);
            while (rs.next()){
                String row = "Start Date: " + rs.getTimestamp(1) + " | "
                        + "Start Place ID: " + rs.getInt(2) + " | "
                        + "Start Place Name: " + rs.getString(3) + " | "
                        + "End Date: " + rs.getTimestamp(4) + " | "
                        + "End Place ID: " + rs.getInt(5) + " | "
                        + "End Place Name: " + rs.getString(6);
                list.add(row);
            }
        }
        return list;
    }

}
