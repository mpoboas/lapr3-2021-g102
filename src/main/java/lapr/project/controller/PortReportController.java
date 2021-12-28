package lapr.project.controller;

import lapr.project.data.PortMonthlyReport;
import lapr.project.model.PortOcupancyRateReport;

import java.io.IOException;
import java.sql.SQLException;

public class PortReportController {
    /**
     * report class
     */
    private PortOcupancyRateReport report;
    /**
     * data class
     */
    private PortMonthlyReport data;

    /**
     *
     * @param year for the report
     * @param month for the report (1-12)
     * @param idPort for th report
     */
    public PortReportController(int year, int month, int idPort){
        this.data = new PortMonthlyReport(idPort, month, year);
    }

    /**
     * set data class, can be used to tests
     * @param data new data class
     */
    public void setData(PortMonthlyReport data){
        this.data = data;
    }

    /**
     * get data from database and do report
     * @throws SQLException exception
     * @throws IOException exception
     */
    public void getDataAndGenerateReport() throws SQLException, IOException {
        this.data.getDataFromDB();
        doReport();
    }

    /**
     * methood to do report
     */
    public void doReport(){
        this.report = new PortOcupancyRateReport(data.getPortMaxCapacity(),data.getDailyOcupiedSpace(), data.getPort(), data.getMonth(),data.getYear());
    }

    /**
     * method to get the generated report
     * @return report
     */
    public PortOcupancyRateReport getReport(){
        return this.report;
    }
}
