package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PortOcupancyRateReportTest {
    PortOcupancyRateReport report;

    @Test
    void setDailyRate() {
        int [] ocupiedSpace = {122, 23,340,500,700,900,999,998,1000,620,400,200,120,100,50,25,12,10,0,120,32,12,100,111,200,32,500,122,322,111};
        Port port = new Port(12,"Test Port", "Europe","Portugal",39.399872, -8.224454);
        report = new PortOcupancyRateReport(1000,ocupiedSpace,port, 3,2021);
        assertEquals(0,report.getRateOnDay(19));
        assertEquals(100,report.getRateOnDay(9));
        assertEquals(11,report.getRateOnDay(30));



    }

    @Test
    void setMaxAndMin() {
        int [] ocupiedSpace = {122, 23,340,500,700,900,999,998,1000,620,400,200,120,100,50,25,12,10,0,120,32,12,100,111,200,32,500,122,322,111};
        Port port = new Port(12,"Test Port", "Europe","Portugal",39.399872, -8.224454);
        report = new PortOcupancyRateReport(1000,ocupiedSpace,port, 3,2021);
        assertEquals(0,report.getOcupationOnDay(19));
        assertEquals(1000,report.getOcupationOnDay(9));
        assertEquals(111,report.getOcupationOnDay(30));
        assertEquals(19,report.getMinDay());
        assertEquals(9,report.getMaxDay());

    }

    @Test
    void testToString() {
        int [] ocupiedSpace = {122, 23,340,500,700,900,999,998,1000,620,400,200,120,100,50,25,12,10,0,120,32,12,100,111,200,32,500,122,322,111};
        Port port = new Port(12,"Test Port", "Europe","Portugal",39.399872, -8.224454);
        report = new PortOcupancyRateReport(1000,ocupiedSpace,port, 3,2021);
        System.out.println(report);
    }
}