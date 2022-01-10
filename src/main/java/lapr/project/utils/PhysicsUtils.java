package lapr.project.utils;

import java.util.ArrayList;
import java.util.List;

public class PhysicsUtils {

    public static final Double CONTAINER_40_AREA = 125.33428;

    public static final Pair<String, Double> MATERIAL_STEEL = new Pair<>("Steel", 46.0);
    public static final Pair<String, Double> MATERIAL_IRON = new Pair<>("Iron", 80.4);
    public static final Pair<String, Double> MATERIAL_CORK = new Pair<>("Cork", 0.039); // cortiça
    public static final Pair<String, Double> MATERIAL_STONE_WOOL = new Pair<>("Stone Wool", 0.035);
    public static final Pair<String, Double> MATERIAL_POLYSTYRENE = new Pair<>("Polystyrene", 0.04);
    public static final Pair<String, Double> MATERIAL_WOOD = new Pair<>("Wood", 0.13);
    public static final Pair<String, Double> MATERIAL_CONCRETE = new Pair<>("Concrete", 0.75);

    /**
     * Calculates the thermal resistance of a container.
     *
     * @param totalArea                       the total area of the container
     * @param materialThermalConductivityList the list of materials´ thermal conductivity
     * @param wallThicknessList               the list of walls´ thickness
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
     * @param wallThickness       the wall thickness
     * @param totalArea           the total area of the surface
     * @return the thermal resistance value
     */
    public static double calculateWallThermalResistance(double thermalConductivity, double wallThickness, double totalArea) {
        return (wallThickness / thermalConductivity) / totalArea;
    }

    public static double calculateEnergyTransferredAsHeat(double temp1, double temp2, double totalResistance){
        return ((temp1 - temp2)/ totalResistance);
    }

    public static double calculateEnergyNecessary(double initialTemperature, double desiredTemperature, double totalResistance, int hour, int min, int sec){
        int totalTime = hour*3600 + min*60 + sec;
        return calculateEnergyTransferredAsHeat(initialTemperature,desiredTemperature,totalResistance) * totalTime;
    }

    /**
     *
     * @return the list of all the materials of the walls
     */
    public static List<Pair<String,Double>> listOfMaterials(){
        List<Pair<String,Double>> list = new ArrayList<>();
        list.add(MATERIAL_STEEL);
        list.add(MATERIAL_IRON);
        list.add(MATERIAL_CORK);
        list.add(MATERIAL_STONE_WOOL);
        list.add(MATERIAL_POLYSTYRENE);
        list.add(MATERIAL_WOOD);
        list.add(MATERIAL_CONCRETE);
        return list;
    }
}
