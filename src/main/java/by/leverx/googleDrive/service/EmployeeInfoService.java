package by.leverx.googleDrive.service;

import by.leverx.googleDrive.dto.EmployeeInfoCreationDto;
import by.leverx.googleDrive.dto.EmployeeInfoDto;
import by.leverx.googleDrive.employee.EmployeeInfo;
import java.util.List;
import java.util.Map;

public interface EmployeeInfoService {

  EmployeeInfoDto getEmployeeById(Long id);

  List<EmployeeInfoDto> getAllEmployees();

  Map<String, Object> getAllEmployeesPage(int page, int size);

  EmployeeInfoDto saveEmployeeByInfoAndFolderId(EmployeeInfoCreationDto creationDto);

  List<EmployeeInfoDto> saveAllEmployeesInfo(
      List<EmployeeInfoCreationDto> employeeInfoListFromJira);

  List<EmployeeInfoDto> updateAssessmentDate(List<EmployeeInfoDto> employeesList);

  List<EmployeeInfoDto> needAssessmentList();

  EmployeeInfoDto changeAssessmentFlagAndAddFolderUrl(Long id, String folderId);

  void deleteEmployeeByFirstAndLastName(String firstName, String lastName);

  void deleteEmployeeById(Long id);

  void deleteAllEmployees();

  public Boolean checkIsEmployeeExistByName(EmployeeInfoCreationDto creationDto);

  public String checkIsEmployeeExistAndGetFolderName(Long id);

  public List<EmployeeInfo> updateAssessmentMarker(List<EmployeeInfoDto> employeesList);
}
