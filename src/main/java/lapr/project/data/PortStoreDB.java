package lapr.project.data;

import lapr.project.model.Port;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PortStoreDB implements Persistable{
    @Override
    public boolean save(DatabaseConnection databaseConnection, Object object) {
        Port port = (Port) object;
        boolean returnValue = false;

        try {

            savePortToDatabase(databaseConnection, port);

            //Delete addresses.

            //Post new addresses.

            //Save changes.
            returnValue = true;

        } catch (SQLException ex) {
            Logger.getLogger(PortStoreDB.class.getName())
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
        Port port = (Port) object;

        try {
            String sqlCommand = "delete from port where idportwarehouse = ?";
            try (PreparedStatement deletePortPreparedStatement = connection.prepareStatement(
                    sqlCommand)) {
                deletePortPreparedStatement.setInt(1, port.getid());
                deletePortPreparedStatement.executeUpdate();
            }

            returnValue = true;

        } catch (SQLException exception) {
            Logger.getLogger(PortStoreDB.class.getName())
                    .log(Level.SEVERE, null, exception);
            databaseConnection
                    .registerError(exception);
            returnValue = false;
        }

        return returnValue;
    }

    /**
     * Checks is a cliente is already registered on the datase. If the port
     * is registered, it updates it. If it is not, it inserts a new one.
     *
     * @param databaseConnection
     * @param port
     * @throws SQLException
     */
    private void savePortToDatabase(DatabaseConnection databaseConnection,
                                      Port port) throws SQLException {

        if (isPortOnDatabase(databaseConnection, port)) {
            updatePortOnDatabase(databaseConnection, port);
        } else {
            insertPositionOnDB(databaseConnection, port);
            insertPortOnDatabase(databaseConnection, port);
        }

    }

    /**
     * Checks if a port is registered on the Database by its ID.
     *
     * @param databaseConnection
     * @param port
     * @return True if the cliente is registered, False if otherwise.
     * @throws SQLException
     */
    private boolean isPortOnDatabase(DatabaseConnection databaseConnection,
                                       Port port) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        boolean isPortOnDatabase = false;

        String sqlCommand = "select * from portwarehouse where idportwarehouse = ?";

        try(PreparedStatement getPortPreparedStatement =
                connection.prepareStatement(sqlCommand)) {

            getPortPreparedStatement.setInt(1, port.getid());

            try (ResultSet portResultSet = getPortPreparedStatement.executeQuery()) {

                if (portResultSet.next()) {
                    // if port already exists in the database
                    isPortOnDatabase = true;
                } else {

                    // if port does not exist in the database
                    isPortOnDatabase = false;
                }
            }
        }
        return isPortOnDatabase;
    }








    private void insertPortOnDatabase(DatabaseConnection databaseConnection,
                                        Port port) throws SQLException {
        Connection connection = databaseConnection.getConnection();
        String sqlCommand =
                "insert into portwarehouse( idportwarehouse, name, continent, country, idposition) values (?, ?, ?, ?, ?)";

        executePortStatementOnDatabase(databaseConnection, port,
                sqlCommand);
    }
    private void insertPositionOnDB(DatabaseConnection databaseConnection,
                                    Port port) throws SQLException{
        Connection connection = databaseConnection.getConnection();
        String sqlCommand = "insert into position( latitude, longitude) values (?, ?)";
        executePositionStatementOnDatabase(databaseConnection, port,
                sqlCommand);
    }


    /**
     * Updates an existing port record on the database.
     *
     * @param databaseConnection
     * @param port
     * @throws SQLException
     */
    private void updatePortOnDatabase(DatabaseConnection databaseConnection,
                                        Port port) throws SQLException {
        Connection connection = databaseConnection.getConnection();
        String sqlCommand =
                "update portwarehouse set name = ? , continent = ?, country = ? where idportwarehouse = ?";

        updatePortStatementOnDatabase(databaseConnection, port,
                sqlCommand);
    }

    private void updatePortStatementOnDatabase(
            DatabaseConnection databaseConnection,
            Port port, String sqlCommand) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        try(PreparedStatement savePortPreparedStatement =
                connection.prepareStatement(
                        sqlCommand)) {
            savePortPreparedStatement.setString(1, port.getName());
            savePortPreparedStatement.setString(2, port.getContinent());
            savePortPreparedStatement.setString(3, port.getCountry());
            savePortPreparedStatement.setInt(4, port.getid());
            savePortPreparedStatement.executeUpdate();
        }

    }

    /**
     * Executes the save Client Statement.
     *
     * @param databaseConnection
     * @param port
     * @throws SQLException
     */
    private void executePortStatementOnDatabase(
            DatabaseConnection databaseConnection,
            Port port, String sqlCommand) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        try(PreparedStatement savePortPreparedStatement =
                connection.prepareStatement(
                        sqlCommand)) {
            savePortPreparedStatement.setInt(1, port.getid());
            savePortPreparedStatement.setString(2, port.getName());
            savePortPreparedStatement.setString(3, port.getContinent());
            savePortPreparedStatement.setString(4, port.getCountry());
            savePortPreparedStatement.setInt(5, getPositionid(databaseConnection, port));
            savePortPreparedStatement.executeUpdate();
        }
        try(PreparedStatement portsTable = connection.prepareStatement("insert into port( idportwarehouse ) values ( ? )")){
            portsTable.setInt(1, port.getid());
            portsTable.executeUpdate();
        }
    }
    private void executePositionStatementOnDatabase(DatabaseConnection databaseConnection,
                                                    Port port, String sqlCommand) throws SQLException{
        Connection connection = databaseConnection.getConnection();
        try(PreparedStatement savePortPreparedStatement =
                connection.prepareStatement(
                        sqlCommand)) {
            savePortPreparedStatement.setDouble(1, port.getLatitude());
            savePortPreparedStatement.setDouble(2, port.getLongitude());
            savePortPreparedStatement.executeUpdate();
        }
    }

    private int getPositionid(DatabaseConnection databaseConnection, Port port) throws SQLException {
        int positionId;
        String sqlCommand = "SELECT IDPOSITION FROM POSITION WHERE LATITUDE = " + port.getLatitude() +" AND LONGITUDE = " +port.getLongitude();
        Connection connection = databaseConnection.getConnection();
        try(PreparedStatement runcommand = connection.prepareStatement(sqlCommand)) {
            try (ResultSet rs = runcommand.executeQuery()) {
                rs.next();
                positionId = rs.getInt(1);
            }
        }

        return positionId;
    }
}
