package by.leverx.googleTest.service;
import by.leverx.googleTest.util.EmployeeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduleJobsManager {

  private JiraService jiraService;

  private EmployeeUtil util;

  private EmployeeInfoService employeeService;


  private GoogleService googleService;

  @Autowired
  public ScheduleJobsManager(JiraService jiraService, EmployeeUtil util,
      EmployeeInfoServiceImpl service) {
    this.jiraService = jiraService;
    this.util = util;
    this.employeeService = service;
  }

  @Scheduled(cron = "0/5 * * * * *")
  public void updateUserList(){
    String users = jiraService.getUsers(); // TODO:  map to CreationDto
//    util.compareEmployees(); // This methode return list of unsaved users.// TODO: rewrite
  }
}
