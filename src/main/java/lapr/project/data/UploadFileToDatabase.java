package lapr.project.data;

import java.io.IOException;

public interface UploadFileToDatabase {

    /**
     * Define file to be uploaded to database
     * @param fileDirectory file to be uploaded
     * @throws IOException exception case file doesnt exist
     */
    void UploadFile(String fileDirectory) throws IOException;
    /**
     * Import file to memory
     * @throws IOException exception case file doesnt exist
     */
    void importFile() throws IOException;

    /**
     * export file to database
     */
    void importToDatabase();


}
