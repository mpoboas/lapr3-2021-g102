package lapr.project.controller;

import lapr.project.model.Company;
import lapr.project.model.ImportPorts;
import lapr.project.stores.PortStore;

import java.io.IOException;

/**
 * This class controls imports ports from a file
 */
public class ImportPortsController {
    /**
     * The app
     */
    private App app;
    /**
     * The company
     */
    private Company company;

    /**
     * The class that imports
     */
    private ImportPorts imp;
    //private PortStore store;

    public ImportPortsController(Company comp){
        this.company = comp;
        imp = new ImportPorts(comp.getPortStore());
    }

    public ImportPortsController(){
        this(App.getInstance().getCompany());
        app = App.getInstance();
    }

    public ImportPortsController(PortStore store){
        //this.store = store;
        imp = new ImportPorts(store);
    }

    /**
     * This method commands the import of the ports
     * @param fileDirectory the file directory that contains the ports information
     * @throws IOException possible exceptions
     */
    public void importPortsData(String fileDirectory) throws IOException {
        imp.importPortsData(fileDirectory);
    }

    /**
     * Adds all the ports imported to the store
     */
    public void addImportedPorts(){
        imp.addPorts();
    }

    /**
     * @return Gets the importer class
     */
    public ImportPorts getImporter(){
        return this.imp;
    }
}
