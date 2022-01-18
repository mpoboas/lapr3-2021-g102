package lapr.project.utils;

import lapr.project.model.CargoManifest;
import lapr.project.model.Container;
import lapr.project.model.ImportCargoManifest;
import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;
import java.io.IOException;
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
    void calculateEnergyTransferredAsHeat() {
        double result = PhysicsUtils.calculateEnergyTransferredAsHeat(20, 7, 1);
        assertEquals(13.0, result);
        double result2 = PhysicsUtils.calculateEnergyTransferredAsHeat(20, 7, 0.024);
        assertEquals(541.6666666666666, result2);
    }

    @Test
    void calculateEnergyNecessary() {
        double result = PhysicsUtils.calculateEnergyNecessary(20, 7, 1, 2, 30, 0);
        assertEquals(117000, result);
        double result2 = PhysicsUtils.calculateEnergyNecessary(20, 7, 0.024, 2, 30, 0);
        assertEquals(4875000, result2);
    }

    @Test
    void calculateEnergyNecessaryInTrip() {
        List<Pair<Integer, Integer>> tripSections = new ArrayList<>();
        tripSections.add(new Pair<>(60, 30));
        tripSections.add(new Pair<>(400, 20));
        tripSections.add(new Pair<>(200, 10));
        tripSections.add(new Pair<>(1000, 12));
        tripSections.add(new Pair<>(3000, 10));
        tripSections.add(new Pair<>(740, 15));
        assertEquals(0.034, PhysicsUtils.get5DegResistance(), 0.01);
        assertEquals(0.024, PhysicsUtils.get7DegResistance(), 0.01);

        List<Pair<Double, Double>> result = PhysicsUtils.energyToBeSupliedTocontainers(tripSections, 12, 14);
        assertEquals(704925.4, result.get(0).getFirst(), 0.1);
        assertEquals(800082.5, result.get(0).getSecond(), 0.1);
        assertEquals(3356787.89, result.get(1).getFirst(), 0.1);
        assertEquals(3014803.7, result.get(1).getSecond(), 0.1);
        assertEquals(1007036.36, result.get(2).getFirst(), 0.1);
        assertEquals(347861.9, result.get(2).getSecond(), 0.1);
        assertEquals(5706539.42, result.get(3).getFirst(), 0.1);
        assertEquals(2898849.79, result.get(3).getSecond(), 0.1);
        assertEquals(15105545.54, result.get(4).getFirst(), 0.1);
        assertEquals(5217929.628, result.get(4).getSecond(), 0.1);
        assertEquals(4968046.08, result.get(5).getFirst(), 0.1);
        assertEquals(3432238.1, result.get(5).getSecond(), 0.1);
    }


    @Test
    void getNumberOfAuxEquipment() {
        List<Pair<Integer, Double>> tripSections = new ArrayList<>();
        tripSections.add(new Pair<>(60, 30.0));
        tripSections.add(new Pair<>(400, 20.0));
        tripSections.add(new Pair<>(200, 10.0));
        tripSections.add(new Pair<>(1000, 12.0));
        tripSections.add(new Pair<>(3000, 10.0));
        tripSections.add(new Pair<>(740, 15.0));

        List<Double> cont5EnergyPerSection = new ArrayList<>();
        cont5EnergyPerSection.add(172506.8835);
        cont5EnergyPerSection.add(821461.3499);
        cont5EnergyPerSection.add(246438.405);
        cont5EnergyPerSection.add(1396484.295);
        cont5EnergyPerSection.add(3696576.074);
        cont5EnergyPerSection.add(1215762.798);

        List<Double> cont7EnergyPerSection = new ArrayList<>();
        cont7EnergyPerSection.add(297486.1677);
        cont7EnergyPerSection.add(1120962.371);
        cont7EnergyPerSection.add(129341.812);
        cont7EnergyPerSection.add(1077848.434);
        cont7EnergyPerSection.add(1940127.181);
        cont7EnergyPerSection.add(1276172.546);

        assertEquals(tripSections.size(), cont5EnergyPerSection.size());
        assertEquals(tripSections.size(), cont7EnergyPerSection.size());
        assertEquals(cont5EnergyPerSection.size(), cont7EnergyPerSection.size());
        assertEquals(2, PhysicsUtils.getNumberOfAuxEquipment(tripSections, cont5EnergyPerSection, cont7EnergyPerSection, Constants.AUX_EQUIPMENT_MAX_POWER));
    }

    @Test
    void listMaterials() {
        List<Pair<String, Double>> list = PhysicsUtils.listOfMaterials();
        assertEquals("Cork", list.get(2).getFirst());
        assertEquals("Concrete", list.get(6).getFirst());

    }
    @Test
    void listContainersEnergy() throws IOException {
        List<Pair<Integer, Double>> tripSections = new ArrayList<>();
        tripSections.add(new Pair<>(60, 30.0));
        tripSections.add(new Pair<>(400, 20.0));
        tripSections.add(new Pair<>(200, 10.0));
        tripSections.add(new Pair<>(1000, 12.0));
        tripSections.add(new Pair<>(3000, 10.0));
        tripSections.add(new Pair<>(740, 15.0));
        ImportCargoManifest importCG = new ImportCargoManifest();
        importCG.importCargoManifestData("data/data-shipsports/LAPR3_G102 - cargoManifests.csv");
        importCG.getListOfImportedCargoManifests().get(0).getContainerList().clear();
        importCG.getListOfImportedCargoManifests().get(0).getContainerList().add(new Pair<>(new Container("Lpru1201232","refrigerated7","iso",12132,13221,231233), new CargoManifest.ContainerPosition(0,0,0)));
        importCG.getListOfImportedCargoManifests().get(0).getContainerList().add(new Pair<>(new Container("Lpru1201233","refrigerated5","iso",12132,13221,231233), new CargoManifest.ContainerPosition(0,0,1)));
        importCG.getListOfImportedCargoManifests().get(0).getContainerList().add(new Pair<>(new Container("Lpru1201234","refrigerated7","iso",12132,13221,231233), new CargoManifest.ContainerPosition(0,1,0)));
        importCG.getListOfImportedCargoManifests().get(0).getContainerList().add(new Pair<>(new Container("Lpru1201235","refrigerated5","iso",12132,13221,231233), new CargoManifest.ContainerPosition(1,0,0)));
        importCG.getListOfImportedCargoManifests().get(0).getContainerList().add(new Pair<>(new Container("Lpru1201236","refrigerated7","iso",12132,13221,231233), new CargoManifest.ContainerPosition(0,1,1)));
        importCG.getListOfImportedCargoManifests().get(0).getContainerList().add(new Pair<>(new Container("Lpru1201237","refrigerated5","iso",12132,13221,231233), new CargoManifest.ContainerPosition(1,1,0)));
        importCG.getListOfImportedCargoManifests().get(0).getContainerList().add(new Pair<>(new Container("Lpru1201238","refrigerated5","iso",12132,13221,231233), new CargoManifest.ContainerPosition(1,0,1)));
        importCG.getListOfImportedCargoManifests().get(0).getContainerList().add(new Pair<>(new Container("Lpru1201239","refrigerated7","iso",12132,13221,231233), new CargoManifest.ContainerPosition(1,1,1)));
        importCG.getListOfImportedCargoManifests().get(0).getContainerList().add(new Pair<>(new Container("Lpru1201240","refrigerated5","iso",12132,13221,231233), new CargoManifest.ContainerPosition(0,0,2)));
        importCG.getListOfImportedCargoManifests().get(0).getContainerList().add(new Pair<>(new Container("Lpru1201241","refrigerated7","iso",12132,13221,231233), new CargoManifest.ContainerPosition(0,2,0)));
        importCG.getListOfImportedCargoManifests().get(0).getContainerList().add(new Pair<>(new Container("Lpru1201242","refrigerated5","iso",12132,13221,231233), new CargoManifest.ContainerPosition(0,0,3)));
        importCG.getListOfImportedCargoManifests().get(0).getContainerList().add(new Pair<>(new Container("Lpru1201243","refrigerated7","iso",12132,13221,231233), new CargoManifest.ContainerPosition(0,3,0)));
        importCG.getListOfImportedCargoManifests().get(0).getContainerList().add(new Pair<>(new Container("Lpru1201244","refrigerated5","iso",12132,13221,231233), new CargoManifest.ContainerPosition(0,4,0)));


        List<Pair<Double,Double>> list = PhysicsUtils.energyToBeSupliedTocontainersDepOnPos(importCG.getListOfImportedCargoManifests().get(0),tripSections);
        assertEquals(411206.5,list.get(0).getFirst(),0.1);
        assertEquals(342892.5,list.get(0).getSecond(),0.1);
        assertEquals(1958126.2,list.get(1).getFirst(),0.1);
        assertEquals(1292058.76,list.get(1).getSecond(),0.1);
        assertEquals(587437.8,list.get(2).getFirst(),0.1);
        assertEquals(149083.7,list.get(2).getSecond(),0.1);
        assertEquals(3328814.66,list.get(3).getFirst(),0.1);
        assertEquals(1242364.19,list.get(3).getSecond(),0.1);
        assertEquals(8811568.23,list.get(4).getFirst(),0.1);
        assertEquals(2236255.55,list.get(4).getSecond(),0.1);
        assertEquals(2898026.8,list.get(5).getFirst(),0.1);
        assertEquals(1470959.2,list.get(5).getSecond(),0.1);

    }


    @Test
    void calculateUnleadedCM(){
        double length = 336.61;
        double width = 45.6;
        double height = 27.2;
        double massTotal = 107849000;
        Pair<Point2D.Double,Double> result = PhysicsUtils.calculateTheUnleadedCenterOfMass(length,width,height,massTotal,"MIDDLE");
        assertEquals(0,result.getFirst().x);
        assertEquals(158.0326,result.getFirst().y);
        assertEquals(9.248,result.getSecond());

        result = PhysicsUtils.calculateTheUnleadedCenterOfMass(175,28,8.5,6278000,"STERN");
        assertEquals(0,result.getFirst().x);
        assertEquals(80.2725,result.getFirst().y);
        assertEquals(2.89,result.getSecond());

        result = PhysicsUtils.calculateTheUnleadedCenterOfMass(175,28,8.5,6278000,"BOW");
        assertEquals(0,result.getFirst().x);
        assertEquals(84.6475,result.getFirst().y);
        assertEquals(2.89,result.getSecond());


        result = PhysicsUtils.calculateTheUnleadedCenterOfMass(175,28,8.5,6278000,"");
        assertEquals(0,result.getFirst().x);
        assertEquals(80.2725,result.getFirst().y);
        assertEquals(2.89,result.getSecond());
    }

}