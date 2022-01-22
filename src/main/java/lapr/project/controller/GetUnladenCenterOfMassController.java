package lapr.project.controller;

import lapr.project.data.ConnectionFactory;
import lapr.project.data.ShipStoreDB;
import lapr.project.model.Company;
import lapr.project.model.Ship;
import lapr.project.utils.Pair;
import lapr.project.utils.PhysicsUtils;

import java.awt.geom.Point2D;
import java.sql.SQLException;

public class GetUnladenCenterOfMassController {

    /**
     * The app
     */
    private App app;

    /**
     * The company
     */
    private final Company company;
    /**
     * The store
     */
    private ShipStoreDB storeDB;
    /**
     * The connection factory
     */
    private ConnectionFactory connectionFactory;
    /**
     * The ship to get the center of mass
     */
    private Ship ship = null;

    public GetUnladenCenterOfMassController(Company comp){
        this.company = comp;
    }

    public GetUnladenCenterOfMassController(ShipStoreDB store, ConnectionFactory cf){
        this(App.getInstance().getCompany());
        app = App.getInstance();
        connectionFactory = cf;
        this.storeDB = store;
    }

    /**
     * Connects to the database to get the ship by the mmsi
     * @param mmsi identifier of the ship
     * @return true if the ship was successfully read
     * @throws SQLException if occured an error in the db
     */
    public boolean getShipInfo(int mmsi) throws SQLException {
        ship = storeDB.getShipByMMSI(connectionFactory.getDatabaseConnection(),mmsi);
        return ship!=null;
    }

    /**
     * Gets the ship by an instance of ship
     * @param ship the instance of ship
     * @return true if the ship was successfully read
     */
    public boolean getShipInfo(Ship ship){
        this.ship = ship;
        return ship!=null;
    }

    /**
     * Gets the coordinates of the center of mass of a ship
     * @param type where is located the control bridge
     * @return the coordinates
     */
    public Pair<Point2D.Double,Double> getCenterOfMassOfAShip(String type) {
        if (0 == ship.getWeight())
            throw new IllegalStateException("The ship does not have the necessary info");
        else
        return PhysicsUtils.calculateTheUnleadedCenterOfMass(ship.getLength(),ship.getWidth(),ship.getDraft(),ship.getWeight(),type);
    }

}
