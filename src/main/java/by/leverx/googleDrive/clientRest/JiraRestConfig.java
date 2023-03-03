package by.leverx.googleDrive.clientRest;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestTemplate;

@Configuration
public class JiraRestConfig {

  @Value("${jira.env.user-name}")
  private String JIRA_USERNAME;

  @Value("${jira.env.password}")
  private String JIRA_PASSWORD;

  @Bean
  public RestTemplate jiraRestTemplate() {
    RestTemplate restTemplate = new RestTemplate();
    ClientHttpRequestInterceptor interceptor = new BasicAuthenticationInterceptor(JIRA_USERNAME,
        JIRA_PASSWORD);
    List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
    interceptors.add(interceptor);
    restTemplate.setInterceptors(interceptors);
    return restTemplate;
  }
}

