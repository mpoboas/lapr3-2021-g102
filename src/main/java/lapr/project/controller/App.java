package lapr.project.controller;

import lapr.project.auth.AuthFacade;
import lapr.project.auth.UserSession;
import lapr.project.model.Company;
import lapr.project.utils.Constants;

public class App {
    private AuthFacade authFacade;
    private Company company;

    private App() {
        this.company = new Company("Company");
        this.authFacade = this.company.getAuthFacade();
        bootstrap();
    }

    public Company getCompany() {
        return this.company;
    }

    public UserSession getCurrentUserSession() {
        return this.authFacade.getCurrentUserSession();
    }

    public boolean doLogin(String email, String pwd) {
        return this.authFacade.doLogin(email, pwd).isLoggedIn();
    }

    public void doLogout() {
        this.authFacade.doLogout();
    }

    private void bootstrap() {
        this.authFacade.addUserRole(Constants.U_ROLE_CLIENT, Constants.U_ROLE_CLIENT);
        this.authFacade.addUserRole(Constants.U_ROLE_FLEET_MANAGER, Constants.U_ROLE_FLEET_MANAGER);
        this.authFacade.addUserRole(Constants.U_ROLE_TRAFFIC_MANAGER, Constants.U_ROLE_TRAFFIC_MANAGER);
        this.authFacade.addUserRole(Constants.U_ROLE_WAREHOUSE_STAFF, Constants.U_ROLE_WAREHOUSE_STAFF);
        this.authFacade.addUserRole(Constants.U_ROLE_WAREHOUSE_MANAGER, Constants.U_ROLE_WAREHOUSE_MANAGER);
        this.authFacade.addUserRole(Constants.U_ROLE_PORT_STAFF, Constants.U_ROLE_PORT_STAFF);
        this.authFacade.addUserRole(Constants.U_ROLE_PORT_MANAGER, Constants.U_ROLE_PORT_MANAGER);
        this.authFacade.addUserRole(Constants.U_ROLE_SHIP_CAPTAIN, Constants.U_ROLE_SHIP_CAPTAIN);
        this.authFacade.addUserRole(Constants.U_ROLE_SHIP_CHIEF_ELECTRIC_ENGINEER, Constants.U_ROLE_SHIP_CHIEF_ELECTRIC_ENGINEER);
        this.authFacade.addUserRole(Constants.U_ROLE_TRUCK_DRIVER, Constants.U_ROLE_TRUCK_DRIVER);
        this.authFacade.addUserRole(Constants.U_ROLE_PROJECT_MANAGER, Constants.U_ROLE_PROJECT_MANAGER);

        this.authFacade.addUserWithRole("Client", "client@lei.sem3.pt", "123456", Constants.U_ROLE_CLIENT);
        this.authFacade.addUserWithRole("Fleet Manager", "fleetmanager@lei.sem3.pt", "123456", Constants.U_ROLE_FLEET_MANAGER);
        this.authFacade.addUserWithRole("Traffic Manager", "trafficmanager@lei.sem3.pt", "123456", Constants.U_ROLE_TRAFFIC_MANAGER);
        this.authFacade.addUserWithRole("Port Staff", "portstaff@lei.sem3.pt", "123456", Constants.U_ROLE_PORT_STAFF);
        this.authFacade.addUserWithRole("Port Manager", "portmanager@lei.sem3.pt", "123456", Constants.U_ROLE_PORT_MANAGER);
        this.authFacade.addUserWithRole("Ship Captain", "shipcaptain@lei.sem3.pt", "123456", Constants.U_ROLE_SHIP_CAPTAIN);
        this.authFacade.addUserWithRole("Ship Chief Electric Engineer", "shipchiefelectricengineer@lei.sem3.pt", "123456", Constants.U_ROLE_SHIP_CHIEF_ELECTRIC_ENGINEER);
        this.authFacade.addUserWithRole("Project Manager", "projectmanager@lei.sem3.pt", "123456", Constants.U_ROLE_PROJECT_MANAGER);

        // Clients
        this.authFacade.addUserWithRole("joaoteixeira08", "joaoteixeira08@lei.sem3.pt", "123456", Constants.U_ROLE_CLIENT);
        this.authFacade.addUserWithRole("luisFigo07", "luisfigo07@lei.sem3.pt", "123456", Constants.U_ROLE_CLIENT);

        // Port Manager
        this.authFacade.addUserWithRole("joaosilva12", "joaosilva12@lei.sem3.pt", "123456", Constants.U_ROLE_PORT_MANAGER);
    }

    // Extracted from https://www.javaworld.com/article/2073352/core-java/core-java-simply-singleton.html?page=2
    private static App singleton = null;

    public static App getInstance() {
        if (singleton == null) {
            synchronized (App.class) {
                singleton = new App();
            }
        }
        return singleton;
    }
}
