package by.leverx.googleDrive.clientRest;

import static org.springframework.http.HttpMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class JiraRestClient {

  @Value("${jira.env.url}")
  private String URL;
  private RestTemplate jiraRestTemplate;

  @Autowired
  public JiraRestClient(RestTemplate jiraRestTemplate) {
    this.jiraRestTemplate = jiraRestTemplate;
  }

  public String performRequest() {
    ResponseEntity<String> exchange = jiraRestTemplate.exchange(URL, GET, HttpEntity.EMPTY,
        String.class);
    return exchange.getBody();
  }
}
