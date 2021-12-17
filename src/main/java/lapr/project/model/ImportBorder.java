package lapr.project.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ImportBorder {

    private static final String countryFile = "data/Data-Sprint3-4/countries.csv";

    public static List<Border> importBorders(String fileDirectory) throws IOException {
        return importBorders(ImportCountry.importCountries(countryFile), fileDirectory);
    }

    public static List<Border> importBorders(List<Country> listCountries, String fileDirectory) throws IOException {
        List<Border> borderList = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(fileDirectory))) {
            sc.nextLine();
            while (sc.hasNextLine()) {
                String[] attributes = sc.nextLine().split(",");
                Country c1 = null;
                Country c2 = null;
                for (Country c : listCountries) {
                    if (attributes[0].equals(c.getCountry()))
                        c1 = c;
                    if (attributes[1].equals(c.getCountry()))
                        c2 = c;
                }
                if (c1 != null && c2 != null) {
                    Border b = new Border(c1, c2);
                    if (!borderList.contains(b))
                        borderList.add(b);
                }
            }
            return borderList;
        }
    }
}
