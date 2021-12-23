package lapr.project.stores;

import lapr.project.data.DatabaseConnection;
import lapr.project.utils.FreightNetwork;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

class FreightStoreTest {
    DatabaseConnection databaseConnection = mock(DatabaseConnection.class);
    FreightNetwork freightNetwork = mock(FreightNetwork.class);
    FreightStore freightStore = new FreightStore();

    /*@Test
    void buildFreightNetworkFromDatabase() {
        doNothing().when(this.freightNetwork).buildFreightNetwork(anyInt());
        this.freightStore.buildFreightNetworkFromDatabase(this.databaseConnection, 5);
        assertNotNull(this.freightStore.getFreightNetwork());
    }*/
}