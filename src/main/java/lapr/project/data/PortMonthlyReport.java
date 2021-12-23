package lapr.project.data;

import lapr.project.model.Port;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.YearMonth;
import java.util.Date;

public class PortMonthlyReport {
    private Port port;
    private final int month;
    private final int year;
    private final int idPort;
    private int [] dailyOcupiedSpace;
    private int portMaxCapacity;
    private ConnectionFactory cf;
    public PortMonthlyReport(int idPort, int month, int year) {
        this.idPort = idPort;
        this.month = month;
        this.year = year;
    }
    public void getDataFromDB() throws SQLException, IOException {
        this.port = getPortFromDB(idPort);
        fillArrayWithDBdata();

    }
    public int[] getDailyOcupiedSpace(){
        return this.dailyOcupiedSpace;
    }
    public Port getPort(){
        return this.port;
    }
    public int getYear(){
        return this.year;
    }
    public int getMonth(){
        return this.month;
    }
    public int getPortMaxCapacity(){
        return this.portMaxCapacity;
    }
    private Port getPortFromDB(int idPort) throws IOException, SQLException {
        String sqlCommand = "Select name, idposition, alpha2codecountry, capacity from portwarehouse where idportwarehouse = ? ";
        String name;
        int idPosition;
        String idCountry;
        String continent;
        String country;
        double longitude;
        double latitude;
        this.cf = new ConnectionFactory();
        try (PreparedStatement ps = cf.getDatabaseConnection().getConnection().prepareStatement(sqlCommand)){
            ps.setInt(1,idPort);
            try(ResultSet rs = ps.executeQuery()) {
                rs.next();
                name = rs.getString(1);
                idPosition = rs.getInt(2);
                idCountry = rs.getString(3);
                this.portMaxCapacity= rs.getInt(4);
            }
        }
        sqlCommand = "Select longitude, latitude from position where idposition = ? ";
        try (PreparedStatement ps = cf.getDatabaseConnection().getConnection().prepareStatement(sqlCommand)){
            ps.setInt(1, idPosition);
            try(ResultSet rs = ps.executeQuery()) {
                rs.next();
               longitude = rs.getDouble(1);
               latitude = rs.getDouble(2);
            }
        }
        sqlCommand = "select Country.name, continent.name from COUNTRY  inner join CONTINENT on COUNTRY.ALPHA2CODECONTINENT = CONTINENT.ALPHA2CODECONTINENT where ALPHA2CODECOUNTRY = ? ";
        try (PreparedStatement ps = cf.getDatabaseConnection().getConnection().prepareStatement(sqlCommand)){
            ps.setString(1, idCountry);
            try(ResultSet rs = ps.executeQuery()) {
                rs.next();
                country = rs.getString(1);
                continent = rs.getString(2);
            }
        }
        return new Port(idPort,name,continent,country,longitude,latitude);
    }
    private void fillArrayWithDBdata() throws SQLException {
        YearMonth yearMonthObject = YearMonth.of(year, month);
        int daysInMonth = yearMonthObject.lengthOfMonth();
        Date d;
        Timestamp ts;
        String sqlCommand = "SELECT (Select count(*) from MANIFESTCONTAINER where IDCARGOMANIFEST in (select IDCARGOMANIFESTUNLOAD from ARRIVALDEPARTURE where IDPORTWAREHOUSEARRIVAL = ? and ARRIVALDATE < ?))-(Select count(*) from MANIFESTCONTAINER where IDCARGOMANIFEST in (select IDCARGOMANIFESTLOAD from ARRIVALDEPARTURE where IDPORTWAREHOUSEARRIVAL = ? and ARRIVALDATE < ? )) as Warehouse_Stock from DUAL";
        this.dailyOcupiedSpace = new int[daysInMonth];
        for(int i = 0 ; i < dailyOcupiedSpace.length; i++){
            d = new Date(year,month,i+1);
            ts = new Timestamp(d.getTime());
            try (PreparedStatement ps = cf.getDatabaseConnection().getConnection().prepareStatement(sqlCommand)){
                ps.setInt(1,this.port.getid());
                ps.setTimestamp(2,ts);
                ps.setInt(3,this.port.getid());
                ps.setTimestamp(4,ts);
                try(ResultSet rs = ps.executeQuery()){
                    rs.next();
                    this.dailyOcupiedSpace[i]= rs.getInt(1);
                }
            }
        }
    }
}
