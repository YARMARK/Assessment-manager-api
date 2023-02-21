package by.leverx.googleTest.service;

import by.leverx.googleTest.employee.dto.EmployeeInfoCreationDto;
import by.leverx.googleTest.employee.dto.EmployeeInfoDto;
import by.leverx.googleTest.facade.EmployeeFacade;
import by.leverx.googleTest.util.EmployeeMappingUtil;
import by.leverx.googleTest.util.EmployeeUtil;
import by.leverx.googleTest.util.GoogleUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduleJobsManager {

  private JiraService jiraService;

  private EmployeeUtil employeeUtil;

  private EmployeeMappingUtil mappingUtil;

  private EmployeeInfoService employeeService;

  private EmployeeFacade facade;

  private GoogleServiceImpl googleServiceImpl;

  private GoogleUtil googleUtil;

  @Autowired
  public ScheduleJobsManager(JiraService jiraService, EmployeeUtil employeeUtil,
      EmployeeMappingUtil mappingUtil, EmployeeInfoService employeeService, EmployeeFacade facade,
      GoogleServiceImpl googleServiceImpl, GoogleUtil googleUtil) {
    this.jiraService = jiraService;
    this.employeeUtil = employeeUtil;
    this.mappingUtil = mappingUtil;
    this.employeeService = employeeService;
    this.facade = facade;
    this.googleServiceImpl = googleServiceImpl;
    this.googleUtil = googleUtil;
  }

  @Scheduled(cron = "0 0 18 * * * ")
  public void updateEmployeeList() throws JsonProcessingException {
    String users = jiraService.getUsers();
    List<EmployeeInfoCreationDto> employeeInfoCreationDto = mappingUtil.mapToInfo(users);
    facade.saveEmployeeList(employeeInfoCreationDto);
  }

  @Scheduled(cron = "0 0 19 L * ?")
  public void createNextMonthFolder() throws Exception {
    String nextMonthFolderName = googleUtil.creatNextMonthFolderName();
    String folderId = googleServiceImpl.searchFolderByFolderName(nextMonthFolderName);
    if (Objects.isNull(folderId)) {
      googleServiceImpl.createFolderByName(nextMonthFolderName);
    }
  }

  @Scheduled(cron = "0 0 19 * * * ")
  public void checkAssessmentNeed() {
    List<EmployeeInfoDto> needAssessment = employeeUtil.checkAssessmentDate(
        employeeService.getAllEmployees());
    if (!needAssessment.isEmpty()) {
      List<EmployeeInfoDto> updatedEmployees = employeeService.updateAssessmentDate(needAssessment);
    }
  }
}

