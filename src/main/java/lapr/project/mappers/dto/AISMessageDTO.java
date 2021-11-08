package lapr.project.mappers.dto;

import java.time.LocalDateTime;

public class AISMessageDTO {
    /**
     * Date/time of AIS message.
     */
    private final LocalDateTime baseDateTime;
    /**
     * ship latitude
     */
    private final double latitude;
    /**
     * ship longitude
     */
    private final double longitude;
    /**
     *Course over ground
     */
    private final double COG;
    /**
     *Speed over ground
     */
    private final double SOG;
    /**
     * ship heading
     */
    private final int heading;

    /**
     * the cargo of the ship
     */
    private int cargo;
    /**
     * the transceiver class of the ship
     */
    private char transceiverClass;

    /**
     * Constructor of the class ais message DTO
     * @param baseDateTime Date/time of AIS message
     * @param latitude ship latitude
     * @param longitude ship longitude
     * @param COG Course over ground
     * @param SOG Speed over ground
     * @param heading ship heading
     */
    public AISMessageDTO(LocalDateTime baseDateTime,double latitude,double longitude,double COG,double SOG,int heading,int cargo, char transceiverClass){
        this.baseDateTime = baseDateTime;
        this.latitude=latitude;
        this.longitude=longitude;
        this.COG= COG;
        this.SOG=SOG;
        this.heading=heading;
        this.cargo= cargo;
        this.transceiverClass=transceiverClass;
    }

    @Override
    public String toString() {
        return "AISMessage{" +
                "baseDateTime=" + baseDateTime +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", COG=" + COG +
                ", SOG=" + SOG +
                ", heading=" + heading +
                '}' + '\n';
    }
}
