package lapr.project.controller;

import lapr.project.model.Ship;

import java.util.ArrayList;
import java.util.List;

public class listShipsController {
    List<Ship>  allShipsOrdDistNMov;
    ArrayList topNShipswithMostkm;
    private int nShips;
    public listShipsController(){


    }
    public listShipsController(int nships){
        this.nShips = nships;
        topNShipswithMostkm = new ArrayList<Ship>();

    }



}
