package lapr.project.controller;

import lapr.project.data.ConnectionFactory;
import lapr.project.data.TripStore;
import lapr.project.model.Company;
import lapr.project.utils.Constants;

import java.sql.SQLException;
import java.util.List;

public class GetVoyagesBelowThresholdController {
    /**
     * The app
     */
    private App app;
    /**
     * The company
     */
    private final Company company;
    /**
     * The connection between trips and the database
     */
    private TripStore tripStoreDB;
    /**
     * The connection factory
     */
    private ConnectionFactory connectionFactory;

    public GetVoyagesBelowThresholdController(Company company) {
        this.company = company;
    }

    /**
     * The main constructor of the controller class
     *
     * @param tripStore connection between trips and the database
     * @param cf        connection factory
     */
    public GetVoyagesBelowThresholdController(TripStore tripStore, ConnectionFactory cf) {
        this(App.getInstance().getCompany());
        app = App.getInstance();
        this.tripStoreDB = tripStore;
        this.connectionFactory = cf;
    }

    /**
     * Connects with the data base to get the list of the voyages below a threshold
     *
     * @param shipMMSI ship mmsi
     * @return the list of voyages
     * @throws SQLException
     */
    public List<String> getShipVoyagesBellowThreshold(int shipMMSI, double threshold) throws SQLException {
        if (app.getCurrentUserSession().isLoggedInWithRole(Constants.U_ROLE_FLEET_MANAGER)) {
            return tripStoreDB.getShipVoyagesBelowThreshold(shipMMSI,threshold, connectionFactory);
        } else
            throw new IllegalStateException("Not authorized");
    }
}
