package lapr.project.utils;

import lapr.project.model.CargoManifest;
import lapr.project.model.Ship;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class PhysicsUtils {

    public static final Double CONTAINER_40_AREA = 125.33428;
    public static final Double FRONTAL_FACE_AREA = 5.6165;
    public static final Double LATERAL_FACE_AREA = 28.76604;
    public static final Double SUPERIOR_FACE_AREA = 28.2846;
    public static final Pair<String, Integer> CONTAINERS_5DEG = new Pair<>("refrigerated5", -5);
    public static final Pair<String, Integer> CONTAINERS_7DEG = new Pair<>("refrigerated7", 7);
    public static final Double resTerm5Deg = 4.48050757426716;
    public static final Double resTerm7Deg = 3.02651059085842;


    public static final Pair<String, Double> MATERIAL_STEEL = new Pair<>("Steel", 46.0);
    public static final Pair<String, Double> MATERIAL_IRON = new Pair<>("Iron", 80.4);
    public static final Pair<String, Double> MATERIAL_CORK = new Pair<>("Cork", 0.039); // cortiça
    public static final Pair<String, Double> MATERIAL_STONE_WOOL = new Pair<>("Stone Wool", 0.034);
    public static final Pair<String, Double> MATERIAL_POLYSTYRENE = new Pair<>("Polystyrene", 0.04);
    public static final Pair<String, Double> MATERIAL_WOOD = new Pair<>("Wood", 0.13);
    public static final Pair<String, Double> MATERIAL_CONCRETE = new Pair<>("Concrete", 0.75);


    public static final double SECTION_1_MASS_PERCENT = 0.8;
    public static final double SECTION_2_MASS_PERCENT = 0.15;
    public static final double SECTION_3_MASS_PERCENT = 0.05;

    public static final double SECTION_1_LENGTH_PERCENT = 0.8;
    public static final double SECTION_2_LENGTH_PERCENT = 0.2;

    public static final double SECTION_1_2_HEIGHT_PERCENT = 0.6;
    public static final double SECTION_3_HEIGHT_PERCENT = 0.4;

    public static final double SECTION_3_LENGTH_PERCENT_DISTANCE_STERN = 0.15;
    public static final double SECTION_3_LENGTH_PERCENT_DISTANCE_MIDDLE = 0.40;
    public static final double SECTION_3_LENGTH_PERCENT_DISTANCE_BOW = 0.65;


    private PhysicsUtils() {

    }

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

    public static double calculateEnergyTransferredAsHeat(double temp1, double temp2, double totalResistance) {
        return ((temp1 - (temp2)) / totalResistance);
    }

    public static double calculateEnergyNecessary(double initialTemperature, double desiredTemperature, double totalResistance, int hour, int min, int sec) {
        int totalTime = hour * 3600 + min * 60 + sec;
        return calculateEnergyTransferredAsHeat(initialTemperature, desiredTemperature, totalResistance) * totalTime;
    }

    public static double get7DegResistance() {
        List<Double> materialThermalConductivityList = new ArrayList<>();
        materialThermalConductivityList.add(PhysicsUtils.MATERIAL_STEEL.getSecond());
        materialThermalConductivityList.add(PhysicsUtils.MATERIAL_CORK.getSecond());
        materialThermalConductivityList.add(PhysicsUtils.MATERIAL_WOOD.getSecond());
        List<Double> wallThicknessList = new ArrayList<>();
        wallThicknessList.add(0.04);
        wallThicknessList.add(0.10);
        wallThicknessList.add(0.06);
        return calculateContainerThermalResistance(PhysicsUtils.CONTAINER_40_AREA, materialThermalConductivityList, wallThicknessList);
    }

    public static double get5DegResistance() {
        List<Double> materialThermalConductivityList = new ArrayList<>();
        materialThermalConductivityList.add(PhysicsUtils.MATERIAL_STEEL.getSecond());
        materialThermalConductivityList.add(PhysicsUtils.MATERIAL_CORK.getSecond());
        materialThermalConductivityList.add(PhysicsUtils.MATERIAL_STONE_WOOL.getSecond());
        List<Double> wallThicknessList = new ArrayList<>();
        wallThicknessList.add(0.04);
        wallThicknessList.add(0.06);
        wallThicknessList.add(0.10);
        return calculateContainerThermalResistance(PhysicsUtils.CONTAINER_40_AREA, materialThermalConductivityList, wallThicknessList);
    }

    public static List<Pair<Double, Double>> energyToBeSupliedTocontainers(List<Pair<Integer, Integer>> tripSections, int n5DegContainers, int n7DegContainers) {
        double containers5DegsRt = get5DegResistance();
        double containers7DegsRt = get7DegResistance();
        List<Pair<Double, Double>> listOfEnergy = new ArrayList<>();
        for (Pair<Integer, Integer> tripSection : tripSections) {
            Pair<Double, Double> energyToBeSuplied = totalEnergyToBeSupliedToContainers(tripSection.getFirst(), tripSection.getSecond(), containers5DegsRt, containers7DegsRt);
            listOfEnergy.add(new Pair<>(energyToBeSuplied.getFirst() * n5DegContainers, energyToBeSuplied.getSecond() * n7DegContainers));
        }
        return listOfEnergy;
    }

    public static Pair<Double, Double> totalEnergyToBeSupliedToContainers(int time, int exTemp, double thermalResisttance5, double thermalResisttance7) {
        double container5Degrees = energyToSupplyToContainer(exTemp, -5, thermalResisttance5, time);
        double container7Degrees = energyToSupplyToContainer(exTemp, 7, thermalResisttance7, time);
        return new Pair<>(container5Degrees, container7Degrees);
    }

    public static double energyToSupplyToContainer(int exTemp, int intTemp, double thermalResistance, int time) {
        return calculateEnergyTransferredAsHeat(exTemp, intTemp, thermalResistance) * time;
    }


    public static List<Pair<Double, Double>> energyToBeSupliedTocontainersDepOnPos(CargoManifest cg, List<Pair<Integer, Double>> tripSections) {
        List<Pair<Double, Double>> listOfEnergyToBeSupplied = new ArrayList<>();
        for (int i = 0; i < tripSections.size(); i++) {
            double energyFor7degContainers = 0;
            double energyFor5degContainers = 0;
            for (int j = 0; j < cg.getContainerList().size(); j++) {
                double areaExposed = determineAreaExposed(cg, cg.getContainerList().get(j).getSecond());
                if (cg.getContainerList().get(j).getFirst().getType().equalsIgnoreCase(PhysicsUtils.CONTAINERS_5DEG.getFirst())) {
                    energyFor5degContainers = energyFor5degContainers + (energyToBeSuppliedToContainer(areaExposed, -5, tripSections.get(i).getSecond()));
                }
                if (cg.getContainerList().get(j).getFirst().getType().equalsIgnoreCase(PhysicsUtils.CONTAINERS_7DEG.getFirst())) {
                    energyFor7degContainers = energyFor7degContainers + (energyToBeSuppliedToContainer(areaExposed, 7, tripSections.get(i).getSecond()));
                }
            }
            listOfEnergyToBeSupplied.add(new Pair<>(energyFor5degContainers * tripSections.get(i).getFirst(), energyFor7degContainers * tripSections.get(i).getFirst()));
        }
        return listOfEnergyToBeSupplied;
    }

    public static double energyToBeSuppliedToContainer(double areaExposed, int intTemp, double exTemp) {
        double energyToSupplyToContainer;
        double resistance = 1;
        if (intTemp == -5) {
            resistance = resTerm5Deg / areaExposed;
        }
        if (intTemp == 7) {
            resistance = resTerm7Deg / areaExposed;
        }
        double energyToSupplyToExposedArea = ((exTemp - intTemp)) / resistance;
        if (intTemp == -5) {
            resistance = resTerm5Deg / (CONTAINER_40_AREA - areaExposed);
        }
        if (intTemp == 7) {
            resistance = resTerm7Deg / (CONTAINER_40_AREA - areaExposed);
        }

        double energyToSupplyToNonExposedArea = ((exTemp - intTemp)) / resistance;
        energyToSupplyToContainer = energyToSupplyToExposedArea + energyToSupplyToNonExposedArea;
        return energyToSupplyToContainer;
    }

    public static double determineAreaExposed(CargoManifest cg, CargoManifest.ContainerPosition currentPos) {
        double areaExposed = 0;
        if (cg.findIfExists(new CargoManifest.ContainerPosition(currentPos.x, currentPos.y, currentPos.z + 1))) {
            areaExposed = areaExposed + PhysicsUtils.SUPERIOR_FACE_AREA;
        }
        if (cg.findIfExists(new CargoManifest.ContainerPosition(currentPos.x + 1, currentPos.y, currentPos.z))) {
            areaExposed = areaExposed + PhysicsUtils.LATERAL_FACE_AREA;
        }
        if (cg.findIfExists(new CargoManifest.ContainerPosition(currentPos.x - 1, currentPos.y, currentPos.z))) {
            areaExposed = areaExposed + PhysicsUtils.LATERAL_FACE_AREA;
        }
        if (cg.findIfExists(new CargoManifest.ContainerPosition(currentPos.x, currentPos.y + 1, currentPos.z))) {
            areaExposed = areaExposed + PhysicsUtils.FRONTAL_FACE_AREA;
        }
        if (cg.findIfExists(new CargoManifest.ContainerPosition(currentPos.x, currentPos.y - 1, currentPos.z))) {
            areaExposed = areaExposed + PhysicsUtils.FRONTAL_FACE_AREA;
        }
        return areaExposed;
    }

    /**
     * Calculates the number of needed auxiliary equipment to supply energy to all the containers in a trip.
     *
     * @param tripSections          the trip sections, where the temperature is varies
     * @param cont5EnergyPerSection the needed energy to supply for the -5 degrees containers per section
     * @param cont7EnergyPerSection the needed energy to supply for the 7 degrees containers per section
     * @return the number of needed auxiliary equipment
     */
    public static int getNumberOfAuxEquipment(List<Pair<Integer, Double>> tripSections, List<Double> cont5EnergyPerSection, List<Double> cont7EnergyPerSection, double equipmentMaxPower) {
        double biggest = (cont5EnergyPerSection.get(0) + cont7EnergyPerSection.get(0)) / tripSections.get(0).getFirst();
        for (int i = 1; i < tripSections.size(); i++) {
            double result = (cont5EnergyPerSection.get(i) + cont7EnergyPerSection.get(i)) / tripSections.get(i).getFirst();
            if (result > biggest) biggest = result;
        }

        return (int) Math.ceil(biggest / equipmentMaxPower);
    }

    /**
     * @return the list of all the materials of the walls
     */
    public static List<Pair<String, Double>> listOfMaterials() {
        List<Pair<String, Double>> list = new ArrayList<>();
        list.add(MATERIAL_STEEL);
        list.add(MATERIAL_IRON);
        list.add(MATERIAL_CORK);
        list.add(MATERIAL_STONE_WOOL);
        list.add(MATERIAL_POLYSTYRENE);
        list.add(MATERIAL_WOOD);
        list.add(MATERIAL_CONCRETE);
        return list;
    }

    /**
     * This method gets the coordinates of the geometric center of a parallelepiped
     *
     * @param length length of the parallelepiped
     * @param width  width of the parallelepiped
     * @param height height of the parallelepiped
     * @param center the y-axis is in the middle of the object
     * @return the coordinates
     */
    public static Pair<Point2D.Double, Double> getGeometricCenterParallelepiped(double length, double width, double height, boolean center) {
        double x;
        if (center)
            x = 0;
        else
            x = width / 2;
        double y = length / 2;
        double z = height / 2;
        return new Pair<>(new Point2D.Double(x, y), z);
    }

    /**
     * This method gets the coordinates of the geometric center of a pyramid
     *
     * @param base       length of the base of the pyramid
     * @param baseHeight height of the base triangle
     * @param height     height of the pyramid
     * @param center     the y-axis is in the middle of the object
     * @return
     */
    public static Pair<Point2D.Double, Double> getGeometricCenterPyramid(double base, double baseHeight, double height, boolean center) {
        double x;
        if (center)
            x = 0;
        else
            x = base / 2;
        double y = ((baseHeight) / 3);
        double z = (height * 2) / 3;
        return new Pair<>(new Point2D.Double(x, y), z);
    }

    /**
     * Defines the center of mass of a system of particles
     *
     * @param coordinatesList List of coordinates
     * @param massList        List of mass of corresponding coordinates
     * @param totalMass       the total mass of the system
     * @return the coordinates of the center of mass
     */
    public static Pair<Point2D.Double, Double> getCenterOfMassPoints(List<Pair<Point2D.Double, Double>> coordinatesList, List<Double> massList, Double totalMass) {
        if (coordinatesList.size() != massList.size())
            throw new IllegalStateException("The list of coordinates and mass must have the same size");

        double x = 0;
        double y = 0;
        double z = 0;
        for (int i = 0; i < coordinatesList.size(); i++) {
            x = x + coordinatesList.get(i).getFirst().x * massList.get(i);
            y = y + coordinatesList.get(i).getFirst().y * massList.get(i);
            z = z + coordinatesList.get(i).getSecond() * massList.get(i);
        }
        x = x / totalMass;
        y = y / totalMass;
        z = z / totalMass;
        return new Pair<>(new Point2D.Double(x, y), z);
    }

    /**
     * This method calculates the center of mass of an unleaded ship
     *
     * @param length    length of the ship
     * @param width     width of the ship
     * @param height    height of the ship
     * @param massTotal total mass of the ship
     * @return coordinates of the center of mass
     */
    public static Pair<Point2D.Double, Double> calculateTheUnleadedCenterOfMass(double length, double width, double height, double massTotal, String bridgePosition) {
        List<Pair<Point2D.Double, Double>> particles = new ArrayList<>();
        List<Double> particlesMass = new ArrayList<>();
        //Section 1
        particles.add(getGeometricCenterParallelepiped(length * SECTION_1_LENGTH_PERCENT, width, (height * SECTION_1_2_HEIGHT_PERCENT), true));
        particlesMass.add(massTotal * SECTION_1_MASS_PERCENT);
        //Section 2
        Pair<Point2D.Double, Double> pyramid = getGeometricCenterPyramid(width, length * SECTION_2_LENGTH_PERCENT, height * SECTION_1_2_HEIGHT_PERCENT, true);
        pyramid.getFirst().y = length * SECTION_1_LENGTH_PERCENT + pyramid.getFirst().y;
        particles.add(pyramid);
        particlesMass.add(massTotal * SECTION_2_MASS_PERCENT);
        //Section 3
        Pair<Point2D.Double, Double> controlBridge = getGeometricCenterParallelepiped(width * 0.80, width * 0.20, height * SECTION_3_HEIGHT_PERCENT, true);
        switch (bridgePosition) {
            case "MIDDLE":
                controlBridge.getFirst().y = /*controlBridge.getFirst().y +*/ (length * SECTION_1_LENGTH_PERCENT) * 0.50;
                //controlBridge.getFirst().y = controlBridge.getFirst().y + length * SECTION_3_LENGTH_PERCENT_DISTANCE_MIDDLE;
                break;
            case "BOW":
                controlBridge.getFirst().y = /*controlBridge.getFirst().y +*/ length * SECTION_3_LENGTH_PERCENT_DISTANCE_BOW;
                break;
            case "STERN":
            default:
                controlBridge.getFirst().y = /*controlBridge.getFirst().y +*/ length * SECTION_3_LENGTH_PERCENT_DISTANCE_STERN;
                break;
        }
        double z = controlBridge.getSecond() + height * SECTION_1_2_HEIGHT_PERCENT;
        particles.add(new Pair<>(controlBridge.getFirst(), z));
        particlesMass.add(massTotal * SECTION_3_MASS_PERCENT);

        return getCenterOfMassPoints(particles, particlesMass, massTotal);
    }

    /**
     * Calculation of the center of mass of a vessel having all the specific parameters
     *
     * @param section1Length   length of the section 1
     * @param section1Width    width of the section 1
     * @param section1Height   height of the section 1
     * @param section1Mass     mass of the section 1
     * @param section2Length   length of the section 2
     * @param section2Width    width of the section 2
     * @param section2Height   height of the section 2
     * @param section2Mass     mass of the section 2
     * @param section3Length   length of the section 3
     * @param section3Width    width of the section 3
     * @param section3Height   height of the section 3
     * @param section3Mass     mass of the section 3
     * @param totalMass        total mass of the vessel
     * @param distanceToBridge distance from the stern to the bridge
     * @return the coordinates of the center of mass
     */
    public static Pair<Point2D.Double, Double> calculateTheUnleadedCenterOfMassWithAllParameters(double section1Length, double section1Width, double section1Height, double section1Mass,
                                                                                                 double section2Length, double section2Width, double section2Height, double section2Mass,
                                                                                                 double section3Length, double section3Width, double section3Height, double section3Mass,
                                                                                                 double totalMass, double distanceToBridge) {
        List<Pair<Point2D.Double, Double>> points = new ArrayList<>();
        List<Double> mass = new ArrayList<>();

        Pair<Point2D.Double, Double> section1Parallelogram = getGeometricCenterParallelepiped(section1Length, section1Width, section1Height, true);
        Pair<Point2D.Double, Double> section2Pyramid = getGeometricCenterPyramid(section2Width, section2Length, section2Height, true);
        section2Pyramid.getFirst().y = section2Pyramid.getFirst().y + section1Length;

        Pair<Point2D.Double, Double> section3aux = getGeometricCenterParallelepiped(section3Length, section3Width, section3Height, true);
        Pair<Point2D.Double, Double> section3ControlBridge = new Pair<>(new Point2D.Double(section3aux.getFirst().x, section3aux.getFirst().y + distanceToBridge), section3aux.getSecond() + section1Height);

        points.add(section1Parallelogram);
        points.add(section2Pyramid);
        points.add(section3ControlBridge);
        mass.add(section1Mass);
        mass.add(section2Mass);
        mass.add(section3Mass);
        return getCenterOfMassPoints(points, mass, totalMass);
    }

    /**
     * @param length           length of the ship
     * @param width            width of the ship
     * @param height           height of the ship
     * @param massTotal        total mass of the ship
     * @param bridgeLength     length of the control bridge
     * @param bridgeWidth      width of the control bridge
     * @param bridgeHeight     height of the control bridge
     * @param distanceToBridge distance from the stern to the bridge
     * @return the coordinates of the center of mass
     */
    public static Pair<Point2D.Double, Double> calculateTheUnleadedCenterOfMassWithBridgeINFO(double length, double width, double height, double massTotal,
                                                                                              double bridgeLength, double bridgeWidth, double bridgeHeight, double distanceToBridge) {

        return calculateTheUnleadedCenterOfMassWithAllParameters(length * SECTION_1_LENGTH_PERCENT, width, height, massTotal * SECTION_1_MASS_PERCENT,
                length * SECTION_2_LENGTH_PERCENT, width, height, massTotal * SECTION_2_MASS_PERCENT,
                bridgeLength, bridgeWidth, bridgeHeight, massTotal * SECTION_3_MASS_PERCENT, massTotal, distanceToBridge);
    }

    public static double getCalculatedDiffDraft(Ship ship, double cargo) {
        double weightWithCargo = ship.getWeight() + cargo;
        double waterToBeDisplacesNoCargo = ship.getWeight() / Constants.WATER_DENSITY;
        double waterToBeDisplacesWithCargo = weightWithCargo / Constants.WATER_DENSITY;
        double diffInDisplacement = waterToBeDisplacesWithCargo - waterToBeDisplacesNoCargo;
        return diffInDisplacement / (ship.getLength() * ship.getWidth());
    }

    public static double shipSinkageWithCargo(Ship ship, int nContainer) {
        double addedWeight = nContainer * (Constants.CONTAINER_MASS / 1000);
        return getCalculatedDiffDraft(ship, addedWeight);
    }

    public static double getDraftAfterAddedCargo(Ship ship, int nContainers) {
        return ship.getDraft() + shipSinkageWithCargo(ship, nContainers);
    }

    /**
     * Creates a matrix with the desired container positions.
     *
     * @param unladenCenterOfMass the unladen ship center of mass
     * @param shipAvailableLength available length on the ship for container placement
     * @param shipAvailableWidth  available width on the ship for container placement
     * @param numContainers       the number of containers to be placed
     * @return the created matrix
     */
    public static Point2D.Double[][] whereToPositionContainersOnVessel(Pair<Point2D.Double, Double> unladenCenterOfMass, double shipAvailableLength, double shipAvailableWidth, double containerLength, double containerWidth, int numContainers) {
        int numMaxContainersX = (int) Math.round(shipAvailableWidth / containerWidth);
        int numMaxContainersY = (int) Math.round(shipAvailableLength / containerLength);
        Point2D.Double[][] containerMatrix = new Point2D.Double[numMaxContainersX][numMaxContainersY];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                containerMatrix[i][j] = new Point2D.Double(0.0, 0.0);
            }
        }

        for (int i = 0; i < containerMatrix.length; i++) {
            for (int j = 0; j < containerMatrix[0].length; j++) {
                double offsetX = containerWidth * ((double) (containerMatrix.length / 2) - i - 1);
                double offsetY = containerLength * ((double) (containerMatrix[0].length / 2) - j - 1);
                if (numContainers > 1) {
                    containerMatrix[i][j] = new Point2D.Double(unladenCenterOfMass.getFirst().getX() - (containerWidth / 2) - offsetX, unladenCenterOfMass.getFirst().getY() - (containerLength / 2) - offsetY);
                    containerMatrix[containerMatrix.length - 1 - i][containerMatrix[0].length - 1 - j] = new Point2D.Double(unladenCenterOfMass.getFirst().getX() + (containerWidth / 2) + offsetX, unladenCenterOfMass.getFirst().getY() + (containerLength / 2) + offsetY);
                    if (!verifyUnladenCenterOfMass(unladenCenterOfMass, containerMatrix[i][j], containerMatrix[containerMatrix.length - 1 - i][containerMatrix[0].length - 1 - j])) throw new IllegalArgumentException("There was an error while filling the array.");
                    numContainers -= 2;
                } else if (numContainers == 1) {
                    containerMatrix[i][j] = new Point2D.Double(unladenCenterOfMass.getFirst().getX() - (containerWidth / 2) - offsetX, unladenCenterOfMass.getFirst().getY() - (containerLength / 2) - offsetY);
                    numContainers = 0;
                }
            }
        }

        return containerMatrix;
    }

    /**
     * Verifies if the center of mass does not change by adding two given containers to the ship cargo.
     *
     * @param unladenCenterOfMass the unladen ship center of mass
     * @return the created matrix
     */
    public static boolean verifyUnladenCenterOfMass(Pair<Point2D.Double, Double> unladenCenterOfMass, Point2D.Double container1, Point2D.Double container2) {
        double totalMass = Constants.CONTAINER_MASS * 2;
        double unladenCenterOfMassX = unladenCenterOfMass.getFirst().getX();
        double unladenCenterOfMassY = unladenCenterOfMass.getFirst().getY();
        double sumContainerY = Constants.CONTAINER_MASS * container1.getY();
        sumContainerY += Constants.CONTAINER_MASS * container2.getY();
        double sumContainerX = Constants.CONTAINER_MASS * container1.getX();
        sumContainerX += Constants.CONTAINER_MASS * container2.getX();

        return (totalMass * unladenCenterOfMassX == sumContainerX && totalMass * unladenCenterOfMassY == sumContainerY);
    }

    /**
     * Calculates the center of mass of the ship with the containers already in place.
     *
     * @param unladenCenterOfMass the unladen ship center of mass
     * @param numContainers       the number of containers on the matrix
     * @param containerMatrix     the matrix of containers
     * @param shipMass            the ship mass
     * @return the total center of mass
     */
    public static Pair<Point2D.Double, Double> calculateTotalCenterOfMass(Pair<Point2D.Double, Double> unladenCenterOfMass, Point2D.Double[][] containerMatrix, int numContainers, double shipMass) {
        Point2D.Double containerMatrixCenterOfMassXY = calculateContainerMatrixCenterOfMassXY(containerMatrix, numContainers);
        double totalContainerMass = Constants.CONTAINER_MASS * numContainers, totalMass = totalContainerMass + shipMass;
        double xCenterOfMass = ((totalContainerMass * containerMatrixCenterOfMassXY.getX()) + (shipMass * unladenCenterOfMass.getFirst().getX())) / totalMass;
        double yCenterOfMass = ((totalContainerMass * containerMatrixCenterOfMassXY.getY()) + (shipMass * unladenCenterOfMass.getFirst().getY())) / totalMass;
        double zCenterOfMass = ((totalContainerMass * 9.695) + (shipMass * unladenCenterOfMass.getSecond())) / totalMass;

        return new Pair<>(new Point2D.Double(xCenterOfMass, yCenterOfMass), zCenterOfMass);
    }

    /**
     * Calculates the center of mass of all the containers together (only considering x and y axis).
     *
     * @param containerMatrix     the matrix of containers
     * @param numContainers       the number of containers on the matrix
     * @return the center of mass (x and y axis only)
     */
    private static Point2D.Double calculateContainerMatrixCenterOfMassXY(Point2D.Double[][] containerMatrix, int numContainers) {
        double xCenterOfMass = 0.0, yCenterOfMass = 0.0;
        for (Point2D.Double[] matrix : containerMatrix) {
            for (int j = 0; j < containerMatrix[0].length; j++) {
                if (matrix[j].getX() != 0 || matrix[j].getY() != 0) {
                    xCenterOfMass += Constants.CONTAINER_MASS * ((double) Math.round(matrix[j].getX() * 100000) / 100000);
                    yCenterOfMass += Constants.CONTAINER_MASS * ((double) Math.round(matrix[j].getY() * 100000) / 100000);
                }
            }
        }
        xCenterOfMass /= Constants.CONTAINER_MASS * numContainers;
        yCenterOfMass /= Constants.CONTAINER_MASS * numContainers;

        return new Point2D.Double(xCenterOfMass, yCenterOfMass);
    }
}
