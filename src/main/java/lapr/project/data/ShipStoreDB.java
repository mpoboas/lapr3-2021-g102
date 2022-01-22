package lapr.project.data;

import lapr.project.model.Ship;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShipStoreDB implements Persistable {
    @Override
    public boolean save(DatabaseConnection databaseConnection, Object object) {
    Ship ship = (Ship) object;
    boolean returnValue = false;
    try {
        saveShipToDatabase(databaseConnection, ship);
        returnValue = true;
    } catch (SQLException ex) {
        Logger.getLogger(ShipStoreDB.class.getName())
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
        Ship ship = (Ship) object;
        try {
            String sqlCommand = "delete from ship where mmsi = ?";
            try (PreparedStatement deleteShipPreparedStatement = connection.prepareStatement(
                    sqlCommand)) {
                deleteShipPreparedStatement.setInt(1, ship.getMMSI());
                deleteShipPreparedStatement.executeUpdate();
            }
            returnValue = true;
        } catch (SQLException exception) {
            Logger.getLogger(ShipStoreDB.class.getName())
                    .log(Level.SEVERE, null, exception);
            databaseConnection
                    .registerError(exception);
            returnValue = false;
        }
        return returnValue;
    }
    private void saveShipToDatabase(DatabaseConnection databaseConnection,
                                         Ship ship) throws SQLException {
        if(!isVesselTypeOnDataBase(databaseConnection, ship)){
            insertVesselTypeOnDatabase(databaseConnection, ship);
        }
        if (isShipOnDatabase(databaseConnection, ship)) {
            updateShipOnDatabase(databaseConnection, ship);
        } else {
            insertShipOnDatabase(databaseConnection, ship);
        }

    }
    private boolean isShipOnDatabase(DatabaseConnection databaseConnection,
                                          Ship ship) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        boolean isShipOnDatabase = false;

        String sqlCommand = "select * from ship where mmsi = ?";

        try(PreparedStatement getShipPreparedStatement =
                    connection.prepareStatement(sqlCommand)) {

            getShipPreparedStatement.setInt(1, ship.getMMSI());

            try (ResultSet shipResultSet = getShipPreparedStatement.executeQuery()) {

                isShipOnDatabase = shipResultSet.next();
            }
        }
        return isShipOnDatabase;
    }
    private void insertShipOnDatabase(DatabaseConnection databaseConnection,
                                           Ship ship) throws SQLException {
        String sqlCommand =
                "insert into ship( imo, shipname, callsign, vesseltype, length, width, capacity, draft, mmsi ) values (?, ?, ?, ?, ? ,? ,? ,? ,? )";

        executeShipStatementOnDatabase(databaseConnection, ship,
                sqlCommand);
    }
    private void updateShipOnDatabase(DatabaseConnection databaseConnection,
                                           Ship ship) throws SQLException {
        String sqlCommand =
                "update ship set imo = ? , shipname = ?, callsign = ?, vesseltype = ?, length = ?, width = ?, capacity = ?, draft = ? where mmsi = ?";

        executeShipStatementOnDatabase(databaseConnection, ship,
                sqlCommand);
    }
    private void executeShipStatementOnDatabase(
            DatabaseConnection databaseConnection,
            Ship ship, String sqlCommand) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        try(PreparedStatement savePortPreparedStatement =
                    connection.prepareStatement(
                            sqlCommand)) {
            savePortPreparedStatement.setString(1, ship.getIMO());
            savePortPreparedStatement.setString(2, ship.getVesselName());
            savePortPreparedStatement.setString(3, ship.getCallSign());
            savePortPreparedStatement.setInt(4, ship.getVesselType());
            savePortPreparedStatement.setDouble(5, ship.getLength());
            savePortPreparedStatement.setDouble(6, ship.getWidth());
            savePortPreparedStatement.setInt(7, ship.getCapacity());
            savePortPreparedStatement.setDouble(8, ship.getDraft());
            savePortPreparedStatement.setInt(9, ship.getMMSI());

            savePortPreparedStatement.executeUpdate();
        }
    }
    private boolean isVesselTypeOnDataBase(DatabaseConnection databaseConnection, Ship ship) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        boolean isVesselTypeOnDatabase = false;

        String sqlCommand = "select * from vesseltype where idvesseltype = ?";

        try(PreparedStatement getVesselTypePreparedStatement =
                    connection.prepareStatement(sqlCommand)) {
            getVesselTypePreparedStatement.setInt(1, ship.getVesselType());
            try (ResultSet vesselTypeResultSet = getVesselTypePreparedStatement.executeQuery()) {
                isVesselTypeOnDatabase = vesselTypeResultSet.next();
            }
        }
        return isVesselTypeOnDatabase;
    }
    private void insertVesselTypeOnDatabase(DatabaseConnection databaseConnection,
                                      Ship ship) throws SQLException {
        String sqlCommand =
                "insert into vesseltype( idvesseltype ) values ( ? )";

        executeVesselTypeStatementOnDatabase(databaseConnection, ship,
                sqlCommand);
    }

    private void executeVesselTypeStatementOnDatabase(DatabaseConnection databaseConnection,
                                                      Ship ship, String sqlCommand)throws SQLException{
        Connection connection = databaseConnection.getConnection();

        try(PreparedStatement saveVesselTypePreparedStatement =
                    connection.prepareStatement(
                            sqlCommand)) {
            saveVesselTypePreparedStatement.setInt(1, ship.getVesselType());
            saveVesselTypePreparedStatement.executeUpdate();
        }
    }

    /**
     * Gets the number of idles days of each ship associated with the given fleet manager, since the beginning of a given year.
     *
     * @param databaseConnection the database connection
     * @param fleetManagerUsername the fleet manager´s username
     * @param year the desired year
     * @return the container´s route
     */
    public String getEachShipNumberOfIdleDays(DatabaseConnection databaseConnection, String fleetManagerUsername, int year) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        try (CallableStatement callableStatement = connection.prepareCall("{? = call fnc_get_each_ship_number_of_idle_days(?, ?)}")) {
            callableStatement.setString(2, fleetManagerUsername);
            callableStatement.setInt(3, year);
            callableStatement.registerOutParameter(1, Types.REF_CURSOR);
            callableStatement.execute();
            ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
            StringBuilder stringBuilder = new StringBuilder();
            while (resultSet.next()) {
                if (resultSet.getObject(3) == null || resultSet.getDouble(3) == 0)
                    stringBuilder.append("The ship with the name ").append(resultSet.getString(2)).append(" does not have idle time.").append("\n");
                else stringBuilder.append("Ship Name: ").append(resultSet.getString(2)).append("\n")
                            .append("Number of idle days since the beginning of the year: ").append(resultSet.getDouble(3)).append("\n");
            }

            return stringBuilder.toString();
        }
    }
}
