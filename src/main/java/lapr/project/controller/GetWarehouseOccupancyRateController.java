package lapr.project.controller;

import lapr.project.data.DatabaseConnection;
import lapr.project.model.Company;
import lapr.project.utils.Constants;

import java.sql.SQLException;

public class GetWarehouseOccupancyRateController {

    /**
     * The app
     */
    private App app;

    /**
     * The company
     */
    private final Company company;

    /**
     * Constructor of the warehouse occupancy rate controller, through a company object.
     *
     * @param company the company object
     */
    public GetWarehouseOccupancyRateController(Company company) {
        this.company = company;
    }

    /**
     * Constructor of the warehouse occupancy rate controller, with no received parameters.
     */
    public GetWarehouseOccupancyRateController() {
        this(App.getInstance().getCompany());
        app = App.getInstance();
    }

    /**
     * Gets the occupancy rate and an estimate of the containers leaving the warehouse during the next 30 days of every warehouse that a port manager has access to.
     *
     * @param databaseConnection  the database connection
     * @param portManagerUsername the port manager´s username
     * @return the container´s route
     */
    public String getEachWarehouseOccupancyRateAndContainersLeaving30Days(DatabaseConnection databaseConnection, String portManagerUsername) throws SQLException {
        if (this.app.getCurrentUserSession().isLoggedInWithRole(Constants.U_ROLE_PORT_MANAGER)) {
            return this.company.getPortStoreDB().getEachWarehouseOccupancyRateAndContainersLeaving30Days(databaseConnection, portManagerUsername);
        } else throw new IllegalStateException("Not authorized.");
    }
}
