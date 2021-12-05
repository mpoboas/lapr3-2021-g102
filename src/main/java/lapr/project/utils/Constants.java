package lapr.project.utils;

/**
 *
 * @author Paulo Maio <pam@isep.ipp.pt>
 */
public class Constants {
    private Constants(){
        throw new IllegalStateException("Utility class");
    }
    //User roles
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
}
