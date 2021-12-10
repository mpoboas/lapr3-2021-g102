package lapr.project.data;

import lapr.project.model.ImportCargoManifest;

import java.io.IOException;

public class UploadCargoManifests implements UploadFileToDatabase{
    private String fileDirectory;
    private ImportCargoManifest imports;
    private ConnectionFactory cf;
    private Persistable persistable;
    @Override
    public void UploadFile(String fileDirectory) throws IOException{
        this.fileDirectory = fileDirectory;
        this.cf = new ConnectionFactory();
    }

    @Override
    public void importFile() throws IOException {
            imports = new ImportCargoManifest();
            imports.importCargoManifestData(this.fileDirectory);

    }
    @Override
    public void importToDatabase(){
        persistable = new CargoManifestStore();
        for(int i = 0; i< imports.getListOfImportedCargoManifests().size(); i++){
            persistable.save(cf.getDatabaseConnection(), imports.getListOfImportedCargoManifests().get(i));
        }
    }
}
