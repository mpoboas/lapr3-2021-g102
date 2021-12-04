package lapr.project.data;

import java.io.IOException;
import java.sql.*;

public class ShipOccupancyRate {
    /**
     * The ship´s id
     */
    private final int shipId;
    /**
     * The cargo manifest´s id
     */
    private final int cargoManifestId;
    /**
     * The ship´s occupancy rate
     */
    private final double shipOccupancyRate;
    /**
     * The connection factory
     */
    private final ConnectionFactory connectionFactory;

    /**
     * Constructor of the ShipOccupancyRate, through a given ship MMSI and cargo manifest id.
     *
     * @param shipMMSI        the desired ship´s MMSI
     * @param idCargoManifest the desired cargo manifest´s id
     */
    public ShipOccupancyRate(ConnectionFactory connectionFactory, int shipMMSI, int idCargoManifest) throws IOException, SQLException {
        this.connectionFactory = connectionFactory;
        this.shipId = getShipIdByMMSI(shipMMSI);
        this.cargoManifestId = idCargoManifest;
        this.shipOccupancyRate = calcShipOccupancyRate();
    }

    /**
     * Constructor of the ShipOccupancyRate, through a given ship MMSI and date and time.
     *
     * @param shipMMSI         the desired ship´s MMSI
     * @param localDateTimeStr the desired date and time string value
     */
    public ShipOccupancyRate(ConnectionFactory connectionFactory, int shipMMSI, String localDateTimeStr) throws IOException, SQLException {
        this.connectionFactory = connectionFactory;
        this.shipId = getShipIdByMMSI(shipMMSI);
        this.cargoManifestId = getCargoManifestIdByDateTime(localDateTimeStr);
        this.shipOccupancyRate = calcShipOccupancyRate();
    }

    /**
     * Gets the ship´s id.
     *
     * @return ship´s id
     */
    public int getShipId() {
        return this.shipId;
    }

    /**
     * Gets the ship´s cargo manifest id.
     *
     * @return ship´s cargo manifest id
     */
    public int getCargoManifestId() {
        return this.cargoManifestId;
    }

    /**
     * Gets the ship´s occupancy rate.
     *
     * @return ship´s occupancy rate
     */
    public double getShipOccupancyRate() {
        return this.shipOccupancyRate;
    }

    /**
     * Gets the ship id on the database, through its MMSI
     *
     * @param shipMMSI the ship´s MMSI
     * @return ship´s id
     * @throws SQLException sql exception
     */
    private int getShipIdByMMSI(int shipMMSI) throws SQLException {
        try (Connection connection = this.connectionFactory.getDatabaseConnection().getConnection(); CallableStatement callableStatement = connection.prepareCall("{? = call getShipIdByMMSI(?)}")) {
            callableStatement.setInt(2, shipMMSI);
            callableStatement.registerOutParameter(1, Types.INTEGER);
            callableStatement.execute();

            return callableStatement.getInt(1);
        }
    }

    /**
     * Gets the ship id on the database, through its MMSI
     *
     * @param localDateTimeStr the desired localDateTime string value
     * @return the cargo manifest´s id
     * @throws SQLException sql exception
     */
    private int getCargoManifestIdByDateTime(String localDateTimeStr) throws SQLException {
        try (Connection connection = this.connectionFactory.getDatabaseConnection().getConnection(); CallableStatement callableStatement = connection.prepareCall("{? = call getCargoManifestIdByDateTime(?)}")) {
            callableStatement.setString(2, localDateTimeStr);
            callableStatement.registerOutParameter(1, Types.INTEGER);
            callableStatement.execute();

            return callableStatement.getInt(1);
        }
    }

    /**
     * Calculates the ship´s occupancy rate (ratio between total number of containers
     * in the ship coming from a given manifest and the total capacity of the ship).
     *
     * @return the calculated occupancy rate
     * @throws SQLException sql exception
     */
    private double calcShipOccupancyRate() throws SQLException {
        try (Connection connection = this.connectionFactory.getDatabaseConnection().getConnection(); CallableStatement callableStatement = connection.prepareCall("{? = call getShipOccupancyRate(?, ?)}")) {
            callableStatement.setInt(2, this.shipId);
            callableStatement.setInt(3, this.cargoManifestId);
            callableStatement.registerOutParameter(1, Types.DOUBLE);
            callableStatement.execute();

            return callableStatement.getDouble(1);
        }
    }
}
