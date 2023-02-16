package by.leverx.googleTest.facade;

import by.leverx.googleTest.employee.EmployeeInfo;
import by.leverx.googleTest.employee.dto.EmployeeInfoCreationDto;
import by.leverx.googleTest.employee.dto.EmployeeInfoDto;
import by.leverx.googleTest.exception.SuchEmployeeAlreadyExist;
import by.leverx.googleTest.repository.EmployeeInfoRepository;
import by.leverx.googleTest.service.EmployeeInfoServiceImpl;
import by.leverx.googleTest.util.EmployeeUtil;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeFacade {

  private EmployeeInfoServiceImpl employeeInfoService;

  private EmployeeInfoRepository repository;

  private EmployeeUtil util;

  @Autowired
  public EmployeeFacade(EmployeeInfoServiceImpl employeeInfoService,
      EmployeeInfoRepository repository, EmployeeUtil util) {
    this.employeeInfoService = employeeInfoService;
    this.repository = repository;
    this.util = util;
  }

  public EmployeeInfoDto getEmployeeById(Long id) {
    return employeeInfoService.getEmployeeById(id);
  }

  public List<EmployeeInfoDto> getAllEmployees() {
    return employeeInfoService.getAllEmployees();
  }

  public EmployeeInfoDto saveEmployeeInfo(EmployeeInfoCreationDto creationDto) {
    if (!util.checkValidEmployee(creationDto)) {
      return employeeInfoService.saveEmployeeByInfoAndFolderId(creationDto);
    }
    throw new SuchEmployeeAlreadyExist("EMPLOYEE ALREADY CREATED");
  }

  public List<EmployeeInfoDto> saveEmployeeList(List<EmployeeInfoCreationDto> inputList) {
    List<EmployeeInfoCreationDto> savedEmployees = new ArrayList<>();
    for (EmployeeInfoCreationDto info : inputList) {
      if (!util.checkValidEmployee(info)) {
        savedEmployees.add(info);
      }
    }
    return employeeInfoService.saveAllEmployeesInfo(savedEmployees);
  }

  public void deleteEmployeeByFirstAndLastName(String firstName, String lastName) {
    employeeInfoService.deleteEmployeeByFirstAndLastName(firstName, lastName);
  }

  public void deleteEmployeeById(Long id) {
    employeeInfoService.deleteEmployeeById(id);
  }

  public void deleteAllEmployees(){
    employeeInfoService.deleteAllEmployees();
  }
}
