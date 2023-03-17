package by.leverx.googleDrive.service;

import static by.leverx.googleDrive.util.EmployeeUtil.checkAssessmentDate;
import static by.leverx.googleDrive.util.GoogleUtil.creatNextMonthFolderName;

import by.leverx.googleDrive.dto.EmployeeInfoCreationDto;
import by.leverx.googleDrive.dto.EmployeeInfoDto;
import by.leverx.googleDrive.dto.mapper.EmployeeInfoMapper;
import by.leverx.googleDrive.service.serviceImpl.JiraService;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduleJobsManager {

  private JiraService jiraService;

  private EmployeeInfoMapper mappingUtil;

  private EmployeeInfoService employeeService;

  private GoogleService googleService;

  @Autowired
  public ScheduleJobsManager(JiraService jiraService, EmployeeInfoMapper mappingUtil,
      EmployeeInfoService employeeService, GoogleService googleService) {
    this.jiraService = jiraService;
    this.mappingUtil = mappingUtil;
    this.employeeService = employeeService;
    this.googleService = googleService;
  }
  @Scheduled(cron = "0 0 18 * * * ")
  public void updateEmployeeList() throws JsonProcessingException {
    String users = jiraService.getUsersAsString();
    List<EmployeeInfoCreationDto> employeeInfoCreationDto = mappingUtil.mapToInfo(users);
    employeeService.saveAllEmployeesInfo(employeeInfoCreationDto);
  }

  @Scheduled(cron = "0 0 19 L * ?")
  public void createNextMonthFolder() throws Exception {
    String nextMonthFolderName = creatNextMonthFolderName();
    googleService.scheduleCreationNextMonthFolder(nextMonthFolderName);
//    String folderId = googleService.searchFolderByFolderName(nextMonthFolderName);
//    if (Objects.isNull(folderId)) {
//      googleService.createFolderByName(nextMonthFolderName);
//    }
  }

  @Scheduled(cron = "0 0 19 * * * ")
  public void checkAssessmentNeed() {
    List<EmployeeInfoDto> needAssessment = checkAssessmentDate(
        employeeService.getAllEmployees());
    if (!needAssessment.isEmpty()) {
      List<EmployeeInfoDto> updatedEmployees = employeeService.updateAssessmentDate(needAssessment);
    }
  }
}

