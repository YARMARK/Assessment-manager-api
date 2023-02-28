package by.leverx.googleDrive.facade;

import by.leverx.googleDrive.dto.EmployeeInfoCreationDto;
import by.leverx.googleDrive.dto.EmployeeInfoDto;
import by.leverx.googleDrive.service.EmployeeInfoService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class EmployeeFacade {

  private EmployeeInfoService employeeInfoService;


  @Autowired
  public EmployeeFacade(EmployeeInfoService employeeInfoService) {
    this.employeeInfoService = employeeInfoService;
  }

  public EmployeeInfoDto getEmployeeById(Long id) {
    return employeeInfoService.getEmployeeById(id);
  }

  public List<EmployeeInfoDto> getAllEmployees() {
    return employeeInfoService.getAllEmployees();
  }

  public List<EmployeeInfoDto> geTAssessmentEmployees() {
    return employeeInfoService.needAssessmentList();
  }

  public Map<String, Object> getAllEmployeesPage(Pageable pageable) {
    return employeeInfoService.getAllEmployeesPage(pageable);
  }

  public EmployeeInfoDto saveEmployeeInfo(EmployeeInfoCreationDto creationDto) {
    return employeeInfoService.saveEmployeeByInfoAndFolderId(creationDto);
  }

  public void deleteEmployeeByFirstAndLastName(String firstName, String lastName) {
    employeeInfoService.deleteEmployeeByFirstAndLastName(firstName, lastName);
  }

  public void deleteEmployeeById(Long id) {
    employeeInfoService.deleteEmployeeById(id);
  }

  public void deleteAllEmployees() {
    employeeInfoService.deleteAllEmployees();
  }
}
