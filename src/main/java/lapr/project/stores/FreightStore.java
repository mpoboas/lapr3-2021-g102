package lapr.project.stores;

import lapr.project.data.DatabaseConnection;
import lapr.project.utils.FreightNetwork;

public class FreightStore {

    /**
     * The network (graph) that contains all the connections between ports and capitals
     */
    private final FreightNetwork freightNetwork;

    /**
     * Constructor of the freight network store, with no received parameters.
     */
    public FreightStore(){
        this.freightNetwork = new FreightNetwork();
    }

    /**
     * Builds the freight network, with data imported from the database.
     * @param databaseConnection the database connection
     * @param numClosestPorts   the desired number of closest ports
     */
    public void buildFreightNetworkFromDatabase(DatabaseConnection databaseConnection, int numClosestPorts)
    {
        this.freightNetwork.getDataFromDatabase(databaseConnection);
        this.freightNetwork.buildFreightNetwork(numClosestPorts);
    }

    /**
     * Gets the freight network.
     */
    public FreightNetwork getFreightNetwork() {
        return this.freightNetwork;
    }
}
