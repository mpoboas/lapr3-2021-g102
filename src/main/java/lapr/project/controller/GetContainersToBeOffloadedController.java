package lapr.project.controller;

import lapr.project.data.ConnectionFactory;
import lapr.project.data.ContainersToBeOffloadedDB;
import lapr.project.model.Company;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;


public class GetContainersToBeOffloadedController {
    /**
     * The app
     */
    private  App app;
    /**
     * The company
     */
    private final Company company;


    public GetContainersToBeOffloadedController(Company comp){
        this.company = comp;
    }

    public GetContainersToBeOffloadedController() {
        this(App.getInstance().getCompany());
        app = App.getInstance();
    }

    public List<String> getContainersToBeOffloaded(ConnectionFactory connectionFactory) throws SQLException {
        return new ContainersToBeOffloadedDB().getContainersToBeOffloaded(1, Timestamp.valueOf(LocalDateTime.now()), connectionFactory);
    }
}
