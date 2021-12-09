package lapr.project.data;

import java.io.IOException;

public interface UploadFileToDatabase {

    void UploadFile(String fileDirectory) throws IOException;
     void importFile(Object o) throws IOException;

     void importToDatabase();


}
