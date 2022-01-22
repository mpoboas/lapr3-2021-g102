package Demos;


import lapr.project.controller.GetAvailableShipsController;
import lapr.project.data.ConnectionFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class US210Demo {
    static GetAvailableShipsController ctrl = new GetAvailableShipsController();

    public static void main(String[] args) {
        System.out.println("Operation:");
        System.out.println("Get Next Monday Available Ships:");
        System.out.println();
        System.out.println("Next Monday: " + ctrl.getNextMonday());
        System.out.println("Available Ships: ");
        List<String> available;
        try {
            available = ctrl.getAvailableShips(ConnectionFactory.getInstance());
            for (String s : available) {
                System.out.println(s);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        System.out.println("Operation ended");




    }
}
