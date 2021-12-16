package lapr.project.data;

import lapr.project.model.Border;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class manages the class border between java and the database
 */
public class BorderStoreDB implements Persistable {
    /**
     * Saves an instance of Border to the database
     * @param databaseConnection database connection
     * @param object object to save
     * @return true if the object was successfully saved, false if not
     */
    @Override
    public boolean save(DatabaseConnection databaseConnection, Object object) {
        Border border = (Border) object;
        boolean returnValue;
        try {
            saveBorderToDatabase(databaseConnection, border);
            returnValue = true;
        } catch (SQLException ex) {
            Logger.getLogger(BorderStoreDB.class.getName())
                    .log(Level.SEVERE, null, ex);
            databaseConnection.registerError(ex);
            returnValue = false;
        }

        return returnValue;
    }

    /**
     * Deletes an instance of border from the database
     * @param databaseConnection database connection
     * @param object object to delete
     * @return true if the object was successfully deleted, false if not
     */
    @Override
    public boolean delete(DatabaseConnection databaseConnection, Object object) {
        Border border = (Border) object;
        boolean returnValue;
        Connection conn = databaseConnection.getConnection();
        try {
            String sql = "delete from border where alpha2CodeCountry1 = ? and alpha2CodeCountry2 = ?";
            try (PreparedStatement deleteBorder = conn.prepareStatement(sql)) {
                deleteBorder.setString(1, border.getCountry1().getAlpha2Code());
                deleteBorder.setString(2, border.getCountry2().getAlpha2Code());
                deleteBorder.executeUpdate();
            }
            returnValue = true;
        } catch (SQLException ex) {
            Logger.getLogger(BorderStoreDB.class.getName())
                    .log(Level.SEVERE, null, ex);
            databaseConnection.registerError(ex);
            returnValue = false;
        }
        return returnValue;
    }

    private void saveBorderToDatabase(DatabaseConnection dbConnection, Border border) throws SQLException {
        if (!isBorderOnDatabase(dbConnection, border))
            insertBorderOnDatabase(dbConnection, border);
    }

    private boolean isBorderOnDatabase(DatabaseConnection dbConnection, Border border) throws SQLException {
        Connection connection = dbConnection.getConnection();
        boolean isBorderOnDB;
        String sqlCommand = "select * from Border where alpha2CodeCountry1 = ? and alpha2CodeCountry2 = ?";
        try (PreparedStatement borderStatement = connection.prepareStatement(sqlCommand)) {
            borderStatement.setString(1, border.getCountry1().getAlpha2Code());
            borderStatement.setString(2, border.getCountry2().getAlpha2Code());

            try (ResultSet rs = borderStatement.executeQuery()) {
                isBorderOnDB = rs.next();
            }
        }
        return isBorderOnDB;
    }

    private void insertBorderOnDatabase(DatabaseConnection dbConnection, Border border) throws SQLException {
        String command = "insert into border(alpha2CodeCountry1, alpha2CodeCountry2) values (?, ?)";
        executeInsertBorderOnDatabase(dbConnection, border, command);
    }

    private void executeInsertBorderOnDatabase(DatabaseConnection dbConnection, Border border, String sql) throws SQLException {
        Connection conn = dbConnection.getConnection();
        try (PreparedStatement saveBorder = conn.prepareStatement(sql)) {
            saveBorder.setString(1, border.getCountry1().getAlpha2Code());
            saveBorder.setString(2, border.getCountry2().getAlpha2Code());
            saveBorder.executeUpdate();
        }
    }

    /**
     * Gets the list of all borders of the database
     * @param dbConnection database connection
     * @return list of borders
     */
    public List<Border> getAllBorders(DatabaseConnection dbConnection) {
        List<Border> borders = new ArrayList<>();
        Connection connection = dbConnection.getConnection();
        String sql = "select * from border";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            CountryStoreDB countryStoreDB = new CountryStoreDB();
            while (rs.next()) {
                borders.add(new Border(
                        countryStoreDB.getCountryByAlpha2Code(dbConnection, rs.getString(1)),
                        countryStoreDB.getCountryByAlpha2Code(dbConnection, rs.getString(2))));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();

        }
        return borders;
    }

}
