package lapr.project.controller;

import lapr.project.data.DatabaseConnection;
import lapr.project.model.Company;
import lapr.project.stores.FreightNetwork;

public class BuildFreightNetworkController {
    /**
     * The app
     */
    private App app;

    /**
     * The company
     */
    private final Company company;

    /**
     * Constructor of the closest port controller, through a company object.
     *
     * @param company the company object
     */
    public BuildFreightNetworkController(Company company) {
        this.company = company;
    }

    /**
     * Constructor of the closest port controller, with no received parameters.
     */
    public BuildFreightNetworkController() {
        this(App.getInstance().getCompany());
        app = App.getInstance();
    }

    public FreightNetwork getFreightNetwork() {
        return this.company.getFreightNetwork();
    }

    /**
     * Builds the freight network, with data imported from the database.
     * @param databaseConnection the database connection
     * @param numClosestPorts   the desired number of closest ports
     */
    public void buildFreightNetworkFromDatabase(DatabaseConnection databaseConnection, int numClosestPorts) {
        this.company.getFreightNetwork().buildFreightNetworkFromDatabase(databaseConnection, numClosestPorts);
    }
}
