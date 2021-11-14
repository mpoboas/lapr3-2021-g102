package lapr.project.controller;

import lapr.project.model.Import;
import lapr.project.stores.ShipStore;

import java.io.IOException;

public class ImportShipsController {
<<<<<<< HEAD
    private App app;
    private Company company;
    private Import imp;
    private ShipStore store;


    public ImportShipsController() {
        this(App.getInstance().getCompany());
        app = App.getInstance();
    }

    public ImportShipsController(Company company) {
        this.company = company;
        imp = new Import(company.getShipStore());
    }

=======
    private final Import imp;

>>>>>>> b5c05951519b6cb19c6500cde8dfc4fcd3d10f20
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
