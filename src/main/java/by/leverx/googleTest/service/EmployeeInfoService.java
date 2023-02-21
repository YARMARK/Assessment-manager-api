package by.leverx.googleTest.service;

import by.leverx.googleTest.employee.dto.EmployeeInfoCreationDto;
import by.leverx.googleTest.employee.dto.EmployeeInfoDto;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Pageable;

public interface EmployeeInfoService {

  EmployeeInfoDto getEmployeeById(Long id);

  List<EmployeeInfoDto> getAllEmployees();

  Map<String, Object> getAllEmployeesPage(Pageable pageable);

  EmployeeInfoDto saveEmployeeByInfoAndFolderId(EmployeeInfoCreationDto creationDto);

  List<EmployeeInfoDto> saveAllEmployeesInfo(List<EmployeeInfoCreationDto> employeeInfoListFromJira);

  List<EmployeeInfoDto> updateAssessmentDate( List<EmployeeInfoDto> employeesList);

  void deleteEmployeeByFirstAndLastName(String firstName, String lastName);

  void deleteEmployeeById(Long id);

  void deleteAllEmployees();
}
