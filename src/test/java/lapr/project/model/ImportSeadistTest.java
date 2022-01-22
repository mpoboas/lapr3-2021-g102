package lapr.project.model;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ImportSeadistTest {

    String file = "data/Data-Sprint3-4/seadists.csv";
    List<SeaDist> importedSeadistList;

    @Test
    void importSeadistData() {
        try {
            this.importedSeadistList = ImportSeadist.importSeadistData(this.file);
            assertEquals(10358, this.importedSeadistList.get(0).getFrom_port().getid());
            assertEquals(246265, this.importedSeadistList.get(0).getTo_port().getid());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void importSeadistDataFileNotFound() {
        assertThrows(IOException.class, ()->{
                    this.importedSeadistList = ImportSeadist.importSeadistData("fail.csv");
                    assertEquals(10358, this.importedSeadistList.get(0).getFrom_port().getid());
                    assertEquals(246265, this.importedSeadistList.get(0).getTo_port().getid());
                });
    }
}