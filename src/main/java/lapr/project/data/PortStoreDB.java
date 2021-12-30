package lapr.project.data;

import lapr.project.model.Port;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PortStoreDB implements Persistable {
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

        try (PreparedStatement getPortPreparedStatement =
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
                "insert into portwarehouse( idportwarehouse, name, alpha2codecountry, idposition, capacity) values (?, ?, ?,  ? , ?)";

        executePortStatementOnDatabase(databaseConnection, port,
                sqlCommand);
    }

    private void insertPositionOnDB(DatabaseConnection databaseConnection,
                                    Port port) throws SQLException {
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
                "update portwarehouse set name = ? , alpha2codecountry = ? , capacity = ? where idportwarehouse = ?";

        updatePortStatementOnDatabase(databaseConnection, port,
                sqlCommand);
    }

    private void updatePortStatementOnDatabase(
            DatabaseConnection databaseConnection,
            Port port, String sqlCommand) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        try (PreparedStatement savePortPreparedStatement =
                     connection.prepareStatement(
                             sqlCommand)) {
            savePortPreparedStatement.setString(1, port.getName());
            savePortPreparedStatement.setString(2, port.getCountry());
            savePortPreparedStatement.setInt(3, port.getCapacity());
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

        try (PreparedStatement savePortPreparedStatement =
                     connection.prepareStatement(
                             sqlCommand)) {
            savePortPreparedStatement.setInt(1, port.getid());
            savePortPreparedStatement.setString(2, port.getName());
            savePortPreparedStatement.setString(3, port.getCountry());
            savePortPreparedStatement.setInt(4, getPositionid(databaseConnection, port));
            savePortPreparedStatement.setInt(5, port.getCapacity());
            savePortPreparedStatement.executeUpdate();
        }
    }

    private void executePositionStatementOnDatabase(DatabaseConnection databaseConnection,
                                                    Port port, String sqlCommand) throws SQLException {
        Connection connection = databaseConnection.getConnection();
        try (PreparedStatement savePortPreparedStatement =
                     connection.prepareStatement(
                             sqlCommand)) {
            savePortPreparedStatement.setDouble(1, port.getLatitude());
            savePortPreparedStatement.setDouble(2, port.getLongitude());
            savePortPreparedStatement.executeUpdate();
        }
    }

    private int getPositionid(DatabaseConnection databaseConnection, Port port) throws SQLException {
        int positionId;
        String sqlCommand = "SELECT IDPOSITION FROM POSITION WHERE LATITUDE = " + port.getLatitude() + " AND LONGITUDE = " + port.getLongitude();
        Connection connection = databaseConnection.getConnection();
        try (PreparedStatement runcommand = connection.prepareStatement(sqlCommand)) {
            try (ResultSet rs = runcommand.executeQuery()) {
                rs.next();
                positionId = rs.getInt(1);
            }
        }

        return positionId;
    }

    public List<Port> getPortsFromDatabase(DatabaseConnection databaseConnection) {
        List<Port> ports = new ArrayList<>();
        try (Statement statement = databaseConnection.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery("select portwarehouse.idportwarehouse, portwarehouse.name, continent.name ,country.name, position.latitude, position.longitude, portwarehouse.capacity from portwarehouse inner join position on position.idposition = portwarehouse.idposition inner join country on country.alpha2codecountry = portwarehouse.alpha2codecountry inner join continent on continent.alpha2codecontinent=country.alpha2codecontinent")) {
            while (resultSet.next()) {
                ports.add(new Port(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getDouble(6),
                        resultSet.getDouble(5), resultSet.getInt(7)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ports;
    }

    /**
     * Gets an instance of Port by a id.
     *
     * @param databaseConnection the database connection
     * @param idPortWarehouse    the id of the desired portwarehouse
     */
    public Port getPortByIdFromDatabase(DatabaseConnection databaseConnection, int idPortWarehouse) {
        Port port = null;
        try (Statement statement = databaseConnection.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery("select portwarehouse.idPortWarehouse, portwarehouse.name, continent.name, country.name, position.longitude, position.latitude, portwarehouse.capacity from portwarehouse inner join position on position.idposition = portwarehouse.idposition inner join country on country.alpha2CodeCountry = portwarehouse.alpha2CodeCountry inner join continent on continent.alpha2CodeContinent = country.alpha2CodeContinent where portwarehouse.idportwarehouse = " + idPortWarehouse);) {
            while (resultSet.next()) {
                port = new Port(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getDouble(5), resultSet.getDouble(6), resultSet.getInt(7));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return port;
    }

    /**
     * Gets the occupancy rate and an estimate of the containers leaving the warehouse during the next 30 days of every warehouse that a port manager has access to.
     *
     * @param databaseConnection the database connection
     * @param portManagerId      the port manager´s identifier
     * @return the container´s route
     */
    public String getEachWarehouseOccupancyRateAndContainersLeaving30Days(DatabaseConnection databaseConnection, int portManagerId) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        try (CallableStatement callableStatementOccupancyRate = connection.prepareCall("{? = call fnc_get_each_warehouse_occupancy_rate(?)}")) {
            callableStatementOccupancyRate.setInt(2, portManagerId);
            callableStatementOccupancyRate.registerOutParameter(1, Types.REF_CURSOR);
            callableStatementOccupancyRate.execute();
            ResultSet resultSetOccupancyRate = (ResultSet) callableStatementOccupancyRate.getObject(1);
            StringBuilder stringBuilder = new StringBuilder();
            while (resultSetOccupancyRate.next()) {
                int warehouseId = resultSetOccupancyRate.getInt(1);
                String warehouseName = resultSetOccupancyRate.getString(2);

                stringBuilder.append("Warehouse ID: ").append(warehouseId).append("\n")
                        .append("Warehouse: ").append(warehouseName).append("\n")
                        .append("Warehouse occupancy rate ").append(resultSetOccupancyRate.getDouble(3)).append("%\n");

                try (CallableStatement callableStatementContainersLeaving = connection.prepareCall("{? = call fnc_get_containers_leaving_next_30_days(?)}")) {
                    callableStatementContainersLeaving.setInt(2, warehouseId);
                    callableStatementContainersLeaving.registerOutParameter(1, Types.REF_CURSOR);
                    callableStatementContainersLeaving.execute();
                    ResultSet resultSetContainersLeaving = (ResultSet) callableStatementContainersLeaving.getObject(1);
                    if (!resultSetContainersLeaving.next())
                        stringBuilder.append("No containers are leaving ").append(warehouseName).append(" in the next 30 days.\n");
                    else {
                        while (resultSetContainersLeaving.next()) {
                            stringBuilder.append("Container ID: ").append(resultSetContainersLeaving.getString(1)).append("\n")
                                    .append("Container gross weight: ").append(resultSetContainersLeaving.getDouble(2)).append("\n")
                                    .append("Container type: ").append(resultSetContainersLeaving.getString(3)).append("\n");
                        }
                    }
                    stringBuilder.append("\n");
                }
            }

            return stringBuilder.toString();
        }
    }
}
