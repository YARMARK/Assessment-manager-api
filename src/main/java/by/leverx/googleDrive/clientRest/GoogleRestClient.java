package by.leverx.googleDrive.clientRest;

import static by.leverx.googleDrive.util.ConstantMessage.getUnableToCreteUploadMessage;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.OK;

import by.leverx.googleDrive.exception.SomethingWentWrongException;
import com.google.api.services.drive.model.File;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class GoogleRestClient {

  @Value("${drive.env.base-url}")
  private String BASE_URL;

  @Value("${templates.env.folder}")
  private String FOLDER_NAME;

  private RestTemplate googleRestTemplate;

  @Autowired
  public GoogleRestClient(RestTemplate googleRestTemplate) {
    this.googleRestTemplate = googleRestTemplate;
  }

  public String performRequest(String url, HttpEntity httpEntity) {
    ResponseEntity<String> exchange = googleRestTemplate.exchange(url, GET, httpEntity,
        String.class);
    if (exchange.getStatusCode() == OK) {
      return exchange.getBody();
    } else {
      throw new SomethingWentWrongException(exchange.getBody(), exchange.getStatusCode());
    }
  }

  public String createFolder(String url, HttpEntity httpEntity) {
    ResponseEntity<File> exchange = googleRestTemplate.exchange(url, POST, httpEntity, File.class);
    if (exchange.getStatusCode() == OK) {
      String folderId = exchange.getBody().getId();
      return folderId;
    } else {
      throw new SomethingWentWrongException(exchange.getBody().toString(),
          exchange.getStatusCode());
    }
  }

  public String searchFolderByName(String url, HttpEntity httpEntity) {
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

  public String uploadDocksToFolder(String url,
      HttpEntity<MultiValueMap<String, Object>> requestEntity) {
    ResponseEntity<File> responseEntity = googleRestTemplate.exchange(url, POST, requestEntity,
        File.class);
    if (responseEntity.getStatusCode() == OK) {
      if (nonNull(responseEntity.getBody())) {
        return responseEntity.getBody().getId();
      } else {
        throw new SomethingWentWrongException(getUnableToCreteUploadMessage());
      }
    } else {
      throw new SomethingWentWrongException(responseEntity.getBody().toString(),
          responseEntity.getStatusCode());
    }
  }
}
