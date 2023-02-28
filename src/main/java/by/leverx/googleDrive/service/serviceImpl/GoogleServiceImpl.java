package by.leverx.googleDrive.service.serviceImpl;

import static by.leverx.googleDrive.util.GoogleUtil.creatCurrentMonthFolderName;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import by.leverx.googleDrive.exception.SomethingWentWrongException;
import by.leverx.googleDrive.service.GoogleService;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
public class GoogleServiceImpl implements GoogleService {

  private GoogleDriveManager manager;

  @Value("${templates.env.folder}")
  private String FOLDER_NAME;

  private final String ERROR_MESSAGE = "UNABLE TO CREAT FOLDER OR UPLOAD FILE";

  @Autowired
  public GoogleServiceImpl(GoogleDriveManager manager) {
    this.manager = manager;
  }

  @Override
  public String createFolderByName(String folderName)
      throws Exception {
    var fileMetaData = new File();
    fileMetaData.setName(folderName)
        .setMimeType("application/vnd.google-apps.folder");
    File file = manager.getService().files().create(fileMetaData)
        .setFields("id")
        .execute();
    return file.getId();
  }

  @Override
  public List<String> uploadDoc(String folderId)
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

  @Override
  public List<File> getPaginationFolderList()
      throws GeneralSecurityException, IOException {
    List<File> targetData = new ArrayList<>();
    List<File> files = getAllFolders().getFiles();
    for (var file : files) {
      targetData.add(file);
    }
//    result.put("folderName", targetData);
//    result.put("currentPage", all.getNumber());
//    result.put("totalEmployees", all.getTotalElements());
//    result.put("totalPages", all.getTotalPages());
    return targetData;
  }

  @Override
  public FileList getAllFolders() throws GeneralSecurityException, IOException {
    String folderId = null;
    String pageToken = null;
    FileList result = null;

    String query = " mimeType = 'application/vnd.google-apps.folder'";
    result = manager.getService().files().list().setQ(query)
        .setSpaces("drive")
        .setFields("nextPageToken, files(id, name)")
        .execute();
    return result;
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

  @Override
  public List<java.io.File> getListOfFiles(String folderName) throws URISyntaxException {
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

  @Override
  public String createFolderAndUploadFile(String folderName)
      throws Exception {
    String folderId = createFolderByName(folderName);
    List<String> fileIdList = uploadDoc(folderId);
    if (nonNull(folderId) && !fileIdList.isEmpty()) {
      return folderId;
    }
    throw new SomethingWentWrongException(ERROR_MESSAGE);
  }

  @Override
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

  @Override
  public List<String> getListOfFileNames() throws IOException, URISyntaxException {
    List<String> fileNames = new ArrayList<>();
    String folderPath = getFolderPath(FOLDER_NAME);
    Files.list(Paths.get(folderPath)).forEach(file -> fileNames.add(file.getFileName().toString()));
    return fileNames;
  }

  @Override
  public String getFolderPath(String folderName) throws URISyntaxException {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    if (classLoader == null) {
      classLoader = GoogleServiceImpl.class.getClassLoader();
    }
    URI folderUrl = classLoader.getResource(folderName).toURI();
    if (folderUrl == null) {
      throw new IllegalArgumentException("Folder not found in classpath: " + folderName);
    }
    String folderPath = Paths.get(folderUrl).toString();
    return folderPath;
  }

  @Override
  @EventListener(ApplicationReadyEvent.class)
  @Order(1)
  public void checkCurrentMontFolder() throws Exception {
    String currentMonthFolderName = creatCurrentMonthFolderName();
    String folderId = searchFolderByFolderName(currentMonthFolderName);
    if (isNull(folderId)) {
      createFolderByName(currentMonthFolderName);
    }
  }

  @Override
  public String searchFolderByFolderName(String folderName)
      throws Exception {
    String folderId = null;
    String pageToken = null;
    FileList result = null;
    String queryType = null;
    String queryName = null;

    if (folderName.isBlank()) {
      System.err.println("Error");
    } else {
      queryType = " mimeType = 'application/vnd.google-apps.folder'";
      queryName = "name = '" + folderName + "'";
    }
    result = manager.getService().files().list().setQ(queryType).setQ(queryName)
        .setSpaces("drive")
        .setFields("nextPageToken, files(id, name)")
        .execute();
    for (var file : result.getFiles()) {
      folderId = file.getId();
    }
    return folderId;
  }

}
