package lapr.project.controller;

import lapr.project.data.PortMonthlyReport;
import lapr.project.model.PortOcupancyRateReport;

import java.io.IOException;
import java.sql.SQLException;

public class PortReportController {
    private PortOcupancyRateReport report;
    private PortMonthlyReport data;
    public PortReportController(int year, int month, int idPort){
        this.data = new PortMonthlyReport(idPort, month, year);
    }
    public void setData(PortMonthlyReport data){
        this.data = data;
    }
    public void getDataAndGenerateReport() throws SQLException, IOException {
        this.data.getDataFromDB();
        doReport();
    }
    public void doReport(){
        this.report = new PortOcupancyRateReport(data.getPortMaxCapacity(),data.getDailyOcupiedSpace(), data.getPort(), data.getMonth(),data.getYear());
    }
    public PortOcupancyRateReport getReport(){
        return this.report;
    }
}
