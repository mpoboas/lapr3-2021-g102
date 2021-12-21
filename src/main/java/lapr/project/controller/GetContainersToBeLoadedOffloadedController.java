package lapr.project.controller;

import lapr.project.data.ConnectionFactory;
import lapr.project.data.ContainersToBeLoadedOffloadedDB;
import lapr.project.model.Company;
import org.mockito.internal.verification.Times;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;


public class GetContainersToBeLoadedOffloadedController {
    /**
     * The app
     */
    private  App app;
    /**
     * The company
     */
    private final Company company;


    public GetContainersToBeLoadedOffloadedController(Company comp){
        this.company = comp;
    }

    public GetContainersToBeLoadedOffloadedController() {
        this(App.getInstance().getCompany());
        app = App.getInstance();
    }


    public List<String> getContainersToBeOffloaded(int idShip, Timestamp date, ConnectionFactory connectionFactory) throws SQLException {
        return new ContainersToBeLoadedOffloadedDB().getContainersToBeOffloaded(idShip, date, connectionFactory);
    }

    public List<String> getContainersToBeLoaded(int idShip, Timestamp date, ConnectionFactory connectionFactory) throws SQLException {
        return new ContainersToBeLoadedOffloadedDB().getContainersToBeLoaded(idShip, date, connectionFactory);
    }
}
