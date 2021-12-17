package lapr.project.data;

import lapr.project.model.ImportSeadist;
import lapr.project.model.SeaDist;

import java.io.IOException;
import java.util.List;

public class UploadSeadist implements UploadFileToDatabase {
    private String fileDirectory;
    private List<SeaDist> importedSeadistList;
    private ConnectionFactory cf;

    @Override
    public void UploadFile(String fileDirectory) throws IOException {
        this.fileDirectory = fileDirectory;
        this.cf = new ConnectionFactory();
    }

    @Override
    public void importFile() throws IOException {
        this.importedSeadistList = ImportSeadist.importSeadistData(this.fileDirectory);
    }

    @Override
    public void importToDatabase() {
        Persistable persistable = new SeadistStoreDB();
        for (SeaDist seaDist : this.importedSeadistList) {
            persistable.save(cf.getDatabaseConnection(), seaDist);
        }
    }
}
