package lapr.project.data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuditTrailDB {

    /**
     * This method accesses the database and gets the list of audit trails of a given container of a given cargo manifest
     * @param containerID container identifier
     * @param cargoManifestID cargo manifest identifier
     * @param cf the connection
     * @return returns the list of all the audits trails
     * @throws SQLException
     */
    public List<String> getAuditTrails(String containerID, int cargoManifestID, ConnectionFactory cf) throws SQLException {
        Connection conn = cf.getDatabaseConnection().getConnection();
        try(CallableStatement statement = conn.prepareCall("{? = call fnc_get_audit_trails(?,?)}")){
            statement.registerOutParameter(1, Types.REF_CURSOR);
            statement.setString(2,containerID);
            statement.setInt(3,cargoManifestID);
            statement.execute();
            ResultSet resultSet = (ResultSet) statement.getObject(1);
            return resultSetToList(resultSet);
        }
    }

    /**
     * This method transforms the result set to a list of strings
     * @param rs result set o list
     * @return the list
     * @throws SQLException
     */
    private List<String> resultSetToList(ResultSet rs) throws SQLException{
        List<String> list = new ArrayList<>();
        while (rs.next()){
            String row = "User/Login: " + rs.getString(1) + " | "
                    + "Date&Time of Operation: " + rs.getTimestamp(2) + " | "
                    + "Type of Operation: " + rs.getString(3) + " | "
                    + "Container ID: " + rs.getString(4) + " | "
                    + "Cargo Manifest ID: " + rs.getInt(5);
            list.add(row);
        }
        return list;
    }


}
