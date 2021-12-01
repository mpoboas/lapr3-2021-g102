package lapr.project.model;

import lapr.project.auth.AuthFacade;
import lapr.project.stores.PortStore;
import lapr.project.stores.ShipStore;

public class Company {
    /**
     * Auth facade.
     */
    private final AuthFacade authFacade;

    /**
     * The designation
     */
    private final String designation;

    /**
     * The store of ships
     */
    private final ShipStore shipStore;
    /**
     * The store of ports
     */
    private final PortStore portStore;

    public Company(String designation) {
        this.designation = designation;
        this.authFacade = new AuthFacade();
        shipStore = new ShipStore();
        portStore = new PortStore();
    }

    /**
     * Gets the auth facade.
     *
     * @return auth facade.
     */
    public AuthFacade getAuthFacade() {
        return authFacade;
    }

    /**
     * @return gets the ship store
     */
    public ShipStore getShipStore() {
        return shipStore;
    }

    /**
     * @return gets the port store
     */
    public PortStore getPortStore() {
        return portStore;
    }
}
