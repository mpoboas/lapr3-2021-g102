package lapr.project.data;

import lapr.project.model.Container;
import lapr.project.model.Ship;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShipStore implements Persistable {
    @Override
    public boolean save(DatabaseConnection databaseConnection, Object object) {
    Ship ship = (Ship) object;
    boolean returnValue = false;
    try {
        saveShipToDatabase(databaseConnection, ship);
        returnValue = true;
    } catch (SQLException ex) {
        Logger.getLogger(ShipStore.class.getName())
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
        Container container = (Container) object;
        try {
            String sqlCommand = "delete from container where idcontainer = ?";
            try (PreparedStatement deleteClientPreparedStatement = connection.prepareStatement(
                    sqlCommand)) {
                deleteClientPreparedStatement.setString(1, container.getId());
                deleteClientPreparedStatement.executeUpdate();
            }
            returnValue = true;
        } catch (SQLException exception) {
            Logger.getLogger(ShipStore.class.getName())
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

                if (shipResultSet.next()) {
                    isShipOnDatabase = true;
                } else {

                    isShipOnDatabase = false;
                }
            }
        }
        return isShipOnDatabase;
    }
    private void insertShipOnDatabase(DatabaseConnection databaseConnection,
                                           Ship ship) throws SQLException {
        String sqlCommand =
                "insert into ship( imo, shipname, callsign, vesseltype, lenght, width, capacity, draft, mmsi ) values (?, ?, ?, ?, ? ,? ,? ,? ,? )";

        executeShipStatementOnDatabase(databaseConnection, ship,
                sqlCommand);
    }
    private void updateShipOnDatabase(DatabaseConnection databaseConnection,
                                           Ship ship) throws SQLException {
        String sqlCommand =
                "update ship set imo = ? , shipname = ?, callsign = ?, vesseltype = ?, lenght = ?, width = ?, capacity = ?, draft = ? where mmsi = ?";

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
            savePortPreparedStatement.setInt(5, ship.getLength());
            savePortPreparedStatement.setInt(6, ship.getWidth());
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
                if (vesselTypeResultSet.next()) {
                    isVesselTypeOnDatabase = true;
                } else {

                    isVesselTypeOnDatabase = false;
                }
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



}
