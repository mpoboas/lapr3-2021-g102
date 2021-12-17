package lapr.project.data;

import lapr.project.model.CargoManifest;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CargoManifestStore implements Persistable{
    @Override
    public boolean save(DatabaseConnection databaseConnection, Object object) {
        CargoManifest cm = (CargoManifest) object;
        boolean returnValue = false;
        try {
            saveCargoManifestToDatabase(databaseConnection, cm);

            returnValue = true;
        } catch (SQLException ex) {
            Logger.getLogger(CargoManifestStore.class.getName())
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
        CargoManifest cm = (CargoManifest) object;
        try {
            String sqlCommand = "delete from cargomanifest where idcargomanifest = ?";
            try (PreparedStatement deleteCargoManifestPreparedStatement = connection.prepareStatement(
                    sqlCommand)) {
                deleteCargoManifestPreparedStatement.setInt(1, cm.getId());
                deleteCargoManifestPreparedStatement.executeUpdate();
            }
            returnValue = true;
        } catch (SQLException exception) {
            Logger.getLogger(CargoManifestStore.class.getName())
                    .log(Level.SEVERE, null, exception);
            databaseConnection
                    .registerError(exception);
            returnValue = false;
        }
        return returnValue;
    }
    private void saveCargoManifestToDatabase(DatabaseConnection databaseConnection,
                                         CargoManifest cm) throws SQLException {

        if (isCargoManifestOnDatabase(databaseConnection, cm)) {
            updateCargoManifestOnDatabase(databaseConnection, cm);
        } else {
            insertCargoManifestOnDatabase(databaseConnection, cm);
            saveContainers(databaseConnection,cm);
        }

    }
    private boolean isCargoManifestOnDatabase(DatabaseConnection databaseConnection,
                                          CargoManifest cm) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        boolean isCargomanifestOnDatabase = false;

        String sqlCommand = "select * from cargomanifest where idcargomanifest = ?";

        try(PreparedStatement getCargoManifestPreparedStatement =
                    connection.prepareStatement(sqlCommand)) {

            getCargoManifestPreparedStatement.setInt(1, cm.getId());

            try (ResultSet cargoManifestResultSet = getCargoManifestPreparedStatement.executeQuery()) {

                if (cargoManifestResultSet.next()) {
                    // if port already exists in the database
                    isCargomanifestOnDatabase = true;
                } else {

                    // if port does not exist in the database
                    isCargomanifestOnDatabase = false;
                }
            }
        }
        return isCargomanifestOnDatabase;
    }
    private void insertCargoManifestOnDatabase(DatabaseConnection databaseConnection,
                                           CargoManifest cm) throws SQLException {
        String sqlCommand =
                "insert into cargomanifest( elaborationdate, idcargomanifesttype, idcargomanifest) values (?, ?, ?)";

        executeCargoManifestStatementOnDatabase(databaseConnection, cm,
                sqlCommand);
    }
    private void updateCargoManifestOnDatabase(DatabaseConnection databaseConnection,
                                           CargoManifest cm) throws SQLException {
        String sqlCommand =
                "update cargomanifest set elaborationdate = ? , type = ? where idcargomanifest = ?";

        executeCargoManifestStatementOnDatabase(databaseConnection, cm,
                sqlCommand);
    }
    private void executeCargoManifestStatementOnDatabase(
            DatabaseConnection databaseConnection,
            CargoManifest cm, String sqlCommand) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        try(PreparedStatement saveCargoManifestPreparedStatement =
                    connection.prepareStatement(
                            sqlCommand)) {

            saveCargoManifestPreparedStatement.setTimestamp(1, new Timestamp(cm.getDate().getTime()));
            saveCargoManifestPreparedStatement.setInt(2, getTypeId(cm));
            saveCargoManifestPreparedStatement.setInt(3, cm.getId());
            saveCargoManifestPreparedStatement.executeUpdate();
        }
    }
    private int getTypeId(CargoManifest cm){
        int id = 1;
        if(cm.getType().equalsIgnoreCase("unload"))
            return 2;
        if(cm.getType().equalsIgnoreCase("load"))
            return 1;
        return id;
    }
    private void saveContainers(DatabaseConnection databaseConnection, CargoManifest cm) throws SQLException{
        String sqlCommand = "insert into manifestcontainer(idcargomanifest, idcontainer, containerpositionx,containerpositiony, containerpositionz) values ( ? , ? , ? , ? , ?) ";
            executeContainerStatementOnDatabase(databaseConnection,cm,sqlCommand);




    }
    private void executeContainerStatementOnDatabase(
            DatabaseConnection databaseConnection,
            CargoManifest cm, String sqlCommand) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        try(PreparedStatement saveContainerPositionPreparedStatement =
                    connection.prepareStatement(
                            sqlCommand)) {
            for(int i = 0; i < cm.getContainerList().size(); i++) {
                saveContainerPositionPreparedStatement.setInt(1, cm.getId());
                saveContainerPositionPreparedStatement.setString(2, cm.getContainerList().get(i).getFirst().getId());
                saveContainerPositionPreparedStatement.setInt(3, cm.getContainerList().get(i).getSecond().x);
                saveContainerPositionPreparedStatement.setInt(4, cm.getContainerList().get(i).getSecond().y);
                saveContainerPositionPreparedStatement.setInt(5, cm.getContainerList().get(i).getSecond().z);
                saveContainerPositionPreparedStatement.executeUpdate();
            }
        }
    }


}
