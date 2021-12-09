package lapr.project.data;

import lapr.project.model.ImportCargoManifest;
import lapr.project.model.ImportPorts;

import java.io.IOException;

public class UploadPorts implements UploadFileToDatabase {
    private String fileDirectory;
    private ImportPorts imports;
    private ConnectionFactory cf;
    private Persistable persistable;
    @Override
    public void UploadFile(String fileDirectory) throws IOException{
        this.fileDirectory = fileDirectory;
        this.cf = new ConnectionFactory();
    }

    @Override
    public void importFile(Object o) throws IOException {
        if(o instanceof ImportCargoManifest){
            imports = new ImportPorts(new lapr.project.stores.PortStore());
            imports.importPortsData(this.fileDirectory);
        }

    }
    @Override
    public void importToDatabase(){
        persistable = new PortStore();
        for(int i = 0; i< imports.getListOfImportedPorts().size(); i++){
            persistable.save(cf.getDatabaseConnection(), imports.getListOfImportedPorts().get(i));
        }
    }
}
