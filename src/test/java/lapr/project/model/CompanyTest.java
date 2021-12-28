package lapr.project.model;

import lapr.project.data.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CompanyTest {

    @Test
    void getShipStoreTest() {
        Company comp = new Company("Company");
        assertNotNull(comp.getShipStore());
    }

    @Test
    void getPortStoreTest(){
        Company comp = new Company("Company");
        assertNotNull(comp.getPortStore());
        assertEquals("Company", comp.getDesignation());
        assertEquals(ShipStoreDB.class,comp.getShipStoreDB().getClass());
        assertEquals(PortStoreDB.class,comp.getPortStoreDB().getClass());
        assertEquals(TripStore.class,comp.getTripStore().getClass());
        assertEquals(SeadistStoreDB.class,comp.getSeadistStoreDB().getClass());
        assertEquals(ContainerStore.class,comp.getContainerStore().getClass());
        assertEquals(CountryStoreDB.class,comp.getCountryStoreDB().getClass());
        assertEquals(BorderStoreDB.class,comp.getBorderStoreDB().getClass());
        assertEquals(CargoManifestStore.class,comp.getCargoManifestStore().getClass());

    }

    @Test
    void getFreightNetworkTest(){
        Company comp = new Company("Company");
        assertNotNull(comp.getFreightNetwork());
    }
}