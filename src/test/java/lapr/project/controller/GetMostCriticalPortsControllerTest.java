package lapr.project.controller;

import lapr.project.model.Company;
import lapr.project.model.Port;
import lapr.project.stores.FreightNetwork;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetMostCriticalPortsControllerTest {
    Company company = App.getInstance().getCompany();
    FreightNetwork freightNetwork = mock(FreightNetwork.class);
    GetMostCriticalPortsController getMostCriticalPortsController;

    @Test
    void getMostCriticalPorts() {
        this.getMostCriticalPortsController = new GetMostCriticalPortsController();
        this.company.setFreightNetwork(this.freightNetwork);
        Port expectedPort = new Port(123, "Pass", "Pass", "Pass", 20, 20, 100);
        List<Port> expectedList = new ArrayList<>();
        expectedList.add(expectedPort);
        when(this.freightNetwork.getMostCriticalPorts(1)).thenReturn(expectedList);
        assertEquals(expectedPort, expectedList.get(0));
    }
}