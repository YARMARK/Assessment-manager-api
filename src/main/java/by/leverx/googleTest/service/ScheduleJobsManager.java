package by.leverx.googleTest.service;

import by.leverx.googleTest.employee.dto.EmployeeInfoCreationDto;
import by.leverx.googleTest.facade.EmployeeFacade;
import by.leverx.googleTest.util.EmployeeMappingUtil;
import by.leverx.googleTest.util.EmployeeUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduleJobsManager {

  private JiraService jiraService;

  private EmployeeUtil util;

  private EmployeeMappingUtil mappingUtil;

  private EmployeeInfoService employeeService;

  private EmployeeFacade facade;

  private GoogleService googleService;

  @Autowired
  public ScheduleJobsManager(JiraService jiraService, EmployeeUtil util,
      EmployeeMappingUtil mappingUtil, EmployeeInfoService employeeService, EmployeeFacade facade,
      GoogleService googleService) {
    this.jiraService = jiraService;
    this.util = util;
    this.mappingUtil = mappingUtil;
    this.employeeService = employeeService;
    this.facade = facade;
    this.googleService = googleService;
  }

  @Scheduled(cron = "1 * * * * *")
  public void updateUserList() throws JsonProcessingException {
    String users = jiraService.getUsers();
    List<EmployeeInfoCreationDto> employeeInfoCreationDto = mappingUtil.mapToInfo(users);
    facade.saveEmployeeList(employeeInfoCreationDto);
  }
}
