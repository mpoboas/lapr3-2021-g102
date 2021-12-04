package lapr.project.mappers;

import lapr.project.mappers.dto.ShipDTO;
import lapr.project.model.Ship;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShipMapperTest {


    @Test
    void testToDTO() {
        Ship ship1 = new Ship(112,"122","test","Ship", 12, 1200, 600 , 21);
        Ship ship2 = new Ship(120,"321","test2","ShipO", 12, 1200, 600 , 21);
        List<Ship> list = new ArrayList<>();
        list.add(ship1);
        list.add(ship2);
        List<ShipDTO> res = new ShipMapper().toDTO(list);

        assertEquals("[Ship{MMSI=112, vesselName='122', IMO='test', callSign='Ship', vesselType=12, length=1200, width=600, draft=21.0}\n" +
                "[], Ship{MMSI=120, vesselName='321', IMO='test2', callSign='ShipO', vesselType=12, length=1200, width=600, draft=21.0}\n" +
                "[]]",res.toString());

    }
}