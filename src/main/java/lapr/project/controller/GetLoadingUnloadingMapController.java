package lapr.project.controller;

import lapr.project.data.ConnectionFactory;
import lapr.project.data.ContainersToBeLoadedOffloadedDB;
import lapr.project.data.LoadingUnloadMapDB;
import lapr.project.model.Company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class GetLoadingUnloadingMapController {
    /**
     * The app
     */
    private  App app;
    /**
     * The company
     */
    private final Company company;


    public GetLoadingUnloadingMapController(Company comp){
        this.company = comp;
    }

    public GetLoadingUnloadingMapController() {
        this(App.getInstance().getCompany());
        app = App.getInstance();
    }

    public List<String> getLoadingUnloadingMap(String username, Timestamp date, ConnectionFactory connectionFactory) throws SQLException {
        return new LoadingUnloadMapDB().getLoadingUnloadingMap(username, date, connectionFactory);
    }

    public ResultSet getLoadingUnloadingMapRaw(String username, Timestamp date, ConnectionFactory connectionFactory) throws SQLException {
        return new LoadingUnloadMapDB().getLoadingUnloadingMapRaw(username, date, connectionFactory);
    }

    public List<String> getManifestContainers(int idCargoManifest, ConnectionFactory connectionFactory) throws SQLException {
        return new LoadingUnloadMapDB().getManifestContainers(idCargoManifest, connectionFactory);
    }


    public int getEmployeeId(String portManagerUsername, ConnectionFactory connectionFactory) throws SQLException {
        return new LoadingUnloadMapDB().getEmployeeId(portManagerUsername, connectionFactory);
    }

    public int getPortId(int employeeId, ConnectionFactory connectionFactory) throws SQLException {
        return new LoadingUnloadMapDB().getPortId(employeeId, connectionFactory);
    }

    public String getPortName(int portId, ConnectionFactory connectionFactory) throws SQLException {
        return new LoadingUnloadMapDB().getPortName(portId, connectionFactory);
    }

    public String getPortManagerName(int employeeId, ConnectionFactory connectionFactory) throws SQLException {
        return new LoadingUnloadMapDB().getEmployeeName(employeeId, connectionFactory);
    }

    public int getVehicleId(int idTrip, ConnectionFactory connectionFactory) throws SQLException {
        return new LoadingUnloadMapDB().getVehicleId(idTrip, connectionFactory);
    }

    public int getVehicleType(int idVehicle, ConnectionFactory connectionFactory) throws SQLException {
        return new LoadingUnloadMapDB().getVehicleType(idVehicle, connectionFactory);
    }

    public String getVehicleName(int idVehicle, int vehicleType, ConnectionFactory connectionFactory) throws SQLException {
        return new LoadingUnloadMapDB().getVehicleName(idVehicle, vehicleType, connectionFactory);
    }


}
