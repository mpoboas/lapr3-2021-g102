package lapr.project.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ImportSeadistTest {

    String file = "data/Data-Sprint3-4/seadists.csv";
    ImportSeadist importSeadist = new ImportSeadist();

    @Test
    void importSeadistData() {
        this.importSeadist.importSeadistData(this.file);
        assertEquals(10358, importSeadist.getImportedSeadistList().get(0).getFrom_port().getid());
        assertEquals(246265, importSeadist.getImportedSeadistList().get(0).getTo_port().getid());
    }

    @Test
    void seadistAlreadyImported() {
        this.importSeadist.importSeadistData(this.file);
        assertTrue(importSeadist.seadistAlreadyImported(this.importSeadist.getImportedSeadistList().get(0).getFrom_port().getid(), this.importSeadist.getImportedSeadistList().get(0).getTo_port().getid()));
    }

    @Test
    void getImportedSeadistList() {
        this.importSeadist.importSeadistData(this.file);
        List<SeaDist> seadistList = this.importSeadist.getImportedSeadistList();
        assertEquals(3673, seadistList.get(0).getSeaDistance());
        assertEquals(3377, seadistList.get(1).getSeaDistance());
    }
}