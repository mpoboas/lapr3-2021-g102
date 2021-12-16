package lapr.project.data;

import lapr.project.model.ImportSeadist;
import lapr.project.model.SeaDist;

import java.io.IOException;
import java.util.List;

public class UploadSeadist implements UploadFileToDatabase {
    private String fileDirectory;
    private ImportSeadist imports;
    private ConnectionFactory cf;

    @Override
    public void UploadFile(String fileDirectory) throws IOException {
        this.fileDirectory = fileDirectory;
        this.cf = new ConnectionFactory();
    }

    @Override
    public void importFile() throws IOException {
        imports = new ImportSeadist();
        imports.importSeadistData(this.fileDirectory);
    }

    @Override
    public void importToDatabase() {
        Persistable persistable = new SeadistStoreDB();
        List<SeaDist> list = imports.getImportedSeadistList();
        for (int i = 0; i < list.size(); i++) {
            persistable.save(cf.getDatabaseConnection(), list.get(i));
        }
    }
}
