package by.leverx.googleDrive.service;

import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.List;
import org.springframework.http.HttpHeaders;

public interface GoogleService {

  String createFolderByName(String folderName) throws Exception;

  List<String> uploadDoc(String folderId)
      throws GeneralSecurityException, IOException, URISyntaxException;

  List<java.io.File> getListOfFiles(String folderName) throws URISyntaxException;

  List<File> getPaginationFolderList()
      throws GeneralSecurityException, IOException;

  FileList getAllFolders() throws GeneralSecurityException, IOException;

  String createFolderAndUploadFile(String folderName)
      throws Exception;

  void deleteFoldrById(String folderId) throws GeneralSecurityException, IOException;

  List<String> getListOfFileNames() throws IOException, URISyntaxException;

  String getFolderPath(String folderName) throws URISyntaxException;

  void checkCurrentMonthFolder() throws Exception;

  public void scheduleCreationNextMonthFolder(String folderName) throws Exception;

  String createFolderByNameAndParentId(String folderName, String parentId) throws Exception;

  String searchFolderByFolderName(String folderName) throws Exception;

   String searchFolderByFolderNameAndParentId(String folderName, String parentId)
      throws Exception;

   String performRequest(String token);

  String clientCreateFolder(String folderName, String token);

  String searchFolderByName(String name, String token, String type);

  List<String> clientUploadDocksToFolder(String folderId, String token)
      throws URISyntaxException;

}
