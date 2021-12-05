package lapr.project.controller;

import lapr.project.data.ConnectionFactory;
import lapr.project.data.ShipOccupancyRate;
import lapr.project.model.Company;
import lapr.project.utils.Constants;

import java.io.IOException;
import java.sql.SQLException;

public class GetShipOccupancyRateController {

    /**
     * The app
     */
    private App app;

    /**
     * The company
     */
    private final Company company;


    /**
     * The ShipOccupancyRate instance
     */
    private ShipOccupancyRate shipOccupancyRate;

    /**
     * Constructor of the ship occupancy rate controller, through a company object.
     *
     * @param company the company object
     */
    public GetShipOccupancyRateController(Company company) {
        this.company = company;
    }

    /**
     * Constructor of the ship occupancy rate controller, with no received parameters.
     */
    public GetShipOccupancyRateController() {
        this(App.getInstance().getCompany());
        app = App.getInstance();
    }

    /**
     * Gets the ship´s id.
     *
     * @return ship´s id
     */
    public int getShipId() {
        return this.shipOccupancyRate.getShipId();
    }

    /**
     * Gets the ship´s cargo manifest id.
     *
     * @return ship´s cargo manifest id
     */
    public int getCargoManifestId() {
        return this.shipOccupancyRate.getCargoManifestId();
    }

    /**
     * Gets the ship´s occupancy rate, through a ship MMSI and a cargo manifest id.
     *
     * @param connectionFactory the connection factory
     * @param shipMMSI the ship´s MMSI
     * @param idCargoManifest the ship cargo manifest´s id
     * @return ship´s occupancy rate id
     */
    public double getShipOccupancyRateById(ConnectionFactory connectionFactory, int shipMMSI, int idCargoManifest) throws SQLException, IOException {
        if (this.app.getCurrentUserSession().isLoggedInWithRole(Constants.U_ROLE_SHIP_CAPTAIN)) {
            this.shipOccupancyRate = new ShipOccupancyRate(connectionFactory, shipMMSI, idCargoManifest);

            return this.shipOccupancyRate.getShipOccupancyRate();
        } else throw new IllegalStateException("Not authorized.");
    }

    /**
     * Gets the ship´s occupancy rate, through a ship MMSI and a date and time.
     *
     * @param connectionFactory the connection factory
     * @param shipMMSI the ship´s MMSI
     * @param localDateTimeStr the desired date and time string value
     * @return ship´s occupancy rate id
     */
    public double getShipOccupancyRateByDateTime(ConnectionFactory connectionFactory, int shipMMSI, String localDateTimeStr) throws SQLException, IOException {
        if (this.app.getCurrentUserSession().isLoggedInWithRole(Constants.U_ROLE_SHIP_CAPTAIN)) {
            this.shipOccupancyRate = new ShipOccupancyRate(connectionFactory, shipMMSI, localDateTimeStr);

            return this.shipOccupancyRate.getShipOccupancyRate();
        } else throw new IllegalStateException("Not authorized.");
    }
}
