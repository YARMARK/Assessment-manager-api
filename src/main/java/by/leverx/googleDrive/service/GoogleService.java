package by.leverx.googleDrive.service;

import com.google.api.services.drive.model.FileList;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.List;

public interface GoogleService {

  String createFolderByName(String folderName) throws Exception;

  List<String> uploadDoc(String folderId, String fileExtension)
      throws GeneralSecurityException, IOException, URISyntaxException;

  List<java.io.File> getListOfFiles(String folderName, String ends) throws URISyntaxException;

  List<String> getAllFolders(String token)
      throws GeneralSecurityException, IOException;

  FileList getAllFolders() throws GeneralSecurityException, IOException;

  String createFolderAndUploadFile(String folderName, String fileExtension)
      throws Exception;

  void deleteFoldrById(String folderId) throws GeneralSecurityException, IOException;

  void checkCurrentMonthFolder() throws Exception;

  public void scheduleCreationNextMonthFolder(String folderName) throws Exception;

  String createFolderByNameAndParentId(String folderName, String parentId) throws Exception;

  String searchFolderByFolderName(String folderName) throws Exception;

   String searchFolderByFolderNameAndParentId(String folderName, String parentId)
      throws Exception;

   String performRequest(String token);

  String clientCreateFolder(String folderName, String token);

  String searchFolderByName(String name, String token, String type);

  List<String> clientUploadDocksToFolder(String folderId, String token, String fileExtension)
      throws URISyntaxException;

}
