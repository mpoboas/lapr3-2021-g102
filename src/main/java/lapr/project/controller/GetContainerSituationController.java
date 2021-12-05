package lapr.project.controller;

import lapr.project.data.ConnectionFactory;

import lapr.project.data.ContainerSituationDB;
import lapr.project.model.Company;


import java.io.IOException;
import java.sql.*;

public class GetContainerSituationController {
    /**
     * The app
     */
    private  App app;
    /**
     * The company
     */
    private final Company company;


    public GetContainerSituationController(Company comp){
        this.company = comp;
    }

    public GetContainerSituationController() {
        this(App.getInstance().getCompany());
        app = App.getInstance();
    }

    public String getSituation(int containerID, ConnectionFactory connectionFactory) throws SQLException {
        return new ContainerSituationDB().getSituation(containerID,connectionFactory);
    }
}
