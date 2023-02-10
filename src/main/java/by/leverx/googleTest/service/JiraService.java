package by.leverx.googleTest.service;

import by.leverx.googleTest.client.JiraClient;
import org.springframework.stereotype.Service;

@Service
public class JiraService {

  private JiraClient jiraClient;

  public JiraService(JiraClient jiraClient) {
    this.jiraClient = jiraClient;
  }

  public String getUsers() { // возвращает лист юзерДто
    String response = jiraClient.performRequest();
    return response; // маппер для маппинга стринги в лист юзеровДто
  }

}
