package by.leverx.googleTest.facade;

import by.leverx.googleTest.employee.EmployeeInfo;
import by.leverx.googleTest.employee.dto.EmployeeInfoCreationDto;
import by.leverx.googleTest.employee.dto.EmployeeInfoDto;
import by.leverx.googleTest.exception.SuchEmployeeAlreadyExist;
import by.leverx.googleTest.repository.EmployeeInfoRepository;
import by.leverx.googleTest.service.EmployeeInfoServiceImpl;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeFacade {

  private EmployeeInfoServiceImpl employeeInfoService;

  private EmployeeInfoRepository repository;

  @Autowired
  public EmployeeFacade(EmployeeInfoServiceImpl employeeInfoService,
      EmployeeInfoRepository repository) {
    this.employeeInfoService = employeeInfoService;
    this.repository = repository;
  }

  public EmployeeInfoDto getEmployeeById(Long id){
    return employeeInfoService.getEmployeeById(id);
   }

   public List<EmployeeInfoDto> getAllEmployees(){
    return employeeInfoService.getAllEmployees();
   }

  public EmployeeInfoDto saveEmployeeInfo(EmployeeInfoCreationDto creationDto) {
    EmployeeInfo byFirstNameAndLastName = repository.findByFirstNameAndLastName(
        creationDto.getFirstName(), creationDto.getLastName());
    if (Objects.isNull(byFirstNameAndLastName)) {
      return employeeInfoService.saveEmployeeByInfoAndFolderId(creationDto);
    }
    throw new SuchEmployeeAlreadyExist("EMPLOYEE ALREADY CREATED");
  }
}
