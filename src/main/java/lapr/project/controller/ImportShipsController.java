package lapr.project.controller;

import lapr.project.model.Import;
import lapr.project.stores.ShipStore;

import java.io.IOException;

public class ImportShipsController {
    private final Import imp;

    public ImportShipsController(ShipStore store){
        imp = new Import(store);
    }

    public void importShipData(String fileDirectory) throws IOException {
        imp.importShipsData(fileDirectory);
    }

    public void addShips(){
        imp.addShips();
    }

    public Import getImp() {
        return imp;
    }
}
