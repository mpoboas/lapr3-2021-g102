package lapr.project.data;

import lapr.project.model.Container;
import lapr.project.model.Trips;

import java.sql.*;
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
        }

    }
    private boolean isTripOnDatabase(DatabaseConnection databaseConnection,
                                          Trips trip) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        boolean isTripOnDatabase = false;

        String sqlCommand = "select * from container where idcontainer = ?";

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
}
