package lapr.project.model;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ImportCountryTest {
    String fileDirectory = "data/Data-Sprint3-4/countries.csv";

    @Test
    void importCountries() {
        try {
            List<Country> list = ImportCountry.importCountries(fileDirectory);
            assertNotEquals(null,list);
            assertNotEquals(0,list.size());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @Test
    void importBordersFileNotFound1() {
        try {
            List<Country> list = ImportCountry.importCountries("fileDirectory");
        } catch (IOException ex) {
            assertEquals(FileNotFoundException.class,ex.getClass());
        }
    }
}