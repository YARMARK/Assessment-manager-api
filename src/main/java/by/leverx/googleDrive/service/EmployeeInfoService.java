package by.leverx.googleDrive.service;

import by.leverx.googleDrive.dto.EmployeeInfoCreationDto;
import by.leverx.googleDrive.dto.EmployeeInfoDto;
import by.leverx.googleDrive.employee.EmployeeInfo;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Pageable;

public interface EmployeeInfoService {

  EmployeeInfoDto getEmployeeById(Long id);

  List<EmployeeInfoDto> getAllEmployees();

  Map<String, Object> getAllEmployeesPage(Pageable pageable);

  EmployeeInfoDto saveEmployeeByInfoAndFolderId(EmployeeInfoCreationDto creationDto);

  List<EmployeeInfoDto> saveAllEmployeesInfo(
      List<EmployeeInfoCreationDto> employeeInfoListFromJira);

  List<EmployeeInfoDto> updateAssessmentDate(List<EmployeeInfoDto> employeesList);

  List<EmployeeInfoDto> needAssessmentList();

  void deleteEmployeeByFirstAndLastName(String firstName, String lastName);

  void deleteEmployeeById(Long id);

  void deleteAllEmployees();

  public Boolean checkValidEmployee(EmployeeInfoCreationDto creationDto);

  public List<EmployeeInfo> updateAssessmentMarker(List<EmployeeInfoDto> employeesList);
}
