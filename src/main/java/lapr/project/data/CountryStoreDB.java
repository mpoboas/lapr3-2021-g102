package lapr.project.data;

import lapr.project.model.Country;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CountryStoreDB implements Persistable {

    /**
     * Saves an instance of Country on database
     *
     * @param databaseConnection the connection
     * @param object             the object to save
     * @return true if it was successfully added, false if not
     */
    @Override
    public boolean save(DatabaseConnection databaseConnection, Object object) {
        Country country = (Country) object;
        boolean returnValue;
        try {
            saveCountryToDatabase(databaseConnection, country);
            returnValue = true;
        } catch (SQLException ex) {
            Logger.getLogger(CountryStoreDB.class.getName())
                    .log(Level.SEVERE, null, ex);
            databaseConnection.registerError(ex);
            returnValue = false;
        }
        return returnValue;
    }

    /**
     * Deletes an instance of Country from the database
     *
     * @param databaseConnection the connection
     * @param object             the object to delete
     * @return true if it was successfully deleted, false if not
     */
    @Override
    public boolean delete(DatabaseConnection databaseConnection, Object object) {
        Country country = (Country) object;
        boolean returnValue;
        Connection connection = databaseConnection.getConnection();

        try {
            String sqlCommand = "delete from country where alpha2CodeCountry = ?";
            try (PreparedStatement deleteCountry = connection.prepareStatement(sqlCommand)) {
                deleteCountry.setString(1, country.getAlpha2Code());
                deleteCountry.executeUpdate();
            }
            returnValue = true;
        } catch (SQLException ex) {
            Logger.getLogger(CountryStoreDB.class.getName())
                    .log(Level.SEVERE, null, ex);
            databaseConnection.registerError(ex);
            returnValue = false;
        }

        return returnValue;
    }


    /**
     * Saves the country instance on the database, verifying if the instance already exists updating the information, or creates a new instance
     *
     * @param dbConnection  the database connection
     * @param country       the Country instance to save
     * @throws SQLException the ssql exception
     */
    private void saveCountryToDatabase(DatabaseConnection dbConnection, Country country) throws SQLException {

        if (isCountryOnDatabase(dbConnection, country)) {
            updateCountryOnDatabase(dbConnection, country);
        } else {
            insertPositionOnDB(dbConnection, country);
            insertCountryOnDatabase(dbConnection, country);
        }
    }

    /**
     * Checks if an instance of Country is already on the database
     *
     * @param databaseConnection the database connection
     * @param country            the instance of country
     * @return true if the country is on the db, false if not
     * @throws SQLException      the SQL exception
     */
    private boolean isCountryOnDatabase(DatabaseConnection databaseConnection, Country country) throws SQLException {
        Connection connection = databaseConnection.getConnection();
        boolean isCountryOnDatabase = false;

        String sqlCommand = "select * from country where alpha2codeCountry = ?";

        try (PreparedStatement countryStatement = connection.prepareStatement(sqlCommand)) {
            countryStatement.setString(1, country.getAlpha2Code());

            try (ResultSet countryRS = countryStatement.executeQuery()) {
                if (countryRS.next()) {
                    isCountryOnDatabase = true;
                }
            }
        }
        return isCountryOnDatabase;
    }

    /**
     * Updates information of a country on an existing instance
     *
     * @param dbConnection  the database connection
     * @param country       the country
     * @throws SQLException the SQL exception
     */
    private void updateCountryOnDatabase(DatabaseConnection dbConnection, Country country) throws SQLException {
        String sqlCommand =
                "update country set  alpha3CodeCountry = ?, name = ?, capital = ?, population = ? where alpha2CodeCountry = ?";
        updateCountryOnDatabase(dbConnection, country, sqlCommand);
    }

    private void updateCountryOnDatabase(DatabaseConnection dbConnection, Country country, String sqlCommand) throws SQLException {
        Connection connection = dbConnection.getConnection();

        try (PreparedStatement updateCountry = connection.prepareStatement(sqlCommand)) {
            updateCountry.setString(1, country.getAlpha3Code());
            updateCountry.setString(2, country.getCountry());
            updateCountry.setString(3, country.getCapital());
            updateCountry.setDouble(4, country.getPopulation());
            updateCountry.setString(5, country.getAlpha2Code());
            updateCountry.executeUpdate();
        }
    }

    /**
     * Inserts a new position to the position table
     *
     * @param databaseConnection the database connection
     * @param country            country that has the position information
     * @throws SQLException      the SQL exception
     */
    private void insertPositionOnDB(DatabaseConnection databaseConnection, Country country) throws SQLException {
        String sqlCommand = "insert into position(latitude, longitude) values (?, ?)";
        executePositionStatementOnDatabase(databaseConnection, country,
                sqlCommand);
    }

    private void executePositionStatementOnDatabase(DatabaseConnection databaseConnection,
                                                    Country country, String sqlCommand) throws SQLException {
        Connection connection = databaseConnection.getConnection();
        try (PreparedStatement savePortPreparedStatement =
                     connection.prepareStatement(
                             sqlCommand)) {
            savePortPreparedStatement.setDouble(1, country.getLatitude());
            savePortPreparedStatement.setDouble(2, country.getLongitude());
            savePortPreparedStatement.executeUpdate();
        }
    }

    /**
     * Inserts an instance of country
     *
     * @param databaseConnection the database connection
     * @param country            the country to insert
     * @throws SQLException      the SQL exception
     */
    private void insertCountryOnDatabase(DatabaseConnection databaseConnection,
                                         Country country) throws SQLException {
        //Connection connection = databaseConnection.getConnection();
        String sqlCommand =
                "insert into country( alpha2CodeCountry, alpha3CodeCountry, idPosition, alpha2CodeContinent, name, capital, population) values (?, ?, ?, ?, ?, ?, ?)";

        executeCountryStatementOnDatabase(databaseConnection, country,
                sqlCommand);
    }

    private void executeCountryStatementOnDatabase(DatabaseConnection dbconnection,
                                                   Country country, String sqlCommand) throws SQLException {

        Connection conn = dbconnection.getConnection();

        try (PreparedStatement saveCountryStatement = conn.prepareStatement(sqlCommand)) {
            saveCountryStatement.setString(1, country.getAlpha2Code());
            saveCountryStatement.setString(2, country.getAlpha3Code());
            saveCountryStatement.setInt(3, getPositionId(dbconnection, country));
            saveCountryStatement.setString(4, getAlpha2CodeContinentCode(dbconnection, country));
            saveCountryStatement.setString(5, country.getCountry());
            saveCountryStatement.setString(6, country.getCapital());
            saveCountryStatement.setDouble(7, country.getPopulation());
            saveCountryStatement.executeUpdate();
        }
    }

    /**
     * Gets the position id from a country's latitude and longitude
     *
     * @param dbconnection  database connection
     * @param country       country that has the information
     * @return the position's id
     * @throws SQLException the SQL exception
     */
    private int getPositionId(DatabaseConnection dbconnection, Country country) throws SQLException {
        int id;
        String command = "SELECT IDPOSITION FROM POSITION WHERE LATITUDE = " + country.getLatitude() + " AND LONGITUDE = " + country.getLongitude();
        Connection connection = dbconnection.getConnection();
        try (PreparedStatement runCommand = connection.prepareStatement(command)) {
            try (ResultSet rs = runCommand.executeQuery()) {
                rs.next();
                id = rs.getInt(1);
            }
        }
        return id;
    }

    /**
     * Gets the alpha 2 code continent from a country continent name
     *
     * @param dbconnection  database connection
     * @param country       country
     * @return the alpha2CodeContinent
     * @throws SQLException the SQL exception
     */
    private String getAlpha2CodeContinentCode(DatabaseConnection dbconnection, Country country) throws SQLException {
        String code;
        String command = "select alpha2CodeContinent from Continent where upper(name) = upper( ? ) ";
        Connection connection = dbconnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(command)) {
            statement.setString(1,country.getContinent());
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                code = resultSet.getString(1);
            }
        }
        return code;
    }

    /**
     * Gets all the instances of Country from the database
     *
     * @param dbConnection database connection
     * @return the list of countries
     */
    public List<Country> getCountriesFromDatabase(DatabaseConnection dbConnection) {
        List<Country> countries = new ArrayList<>();
        Connection connection = dbConnection.getConnection();
        String sql = "select c.alpha2CodeCountry, c.alpha3CodeCountry, c.name, c.capital, c.population, Continent.name, p.latitude, p.longitude " +
                "from Country c " +
                "inner join Continent " +
                "on c.alpha2CodeContinent = Continent.alpha2CodeContinent " +
                "inner join Position p " +
                "on c.idPosition = p.idPosition";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                countries.add(new Country(rs.getString(6),
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(5),
                        rs.getString(4),
                        rs.getDouble(7),
                        rs.getDouble(8)
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return countries;
    }

    /**
     * Gets an instance of country by its alpha2Code
     *
     * @param alpha2Code code to identify the country
     * @return the instance
     */
    public Country getCountryByAlpha2Code(DatabaseConnection dbConnection, String alpha2Code) /*throws IOException*/ {
        Country country = null;
        Connection connection = dbConnection.getConnection();
        //Connection connection = ConnectionFactory.getInstance().getDatabaseConnection().getConnection();
        String sql = "select c.alpha2CodeCountry, c.alpha3CodeCountry, c.name, c.capital, c.population, t.name, p.latitude, p.longitude" +
                " from Country c " +
                "inner join Continent t " +
                "on c.alpha2CodeContinent = t.alpha2CodeContinent " +
                "inner join Position p " +
                "on c.idPosition = p.idPosition " +
                "where c.alpha2CodeCountry = ? ";

        try (PreparedStatement statement = connection.prepareStatement(sql);
            ){
            statement.setString(1,alpha2Code);
            try( ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                country = new Country(resultSet.getString(6),
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(5),
                        resultSet.getString(4),
                        resultSet.getDouble(7),
                        resultSet.getDouble(8));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return country;
    }


}
