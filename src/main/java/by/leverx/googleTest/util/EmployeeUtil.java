package by.leverx.googleTest.util;

import by.leverx.googleTest.employee.EmployeeInfo;
import by.leverx.googleTest.employee.EmployeeInfoMapping;
import by.leverx.googleTest.employee.dto.EmployeeInfoCreationDto;
import by.leverx.googleTest.repository.EmployeeInfoRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.checkerframework.checker.units.qual.A;
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

  public List<EmployeeInfo> compareEmployees(List<EmployeeInfo> employeesList) {
    List<EmployeeInfo> employeeList = new ArrayList<>();
    for (EmployeeInfo info : employeesList) {
      EmployeeInfo infoFromDatabase = repository.findByFirstNameAndLastName(
          info.getFirstName(), info.getLastName());
      if (Objects.isNull(infoFromDatabase)) {
        employeeList.add(infoFromDatabase);
      }
    }
    return employeesList;
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
}
