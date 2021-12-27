package lapr.project.controller;

import lapr.project.data.ConnectionFactory;
import lapr.project.data.ContainerSituationDB;
import lapr.project.model.Company;
import lapr.project.utils.Constants;

import java.sql.SQLException;

public class GetContainerSituationControllerV2 {
    /**
     * The app
     */
    private  App app;
    /**
     * The company
     */
    private final Company company;
    /**
     * The connection between container situation and the database
     */
    private ContainerSituationDB containerSituationDB;
    /**
     * The connection factory
     */
    private ConnectionFactory connectionFactory;


    /**
     * Constructor of the controller
     * @param comp the company
     */
    public GetContainerSituationControllerV2(Company comp){
        this.company = comp;
    }

    /**
     * The main constructor of the controller
     * @param containerSituationDB The connection between container situation and the database
     * @param cf The connection factory
     */
    public GetContainerSituationControllerV2(ContainerSituationDB containerSituationDB, ConnectionFactory cf) {
        this(App.getInstance().getCompany());
        app = App.getInstance();
        this.containerSituationDB = containerSituationDB;
        this.connectionFactory = cf;
    }

    /**
     * The situation of a given container
     * @param containerID the container id
     * @return the current situation
     * @throws SQLException exception
     */
    public String getSituation2(String containerID) throws SQLException {
        if (this.app.getCurrentUserSession().isLoggedInWithRole(Constants.U_ROLE_CLIENT)){
            return containerSituationDB.getSituation2(containerID,app.getCurrentUserSession().getUserName().trim(),connectionFactory);
        } else
            throw new IllegalStateException("Not authorized");
    }


}
