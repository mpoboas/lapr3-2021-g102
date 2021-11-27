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
