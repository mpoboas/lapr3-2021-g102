package lapr.project.data;

import lapr.project.model.Container;
import lapr.project.model.Port;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContainerStore implements Persistable{
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
            Logger.getLogger(PortStore.class.getName())
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
            Logger.getLogger(PortStore.class.getName())
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

        try(PreparedStatement getClientsPreparedStatement =
                    connection.prepareStatement(sqlCommand)) {

            getClientsPreparedStatement.setString(1, container.getId());

            try (ResultSet clientsResultSet = getClientsPreparedStatement.executeQuery()) {

                if (clientsResultSet.next()) {
                    // if port already exists in the database
                    isContainerOnDatabase = true;
                } else {

                    // if port does not exist in the database
                    isContainerOnDatabase = false;
                }
            }
        }
        return isContainerOnDatabase;
    }
    private void insertContainerOnDatabase(DatabaseConnection databaseConnection,
                                           Container container) throws SQLException {
        String sqlCommand =
                "insert into container(idcontainer, isocode, idcontainertype, gross, tare, payload) values (?, ?, ?, ?, ?);";

        executePortStatementOnDatabase(databaseConnection, container,
                sqlCommand);
    }
    private void updateContainerOnDatabase(DatabaseConnection databaseConnection,
                                           Container container) throws SQLException {
        String sqlCommand =
                "update portwarehouse set name = ? , continent = ?, country = ? where id = ?";

        executePortStatementOnDatabase(databaseConnection, container,
                sqlCommand);
    }
    private void executePortStatementOnDatabase(
            DatabaseConnection databaseConnection,
            Container container, String sqlCommand) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        try(PreparedStatement savePortPreparedStatement =
                    connection.prepareStatement(
                            sqlCommand)) {
            savePortPreparedStatement.setString(1, container.getId());
            savePortPreparedStatement.setString(2, container.getIsoCode());
            savePortPreparedStatement.setInt(3, getTypeId(container));
            savePortPreparedStatement.setInt(4, container.getGross());
            savePortPreparedStatement.setInt(5, container.getTare());
            savePortPreparedStatement.setInt(5, container.getPayload());

            savePortPreparedStatement.executeUpdate();
        }
    }
    private int getTypeId(Container container){
        int id = 3;
        if(container.getType().equalsIgnoreCase("normal"))
            return 3;
        if(container.getType().equalsIgnoreCase("refrigerated5"))
            return 2;
        if(container.getType().equalsIgnoreCase("refrigerated7"))
            return 1;
        return id;
    }

}
