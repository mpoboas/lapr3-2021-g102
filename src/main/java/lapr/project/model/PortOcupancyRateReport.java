package lapr.project.model;

import org.apache.commons.lang3.StringUtils;

public class PortOcupancyRateReport {
    private final int portMaxCapacity;
    private final Port p;
    private final int [] dailyOcupiedSpace;
    private final int [] dailyOcupiedRate;
    private int dayMaxCapacity;
    private int dayMinCapacity;
    private final int month;
    private final int year;

    public PortOcupancyRateReport(int portMaxCapacity, int [] dailyOcupiedSpace, Port port, int month, int year){
        this.portMaxCapacity = portMaxCapacity;
        this.dailyOcupiedSpace = dailyOcupiedSpace;
        this.dailyOcupiedRate = new int [dailyOcupiedSpace.length];
        this.p = port;
        this.month =  month;
        this.year = year;
        setDailyRate();
        setMaxAndMin();
    }
    public int getOcupationOnDay(int i){
        return this.dailyOcupiedSpace[i-1];
    }
    public int getRateOnDay(int i){
        return this.dailyOcupiedRate[i-1];
    }
    public int getMaxDay(){
        return this.dayMaxCapacity;
    }
    public int getMinDay(){
        return this.dayMinCapacity;
    }
    public void setDailyRate(){
        for( int i = 0; i< dailyOcupiedRate.length; i++){
            this.dailyOcupiedRate[i] = (int)(((double)this.dailyOcupiedSpace[i] / this.portMaxCapacity) * 100);
        }
    }
    public void setMaxAndMin(){
        int pos= 0;
        int min = this.dailyOcupiedSpace[0];
        for(int i = 0; i< this.dailyOcupiedSpace.length; i++){
            if(this.dailyOcupiedSpace[i]< min){
                pos = i +1;
                min = dailyOcupiedSpace[i];
            }
        }
        this.dayMinCapacity = pos;
        pos = 0;
        int max = this.dailyOcupiedSpace[0];
        for(int i = 0; i< this.dailyOcupiedSpace.length; i++){
            if(this.dailyOcupiedSpace[i]>max){
                pos = i + 1;
                max = this.dailyOcupiedSpace[i];
            }
        }
        this.dayMaxCapacity = pos;
    }
    public String toString(){
        String isOcupied = "█";
        String e = " ";
        StringBuilder s = new StringBuilder("Port Warehouse Report\nPortWarehouse ID:" + this.p.getid() + "\nPortWarehouse Name: " + this.p.getName() + "\nCoordinate: " + this.p.getCoords().toString() + "\n");
        s.append("Report from month: ").append(this.month).append("/").append(this.year).append("\n\n\n\n");
        for(int i = 0 ; i< this.dailyOcupiedSpace.length ; i++ ){
            s.append("Day ").append(i + 1).append(": [").append(StringUtils.repeat(isOcupied,this.dailyOcupiedSpace[i])).append(StringUtils.repeat(e,this.portMaxCapacity - this.dailyOcupiedSpace[i])).append("] Rate ").append(this.dailyOcupiedRate[i]).append("% \n");
        }
        s.append("Day with most capacity: ").append(this.dayMaxCapacity).append("\nRate ocupied in that day: ").append(this.dailyOcupiedRate[dayMaxCapacity - 1]).append("%\n\n");
        s.append("Day with least capacity: ").append(this.dayMinCapacity).append("\nRate ocupied in that day: ").append(this.dailyOcupiedRate[dayMinCapacity - 1]).append("%\n\n");
        return s.toString();
    }
}
