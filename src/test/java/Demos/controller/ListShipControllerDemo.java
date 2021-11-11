package Demos.controller;

import lapr.project.controller.ListShipsController;
import lapr.project.model.Import;
import lapr.project.stores.ShipStore;
import org.junit.Test;

import java.io.IOException;

public class ListShipControllerDemo {
    @Test
    public void Demo1() throws IOException {
        ShipStore store = new ShipStore();
        Import im = new Import(store);
        im.importShipsData("ships_for_Tests.csv");
        ShipStore s = im.getShipStore();
        ListShipsController cont = new ListShipsController(false,1, s);
        System.out.println(cont);


    }
    @Test
    public void Demo2(){

    }


}
