package lapr.project.data;


import java.sql.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class ContainersToBeLoadedDB {

    public List<String> getContainersToBeLoaded(int idShip, Timestamp date, ConnectionFactory connectionFactory) throws SQLException {
        try (Connection connection = connectionFactory.getDatabaseConnection().getConnection();
             CallableStatement callableStatement = connection.prepareCall("{ ? = call fnc_get_containers_to_be_loaded(?, ?) }");) {
            callableStatement.registerOutParameter(1, Types.REF_CURSOR);
            callableStatement.setInt(2, idShip);
            Timestamp now = Timestamp.valueOf(LocalDateTime.now());
            callableStatement.setTimestamp(3, date);
            callableStatement.execute();
            ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
            return result(resultSet);
        }
    }

    private List<String> result(ResultSet resultSet) throws SQLException {
        List<String> result = new ArrayList<>();
        while (resultSet.next()) {
            String row = "Cargo Manifest ID: " + resultSet.getString(1) + " | "
                    + "Container ID: " + resultSet.getString(2) + " | "
                    + "Container Position (X,Y,Z) " + resultSet.getInt(3) + '\n';
            result.add(row);
        }
        return result;
    }
}
