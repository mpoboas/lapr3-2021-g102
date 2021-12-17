package lapr.project.data;

import lapr.project.model.Border;
import lapr.project.model.ImportBorder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UploadBorder implements UploadFileToDatabase {
    private String fileDirectory;
    private ConnectionFactory cf;
    private List<Border> importedBorders;

    @Override
    public void UploadFile(String fileDirectory) throws IOException {
        this.fileDirectory = fileDirectory;
        this.cf = ConnectionFactory.getInstance();
        importedBorders = new ArrayList<>();
    }
    @Override
    public void importFile() throws IOException {
        importedBorders = ImportBorder.importBorders(fileDirectory);
    }
    @Override
    public void importToDatabase() {
        Persistable persistable = new BorderStoreDB();
        for (Border b : importedBorders) {
            persistable.save(cf.getDatabaseConnection(),b);
        }
    }
}
