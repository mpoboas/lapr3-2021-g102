package lapr.project.data;

import lapr.project.model.SeaDist;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SeadistStoreDB implements Persistable {

    /**
     * Saves an instance of Seadist on database.
     *
     * @param databaseConnection the connection
     * @param object             the object to save
     * @return true if it was successfully added, false if not
     */
    @Override
    public boolean save(DatabaseConnection databaseConnection, Object object) {
        SeaDist seadist = (SeaDist) object;
        boolean returnValue;

        try {
            saveSeadistToDatabase(databaseConnection, seadist);
            returnValue = true;
        } catch (SQLException ex) {
            Logger.getLogger(SeadistStoreDB.class.getName())
                    .log(Level.SEVERE, null, ex);
            databaseConnection.registerError(ex);
            returnValue = false;
        }
        return returnValue;
    }

    /**
     * Deletes an instance of Seadist from the database.
     *
     * @param databaseConnection the connection
     * @param object             the object to delete
     * @return true if it was successfully deleted, false if not
     */
    @Override
    public boolean delete(DatabaseConnection databaseConnection, Object object) {
        SeaDist seadist = (SeaDist) object;
        boolean returnValue;

        try {
            String sqlCommand = "delete from Seadist where idPortWarehouseFrom = ? and idPortWarehouseTo = ?";
            try (PreparedStatement deleteSeadist = databaseConnection.getConnection().prepareStatement(sqlCommand)) {
                deleteSeadist.setInt(1, seadist.getFrom_port().getid());
                deleteSeadist.setInt(2, seadist.getTo_port().getid());
                deleteSeadist.executeUpdate();
            }
            returnValue = true;
        } catch (SQLException ex) {
            Logger.getLogger(SeadistStoreDB.class.getName())
                    .log(Level.SEVERE, null, ex);
            databaseConnection.registerError(ex);
            returnValue = false;
        }
        return returnValue;
    }

    /**
     * Saves the Seadist instance on the database, verifying if the instance already exists updating the information, or creates a new instance.
     *
     * @param dbConnection the database connection
     * @param seadist      the instance of Seadist to save
     * @throws SQLException in case of an error, throws an SQL exception
     */
    private void saveSeadistToDatabase(DatabaseConnection dbConnection, SeaDist seadist) throws SQLException {
        if (isSeadistOnDatabase(dbConnection, seadist)) {
            updateSeadistOnDatabase(dbConnection, seadist);
        } else {
            insertSeadistOnDatabase(dbConnection, seadist);
        }
    }

    /**
     * Checks if an instance of Seadist is already on the database.
     *
     * @param databaseConnection the database connection
     * @param seadist            the instance of Seadist
     * @return true if the country is on the db, false if not
     * @throws SQLException in case of an error, throws an SQL exception
     */
    private boolean isSeadistOnDatabase(DatabaseConnection databaseConnection, SeaDist seadist) throws SQLException {
        boolean isSeadistOnDatabase;
        String sqlCommand = "select * from Seadist where idPortWarehouseFrom = ? AND idPortWarehouseTo = ?";

        try (PreparedStatement seadistStatement = databaseConnection.getConnection().prepareStatement(sqlCommand)) {
            seadistStatement.setInt(1, seadist.getFrom_port().getid());
            seadistStatement.setInt(2, seadist.getTo_port().getid());

            try (ResultSet seadistRS = seadistStatement.executeQuery()) {
                isSeadistOnDatabase = seadistRS.next();
            }
        }
        return isSeadistOnDatabase;
    }

    /**
     * Updates information of a Seadist on an existing instance.
     *
     * @param dbConnection the database connection
     * @param seadist      the instance of Seadist
     * @throws SQLException in case of an error, throws an SQL exception
     */
    private void updateSeadistOnDatabase(DatabaseConnection dbConnection, SeaDist seadist) throws SQLException {
        String sqlCommand = "update Seadist set idPortWarehouseFrom = ?, idPortWarehouseTo = ?, seadist = ? where idPortWarehouseFrom = ? AND idPortWarehouseTo = ?";
        executeSeadistStatementOnDatabase(dbConnection, seadist, sqlCommand);
    }

    /**
     * Inserts a new instance of Seadist in the database.
     *
     * @param databaseConnection the database connection
     * @param seadist            the instance of Seadist
     * @throws SQLException in case of an error, throws an SQL exception
     */
    private void insertSeadistOnDatabase(DatabaseConnection databaseConnection, SeaDist seadist) throws SQLException {
        String sqlCommand = "insert into Seadist(idPortWarehouseFrom, idPortWarehouseTo, seadist) values (?, ?, ?)";

        executeSeadistStatementOnDatabase(databaseConnection, seadist, sqlCommand);
    }

    /**
     * Executes a statement on an existing instance of Seadist.
     *
     * @param dbConnection the database connection
     * @param seadist      the instance of Seadist
     * @param sqlCommand   the SQL command to be performed
     * @throws SQLException in case of an error, throws an SQL exception
     */
    private void executeSeadistStatementOnDatabase(DatabaseConnection dbConnection, SeaDist seadist, String sqlCommand) throws SQLException {
        try (PreparedStatement updateSeadist = dbConnection.getConnection().prepareStatement(sqlCommand)) {
            updateSeadist.setInt(1, seadist.getFrom_port().getid());
            updateSeadist.setInt(2, seadist.getTo_port().getid());
            updateSeadist.setInt(3, seadist.getSeaDistance());
            updateSeadist.executeUpdate();
        }
    }
}
