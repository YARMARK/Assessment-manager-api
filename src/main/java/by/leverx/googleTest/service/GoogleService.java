package by.leverx.googleTest.service;

import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Pageable;

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

  void checkCurrentMontFolder() throws Exception;

  String searchFolderByFolderName(String folderName) throws Exception;

}
