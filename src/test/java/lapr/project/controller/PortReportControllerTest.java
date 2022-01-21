package lapr.project.controller;

import lapr.project.data.PortMonthlyReport;
import lapr.project.model.Port;
import lapr.project.model.PortOcupancyRateReport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class PortReportControllerTest {
    PortReportController controller;
    @Mock
    PortMonthlyReport data;

    @Test
    @ExtendWith(MockitoExtension.class)
    void getDataAndGenerateReport() throws SQLException, IOException {
        int [] ocupiedSpace = {122, 23,340,500,700,900,999,998,1000,620,400,200,120,100,50,25,12,10,0,120,32,12,100,111,200,32,500,122,322,111};
        Port port = new Port(12,"Test Port", "Europe","Portugal",39.399872, -8.224454,12322);
        this.controller = new PortReportController(2021,3,10136);
        this.controller.setData(data);
        Mockito.when(this.data.getMonth()).thenReturn(3);
        Mockito.when(this.data.getYear()).thenReturn(2021);
        Mockito.when(this.data.getPortMaxCapacity()).thenReturn(1000);
        Mockito.when(this.data.getPort()).thenReturn(port);
        Mockito.when(this.data.getDailyOcupiedSpace()).thenReturn(ocupiedSpace);
        this.controller.getDataAndGenerateReport();
        this.controller.doReport();
        PortOcupancyRateReport report = this.controller.getReport();
        assertEquals(19,report.getMinDay());
        assertEquals(9,report.getMaxDay());
        assertEquals(122,report.getOcupationOnDay(1));
        //System.out.println(report);

    }


}