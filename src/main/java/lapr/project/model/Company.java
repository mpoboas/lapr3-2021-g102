package lapr.project.model;

import lapr.project.auth.AuthFacade;
import lapr.project.data.ContainerStore;
import lapr.project.stores.PortStore;
import lapr.project.stores.ShipStore;
import lapr.project.stores.FreightNetwork;

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

    /**
     * The freight store
     */
    private final FreightNetwork freightNetwork;

    /**
     * The store of containers
     */
    private final ContainerStore containerStore;

    public Company(String designation) {
        this.designation = designation;
        this.authFacade = new AuthFacade();
        this.shipStore = new ShipStore();
        this.portStore = new PortStore();
        this.freightNetwork = new FreightNetwork();
        this.containerStore = new ContainerStore();
    }

    /**
     * Gets the auth facade.
     *
     * @return auth facade.
     */
    public AuthFacade getAuthFacade() {
        return this.authFacade;
    }

    /**
     * Gets the company´s designation.
     *
     * @return designation.
     */
    public String getDesignation() {
        return designation;
    }

    /**
     * Gets the store of ships
     *
     * @return the ShipStore instance
     */
    public ShipStore getShipStore() {
        return this.shipStore;
    }

    /**
     * Gets the store of ports
     *
     * @return the PortStore instance
     */
    public PortStore getPortStore() {
        return this.portStore;
    }

    /**
     * Gets the freight network.
     *
     * @return the FreightNetwork instance
     */
    public FreightNetwork getFreightNetwork() {
        return this.freightNetwork;
    }

    /**
     * Gets the store of containers.
     *
     * @return the ContainerStore instance
     */
    public ContainerStore getContainerStore() {
        return containerStore;
    }
}
