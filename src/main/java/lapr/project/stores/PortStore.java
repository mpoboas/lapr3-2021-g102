package lapr.project.stores;

import lapr.project.model.Port;
import lapr.project.utils.PortTree;

import java.util.List;

public class PortStore {

    /**
     * The 2d-tree that contains all the ports organised
     */
    private final PortTree portTree;

    public PortStore(){
        portTree= new PortTree();
    }

    /**
     * Creates an instance of port
     * @param id port id
     * @param name port name
     * @param continent port continent
     * @param country port country
     * @param longitude port longitude
     * @param latitude port latitude
     * @return the instance of the port created
     */
    public Port createPort(int id,String name, String continent,String country,double longitude,double latitude,int capacity){
        return new Port(id,name,continent,country,longitude,latitude,capacity);
    }
    /**
     * Build the tree balanced
     * @param ports the list of the ports
     */
    public void buildPortTree(List<Port> ports){
        portTree.buildKDTree(ports);
    }

    /**
     * @return gets the port Tree
     */
    public PortTree getPortTree() {
        return portTree;
    }
}
