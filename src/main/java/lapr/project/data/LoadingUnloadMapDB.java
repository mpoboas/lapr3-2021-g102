package lapr.project.data;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LoadingUnloadMapDB {

    public int getEmployeeId(String portManagerUsername, ConnectionFactory connectionFactory) throws SQLException {
        String sqlCommand = "SELECT IDEMPLOYEE FROM EMPLOYEE WHERE IDUSER = (SELECT IDUSER FROM SYSTEMUSER WHERE USERNAME = ?)";
        Connection connection = connectionFactory.getDatabaseConnection().getConnection();
        try (PreparedStatement getEmployeeId = connection.prepareStatement(sqlCommand)) {
            getEmployeeId.setString(1, portManagerUsername);
            try (ResultSet rs = getEmployeeId.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    public String getEmployeeName(int employeeId, ConnectionFactory connectionFactory) throws SQLException {
        String sqlCommand = "SELECT NAME FROM EMPLOYEE WHERE IDEMPLOYEE = ?";
        Connection connection = connectionFactory.getDatabaseConnection().getConnection();
        try (PreparedStatement getPortManagerName = connection.prepareStatement(sqlCommand)) {
            getPortManagerName.setInt(1, employeeId);
            try (ResultSet rs = getPortManagerName.executeQuery()) {
                rs.next();
                return rs.getString(1);
            }
        }
    }

    public int getPortId(int employeeId, ConnectionFactory connectionFactory) throws SQLException {
        String sqlCommand = "SELECT IDPORTWAREHOUSE FROM PORTWAREHOUSESTAFF WHERE IDEMPLOYEE = ?";
        Connection connection = connectionFactory.getDatabaseConnection().getConnection();
        try (PreparedStatement getPortId = connection.prepareStatement(sqlCommand)) {
            getPortId.setInt(1, employeeId);
            try (ResultSet rs = getPortId.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    public String getPortName(int idPort, ConnectionFactory connectionFactory) throws SQLException {
        String sqlCommand = "SELECT NAME FROM PORTWAREHOUSE WHERE IDPORTWAREHOUSE = ?";
        Connection connection = connectionFactory.getDatabaseConnection().getConnection();
        try (PreparedStatement getPortName = connection.prepareStatement(sqlCommand)) {
            getPortName.setInt(1, idPort);
            try (ResultSet rs = getPortName.executeQuery()) {
                rs.next();
                return rs.getString(1);
            }
        }
    }

    public int getVehicleId(int idTrip, ConnectionFactory connectionFactory) throws SQLException {
        String sqlCommand = "SELECT IDVEHICLE FROM TRIP WHERE IDTRIP = ?";
        Connection connection = connectionFactory.getDatabaseConnection().getConnection();
        try (PreparedStatement getVehicleId = connection.prepareStatement(sqlCommand)) {
            getVehicleId.setInt(1, idTrip);
            try (ResultSet rs = getVehicleId.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    public int getVehicleType(int idVehicle, ConnectionFactory connectionFactory) throws SQLException {
        String sqlCommand = "SELECT IDVEHICLETYPE FROM VEHICLE WHERE IDVEHICLE = ?";
        Connection connection = connectionFactory.getDatabaseConnection().getConnection();
        try (PreparedStatement getVehicleType = connection.prepareStatement(sqlCommand)) {
            getVehicleType.setInt(1, idVehicle);
            try (ResultSet rs = getVehicleType.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    public String getVehicleName(int idVehicle, int vehicleType, ConnectionFactory connectionFactory) throws SQLException {
        String sqlCommand;
        if (vehicleType == 1) sqlCommand = "SELECT SHIPNAME FROM SHIP WHERE IDVEHICLE = ?";
        else return ("TRUCK " + idVehicle);
        Connection connection = connectionFactory.getDatabaseConnection().getConnection();
        try (PreparedStatement getVehicleName = connection.prepareStatement(sqlCommand)) {
            getVehicleName.setInt(1, idVehicle);
            try (ResultSet rs = getVehicleName.executeQuery()) {
                rs.next();
                return rs.getString(1);
            }
        }
    }

    public ResultSet getLoadingUnloadingMapRaw(String portManagerUsername, Timestamp date, ConnectionFactory connectionFactory) throws SQLException {
        Connection connection = connectionFactory.getDatabaseConnection().getConnection();
        try (CallableStatement callableStatement = connection.prepareCall("{ ? = call fnc_get_loading_unloading_map(?, ?) }");) {
            callableStatement.registerOutParameter(1, Types.REF_CURSOR);
            callableStatement.setString(2, portManagerUsername);
            Timestamp now = Timestamp.valueOf(LocalDateTime.now());
            callableStatement.setTimestamp(3, date);
            callableStatement.execute();
            ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
            return resultSet;
        }
    }

    public List<String> getLoadingUnloadingMap(String portManagerUsername, Timestamp date, ConnectionFactory connectionFactory) throws SQLException {
        Connection connection = connectionFactory.getDatabaseConnection().getConnection();
        try (CallableStatement callableStatement = connection.prepareCall("{ ? = call fnc_get_loading_unloading_map(?, ?) }");) {
            callableStatement.registerOutParameter(1, Types.REF_CURSOR);
            callableStatement.setString(2, portManagerUsername);
            Timestamp now = Timestamp.valueOf(LocalDateTime.now());
            callableStatement.setTimestamp(3, date);
            callableStatement.execute();
            ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
            return result(resultSet, connectionFactory);
        }
    }

    public List<String> getManifestContainers(int idCargoManifest, ConnectionFactory connectionFactory) throws SQLException {
        Connection connection = connectionFactory.getDatabaseConnection().getConnection();
        try (CallableStatement callableStatement = connection.prepareCall("{ ? = call fnc_get_manifest_containers(?) }");) {
            callableStatement.registerOutParameter(1, Types.REF_CURSOR);
            callableStatement.setInt(2, idCargoManifest);
            callableStatement.execute();
            ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
            return result(resultSet, connectionFactory);
        }
    }

    private List<String> result(ResultSet arrivalDepartures, ConnectionFactory cf) throws SQLException {
        List<String> result = new ArrayList<>();
        while (arrivalDepartures.next()) {
            int idCargoManifest = arrivalDepartures.getInt(4);
            if (idCargoManifest != 0) {
                int cargoManifestType = arrivalDepartures.getInt(5);
                if (cargoManifestType == 1) {
                    String row = "[TRIP " + arrivalDepartures.getInt(2) + "] " + arrivalDepartures.getString(1) + " | LOAD | "
                            + this.getPortName(arrivalDepartures.getInt(3), cf) + " -> " + this.getVehicleName(this.getVehicleId(arrivalDepartures.getInt(2), cf),this.getVehicleType(this.getVehicleId(arrivalDepartures.getInt(2), cf), cf), cf);
                         result.add(row);

                } else if (cargoManifestType == 2) {
                    String row = "[TRIP " + arrivalDepartures.getInt(2) + "] " + arrivalDepartures.getString(1) + " | UNLOAD | "
                            +  this.getVehicleName(this.getVehicleId(arrivalDepartures.getInt(2), cf),this.getVehicleType(this.getVehicleId(arrivalDepartures.getInt(2), cf), cf), cf) + " -> " + this.getPortName(arrivalDepartures.getInt(3), cf);
                    result.add(row);
                }
            }

        }
        return result;
    }
}
