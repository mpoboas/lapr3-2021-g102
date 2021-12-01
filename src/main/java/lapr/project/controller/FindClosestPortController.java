package lapr.project.controller;

import lapr.project.model.Company;
import lapr.project.model.Port;
import lapr.project.model.Ship;
import lapr.project.stores.PortStore;
import lapr.project.stores.ShipStore;
import lapr.project.utils.Coordinates;

import java.time.LocalDateTime;

public class FindClosestPortController {
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
    public FindClosestPortController(Company company) {
        this.company = company;
    }

    /**
     * Constructor of the closest port controller, with no received parameters.
     */
    public FindClosestPortController() {
        this(App.getInstance().getCompany());
        app = App.getInstance();
    }

    /**
     * Returns the ship store.
     *
     * @return ship store instance
     */
    public ShipStore getShipStore() {
        return this.company.getShipStore();
    }

    /**
     * Returns the port store.
     *
     * @return port store instance
     */
    public PortStore getPortStore() {
        return this.company.getPortStore();
    }

    public Port findClosestPort(String callSign, LocalDateTime dateTime) {
        Ship ship = this.getShipStore().getShipByCallSign(callSign);
        Coordinates shipCoordinates = this.getShipCoordinatesAtGivenTime(ship, dateTime);

        return this.getPortStore().getPortTree().findNearestNeighbour(shipCoordinates.getLongitude(), shipCoordinates.getLatitude());
    }

    /**
     * Gets a ship's coordinates given a certain date and time
     *
     * @param ship     the ship´s call sign
     * @param dateTime the date and time
     * @return positional message at the given date and time
     */
    private Coordinates getShipCoordinatesAtGivenTime(Ship ship, LocalDateTime dateTime) {
        return ship.getAISAtDateTime(dateTime).getCoordinates();
    }
}
