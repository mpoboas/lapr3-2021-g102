package lapr.project.model;

import lapr.project.utils.Pair;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ImportCargoManifest {

    private final List<CargoManifest> listOfImportedCargoManifests;


    public ImportCargoManifest() {
        listOfImportedCargoManifests = new ArrayList<>();
    }

    public void importCargoManifestData(String fileDirectory) throws IOException {

        try(Scanner readfile = new Scanner(new File(fileDirectory))) {
            readfile.nextLine();
            while (readfile.hasNextLine()) {
                String[] attributes = readfile.nextLine().split(",");
                CargoManifest cm;
                if (!cargoManifestAlreadyImported(attributes[0])) {
                    cm = createCargoManifest(attributes);
                } else {
                    cm = getCargoManifestByID(attributes[0]);
                }
                cm.addContainer(createContainer(attributes));
                if (!listOfImportedCargoManifests.contains(cm))
                    listOfImportedCargoManifests.add(cm);
            }
        }
    }

    public boolean cargoManifestAlreadyImported(String id) {
        int p = Integer.parseInt(id);
        for (CargoManifest s : listOfImportedCargoManifests) {
            if (s.getId() == p)
                return true;
        }
        return false;
    }

    public CargoManifest getCargoManifestByID(String id) {
        int p = Integer.parseInt(id);
        for (CargoManifest s : listOfImportedCargoManifests) {
            if (s.getId() == p)
                return s;
        }
        throw new IllegalArgumentException("Not found");
    }

    private CargoManifest createCargoManifest(String[] attributes) {
        int id = Integer.parseInt(attributes[0]);
        Date elaborationDate = new Date(attributes[1]);
        String type = attributes[2];

        return new CargoManifest(id, elaborationDate,type, new ArrayList<>());
    }
    private Pair<Container, CargoManifest.ContainerPosition> createContainer(String[] attributes) {
        String idContainer = attributes[3];
        int posX = Integer.parseInt(attributes [4]);
        int posY = Integer.parseInt(attributes [5]);
        int posz = Integer.parseInt(attributes [6]);
        Container c = new Container(idContainer, null, null,0,0,0);
        CargoManifest.ContainerPosition pos = new CargoManifest.ContainerPosition(posX, posY,posz);
        return new Pair<>(c,pos);
    }

    public List<CargoManifest> getListOfImportedCargoManifests() {
        return listOfImportedCargoManifests;
    }
}
