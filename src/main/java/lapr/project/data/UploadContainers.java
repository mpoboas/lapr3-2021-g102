package lapr.project.data;

import lapr.project.model.ImportCargoManifest;
import lapr.project.model.ImportContainers;

import java.io.IOException;

public class UploadContainers implements UploadFileToDatabase{
    private String fileDirectory;
    private ImportContainers imports;
    private ConnectionFactory cf;
    private Persistable persistable;
    @Override
    public void UploadFile(String fileDirectory) throws IOException{
        this.fileDirectory = fileDirectory;
        this.cf = new ConnectionFactory();
    }

    @Override
    public void importFile() {
            imports = new ImportContainers();
            imports.importContainersData(this.fileDirectory);
    }
    @Override
    public void importToDatabase(){
        persistable = new ContainerStore();
        for(int i = 0; i< imports.getListOfImportedContainers().size(); i++){
            persistable.save(cf.getDatabaseConnection(), imports.getListOfImportedContainers().get(i));
        }
    }

}
