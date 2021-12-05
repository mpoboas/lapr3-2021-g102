package lapr.project.controller;

import lapr.project.data.ConnectionFactory;

import lapr.project.data.ContainerSituationDB;
import lapr.project.model.Company;

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


    /**
     * Constructor of the controller
     * @param comp the company
     */
    public GetContainerSituationController(Company comp){
        this.company = comp;
    }

    /**
     * Constructor without parameters
     */
    public GetContainerSituationController() {
        this(App.getInstance().getCompany());
        app = App.getInstance();
    }

    /**
     * Gets the situation of a specific container
     * @param containerID the container id to know the situation
     * @param connectionFactory
     * @return the situation
     * @throws SQLException
     */
    public String getSituation(int containerID, ConnectionFactory connectionFactory) throws SQLException {
        return new ContainerSituationDB().getSituation(containerID,connectionFactory);
    }
}
