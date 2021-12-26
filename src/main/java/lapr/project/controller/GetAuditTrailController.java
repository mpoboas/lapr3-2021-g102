package lapr.project.controller;

import lapr.project.data.AuditTrailDB;
import lapr.project.data.ConnectionFactory;
import lapr.project.model.Company;

import java.sql.SQLException;
import java.util.List;

public class GetAuditTrailController {
    /**
     * The App
     */
    private App app;
    /**
     * The company
     */
    private Company company;
    /**
     * The connection between audit trails between the database
     */
    private AuditTrailDB auditTrailDB;
    /**
     * The connection factory
     */
    private ConnectionFactory cf;


    public GetAuditTrailController(Company company){
        this.company = company;
    }

    /**
     * The constructor of the controller
     * @param auditTrailDB The connection between audit trails between the database
     * @param cf The connection factory
     */
    public GetAuditTrailController(AuditTrailDB auditTrailDB, ConnectionFactory cf){
        this(App.getInstance().getCompany());
        this.auditTrailDB = auditTrailDB;
        this.cf = cf;
        app = App.getInstance();
    }

    /**
     * Returns the list of all audit trails of a given container of a given cargo manifest
     * @param containerID the container id
     * @param cargoManifestID the cargo manifest Id
     * @return the list with the audit trails
     * @throws SQLException exception
     */
    public List<String> getAuditTrails(String containerID, int cargoManifestID) throws SQLException {
        return auditTrailDB.getAuditTrails(containerID,cargoManifestID,cf);
    }
}
