package lapr.project.model;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ImportBorderTest {
    String fileDirectory = "data/Data-Sprint3-4/borders.csv";

    @Test
    void importBorders() {
        try {
            List<Border> list = ImportBorder.importBorders(fileDirectory);
            assertNotEquals(null,list);
            assertNotEquals(0,list.size());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @Test
    void importBordersFileNotFound1() {
        try {
            List<Border> list = ImportBorder.importBorders("fileDirectory");
        } catch (IOException ex) {
            assertEquals(FileNotFoundException.class,ex.getClass());
        }
    }
}