package lapr.project.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ImportCountry {

    public static List<Country> importCountries(String fileDirectory) throws IOException {
        List<Country> importedCountries = new ArrayList<>();
        try (Scanner readfile = new Scanner(new File(fileDirectory))) {
            readfile.nextLine();
            while (readfile.hasNextLine()){
                String[] attributes = readfile.nextLine().split(",");
                Country country = new Country(attributes[0],attributes[1],attributes[2],attributes[3],
                        Double.parseDouble(attributes[4]) ,attributes[5],
                        Double.parseDouble(attributes[7]),Double.parseDouble(attributes[6]));
                if (!importedCountries.contains(country))
                    importedCountries.add(country);
            }
        }
        return importedCountries;
    }
}
