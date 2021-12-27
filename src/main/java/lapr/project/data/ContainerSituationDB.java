package lapr.project.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class ContainerSituationDB {

    /**
     * Gets the situation of a specific container
     * @param containerID the container id to get the situation
     * @param conFac
     * @return the situation of the container
     * @throws SQLException
     */
    public String getSituation(int containerID, ConnectionFactory conFac) throws SQLException {
        Connection conn = conFac.getDatabaseConnection().getConnection();
        try ( CallableStatement clst = conn.prepareCall("{ ? = call fnc_get_container_status(?) }");) {
            clst.registerOutParameter(1, Types.VARCHAR);
            clst.setInt(2, containerID);
            clst.execute();
            return clst.getString(1);
        }
    }

    /**
     * Gets the situation of a specific container
     * @param containerID the container id to get the situation
     * @param conFac
     * @return the situation of the container
     * @throws SQLException
     */
    public String getSituation2(String containerID, String name, ConnectionFactory conFac) throws SQLException {
        Connection conn = conFac.getDatabaseConnection().getConnection();
        try (CallableStatement clst = conn.prepareCall("{ ? = call fnc_get_container_status(?,?) }");) {
            clst.registerOutParameter(1, Types.VARCHAR);
            clst.setString(2, containerID);
            clst.setString(3, name);
            clst.execute();
            return clst.getString(1);
        }
    }
}
