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

        try(PreparedStatement getContainerPreparedStatement =
                    connection.prepareStatement(sqlCommand)) {

            getContainerPreparedStatement.setString(1, container.getId());

            try (ResultSet ccontainerResultSet = getContainerPreparedStatement.executeQuery()) {

                if (ccontainerResultSet.next()) {
                    isContainerOnDatabase = true;
                } else {

                    isContainerOnDatabase = false;
                }
            }
        }
        return isContainerOnDatabase;
    }
    private void insertContainerOnDatabase(DatabaseConnection databaseConnection,
                                           Container container) throws SQLException {
        String sqlCommand =
                "insert into container( isocode, idcontainertype, gross, tare, payload, idcontainer) values (?, ?, ?, ?, ?)";

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

        try(PreparedStatement saveContainerPreparedStatement =
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
