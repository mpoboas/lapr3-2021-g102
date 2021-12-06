package lapr.project.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ImportContainers {
    private final List<Container> listOfImportedContainers;
    public ImportContainers(){
        this.listOfImportedContainers = new ArrayList<>();
    }

    /**
     *
     * @param fileDirectory
     */
    public void importContainersData(String fileDirectory){
        try(Scanner read = new Scanner(new File(fileDirectory))) {
            read.nextLine();
            while (read.hasNextLine()) {
                String[] attributes = read.nextLine().split(",");
                if (!containerAlreadyImported(attributes[0]))
                    listOfImportedContainers.add(createContainer(attributes));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * @param code
     * @return
     */
    public boolean containerAlreadyImported(String code){
        String id = code;
        for (Container p: listOfImportedContainers) {
            if (p.getId().equalsIgnoreCase(id))
                return true;
        }
        return false;
    }
    public List<Container> getListOfImportedContainers(){
        return listOfImportedContainers;
    }

    private Container createContainer(String[] atributtes){
        String idContainer = atributtes[0];
        String type = atributtes[1];
        String isoCode = atributtes[2];
        int tare = Integer.parseInt(atributtes[3]);
        int payload = Integer.parseInt(atributtes[4]);
        int gross = Integer.parseInt(atributtes[5]);
        return new Container(idContainer, type, isoCode, tare, payload, gross);
    }
}
