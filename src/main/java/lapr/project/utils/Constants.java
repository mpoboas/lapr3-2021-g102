package lapr.project.utils;

import lapr.project.model.Ship;

/**
 *
 * @author Paulo Maio <pam@isep.ipp.pt>
 */
public class Constants {
    private Constants(){
        throw new IllegalStateException("Utility class");
    }
    // User roles
    public static final String U_ROLE_CLIENT = "CLIENT";
    public static final String U_ROLE_FLEET_MANAGER = "FLEET MANAGER";
    public static final String U_ROLE_TRAFFIC_MANAGER = "TRAFFIC MANAGER";
    public static final String U_ROLE_WAREHOUSE_STAFF = "WAREHOUSE STAFF";
    public static final String U_ROLE_WAREHOUSE_MANAGER = "WAREHOUSE MANAGER";
    public static final String U_ROLE_PORT_STAFF = "PORT STAFF";
    public static final String U_ROLE_PORT_MANAGER = "PORT MANAGER";
    public static final String U_ROLE_SHIP_CAPTAIN = "SHIP CAPTAIN";
    public static final String U_ROLE_SHIP_CHIEF_ELECTRIC_ENGINEER = "SHIP CHIEF ELECTRIC ENGINEER";
    public static final String U_ROLE_TRUCK_DRIVER = "TRUCK DRIVER";
    public static final String U_ROLE_PROJECT_MANAGER = "PROJECT MANAGER";

    // Ship power equipment
    public static final double AUX_EQUIPMENT_MAX_POWER = 75000;

    // Containers
    public static final double CONTAINER_MASS = 500.0;
    public static final double CONTAINER_LENGTH = 12.036;
    public static final double CONTAINER_WIDTH = 2.35;

    // Ships
    public static final double SHIP_MASS = 6278000.0;
    public static final double SHIP_AVAILABLE_LENGTH = 121.0;
    public static final double SHIP_AVAILABLE_WIDTH = 24.0;

    public static final Ship EVER_ACE = new Ship(366971340,"Ever Ace","-","D5OB2",0,400,62,13.5,235579);
    public static final Ship CASON_J_CALLAWAY= new Ship(366971340,"Sabrina","-","D5OB2",0,228.45,21.34,8,12309);
    public static final Ship SABRINA = new Ship(366971341,"Sabrina","-","D5OB3",0,175,28,8.5,6278000.0);
    public static final double WATER_DENSITY = 1.027;
}
