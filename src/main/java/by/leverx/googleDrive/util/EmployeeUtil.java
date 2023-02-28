package by.leverx.googleDrive.util;

import by.leverx.googleDrive.dto.EmployeeInfoDto;
import by.leverx.googleDrive.employee.EmployeeInfo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EmployeeUtil {

  public static EmployeeInfo setIncomeAndAssessmentDates(EmployeeInfo employeeInfo) {
    LocalDate dateOfIncoming = LocalDate.now();
    employeeInfo.setDateOfIncoming(dateOfIncoming);
    employeeInfo.setPreviousAssessmentDate(dateOfIncoming);
    return employeeInfo;
  }

  public static EmployeeInfo setFolderUrl(EmployeeInfo info, String folderId) {
    String urlTemplates = "https://drive.google.com/drive/folders/" + folderId + "?usp=share_link";
    info.setLastAssessmentFolder(urlTemplates);
    return info;
  }

  public static List<EmployeeInfoDto> checkAssessmentDate(
      List<EmployeeInfoDto> employeeInfoDtoList) {
    LocalDate assessmentDeadline = LocalDate.now().minusYears(1);
    List<EmployeeInfoDto> returnList = new ArrayList<>();
    for (EmployeeInfoDto employeeInfo : employeeInfoDtoList) {
      if (employeeInfo.getPreviousAssessmentDate().isEqual(assessmentDeadline)) {
        employeeInfo.setNeedAssessment(true);
        returnList.add(employeeInfo);
      }
    }
    return returnList;
  }

  public static List<EmployeeInfoDto> sortByAssessmentDate(List<EmployeeInfoDto> employeeList) {
    Collections.sort(employeeList,
        Comparator.comparing(EmployeeInfoDto::getPreviousAssessmentDate));
    return employeeList;
  }
}
