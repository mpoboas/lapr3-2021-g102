package lapr.project.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class imports the seadists from a file.
 */
public class ImportSeadist {

    /**
     * List of all the seadists from the file.
     */
    private final List<SeaDist> importedSeadistList;

    /**
     * ImportSeadist constructor, with no parameters.
     */
    public ImportSeadist() {
        importedSeadistList = new ArrayList<>();
    }

    /**
     * Imports all the attributes from a file and creates the object.
     *
     * @param fileDirectory file path that contains the attributes
     */
    public void importSeadistData(String fileDirectory) {
        try (Scanner read = new Scanner(new File(fileDirectory))) {
            read.nextLine();
            while (read.hasNextLine()) {
                String[] attributes = read.nextLine().split(",");
                if (!seadistAlreadyImported(Integer.parseInt(attributes[1]), Integer.parseInt(attributes[4])))
                    importedSeadistList.add(createSeadist(attributes));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Verifies if an instance of Seadist was already imported.
     *
     * @param from_port_id id of the source port
     * @param to_port_id   if of the destination port
     * @return true if the instance of Seadist was already imported, false if it wasn´t
     */
    public boolean seadistAlreadyImported(int from_port_id, int to_port_id) {
        for (SeaDist seaDist : this.importedSeadistList) {
            if (seaDist.getFrom_port().getid() == from_port_id && seaDist.getTo_port().getid() == to_port_id)
                return true;
        }
        return false;
    }

    /**
     * Creates an instance of Seadist.
     *
     * @param attributes the array that contains all the Seadist instance data
     * @return the new instance of Seadist
     */
    private SeaDist createSeadist(String[] attributes) {
        Port from_port = new Port(Integer.parseInt(attributes[1]), null, null, null, 0.0, 0.0);
        Port to_port = new Port(Integer.parseInt(attributes[4]), null, null, null, 0.0, 0.0);
        return new SeaDist(null, from_port, null, to_port, Integer.parseInt(attributes[6]));
    }

    /**
     * Gets the list of imported instances of Seadist.
     *
     * @return list of instances of Seadist
     */
    public List<SeaDist> getImportedSeadistList() {
        return importedSeadistList;
    }
}
