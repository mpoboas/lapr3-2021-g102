package Demos;

import lapr.project.controller.App;
import lapr.project.controller.AuthController;
import lapr.project.controller.GetLoadingUnloadingMapController;
import lapr.project.data.ConnectionFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class US407Demo {
    static Scanner scan = new Scanner(System.in);
    static GetLoadingUnloadingMapController ctrl = new GetLoadingUnloadingMapController();
    static ConnectionFactory connectionFactory;

    static {
        try {
            connectionFactory = new ConnectionFactory();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        System.out.println("Enter email: (e.g. joaosilva12@lei.sem3.pt)");
        String email = scan.next();
        System.out.println("Enter password: ");
        String password = scan.next();
        AuthController authController = new AuthController();
        if (!authController.doLogin(email, password)) System.out.println("Invalid Login Credentials.");
        else {
            String username = App.getInstance().getCurrentUserSession().getUserName();
            int employeeId = ctrl.getEmployeeId(username, connectionFactory);
            String employeeName = ctrl.getPortManagerName(employeeId, connectionFactory);
            int portId = ctrl.getPortId(employeeId, connectionFactory);
            String portName = ctrl.getPortName(portId, connectionFactory);
            System.out.println("Date (e.g: 2021-12-01T00:00:00):");
            LocalDateTime date = LocalDateTime.parse(scan.next());
            LocalDateTime seven_days_from_date = date.plusDays(7);
            System.out.println("Loading/unloading map for the port " + portName + " between " + date + " and " + seven_days_from_date);
            System.out.println("Port Manager: " + employeeName);
            List<String> arrivalDepartures = ctrl.getLoadingUnloadingMap(username, Timestamp.valueOf(date), connectionFactory);
            for (String arrivalDeparture : arrivalDepartures) {
                System.out.println(arrivalDeparture);
            }
        }

    }
}
