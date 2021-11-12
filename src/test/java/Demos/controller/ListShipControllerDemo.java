package Demos.controller;

import lapr.project.controller.ListShipsController;
import lapr.project.model.Import;
import lapr.project.stores.ShipStore;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;

public class ListShipControllerDemo {
    @Test
    public void Demo1() throws IOException {
        ShipStore store = new ShipStore();
        Import im = new Import(store);
        im.importShipsData("data/data-shipsports/data-shipsports/data-ships&ports/sships.csv");
        im.addShips();
        store = im.getShipStore();
        ListShipsController cont = new ListShipsController(true,1, store);

        System.out.println(cont);


    }
    @Test
    public void Demo2() throws IOException {
        ShipStore store = new ShipStore();
        Import im = new Import(store);
        im.importShipsData("data/data-shipsports/data-shipsports/data-ships&ports/sships.csv");
        im.addShips();
        store = im.getShipStore();
        ListShipsController cont = new ListShipsController(20, LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0) , LocalDateTime.of(2021, Month.JANUARY, 1, 0, 0),store);

        System.out.println(cont);

    }


}
