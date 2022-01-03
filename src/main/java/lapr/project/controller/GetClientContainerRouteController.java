package lapr.project.controller;

import lapr.project.data.DatabaseConnection;
import lapr.project.model.Company;
import lapr.project.utils.Constants;

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
     * @param clientUsername the client´s username
     * @param containerId the container´s identifier
     * @return the container´s route
     */
    public String getClientContainerRoute(DatabaseConnection databaseConnection, String clientUsername, String containerId) throws SQLException {
        if (this.app.getCurrentUserSession().isLoggedInWithRole(Constants.U_ROLE_CLIENT)) {
            return this.company.getContainerStore().getClientContainerRoute(databaseConnection, clientUsername, containerId);
        } else throw new IllegalStateException("Not authorized.");
    }
}
