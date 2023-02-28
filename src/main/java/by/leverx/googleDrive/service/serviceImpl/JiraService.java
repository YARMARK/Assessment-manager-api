package by.leverx.googleDrive.service.serviceImpl;

import by.leverx.googleDrive.clientRest.JiraRestClient;
import org.springframework.stereotype.Service;

@Service
public class JiraService {

  private JiraRestClient jiraRestClient;

  public JiraService(JiraRestClient jiraRestClient) {
    this.jiraRestClient = jiraRestClient;
  }

  public String getUsersAsString() {
    String response = jiraRestClient.performRequest();
    return response;
  }

}
