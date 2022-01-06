package lapr.project.data;

import lapr.project.model.Port;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.YearMonth;

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

    /**
     * Method to import data from port from database
     * @param idPort required port id
     * @return port required from databse
     * @throws IOException exception
     * @throws SQLException exception
     */
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
        return new Port(idPort,name,continent,country,longitude,latitude,this.portMaxCapacity);
    }

    /**
     * Method to get the ocupancy in the warehouse/port from databse
     * @throws SQLException exception
     */
    private void fillArrayWithDBdata() throws SQLException {
        YearMonth yearMonthObject = YearMonth.of(year, month);
        int daysInMonth = yearMonthObject.lengthOfMonth();
        String s;
        String sqlCommand = "SELECT getOcupancyInDay(?,?) as oc from dual";
        this.dailyOcupiedSpace = new int[daysInMonth];
        for(int i = 0 ; i < dailyOcupiedSpace.length; i++){
            s = year+"-"+month+"-"+(i+1);
            try (PreparedStatement ps = cf.getDatabaseConnection().getConnection().prepareStatement(sqlCommand)){
                ps.setInt(1,this.port.getid());
                ps.setString(2,s);
                try(ResultSet rs = ps.executeQuery()){
                    rs.next();
                    this.dailyOcupiedSpace[i]= rs.getInt(1);
                }
            }
        }
    }
}
