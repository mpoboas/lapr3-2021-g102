package lapr.project.model;

import lapr.project.auth.AuthFacade;
import lapr.project.data.*;
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
     * Trip store
     */
    private final TripStore tripStore;
    private final ShipStoreDB shipStoreDB;
    private final PortStoreDB portStoreDB;
    private final SeadistStoreDB seadistStoreDB;
    private final ContainerStore containerStore;
    private final CountryStoreDB countryStoreDB;
    private final BorderStoreDB borderStoreDB;
    private final CargoManifestStore cargoManifestStore;


    /**
     * The freight store
     */
    private final FreightNetwork freightNetwork;

    public Company(String designation) {
        this.designation = designation;
        this.authFacade = new AuthFacade();
        this.shipStore = new ShipStore();
        this.portStore = new PortStore();
        this.freightNetwork = new FreightNetwork();
        this.tripStore = new TripStore();
        this.shipStoreDB = new ShipStoreDB();
        this.portStoreDB = new PortStoreDB();
        this.seadistStoreDB = new SeadistStoreDB();
        this.containerStore = new ContainerStore();
        this.countryStoreDB = new CountryStoreDB();
        this.borderStoreDB = new BorderStoreDB();
        this.cargoManifestStore = new CargoManifestStore();

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
     * @return gets the ship store
     */
    public ShipStore getShipStore() {
        return this.shipStore;
    }

    /**
     * @return gets the port store
     */
    public PortStore getPortStore() {
        return this.portStore;
    }

    /**
     * Gets the freight network.
     *
     * @return FreightNetwork instance
     */
    public FreightNetwork getFreightNetwork() {
        return this.freightNetwork;
    }
    public ShipStoreDB getShipStoreDB(){
        return this.shipStoreDB;
    }

    public TripStore getTripStore(){
        return this.tripStore;
    }
    public PortStoreDB getPortStoreDB(){
        return this.portStoreDB;
    }

    public CargoManifestStore getCargoManifestStore(){
        return this.cargoManifestStore;
    }
    public BorderStoreDB getBorderStoreDB(){
        return this.borderStoreDB;
    }
    public SeadistStoreDB getSeadistStoreDB(){
        return this.seadistStoreDB;
    }
    public ContainerStore getContainerStore(){
        return this.containerStore;
    }
    public CountryStoreDB getCountryStoreDB(){
        return this.countryStoreDB;
    }





}
