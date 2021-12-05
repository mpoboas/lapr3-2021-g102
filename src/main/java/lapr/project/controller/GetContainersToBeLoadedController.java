package lapr.project.controller;

import lapr.project.data.ConnectionFactory;
import lapr.project.data.ContainersToBeLoadedDB;
import lapr.project.model.Company;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;


public class GetContainersToBeLoadedController {
    /**
     * The app
     */
    private  App app;
    /**
     * The company
     */
    private final Company company;


    public GetContainersToBeLoadedController(Company comp){
        this.company = comp;
    }

    public GetContainersToBeLoadedController() {
        this(App.getInstance().getCompany());
        app = App.getInstance();
    }

    public List<String> getContainersToBeLoaded(ConnectionFactory connectionFactory) throws SQLException {
        return new ContainersToBeLoadedDB().getContainersToBeLoaded(1, Timestamp.valueOf(LocalDateTime.now()), connectionFactory);
    }
}
