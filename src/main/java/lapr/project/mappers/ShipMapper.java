package lapr.project.mappers;

import lapr.project.mappers.dto.ShipDTO;
import lapr.project.model.Ship;

import java.util.ArrayList;
import java.util.List;

public class ShipMapper {

    public ShipDTO toDTO(Ship ship) {
        return new ShipDTO(ship.getMMSI(), ship.getVesselName(), ship.getIMO(), ship.getCallSign(),
                ship.getVesselType(), ship.getLength(), ship.getWidth(), ship.getDraft(),
                new AISMessageMapper().toDTO(ship.getAisMessagesList()),ship.getCargo(), ship.getTransceiverClass());
    }

    public List<ShipDTO> toDTO(List<Ship> shipList){
        List<ShipDTO> listDTO = new ArrayList<>();
        for (Ship s: shipList) {
            listDTO.add(toDTO(s));
        }
        return listDTO;
    }

}
