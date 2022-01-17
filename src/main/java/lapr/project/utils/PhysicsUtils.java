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
        return ((temp1 - (temp2))/ totalResistance);
    }

    public static double calculateEnergyNecessary(double initialTemperature, double desiredTemperature, double totalResistance, int hour, int min, int sec){
        int totalTime = hour*3600 + min*60 + sec;
        return calculateEnergyTransferredAsHeat(initialTemperature,desiredTemperature,totalResistance) * totalTime;
    }
    public static double get7DegResistance(){
        List<Double> materialThermalConductivityList = new ArrayList<>();
        materialThermalConductivityList.add(PhysicsUtils.MATERIAL_STEEL.getSecond());
        materialThermalConductivityList.add(PhysicsUtils.MATERIAL_CORK.getSecond());
        materialThermalConductivityList.add(PhysicsUtils.MATERIAL_WOOD.getSecond());
        List<Double> wallThicknessList = new ArrayList<>();
        wallThicknessList.add(0.04);
        wallThicknessList.add(0.10);
        wallThicknessList.add(0.06);
        return calculateContainerThermalResistance(PhysicsUtils.CONTAINER_40_AREA,materialThermalConductivityList,wallThicknessList);
    }
    public static double get5DegResistance(){
        List<Double> materialThermalConductivityList = new ArrayList<>();
        materialThermalConductivityList.add(PhysicsUtils.MATERIAL_STEEL.getSecond());
        materialThermalConductivityList.add(PhysicsUtils.MATERIAL_CORK.getSecond());
        materialThermalConductivityList.add(PhysicsUtils.MATERIAL_STONE_WOOL.getSecond());
        List<Double> wallThicknessList = new ArrayList<>();
        wallThicknessList.add(0.04);
        wallThicknessList.add(0.10);
        wallThicknessList.add(0.06);
        return calculateContainerThermalResistance(PhysicsUtils.CONTAINER_40_AREA,materialThermalConductivityList,wallThicknessList);
    }
    public static List<Pair<Double,Double>> energyToBeSupliedTocontainers(List<Pair<Integer, Integer>> tripSections, int n5DegContainers, int n7DegContainers){
        double containers5DegsRt = get5DegResistance();
        double containers7DegsRt = get7DegResistance();
        List<Pair<Double,Double>> listOfEnergy = new ArrayList<>();
        for (Pair<Integer, Integer> tripSection : tripSections) {
            Pair<Double, Double> energyToBeSuplied = totalEnergyToBeSupliedToContainers(tripSection.getFirst(), tripSection.getSecond(), containers5DegsRt, containers7DegsRt);
            listOfEnergy.add(new Pair<>(energyToBeSuplied.getFirst() * n5DegContainers, energyToBeSuplied.getSecond() * n7DegContainers));
        }
        return listOfEnergy;
    }
    public static Pair<Double,Double> totalEnergyToBeSupliedToContainers(int time, int exTemp, double thermalResisttance5, double thermalResisttance7){
        double container5Degrees = energyToSupplyToContainer(exTemp,-5,thermalResisttance5,time);
        double container7Degrees = energyToSupplyToContainer(exTemp,7,thermalResisttance7,time);
        return new Pair<>(container5Degrees,container7Degrees);
    }

    public static double energyToSupplyToContainer(int exTemp, int intTemp, double thermalResistance, int time){
        return calculateEnergyTransferredAsHeat(exTemp,intTemp,thermalResistance) * time;
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
