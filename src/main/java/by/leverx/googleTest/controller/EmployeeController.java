package by.leverx.googleTest.controller;

import by.leverx.googleTest.employee.EmployeeInfo;
import by.leverx.googleTest.employee.dto.EmployeeInfoCreationDto;
import by.leverx.googleTest.employee.dto.EmployeeInfoDto;
import by.leverx.googleTest.facade.EmployeeFacade;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class EmployeeController {

  private EmployeeFacade facade;

  @Autowired
  public EmployeeController(EmployeeFacade facade) {
    this.facade = facade;
  }

  @GetMapping("/employees/{id}")
  public ResponseEntity<EmployeeInfoDto> getUser(@PathVariable("id") Long id) {
    EmployeeInfoDto employeeById = facade.getEmployeeById(id);
    return Objects.nonNull(employeeById) ? ResponseEntity.ok().body(employeeById)
        : ResponseEntity.notFound().build();
  }

    @GetMapping("/employees")
  public ResponseEntity<List<EmployeeInfoDto>> getUser() {
      List<EmployeeInfoDto> allEmployees = facade.getAllEmployees();
    return !allEmployees.isEmpty()? ResponseEntity.ok().body(allEmployees)
        : ResponseEntity.notFound().build();
  }
  @PostMapping("/employees")
  public ResponseEntity<EmployeeInfoDto> saveUser(@RequestBody EmployeeInfoCreationDto dto) {
    EmployeeInfoDto employeeById = facade.saveEmployeeInfo(dto);
    return Objects.nonNull(employeeById) ? ResponseEntity.ok().body(employeeById)
        : ResponseEntity.notFound().build();
  }
}
