package lapr.project.model;

import lapr.project.stores.ShipStore;

public class Company {

    private final String designation;

    private final ShipStore shipStore;

    public Company(String designation){
        this.designation=designation;
        shipStore = new ShipStore();
    }

    public ShipStore getShipStore(){return shipStore;}
}
