package lapr.project.data;

import lapr.project.model.ImportPorts;
import lapr.project.model.Port;
import lapr.project.stores.PortStore;

import java.io.IOException;
import java.util.List;

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
    public void importFile() throws IOException {
        imports = new ImportPorts(new PortStore());
        imports.importPortsData(this.fileDirectory);

    }
    @Override
    public void importToDatabase(){
        persistable = new PortStoreDB();
        List<Port> list = imports.getListOfImportedPorts();
        for(int i = 0; i< list.size(); i++){
            persistable.save(cf.getDatabaseConnection(), list.get(i));
        }
    }
}
