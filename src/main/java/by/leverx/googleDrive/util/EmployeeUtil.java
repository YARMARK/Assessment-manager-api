package by.leverx.googleDrive.util;

import by.leverx.googleDrive.dto.EmployeeInfoDto;
import by.leverx.googleDrive.employee.EmployeeInfo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EmployeeUtil {

  private static final String URL_PREFIX = "https://drive.google.com/drive/folders/";

  private static final String URL_POSTFIX = "?usp=share_link";


  public static EmployeeInfo setIncomeAndAssessmentDates(EmployeeInfo employeeInfo) {
    LocalDate dateOfIncoming = LocalDate.now();
    LocalDate nextAssessment = dateOfIncoming.plusYears(1);
    employeeInfo.setDateOfIncoming(dateOfIncoming);
    employeeInfo.setPreviousAssessmentDate(dateOfIncoming);
    employeeInfo.setNextAssessmentDate(nextAssessment);
    return employeeInfo;
  }

  public static EmployeeInfo setFolderUrl(EmployeeInfo info, String folderId) {
    String urlTemplates = URL_PREFIX + folderId + URL_POSTFIX;
    info.setLastAssessmentFolder(urlTemplates);
    return info;
  }

  public static EmployeeInfo changeAssessmentFlag(EmployeeInfo info){
    Boolean assessmentFlag = false;
    info.setNeedAssessment(assessmentFlag);
    return info;
  }

  public static List<EmployeeInfoDto> checkAssessmentDate(
      List<EmployeeInfoDto> employeeInfoDtoList) {
    LocalDate currentDate = LocalDate.now();
    List<EmployeeInfoDto> returnList = new ArrayList<>();
    for (EmployeeInfoDto employeeInfo : employeeInfoDtoList) {
      if (employeeInfo.getNextAssessmentDate().isEqual(currentDate)) {
        employeeInfo.setNeedAssessment(true);
        LocalDate nextAssessment = currentDate.plusYears(1);
        employeeInfo.setNextAssessmentDate(nextAssessment);
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
