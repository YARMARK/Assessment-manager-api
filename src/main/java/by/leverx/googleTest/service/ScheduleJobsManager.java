package by.leverx.googleTest.service;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduleJobsManager {

  private JiraService jiraService;

//  private UserInfoServiceImpl service;

  public ScheduleJobsManager(JiraService jiraService) {
    this.jiraService = jiraService;
  }
  @Scheduled(cron = "1 * * * * *")
  public void updateUserList(){
    String users = jiraService.getUsers();
    System.out.println(users);
  }
}
