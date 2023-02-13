package by.leverx.googleTest.service;

import by.leverx.googleTest.employee.EmployeeInfo;
import by.leverx.googleTest.employee.dto.EmployeeInfoCreationDto;
import by.leverx.googleTest.employee.dto.EmployeeInfoDto;
import java.util.List;

public interface EmployeeInfoService {

  EmployeeInfoDto getEmployeeById(Long id);

  List<EmployeeInfoDto> getAllEmployees();

  EmployeeInfoDto saveEmployee(EmployeeInfo employeeInfo);

  EmployeeInfoDto saveEmployeeByInfoAndFolderId(EmployeeInfoCreationDto creationDto);

  List<EmployeeInfoDto> saveAllEmployeesInfo(List<EmployeeInfo> employeeInfoListFromJira);

  void deleteEmployee(String firstName, String lastName);
}
