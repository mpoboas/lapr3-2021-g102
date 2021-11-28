package lapr.project.model;

import lapr.project.stores.PortStore;
import lapr.project.stores.ShipStore;

public class Company {

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

    public Company(String designation){
        this.designation=designation;
        shipStore = new ShipStore();
        portStore = new PortStore();
    }

    /**
     * @return gets the ship store
     */
    public ShipStore getShipStore(){return shipStore;}

    /**
     * @return gets the port store
     */
    public PortStore getPortStore() {
        return portStore;
    }
}
