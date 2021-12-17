package lapr.project.data;

import lapr.project.model.Country;
import lapr.project.model.ImportCountry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UploadCountry implements UploadFileToDatabase{
    private String fileDirectory;
    private ConnectionFactory cf;
    private List<Country> importedCountries;

    @Override
    public void UploadFile(String fileDirectory) throws IOException {
        this.fileDirectory = fileDirectory;
        this.cf = ConnectionFactory.getInstance();
        this.importedCountries = new ArrayList<>();
    }
    @Override
    public void importFile() throws IOException {
        importedCountries = ImportCountry.importCountries(fileDirectory);
    }
    @Override
    public void importToDatabase() {
        Persistable persistable = new CountryStoreDB();
        for (Country country: importedCountries) {
            persistable.save(cf.getDatabaseConnection(),country);
        }

    }
}
