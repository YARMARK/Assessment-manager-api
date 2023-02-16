package by.leverx.googleTest.service;

import by.leverx.googleTest.exception.SomethingWentWrongException;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.model.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GoogleService {

  private GoogleDriveManager manager;

  @Value("${templates.env.folder}")
  private String FOLDER_NAME;

  @Autowired
  public GoogleService(GoogleDriveManager manager) {
    this.manager = manager;
  }

  private String createFolderByName(String folderName)
      throws GeneralSecurityException, IOException {
    var fileMetaData = new File();
    fileMetaData.setName(folderName)
        .setMimeType("application/vnd.google-apps.folder");
    File file = manager.getService().files().create(fileMetaData)
        .setFields("id")
        .execute();
    return file.getId();
  }

  private List<String> uploadDoc(String folderId)
      throws GeneralSecurityException, IOException, URISyntaxException {
    List<java.io.File> fileList = getListOfFiles(FOLDER_NAME);
    var fileMetadata = new File();
    fileMetadata.setMimeType("application/vnd.google-apps.spreadsheet");
    fileMetadata.setParents(Collections.singletonList(folderId));
    java.io.File filePath;
    List<String> fileIdList = new ArrayList<>();
    for (java.io.File file : fileList) {
      fileMetadata.setName(file.getName());
      filePath = new java.io.File(file.getAbsolutePath());
      FileContent mediaFile = new FileContent(
          "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", filePath);
      var uploadFile = manager.getService().files().create(fileMetadata, mediaFile)
          .setFields("id, parents")
          .execute();
      fileIdList.add(uploadFile.getId());
    }
    return fileIdList;
  }

//  private List<java.io.File> getListOfFiles(String folderPath) {
//    var folder = new java.io.File(folderPath);
//    java.io.File[] listOfFiles = folder.listFiles();
//    String regex = "[A-Za-z\\d]{1,50}.xlsx";
//    List<java.io.File> returnList = new ArrayList<>();
//      for (java.io.File file : listOfFiles) {
//        if (file.isFile() && file.getName().matches(regex)) {
//          returnList.add(file);
//        }
//      }
//      return returnList;
//  }

  private List<java.io.File> getListOfFiles(String folderName) throws URISyntaxException {
    var folder = new java.io.File(getFolderPath(folderName));
    java.io.File[] listOfFiles = folder.listFiles();
    String regex = "[A-Za-z\\d]{1,50}.xlsx";
    List<java.io.File> returnList = new ArrayList<>();
    for (java.io.File file : listOfFiles) {
      if (file.isFile() && file.getName().matches(regex)) {
        returnList.add(file);
      }
    }
    return returnList;
  }

  public String createFolderAndUploadFile(String folderName)
      throws GeneralSecurityException, IOException, URISyntaxException {
    String folderId = createFolderByName(folderName);
    List<String> fileIdList = uploadDoc(folderId);
    if (Objects.nonNull(folderId) && !fileIdList.isEmpty()) {
      return folderId;
    }
    throw new SomethingWentWrongException("UNABLE TO CREAT FOLDER OR UPLOAD FILE");
  }

  public void deleteFoldrById(String folderId) throws GeneralSecurityException, IOException {
    manager.getService().files().delete(folderId).execute();
  }
//
//  public List<String> getListOfFileNames() {
//    var folder = new java.io.File("/templates");
//    java.io.File[] listOfFiles = folder.listFiles();
//    String regex = "[A-Za-z\\d]{1,50}.xlsx";
//    List<String> returnList = new ArrayList<>();
//    for (java.io.File file : listOfFiles) {
//      if (file.isFile() && file.getName().matches(regex)) {
//        returnList.add(file.getName());
//      }
//    }
//    return returnList;
//  }

  public List<String> getListOfFileNames() throws IOException, URISyntaxException {
    List<String> fileNames = new ArrayList<>();
    String folderPath = getFolderPath(FOLDER_NAME);
    Files.list(Paths.get(folderPath)).forEach(file -> fileNames.add(file.getFileName().toString()));
    return fileNames;
  }

  private String getFolderPath(String folderName) throws URISyntaxException {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    if (classLoader == null) {
      classLoader = GoogleService.class.getClassLoader();
    }
    URI folderUrl = classLoader.getResource(folderName).toURI();
    if (folderUrl == null) {
      throw new IllegalArgumentException("Folder not found in classpath: " + folderName);
    }
    String folderPath = Paths.get(folderUrl).toString();
    return folderPath;
  }
}
