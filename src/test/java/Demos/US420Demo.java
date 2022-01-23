package Demos;

import lapr.project.model.Ship;
import lapr.project.utils.PhysicsUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class US420Demo {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        List<Ship> listOfShips = new ArrayList<>();
        listOfShips.add(new Ship(636018075, "MSC Roma", "IMO9304447","D5OB2",0,336.61,45.6,12.6,107849));
        listOfShips.add(new Ship(422108000, "Sabrina", "IMO8215742","EPBN",0,175,28,4.5,6278));
        listOfShips.add(new Ship(366971340, "Cason J. Callaway", "-","WDH7556",0,228.45,21.34,8,12309));
        listOfShips.add(new Ship(352986146, "Ever Ace", "IMO9893890","3E2382",0,400,62,13.5,235579));
        listOfShips.add(new Ship(353136000, "Ever GIven", "IMO9811000","H3RC",0,399.94,58.8,14.5,219079));
        listOfShips.add(new Ship(228386700, "CMACGM JACQUES SAADE", "IMO9839179","FLZE",0,399.9,61.3,16,236583));
        listOfShips.add(new Ship(351297000, "HMM ALGECIRAS", "IMO9863297","3EDF2",0,399.9,61,16.525,228283));
        listOfShips.add(new Ship(219678000, "Vistula Maersk", "IMO9775737","OWYC2",0,200,35.3,11,34882));
        listOfShips.add(new Ship(220187000, "Axel Maersk", "IMO9260419","OUUY2",0,352.25,42.9,15,93496));

        for(int i = 0 ; i< listOfShips.size(); i++){
            System.out.println((i+1)+ " - "+ listOfShips.get(i));
        }
        System.out.println("Choose a ship");
        int ship = scan.nextInt();
        System.out.println("Numebr of containers");
        int nContainers = scan.nextInt();
        double sinkage = PhysicsUtils.shipSinkageWithCargo(listOfShips.get(ship-1),nContainers);
        double afterDrat = PhysicsUtils.getDraftAfterAddedCargo(listOfShips.get(ship-1),nContainers);
        System.out.println("\n\n\nHow much will the ship sink with added cargo: " + sinkage +" m\nShip original draft: "+ listOfShips.get(ship-1).getDraft()+" m\nShip draft after adding cargo: "+ afterDrat+" m");
    }

}
