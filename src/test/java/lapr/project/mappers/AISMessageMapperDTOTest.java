package lapr.project.mappers;

import lapr.project.mappers.dto.AISMessageDTO;
import lapr.project.model.AISMessage;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AISMessageMapperDTOTest {

    @Test
    void toDTO() {
        //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy H:mm");
        //LocalDateTime baseDateTime = LocalDateTime.parse("26/05/2018 2:00", dtf);
        AISMessage ais1 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 26, 2, 0), 13, 14, 21, 13, 4, 3000, 'B');

        AISMessageDTO aisDTO1 = new AISMessageMapper().toDTO(ais1);

        assertEquals("AISMessage{" +
                "baseDateTime=2018-06-26T02:00" +
                ", latitude=13.0, longitude=14.0, COG=21.0, SOG=13.0, heading=4, cargo=3000" +
                ", transceiver class=B" +
                '}' + '\n',aisDTO1.toString());

    }

    @Test
    void testToDTO() {
        AISMessage ais1 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 26, 2, 0), 13, 14, 21, 13, 4, 3000, 'B');
        AISMessage ais2 = new AISMessage(LocalDateTime.of(2018, Month.JUNE, 27, 1, 0), 14, 15, 21, 13, 4, 3000, 'B');
        List<AISMessage> list = new ArrayList<>();
        list.add(ais1);
        list.add(ais2);
        List<AISMessageDTO> res = new AISMessageMapper().toDTO(list);

        assertEquals("[AISMessage{" +
                "baseDateTime=2018-06-26T02:00" +
                ", latitude=13.0, longitude=14.0, COG=21.0, SOG=13.0, heading=4, cargo=3000" +
                ", transceiver class=B" +
                '}' + '\n' + ", AISMessage{" +
                "baseDateTime=2018-06-27T01:00" +
                ", latitude=14.0, longitude=15.0, COG=21.0, SOG=13.0, heading=4, cargo=3000" +
                ", transceiver class=B" +
                '}' + '\n' +"]" , res.toString());


    }
}