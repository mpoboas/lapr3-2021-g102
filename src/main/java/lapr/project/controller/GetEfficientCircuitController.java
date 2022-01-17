package lapr.project.controller;

import lapr.project.model.Company;
import lapr.project.model.Place;
import lapr.project.stores.FreightNetwork;
import lapr.project.utils.Constants;

import java.util.LinkedList;

public class GetEfficientCircuitController {
    /**
     * The app
     */
    private App app;

    /**
     * The company
     */
    private final Company company;

    /**
     * The freight Network
     */
    private FreightNetwork fc;

    /**
     * Constructor of the most efficient circuit controller, through a company object.
     *
     * @param company the company object
     */
    public GetEfficientCircuitController(Company company) {
        this.company = company;

    }

    /**
     * Constructor of the most efficient circuit controller, with no received parameters.
     */
    public GetEfficientCircuitController() {
        this(App.getInstance().getCompany());
        this.app = App.getInstance();
    }

    /**
     * Gets the most efficient circuit that starts from a source location and visits the greatest number of other locations once,
     * returning to the starting location and with the shortest total distance.
     *
     * @return the list of places
     */
    public LinkedList<Place> getEfficientCycle() {
        if (App.getInstance().getCurrentUserSession().isLoggedInWithRole(Constants.U_ROLE_TRAFFIC_MANAGER)) {
            this.fc = company.getFreightNetwork();
            return fc.getEfficientCycle();
        } else
            throw new IllegalStateException("Not Authorized");
    }
}
