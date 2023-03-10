package by.leverx.googleDrive.clientRest;

import static by.leverx.googleDrive.util.GoogleUtil.getListOfFiles;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.OK;

import by.leverx.googleDrive.exception.NoAuthOrNoPermissionException;
import by.leverx.googleDrive.exception.SomethingWentWrongException;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.function.ServerRequest.Headers;

@Component
public class GoogleRestClient {

  @Value("${drive.env.base-url}")
  private String BASE_URL;

  @Value("${templates.env.folder}")
  private String FOLDER_NAME;

  private RestTemplate googleRestTemplate;

  private static final String ERROR_MESSAGE = "Token doesn't valid or permissions don't exist";

  private static final String ERROR = "Unable to create folder or upload file";


  @Autowired
  public GoogleRestClient(RestTemplate googleRestTemplate) {
    this.googleRestTemplate = googleRestTemplate;
  }

  private HttpHeaders createHeader(String token) {
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(token);
    headers.setContentType(MediaType.APPLICATION_JSON);
    return headers;
  }

  private HttpHeaders createUploadHeader(String token, String name) {
    HttpHeaders headers = new HttpHeaders();
//    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    headers.setBearerAuth(token);
    headers.set("Content-Disposition", "attachment; filename=\"" + name + "\"");
//    headers.set("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    return headers;
  }

  public String performRequest(String token) {
    String mainUrl = BASE_URL + "drive/v3/files";
    HttpEntity httpEntity = new HttpEntity<>(createHeader(token));
    ResponseEntity<String> exchange = googleRestTemplate.exchange(mainUrl, GET, httpEntity,
        String.class);
    if (exchange.getStatusCode() == OK) {
      return exchange.getBody();
    } else {
      throw new NoAuthOrNoPermissionException(ERROR_MESSAGE);
    }
  }

  public String createFolder(String folderName, String token) {
    File folderMetadata = createFolderMetadata(folderName);
    HttpEntity<File> httpEntity = new HttpEntity<>(folderMetadata, createHeader(token));
    String url = BASE_URL + "drive/v3/files";
    ResponseEntity<File> exchange = googleRestTemplate.exchange(url, POST, httpEntity,
        File.class);
    String folderId = exchange.getBody().getId();
    return folderId;
  }

  public String searchFolderByName(String name, String token) {
    HttpHeaders headers = createHeader(token);
    HttpEntity<FileList> httpEntity = new HttpEntity<>(headers);
    String query = "name='" + name + "' and mimeType='application/vnd.google-apps.folder'";
    String fields = "files(id, name)";
    String url = BASE_URL + "drive/v3/files/?q=" + query + "&fields" + fields;
    ResponseEntity<Map> exchange = googleRestTemplate.exchange(url, GET, httpEntity,
        Map.class);
    if (exchange.getStatusCode() == OK) {
      Map<String, Object> responseBody = exchange.getBody();
      List<Map<String, String>> files = (List<Map<String, String>>) responseBody.get("files");
      if (files != null && files.size() > 0) {
        Object id = files.get(0).get("id");
        return id.toString();
      }
    }
    return null;
  }

  public List<String> uploadDocksToFolder(String folderId, String token)
      throws URISyntaxException {
    List<java.io.File> listOfFiles = getListOfFiles(FOLDER_NAME);
    List<String> filesIdList = new ArrayList<>();
    File metadata = createMetadata(folderId, "application/vnd.google-apps.spreadsheet");
    java.io.File filePath;
    for (var file : listOfFiles) {
      metadata.setName(file.getName());
      filePath = new java.io.File(file.getAbsolutePath());
      FileSystemResource resource = new FileSystemResource(filePath);
      HttpHeaders uploadHeader = createUploadHeader(token, filePath.getName());
      MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
      body.add("metadata", metadata);
      body.add("file", resource);
      HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, uploadHeader);
      ResponseEntity<File> responseEntity = googleRestTemplate.exchange(
          BASE_URL + "upload/drive/v3/files?uploadType=multipart", POST, requestEntity, File.class);
      if (responseEntity.getStatusCode() == OK) {
        if (nonNull(responseEntity.getBody())) {
          filesIdList.add(responseEntity.getBody().getId());
        }
      }
    }
    return filesIdList;
  }

  private File createFolderMetadata(String folderName) {
    File metadata = new File();
    metadata.setName(folderName)
        .setMimeType("application/vnd.google-apps.folder");
    return metadata;
  }

  private File createMetadata(String folderId, String mimeType) {
    File metadata = new File();
    metadata.setParents(Collections.singletonList(folderId)).setMimeType(mimeType);
    return metadata;
  }

}
