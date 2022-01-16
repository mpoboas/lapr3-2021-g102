package lapr.project.controller;

import lapr.project.model.Company;
import lapr.project.model.Port;

import java.util.ArrayList;
import java.util.List;

public class GetMostCriticalPortsController {
    /**
     * The app
     */
    private App app;

    /**
     * The company
     */
    private final Company company;

    /**
     * Constructor of the most critical ports controller, through a company object.
     *
     * @param company the company object
     */
    public GetMostCriticalPortsController(Company company) {
        this.company = company;
    }

    /**
     * Constructor of the most critical ports controller, with no received parameters.
     */
    public GetMostCriticalPortsController() {
        this(App.getInstance().getCompany());
        app = App.getInstance();
    }

    /**
     * Gets the n ports with greater centrality (greatest number of shortest paths that pass through it).
     *
     * @param numPorts the desired number of most critical ports
     * @return the list of ports with greater centrality
     */
    public List<Port> getMostCriticalPorts(int numPorts) {
        return new ArrayList<>(this.company.getFreightNetwork().getMostCriticalPorts(numPorts));
    }
}
