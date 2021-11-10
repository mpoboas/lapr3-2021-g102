package lapr.project.mappers.dto;


import java.util.List;

public class ShipDTO {
    /**
     * the mmsi code of the ship
     */
    private final int MMSI;
    /**
     * the vessel name of the ship
     */
    private final String vesselName;
    /**
     * the imo code of the ship
     */
    private final String IMO;
    /**
     * the call sign of the ship
     */
    private final String callSign;
    /**
     * the vessel type of the ship
     */
    private final int vesselType;
    /**
     * the length of the ship
     */
    private final int length;
    /**
     * the width of the ship
     */
    private final int width;
    /**
     * the draft of the ship
     */
    private final double draft;
    /**
     * all the ais messages of the ship
     */
    private final List<AISMessageDTO> aisMessagesList;


    /**
     *
     * @param mmsi             the mmsi code of the ship
     * @param vesselName       the vessel name of the ship
     * @param imo              the imo code of the ship
     * @param callSign         the call sign of the ship
     * @param vesselType       the vessel type of the ship
     * @param length           the length of the ship
     * @param width            the width of the ship
     * @param draft            the draft of the ship
     */
    public ShipDTO(int mmsi, String vesselName, String imo, String callSign, int vesselType, int length, int width, double draft, List<AISMessageDTO> aismessges) {
        MMSI = mmsi;
        this.vesselName = vesselName;
        IMO = imo;
        this.callSign = callSign;
        this.vesselType = vesselType;
        this.length = length;
        this.width = width;
        this.draft = draft;
        aisMessagesList = aismessges;
    }

    /**
     *
     * @return the ship information
     */
    @Override
    public String toString() {
        return "Ship{" +
                "MMSI=" + MMSI +
                ", vesselName='" + vesselName + '\'' +
                ", IMO='" + IMO + '\'' +
                ", callSign='" + callSign + '\'' +
                ", vesselType=" + vesselType +
                ", length=" + length +
                ", width=" + width +
                ", draft=" + draft +
                '}' + '\n' + aisMessagesList;
    }
}
