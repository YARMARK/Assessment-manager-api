package by.leverx.googleTest.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class JiraClient {

  private RestTemplate template;

  @Value("${jira.env.url}")
  private String URL;

  @Value("${jira.env.user-name}")
  private String JIRA_USERNAME;

  @Value("${jira.env.password}")
  private String JIRA_PASSWORD;

  @Autowired
  public JiraClient(RestTemplate template) {
    this.template = template;
  }

  private HttpEntity createHttpEntity (){
    HttpHeaders headers = new HttpHeaders();
    headers.setBasicAuth(JIRA_USERNAME,JIRA_PASSWORD);
    return new HttpEntity(headers);
  }

  public String performRequest (){
    ResponseEntity<String> exchange = template.exchange(URL, HttpMethod.GET, createHttpEntity(), String.class);
    return exchange.getBody();
  }
}
