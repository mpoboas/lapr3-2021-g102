package lapr.project.mappers;

import lapr.project.mappers.dto.AISMessageDTO;
import lapr.project.model.AISMessage;

import java.util.ArrayList;
import java.util.List;

public class AISMessageMapper {

    public AISMessageDTO toDTO(AISMessage msg){
        return new AISMessageDTO(msg.getBaseDateTime(), msg.getCoordinates().getLatitude(), msg.getCoordinates().getLongitude(), msg.getCOG(), msg.getSOG(), msg.getHeading(),msg.getCargo(),msg.getTransceiverClass());
    }

    public List<AISMessageDTO> toDTO(List<AISMessage> msgList){
        List<AISMessageDTO> list = new ArrayList<>();
        for (AISMessage msg: msgList) {
            list.add(toDTO(msg));
        }
        return list;
    }
}
