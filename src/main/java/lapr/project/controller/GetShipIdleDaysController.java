package lapr.project.controller;

import lapr.project.data.DatabaseConnection;
import lapr.project.model.Company;
import lapr.project.utils.Constants;

import java.sql.SQLException;

public class GetShipIdleDaysController {
    /**
     * The App
     */
    private App app;
    /**
     * The company
     */
    private Company company;

    /**
     * Constructor of the ship number of idle days controller, through a company object.
     *
     * @param company the company object
     */
    public GetShipIdleDaysController(Company company) {
        this.company = company;
    }

    /**
     * Constructor of the ship number of idle days controller, with no received parameters.
     */
    public GetShipIdleDaysController() {
        this(App.getInstance().getCompany());
        app = App.getInstance();
    }

    /**
     * Gets the number of idles days of each ship associated with the given fleet manager, since the beginning of a given year.
     *
     * @param databaseConnection the database connection
     * @param fleetManagerUsername the fleet manager´s username
     * @param year the desired year
     * @return the container´s route
     */
    public String getEachShipNumberOfIdleDays(DatabaseConnection databaseConnection, String fleetManagerUsername, int year) throws SQLException {
        if (this.app.getCurrentUserSession().isLoggedInWithRole(Constants.U_ROLE_FLEET_MANAGER)) {
            return this.company.getShipStoreDB().getEachShipNumberOfIdleDays(databaseConnection, fleetManagerUsername, year);
        } else throw new IllegalStateException("Not authorized.");
    }

}
