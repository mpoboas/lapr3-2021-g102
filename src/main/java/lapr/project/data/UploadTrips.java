package lapr.project.data;

import lapr.project.model.Import;
import lapr.project.model.ImportTrip;
import lapr.project.model.Ship;
import lapr.project.model.Trips;
import lapr.project.stores.ShipStore;

import java.io.IOException;
import java.util.List;

public class UploadTrips implements UploadFileToDatabase{
    private String fileDirectory;
    private ImportTrip imports;
    private ConnectionFactory cf;
    private Persistable persistable;
    @Override
    public void UploadFile(String fileDirectory) throws IOException {
        this.fileDirectory = fileDirectory;
        this.cf = new ConnectionFactory();
    }

    @Override
    public void importFile() throws IOException {
        imports = new ImportTrip();
        imports.importTripsData(this.fileDirectory);
    }
    @Override
    public void importToDatabase(){
        persistable = new TripStore();
        List<Trips> list = imports.getListOfImportedTrips();
        for (Trips trip : list) {
            persistable.save(cf.getDatabaseConnection(), trip);
        }
    }
}
