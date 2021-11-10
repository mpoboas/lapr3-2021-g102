package lapr.project.controller;

import lapr.project.model.Import;
import lapr.project.stores.ShipStore;

import java.io.IOException;

public class ImportShipsController {
    //private App app;
    //private Company company;
    private Import imp;
    private ShipStore store;

    /*
    public ImportShipController() {
        //this(App.getInstance().getCompany());
        //app = App.getInstance();
    }

    public ImportShipController(Company company) {
        this.company = company;
    }*/

    public ImportShipsController(ShipStore store){
        this.store = store;
        imp = new Import(store);
    }

    public void importShipData(String fileDirectory) throws IOException {
        imp.importShipsData(fileDirectory);
    }

    public void addShips(){
        imp.addShips();
    }
}
