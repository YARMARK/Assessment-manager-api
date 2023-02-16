package by.leverx.googleTest.service;

import by.leverx.googleTest.employee.dto.EmployeeInfoCreationDto;
import by.leverx.googleTest.employee.dto.EmployeeInfoDto;
import java.util.List;

public interface EmployeeInfoService {

  EmployeeInfoDto getEmployeeById(Long id);

  List<EmployeeInfoDto> getAllEmployees();

  EmployeeInfoDto saveEmployeeByInfoAndFolderId(EmployeeInfoCreationDto creationDto);

  List<EmployeeInfoDto> saveAllEmployeesInfo(List<EmployeeInfoCreationDto> employeeInfoListFromJira);

  void deleteEmployeeByFirstAndLastName(String firstName, String lastName);

  void deleteEmployeeById(Long id);

  void deleteAllEmployees();
}
