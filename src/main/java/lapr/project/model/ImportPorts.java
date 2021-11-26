package lapr.project.model;

//import lapr.project.stores.PortStore;

import java.io.File;;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ImportPorts {

    private final List<Port> listOfImportedPorts;

    //private final PortStore store;

    public ImportPorts(/*PortStore store*/){
        //this.store = store;
        listOfImportedPorts = new ArrayList<>();
    }

    public void importPortsData(String fileDirectory) throws IOException {
        Scanner read = new Scanner(new File(fileDirectory));
        read.nextLine();
        while (read.hasNextLine()){
            String[] attributes = read.nextLine().split(",");
            if (!portAlreadyImported(attributes[2]))
                listOfImportedPorts.add(createPort(attributes));
        }
        read.close();
    }

    public boolean portAlreadyImported(String code){
        int id = Integer.parseInt(code);
        for (Port p: listOfImportedPorts) {
            if (p.getid() == id)
                return true;
        }
        return false;
    }

    private Port createPort(String[] attributes){
        String continent = attributes[0];
        String country = attributes[1];
        int code = Integer.parseInt(attributes[2]);
        String name = attributes[3];
        double latitude = Double.parseDouble(attributes[4]);
        double longitude = Double.parseDouble(attributes[5]);
        return new Port(code,name,continent,country, longitude, latitude);
    }

    public List<Port> getListOfImportedPorts(){
        return listOfImportedPorts;
    }


}
