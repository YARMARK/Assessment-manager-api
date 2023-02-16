package by.leverx.googleTest.controller;

import by.leverx.googleTest.employee.dto.EmployeeInfoCreationDto;
import by.leverx.googleTest.employee.dto.EmployeeInfoDto;
import by.leverx.googleTest.facade.EmployeeFacade;
import by.leverx.googleTest.service.GoogleService;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class EmployeeController {

  private EmployeeFacade facade;

  private GoogleService service;

  @Autowired
  public EmployeeController(EmployeeFacade facade, GoogleService service) {
    this.facade = facade;
    this.service = service;
  }

  @GetMapping("/employees/{id}")
  public ResponseEntity<EmployeeInfoDto> getEmployee(@PathVariable("id") Long id) {
    EmployeeInfoDto employeeById = facade.getEmployeeById(id);
    return Objects.nonNull(employeeById) ? ResponseEntity.ok().body(employeeById)
        : ResponseEntity.notFound().build();
  }

  @GetMapping("/employees")
  public ResponseEntity<List<EmployeeInfoDto>> getEmployees() {
    List<EmployeeInfoDto> allEmployees = facade.getAllEmployees();
    return !allEmployees.isEmpty() ? ResponseEntity.ok().body(allEmployees)
        : ResponseEntity.notFound().build();
  }

  @GetMapping("/templateFiles")
  public ResponseEntity<List<String>> getFileList() throws IOException, URISyntaxException {
    List<String> listOfFileNames = service.getListOfFileNames();
    return !listOfFileNames.isEmpty() ? ResponseEntity.ok().body(listOfFileNames)
        : ResponseEntity.notFound().build();
  }

  @PostMapping("/employees")
  public ResponseEntity<EmployeeInfoDto> saveEmployee(@RequestBody EmployeeInfoCreationDto dto) {
    EmployeeInfoDto employeeById = facade.saveEmployeeInfo(dto);
    return Objects.nonNull(employeeById) ? ResponseEntity.ok().body(employeeById)
        : ResponseEntity.notFound().build();
  }

  @DeleteMapping("/employees/{firstName}/{lastName}")
  public ResponseEntity<Void> deleteEmployeeByFirstAndLastName(@PathVariable String firstName,
      @PathVariable String lastName) {
    facade.deleteEmployeeByFirstAndLastName(firstName,lastName);
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/employees/{id}")
  public ResponseEntity<Void> deleteEmployeeById(@PathVariable Long id) {
    facade.deleteEmployeeById(id);
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/employees")
  public ResponseEntity<Void> deleteAllEmployees() {
    facade.deleteAllEmployees();
    return ResponseEntity.notFound().build();
  }

}
