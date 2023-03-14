package by.leverx.googleDrive.service.serviceImpl;

import static by.leverx.googleDrive.util.GoogleUtil.creatCurrentMonthFolderName;
import static by.leverx.googleDrive.util.GoogleUtil.getMimeType;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import by.leverx.googleDrive.clientRest.GoogleRestClient;
import by.leverx.googleDrive.exception.SomethingWentWrongException;
import by.leverx.googleDrive.service.GoogleService;
import by.leverx.googleDrive.service.manager.GoogleDriveManager;
import by.leverx.googleDrive.service.manager.ServiceDriveManger;
import by.leverx.googleDrive.util.ConstantMessage;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
public class GoogleServiceImpl implements GoogleService {

  private GoogleDriveManager driveManager;

  private ServiceDriveManger serviceDriveManger;

  private GoogleRestClient restClient;

  @Value("${templates.env.folder}")
  private String FOLDER_NAME;

  @Value("${drive.env.base-url}")
  private String BASE_URL;


  @Autowired
  public GoogleServiceImpl(GoogleDriveManager driveManager, ServiceDriveManger serviceDriveManger,
      GoogleRestClient restClient) {
    this.driveManager = driveManager;
    this.serviceDriveManger = serviceDriveManger;
    this.restClient = restClient;
  }

  @Override
  @EventListener(ApplicationReadyEvent.class)
  @Order(1)
  public void checkCurrentMontFolder() throws Exception {
    String currentMonthFolderName = creatCurrentMonthFolderName();
    String parentFolderId = searchFolderByFolderName("Assessments");
    if (nonNull(parentFolderId)) {
      String folderId = searchFolderByFolderNameAndParentId(currentMonthFolderName, parentFolderId);
      if (isNull(folderId)) {
        createFolderByNameAndParentId(currentMonthFolderName, parentFolderId);
      }
    }
  }

  @Override
  public String searchFolderByFolderNameAndParentId(String folderName, String parentId)
      throws Exception {
    String folderId = StringUtils.EMPTY;
    String pageToken = StringUtils.EMPTY;
    FileList result;
    String queryType = StringUtils.EMPTY;
    String queryName = StringUtils.EMPTY;

    if (folderName.isBlank()) {
      System.err.println("Error");
    } else {
      queryType = " mimeType = 'application/vnd.google-apps.folder'";
      queryName = "name = '" + folderName + "' and '" + parentId + "' in parents";
    }
    result = serviceDriveManger.getDriveService().files().list().setQ(queryType).setQ(queryName)
        .setSpaces("drive")
        .setFields("nextPageToken, files(id, name)")
        .execute();
    for (var file : result.getFiles()) {
      folderId = file.getId();
    }
    return folderId;
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
    result = serviceDriveManger.getDriveService().files().list().setQ(queryType).setQ(queryName)
        .setSpaces("drive")
        .setFields("nextPageToken, files(id, name)")
        .execute();
    for (var file : result.getFiles()) {
      folderId = file.getId();
    }
    return folderId;
  }

  @Override // CREATE FOLDER IN GOOGLE DRIVE SPACE
  public String createFolderByNameAndParentId(String folderName, String parentId)
      throws Exception {
    var fileMetaData = new File();
    fileMetaData.setName(folderName)
        .setMimeType("application/vnd.google-apps.folder")
        .setParents(Collections.singletonList(parentId));
    File file = serviceDriveManger.getDriveService().files().create(fileMetaData)
        .setFields("id")
        .execute();
    return file.getId();
  }

  @Override // CREATE FOLDER IN GOOGLE DRIVE SPACE
  public String createFolderByName(String folderName)
      throws Exception {
    var fileMetaData = new File();
    fileMetaData.setName(folderName)
        .setMimeType("application/vnd.google-apps.folder");
    File file = serviceDriveManger.getDriveService().files().create(fileMetaData)
        .setFields("id")
        .execute();
    return file.getId();
  }

  private HttpHeaders createHeader(String token) {
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(token);
    headers.setContentType(MediaType.APPLICATION_JSON);
    return headers;
  }

  private HttpHeaders createUploadHeader(String token, String name) {
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(token);
    headers.set("Content-Disposition", "attachment; filename=\"" + name + "\"");
    return headers;
  }

  @Override
  public String performRequest(String token) {
    String mainUrl = BASE_URL + "drive/v3/files";
    HttpHeaders header = createHeader(token);
    HttpEntity httpEntity = new HttpEntity<>(header);
    String response = restClient.performRequest(mainUrl, httpEntity);
    return response;
  }

  @Override
  public String clientCreateFolder(String folderName, String token) {
    File metadata = new File();
    metadata.setName(folderName)
        .setMimeType("application/vnd.google-apps.folder");
    HttpHeaders header = createHeader(token);
    HttpEntity<File> httpEntity = new HttpEntity<>(metadata, header);
    String url = BASE_URL + "drive/v3/files";
    String folderId = restClient.createFolder(url, httpEntity);
    return folderId;
  }

  @Override
  public String searchFolderByName(String name, String token, String type) {
    HttpHeaders headers = createHeader(token);
    HttpEntity<FileList> httpEntity = new HttpEntity<>(headers);
    String mimeType = getMimeType(type);
    String query = "name='" + name + "' and mimeType='" + mimeType + "'";
    String fields = "files(id, name)";
    String url = BASE_URL + "drive/v3/files/?q=" + query + "&fields" + fields;
    String folderId = restClient.searchFolderByName(url, httpEntity);
    return folderId;
  }

  @Override
  public List<String> clientUploadDocksToFolder(String folderId, String token)
      throws URISyntaxException {
    List<String> idList = new ArrayList<>();
    List<java.io.File> listOfFiles = getListOfFiles(FOLDER_NAME);
    File metadata = new File();
    metadata.setParents(Collections.singletonList(folderId))
        .setMimeType("application/vnd.google-apps.spreadsheet");
    String url = BASE_URL + "upload/drive/v3/files?uploadType=multipart";
    java.io.File filePath;
    for (var file : listOfFiles) {
      metadata.setName(file.getName());
      filePath = new java.io.File(file.getAbsolutePath());
      FileSystemResource resource = new FileSystemResource(filePath);
      HttpHeaders uploadHeader = createUploadHeader(token, filePath.getName());
      MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
      body.add("metadata", metadata);
      body.add("file", resource);
      HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body,
          uploadHeader);
      String filesId = restClient.uploadDocksToFolder(url, requestEntity);
      idList.add(filesId);
    }
    return idList;
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
      var uploadFile = driveManager.getService().files().create(fileMetadata, mediaFile)
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
    result = driveManager.getService().files().list().setQ(query)
        .setSpaces("drive")
        .setFields("nextPageToken, files(id, name)")
        .execute();
    return result;
  }

  @Override
  public List<java.io.File> getListOfFiles(String folderName) throws URISyntaxException {
    var folder = new java.io.File(getFolderPath(folderName));
    java.io.File[] listOfFiles = folder.listFiles();
    List<java.io.File> returnList = new ArrayList<>();
    for (java.io.File file : listOfFiles) {
      if (file.isFile() && file.getName().endsWith("xlsx")) {
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
    throw new SomethingWentWrongException(ConstantMessage.getUnableToCreteUploadMessage());
  }

  @Override
  public void deleteFoldrById(String folderId) throws GeneralSecurityException, IOException {
    driveManager.getService().files().delete(folderId).execute();
  }

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

}
