package by.leverx.googleTest.util;

import by.leverx.googleTest.employee.EmployeeInfo;
import by.leverx.googleTest.employee.dto.EmployeeInfoCreationDto;
import by.leverx.googleTest.employee.dto.EmployeeInfoDto;
import by.leverx.googleTest.repository.EmployeeInfoRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeUtil {

  private EmployeeInfoRepository repository;

  @Autowired
  public EmployeeUtil(EmployeeInfoRepository repository) {
    this.repository = repository;
  }

  public EmployeeInfo setIncomeAndAssessmentDates(EmployeeInfo employeeInfo) {
    LocalDate dateOfIncoming = LocalDate.now();
    employeeInfo.setDateOfIncoming(dateOfIncoming);
    employeeInfo.setPreviousAssessmentDate(dateOfIncoming);
    return employeeInfo;
  }

  public EmployeeInfo setFolderUrl(EmployeeInfo info, String folderId) {
    String urlTemplates = "https://drive.google.com/drive/folders/" + folderId + "?usp=share_link";
    info.setLastAssessmentFolder(urlTemplates);
    return info;
  }

  public Boolean checkValidEmployee(EmployeeInfoCreationDto creationDto) {
    EmployeeInfo byFirstNameAndLastName = repository.findByFirstNameAndLastName(
        creationDto.getFirstName(), creationDto.getLastName());
    if (Objects.isNull(byFirstNameAndLastName)) {
      return false;
    } else {
      return true;
    }
  }

  public List<EmployeeInfoDto> checkAssessmentDate(List<EmployeeInfoDto> employeeInfoDtoList) {
    LocalDate assessmentDeadline = LocalDate.now().minusYears(1);
    List<EmployeeInfoDto> returnList = new ArrayList<>();
    for (EmployeeInfoDto employeeInfo: employeeInfoDtoList) {
      if (employeeInfo.getPreviousAssessmentDate().isEqual(assessmentDeadline)) {
        employeeInfo.setNeedAssessment(true);
        returnList.add(employeeInfo);
      }
    }
    return returnList;
  }

  public List<EmployeeInfo> updateAssessmentMarker(List<EmployeeInfoDto> employeesList) {
    List<EmployeeInfo> returnList = new ArrayList<>();
    for (EmployeeInfoDto infoDto : employeesList) {
      EmployeeInfo employeeInfo = repository.findByFirstNameAndLastName(
          infoDto.getFirstName(), infoDto.getLastName());
      employeeInfo.setNeedAssessment(true);
      returnList.add(employeeInfo);
    }
    return returnList;
  }
}
