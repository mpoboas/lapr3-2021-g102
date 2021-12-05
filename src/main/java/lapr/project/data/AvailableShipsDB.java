package lapr.project.data;


import java.sql.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class AvailableShipsDB {

    public List<String> getAvailableShips(ConnectionFactory connectionFactory) throws SQLException {
        try (Connection conn = connectionFactory.getDatabaseConnection().getConnection();
             CallableStatement clst = conn.prepareCall("{ ? = call fnc_n_monday_available_ship(?) }");) {
            clst.registerOutParameter(1, Types.REF_CURSOR);
            Timestamp now = Timestamp.valueOf(LocalDateTime.now());
            clst.setTimestamp(2, now);
            ResultSet resultSet = (ResultSet) clst.getObject(1);
            return result(resultSet);
        }
    }

    public String getNextMonday() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return dateFormatter.format(LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)));
    }

    private List<String> result(ResultSet resultSet) throws SQLException {
        List<String> result = new ArrayList<>();
        while (resultSet.next()) {
            String row = "VehicleID: " + resultSet.getString(1) + " | "
                    + "Ship Name: " + resultSet.getString(2) + " | "
                    + "Ship MMSI: " + resultSet.getInt(3) + " | "
                    + "Latitude: " + resultSet.getDouble(4) + " | "
                    + "Longitude: " + resultSet.getDouble(5) + '\n';
            result.add(row);
        }
        return result;
    }
}
