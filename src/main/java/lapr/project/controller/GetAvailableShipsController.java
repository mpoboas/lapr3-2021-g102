package lapr.project.controller;

import lapr.project.data.AvailableShipsDB;
import lapr.project.data.ConnectionFactory;
import lapr.project.model.Company;

import java.sql.SQLException;
import java.util.List;


public class GetAvailableShipsController {
    /**
     * The app
     */
    private  App app;
    /**
     * The company
     */
    private final Company company;


    public GetAvailableShipsController(Company comp){
        this.company = comp;
    }

    public GetAvailableShipsController() {
        this(App.getInstance().getCompany());
        app = App.getInstance();
    }

    public String getNextMonday(){
        return new AvailableShipsDB().getNextMonday();
    }

    public List<String> getAvailableShips(ConnectionFactory connectionFactory) throws SQLException {
        return new AvailableShipsDB().getAvailableShips(connectionFactory);
    }
}
