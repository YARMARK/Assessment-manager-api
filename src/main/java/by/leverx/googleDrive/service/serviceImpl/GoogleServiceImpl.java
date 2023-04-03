package by.leverx.googleDrive.service.serviceImpl;

import static by.leverx.googleDrive.util.ConstantMessage.ExceptionConstant.UNABLE_TO_CRETE_UPLOAD;
import static by.leverx.googleDrive.util.FileUtil.getFolderPath;
import static by.leverx.googleDrive.util.GoogleUtil.creatCurrentMonthFolderName;
import static by.leverx.googleDrive.util.GoogleUtil.getMimeType;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

import by.leverx.googleDrive.clientRest.GoogleRestClient;
import by.leverx.googleDrive.exception.SomethingWentWrongException;
import by.leverx.googleDrive.service.GoogleService;
import by.leverx.googleDrive.service.manager.GoogleDriveManager;
import by.leverx.googleDrive.service.manager.ServiceDriveManger;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import java.io.IOException;
import java.net.URISyntaxException;
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
public class GoogleServiceImpl  implements GoogleService {

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
  public void checkCurrentMonthFolder() throws Exception {
    String currentMonthFolderName = creatCurrentMonthFolderName();
    String parentFolderId = searchFolderByFolderName("Assessments");
    if (nonNull(parentFolderId)) {
      String folderId = searchFolderByFolderNameAndParentId(currentMonthFolderName, parentFolderId);
      if (isBlank(folderId)) {
        createFolderByNameAndParentId(currentMonthFolderName, parentFolderId);
      }
    } else {
      System.err.println("Folder Assessments not found");
    }
  }

  @Override
  public void scheduleCreationNextMonthFolder(String folderName) throws Exception {
    String parentFolderId = searchFolderByFolderName("Assessments");
    if (nonNull(parentFolderId)) {
      String folderId = searchFolderByFolderNameAndParentId(folderName, parentFolderId);
      if (isNull(folderId)) {
        createFolderByNameAndParentId(folderName, parentFolderId);
      }
    } else {
      System.err.println("Folder 'Assessments' not found");
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
    String mainUrl = createAssessmentsFolderFilesUrl(token);
    HttpHeaders header = createHeader(token);
    HttpEntity httpEntity = new HttpEntity<>(header);
    String response = restClient.performRequest(mainUrl, httpEntity);
    return response;
  }

  private String createAssessmentsFolderFilesUrl(String token) {
    String folderName = "Assessments";
    String folderId = searchFolderByName(folderName, token, "folder");
    String mainUrl = BASE_URL + "drive/v3/files?q='" + folderId + "'+in+parents&fields=files(id,name)";
    return mainUrl;
  }

  @Override
  public List<String> getAllFolders(String token) {
    HttpHeaders header = createHeader(token);
    String mimeType = getMimeType("folder");
    String query = "mimeType='" + mimeType + "'";
    String url =  BASE_URL + "drive/v3/files/?q=" + query;
    HttpEntity<FileList> httpEntity = new HttpEntity(header);
    List<String> allFolder = restClient.getAllFolder(url, httpEntity);
    return allFolder;
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
  public List<String> clientUploadDocksToFolder(String folderId, String token, String fileExtension)
      throws URISyntaxException {
    List<String> idList = new ArrayList<>();
    List<java.io.File> listOfFiles = getListOfFiles(FOLDER_NAME, fileExtension);
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
  public List<String> uploadDoc(String folderId, String fileExtension)
      throws GeneralSecurityException, IOException, URISyntaxException {
    List<java.io.File> fileList = getListOfFiles(FOLDER_NAME, fileExtension);
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
  public List<java.io.File> getListOfFiles(String folderName,String fileExtension) throws URISyntaxException {
    var folder = new java.io.File(getFolderPath(folderName));
    java.io.File[] listOfFiles = folder.listFiles();
    List<java.io.File> returnList = new ArrayList<>();
    for (java.io.File file : listOfFiles) {
      if (file.isFile() && file.getName().endsWith(fileExtension)) {
        returnList.add(file);
      }
    }
    return returnList;
  }

  @Override
  public String createFolderAndUploadFile(String folderName, String fileExtension)
      throws Exception {
    String folderId = createFolderByName(folderName);
    List<String> fileIdList = uploadDoc(folderId, fileExtension);
    if (nonNull(folderId) && !fileIdList.isEmpty()) {
      return folderId;
    }
    throw new SomethingWentWrongException(UNABLE_TO_CRETE_UPLOAD);
  }

  @Override
  public void deleteFoldrById(String folderId) throws GeneralSecurityException, IOException {
    driveManager.getService().files().delete(folderId).execute();
  }
}
