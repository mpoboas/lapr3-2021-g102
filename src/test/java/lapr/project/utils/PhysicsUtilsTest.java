package lapr.project.utils;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PhysicsUtilsTest {
    @Test
    void calculateWallThermalResistance() {
        double result = PhysicsUtils.calculateWallThermalResistance(46, 0.04, 125.33428);
        assertEquals(6.937967947725908E-6, result);
    }

    @Test
    void calculateContainerThermalResistance() {
        List<Double> materialThermalConductivityList = new ArrayList<>();
        materialThermalConductivityList.add(46.0);
        materialThermalConductivityList.add(0.039);
        materialThermalConductivityList.add(0.13);

        List<Double> wallThicknessList = new ArrayList<>();
        wallThicknessList.add(0.04);
        wallThicknessList.add(0.10);
        wallThicknessList.add(0.06);
        double result = PhysicsUtils.calculateContainerThermalResistance(125.33428, materialThermalConductivityList, wallThicknessList);
        assertEquals(0.024147508493753002, result);
    }

    @Test
    void calculateEnergyTransferredAsHeat(){
        double result = PhysicsUtils.calculateEnergyTransferredAsHeat(20,7,1);
        assertEquals(13.0,result);
        double result2 = PhysicsUtils.calculateEnergyTransferredAsHeat(20,7,0.024);
        assertEquals(541.6666666666666,result2);
    }

    @Test
    void calculateEnergyNecessary(){
        double result = PhysicsUtils.calculateEnergyNecessary(20,7,1,2,30,0);
        assertEquals(117000,result);
        double result2 = PhysicsUtils.calculateEnergyNecessary(20,7,0.024,2,30,0);
        assertEquals(4875000,result2);
    }
    @Test
    void calculateEnergyNecessaryInTrip(){
        List<Pair<Integer,Integer>> tripSections = new ArrayList<>();
        tripSections.add(new Pair<>(60,30));
        tripSections.add(new Pair<>(400,20));
        tripSections.add(new Pair<>(200,10));
        tripSections.add(new Pair<>(1000,12));
        tripSections.add(new Pair<>(3000,10));
        tripSections.add(new Pair<>(740,15));
        assertEquals(0.034,PhysicsUtils.get5DegResistance(),0.01);
        assertEquals(0.024,PhysicsUtils.get7DegResistance(),0.01);

        List<Pair<Double,Double>> result = PhysicsUtils.energyToBeSupliedTocontainers(tripSections,12,14);
        assertEquals(738077.48, result.get(0).getFirst(),0.1);
        assertEquals(800082.5, result.get(0).getSecond(),0.1);
        assertEquals(3514654.67, result.get(1).getFirst(),0.1);
        assertEquals(3014803.7, result.get(1).getSecond(),0.1);
        assertEquals(1054396.403, result.get(2).getFirst(),0.1);
        assertEquals(347861.9, result.get(2).getSecond(),0.1);
        assertEquals(5974912.95, result.get(3).getFirst(),0.1);
        assertEquals(2898849.79, result.get(3).getSecond(),0.1);
        assertEquals(15815946.05, result.get(4).getFirst(),0.1);
        assertEquals(5217929.628, result.get(4).getSecond(),0.1);
        assertEquals(5201688.9, result.get(5).getFirst(),0.1);
        assertEquals(3432238.1, result.get(5).getSecond(),0.1);



    }
}