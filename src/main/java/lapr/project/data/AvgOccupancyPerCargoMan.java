package lapr.project.data;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AvgOccupancyPerCargoMan {
    private int shipMMSI;
    private String date1;
    private String date2;
    public AvgOccupancyPerCargoMan(int shipMMSI,String date1, String date2){
        this.shipMMSI = shipMMSI;
        this.date1 = date1;
        this.date2 = date2;
    }
    public int getShipMMSI(){
        return this.shipMMSI;
    }
    public String getDate1(){
        return this.date1;
    }
    public String getDate2(){
        return this.date2;
    }
    public void setShipMMSI(int shipMMSI){
        this.shipMMSI = shipMMSI;
    }
    public void setDate1(String date1){
        this.date1 = date1;
    }
    public void setDate2(String date2){
        this.date2 = date2;
    }
    public double getDataFromDB() throws IOException, SQLException {
        return accessDB();
    }
    private double accessDB() throws IOException, SQLException {
        ConnectionFactory cf = new ConnectionFactory();
        double avg;
        String sqlCommand = "SELECT getOccupancyRatePerCMInPeriod( ? , ? , ? ) from dual";
        try (PreparedStatement ps = cf.getDatabaseConnection().getConnection().prepareStatement(sqlCommand)){
            ps.setInt(1,this.shipMMSI);
            ps.setString(2,this.date1);
            ps.setString(3,this.date2);
            try(ResultSet rs = ps.executeQuery()){
                rs.next();
                avg = rs.getDouble(1);
            }
        }
        return  avg;
    }
}
