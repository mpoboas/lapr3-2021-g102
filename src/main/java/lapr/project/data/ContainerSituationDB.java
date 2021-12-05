package lapr.project.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class ContainerSituationDB {

    public String getSituation(int containerID, ConnectionFactory conFac) throws SQLException {
        Connection conn = conFac.getDatabaseConnection().getConnection();
        CallableStatement clst = conn.prepareCall("{ ? = call fnc_get_container_status(?) }");
        clst.registerOutParameter(1, Types.VARCHAR);
        clst.setInt(2,containerID);
        System.out.println(clst.execute());
        return  clst.getString(1);
    }
}
