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
}