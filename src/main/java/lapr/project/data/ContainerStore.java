package lapr.project.data;

import lapr.project.model.Container;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContainerStore implements Persistable {
    @Override
    public boolean save(DatabaseConnection databaseConnection, Object object) {
        Container container = (Container) object;
        boolean returnValue = false;
        try {
            saveContainerToDatabase(databaseConnection, container);
            //Delete addresses.
            //Post new addresses.
            //Save changes.
            returnValue = true;
        } catch (SQLException ex) {
            Logger.getLogger(ContainerStore.class.getName())
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
            try (PreparedStatement deleteContainerPreparedStatement = connection.prepareStatement(
                    sqlCommand)) {
                deleteContainerPreparedStatement.setString(1, container.getId());
                deleteContainerPreparedStatement.executeUpdate();
            }
            returnValue = true;
        } catch (SQLException exception) {
            Logger.getLogger(ContainerStore.class.getName())
                    .log(Level.SEVERE, null, exception);
            databaseConnection
                    .registerError(exception);
            returnValue = false;
        }
        return returnValue;
    }

    private void saveContainerToDatabase(DatabaseConnection databaseConnection,
                                         Container container) throws SQLException {

        if (isContainerOnDatabase(databaseConnection, container)) {
            updateContainerOnDatabase(databaseConnection, container);
        } else {
            insertContainerOnDatabase(databaseConnection, container);
        }

    }

    private boolean isContainerOnDatabase(DatabaseConnection databaseConnection,
                                          Container container) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        boolean isContainerOnDatabase = false;

        String sqlCommand = "select * from container where idcontainer = ?";

        try (PreparedStatement getContainerPreparedStatement =
                     connection.prepareStatement(sqlCommand)) {

            getContainerPreparedStatement.setString(1, container.getId());

            try (ResultSet ccontainerResultSet = getContainerPreparedStatement.executeQuery()) {

                isContainerOnDatabase = ccontainerResultSet.next();
            }
        }
        return isContainerOnDatabase;
    }

    private void insertContainerOnDatabase(DatabaseConnection databaseConnection,
                                           Container container) throws SQLException {
        String sqlCommand =
                "insert into container( isocode, idcontainertype, gross, tare, payload, idcontainer) values (?, ?, ?, ?, ?, ?)";

        executeContainerStatementOnDatabase(databaseConnection, container,
                sqlCommand);
    }

    private void updateContainerOnDatabase(DatabaseConnection databaseConnection,
                                           Container container) throws SQLException {
        String sqlCommand =
                "update container set isocode = ? , idcontainertype = ?, gross = ? , tare = ? , payload = ? where id = ?";

        executeContainerStatementOnDatabase(databaseConnection, container,
                sqlCommand);
    }

    private void executeContainerStatementOnDatabase(
            DatabaseConnection databaseConnection,
            Container container, String sqlCommand) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        try (PreparedStatement saveContainerPreparedStatement =
                     connection.prepareStatement(
                             sqlCommand)) {
            saveContainerPreparedStatement.setString(1, container.getIsoCode());
            saveContainerPreparedStatement.setInt(2, getTypeId(container));
            saveContainerPreparedStatement.setInt(3, container.getGross());
            saveContainerPreparedStatement.setInt(4, container.getTare());
            saveContainerPreparedStatement.setInt(5, container.getPayload());
            saveContainerPreparedStatement.setString(6, container.getId());


            saveContainerPreparedStatement.executeUpdate();
        }
    }

    private int getTypeId(Container container) {
        int id = 3;
        if (container.getType().equalsIgnoreCase("normal"))
            return 3;
        if (container.getType().equalsIgnoreCase("refrigerated5"))
            return 2;
        if (container.getType().equalsIgnoreCase("refrigerated7"))
            return 1;
        return id;
    }

    /**
     * Gets the route of a specified container, leased by a specified client.
     *
     * @param databaseConnection the database connection
     * @param clientUsername           the client´s username
     * @param containerId        the container´s identifier
     * @return the container´s route
     */
    public String getClientContainerRoute(DatabaseConnection databaseConnection, String clientUsername, String containerId) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        try (CallableStatement callableStatement = connection.prepareCall("{? = call fnc_get_client_container_route(?, ?)}")) {
            callableStatement.setString(2, clientUsername);
            callableStatement.setString(3, containerId);
            callableStatement.registerOutParameter(1, Types.REF_CURSOR);
            callableStatement.execute();
            ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Container with the identifier ").append(containerId).append(", leased by the client ").append(clientUsername).append(":\n\n");
            int count = 0;
            while (resultSet.next()) {
                if (resultSet.getTimestamp(6) == null) {
                    stringBuilder.append("Container is on board of a ").append(resultSet.getString(8))
                            .append("in the middle of the ocean. The expected date and time of arrival at the PortWarehouse ").append(resultSet.getString(4))
                            .append(" is ").append(resultSet.getTimestamp(7)).append("\n");
                } else if (resultSet.getInt(1) == resultSet.getInt(2)) {
                    stringBuilder.append("Container was shipped at the PortWarehouse ").append(resultSet.getString(3)).append(" at ").append(resultSet.getTimestamp(6)).append("\n");
                } else {
                    count++;
                    stringBuilder.append("Movement ").append(count).append(":\n")
                            .append("Departure PortWarehouse: ").append(resultSet.getString(3)).append("\n")
                            .append("Arrival PortWarehouse ID: ").append(resultSet.getString(4)).append("\n")
                            .append("Date and time of departure: ").append(resultSet.getTimestamp(5)).append("\n")
                            .append("Date and time of arrival: ").append(resultSet.getTimestamp(6)).append("\n")
                            .append("Date and time of expected arrival: ").append(resultSet.getTimestamp(7)).append("\n")
                            .append("Mean of transport: ").append(resultSet.getString(8)).append("\n\n");
                }
            }

            return stringBuilder.toString();
        }
    }
}
