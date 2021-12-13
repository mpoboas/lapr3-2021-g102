package lapr.project.data;

import lapr.project.model.Import;
import lapr.project.model.Ship;
import lapr.project.stores.ShipStore;

import java.io.IOException;
import java.util.List;

public class UploadShip implements UploadFileToDatabase{
    private String fileDirectory;
    private Import imports;
    private ConnectionFactory cf;
    private Persistable persistable;
    @Override
    public void UploadFile(String fileDirectory) throws IOException {
        this.fileDirectory = fileDirectory;
        this.cf = new ConnectionFactory();
    }

    @Override
    public void importFile() throws IOException {
        imports = new Import(new ShipStore());
        imports.importShipsData(this.fileDirectory);

    }
    @Override
    public void importToDatabase(){
        persistable = new ShipStoreDB();
        List<Ship> list = imports.getListOfImportedShips();
        for (Ship ship : list) {
            persistable.save(cf.getDatabaseConnection(), ship);
        }
    }
}
