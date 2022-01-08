package lapr.project.controller;

import lapr.project.data.AvgOccupancyPerCargoMan;

import java.io.IOException;
import java.sql.SQLException;

public class AvgOccupancyCGinShipController {
    private AvgOccupancyPerCargoMan data;
    public AvgOccupancyCGinShipController(int shipMMSI, String date1, String date2){
        setData(new AvgOccupancyPerCargoMan(shipMMSI,date1,date2));
    }
    public void setShipMMSI(int shipMMSI){
        this.data.setShipMMSI(shipMMSI);
    }
    public void setDate1(String date1){
        this.data.setDate1(date1);
    }
    public void setDate2(String date2){
        this.data.setDate2(date2);
    }
    public int getShipMMSI(){
        return this.data.getShipMMSI();

    }
    public String getDate1(){
        return this.data.getDate1();
    }
    public String getDate2(){
        return this.data.getDate2();
    }
    public void setData(AvgOccupancyPerCargoMan data){
        this.data = data;
    }
    public double getResult() throws SQLException, IOException {
        return this.data.getDataFromDB();
    }
}
