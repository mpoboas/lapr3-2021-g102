package lapr.project.controller;

import lapr.project.data.DatabaseConnection;
import lapr.project.model.Company;

import java.sql.SQLException;

public class GetClientContainerRouteController {
    /**
     * The App
     */
    private App app;
    /**
     * The company
     */
    private Company company;

    /**
     * Constructor of the client container route controller, through a company object.
     *
     * @param company the company object
     */
    public GetClientContainerRouteController(Company company) {
        this.company = company;
    }

    /**
     * Constructor of the client container route controller, with no received parameters.
     */
    public GetClientContainerRouteController() {
        this(App.getInstance().getCompany());
        app = App.getInstance();
    }

    /**
     * Gets the route of a specified container, leased by a specified client.
     *
     * @param databaseConnection the database connection
     * @param clientId the client´s identifier
     * @param containerId the container´s identifier
     * @return the container´s route
     */
    public String getClientContainerRoute(DatabaseConnection databaseConnection, int clientId, String containerId) throws SQLException {
        return this.company.getContainerStore().getClientContainerRoute(databaseConnection, clientId, containerId);
    }
}
