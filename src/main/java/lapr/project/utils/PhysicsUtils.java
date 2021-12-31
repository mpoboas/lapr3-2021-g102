package lapr.project.utils;

import java.util.List;

public class PhysicsUtils {
    /**
     * Calculates the thermal resistance of a container.
     *
     * @param totalArea the total area of the container
     * @param materialThermalConductivityList the list of materials´ thermal conductivity
     * @param wallThicknessList the list of walls´ thickness
     * @return the thermal resistance value
     */
    public static double calculateContainerThermalResistance(double totalArea, List<Double> materialThermalConductivityList, List<Double> wallThicknessList) {
        if (materialThermalConductivityList.size() == wallThicknessList.size()) {
            double sum = 0;
            for (int i = 0; i < materialThermalConductivityList.size(); i++) {
                sum += calculateWallThermalResistance(materialThermalConductivityList.get(i), wallThicknessList.get(i), totalArea);
            }
            return sum;
        } else throw new IllegalArgumentException("Lists must have the same number of elements.");
    }

    /**
     * Calculates the thermal resistance of a wall.
     *
     * @param thermalConductivity the thermal conductivity of the material
     * @param wallThickness the wall thickness
     * @param totalArea the total area of the surface
     * @return the thermal resistance value
     */
    public static double calculateWallThermalResistance(double thermalConductivity, double wallThickness, double totalArea) {
        return (wallThickness / thermalConductivity) / totalArea;
    }
}
