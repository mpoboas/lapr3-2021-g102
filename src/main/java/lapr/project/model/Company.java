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
     * The Trip store
     */
    private final TripStore tripStore;

    /**
     * The Ship store from database
     */
    private final ShipStoreDB shipStoreDB;

    /**
     * The Port store from database
     */
    private final PortStoreDB portStoreDB;

    /**
     * The Seadist store from database
     */
    private final SeadistStoreDB seadistStoreDB;

    /**
     * The Container store from database
     */
    private final ContainerStore containerStore;

    /**
     * The Country store from database
     */
    private final CountryStoreDB countryStoreDB;

    /**
     * The Border store from database
     */
    private final BorderStoreDB borderStoreDB;

    /**
     * The Cargo Manifest store from database
     */
    private final CargoManifestStore cargoManifestStore;

    /**
     * The freight store
     */
    private FreightNetwork freightNetwork;

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

    /**
     * Gets the Ship store from database.
     *
     * @return ShipStoreDB instance
     */
    public ShipStoreDB getShipStoreDB() {
        return this.shipStoreDB;
    }

    /**
     * Gets the Trip store from database.
     *
     * @return TripStore instance
     */
    public TripStore getTripStore() {
        return this.tripStore;
    }

    /**
     * Gets the Port store from database.
     *
     * @return PortStoreDB instance
     */
    public PortStoreDB getPortStoreDB() {
        return this.portStoreDB;
    }

    /**
     * Gets the Cargo Manifest store from database.
     *
     * @return CargoManifestStore instance
     */
    public CargoManifestStore getCargoManifestStore() {
        return this.cargoManifestStore;
    }

    /**
     * Gets the Border store from database.
     *
     * @return BorderStoreDB instance
     */
    public BorderStoreDB getBorderStoreDB() {
        return this.borderStoreDB;
    }

    /**
     * Gets the Seadist store from database.
     *
     * @return SeadistStoreDB instance
     */
    public SeadistStoreDB getSeadistStoreDB() {
        return this.seadistStoreDB;
    }

    /**
     * Gets the Container store from database.
     *
     * @return ContainerStoreDB instance
     */
    public ContainerStore getContainerStore() {
        return this.containerStore;
    }

    /**
     * Gets the Country store from database.
     *
     * @return CountryStoreDB instance
     */
    public CountryStoreDB getCountryStoreDB() {
        return this.countryStoreDB;
    }

    /**
     * Sets the Freight Network (just for mockito usage).
     */
    public void setFreightNetwork(FreightNetwork freightNetwork) {
        this.freightNetwork = freightNetwork;
    }
}
