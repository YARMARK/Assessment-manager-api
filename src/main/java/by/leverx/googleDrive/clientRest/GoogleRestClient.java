package by.leverx.googleDrive.clientRest;

import static org.springframework.http.HttpMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GoogleRestClient {

  @Value("${drive.env.base-url}")
  private String BASE_URL;

  private RestTemplate googleRestTemplate;

  @Autowired
  public GoogleRestClient(RestTemplate googleRestTemplate) {
    this.googleRestTemplate = googleRestTemplate;
  }

  private HttpEntity createHttpEntity(String token) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + token);
    return new HttpEntity(headers);
  }

  public String performRequest(String token) {
    String mainUrl = BASE_URL + "/files";
    ResponseEntity<String> exchange = googleRestTemplate.exchange(mainUrl, GET,
        createHttpEntity(token), String.class);
    return exchange.getBody();
  }
}
