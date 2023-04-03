package by.leverx.googleDrive.controller;

import static by.leverx.googleDrive.util.ConstantMessage.SwaggerConstant.SWAGGER_EMPLOYEE_TAG;
import static java.util.Objects.nonNull;

import by.leverx.googleDrive.dto.EmployeeInfoCreationDto;
import by.leverx.googleDrive.dto.EmployeeInfoDto;
import by.leverx.googleDrive.facade.EmployeeFacade;
import by.leverx.googleDrive.service.serviceImpl.GoogleServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/employees")
@Api(tags = {SWAGGER_EMPLOYEE_TAG})
public class EmployeeController {

  private EmployeeFacade facade;

  private GoogleServiceImpl service;

  @Autowired
  public EmployeeController(EmployeeFacade facade, GoogleServiceImpl service) {
    this.facade = facade;
    this.service = service;
  }

  @GetMapping("/{id}")
  @ApiOperation("returns employee by id.")
  public ResponseEntity<EmployeeInfoDto> getEmployee(
      @ApiParam("Employee id.") @PathVariable("id") Long id) {
    EmployeeInfoDto employeeById = facade.getEmployeeById(id);
    return nonNull(employeeById) ?
        ResponseEntity.ok().body(employeeById) :
        ResponseEntity.notFound().build();
  }

  @GetMapping("/")
  @ApiOperation("returns list of all employees.")
  public ResponseEntity<List<EmployeeInfoDto>> getEmployees() {
    List<EmployeeInfoDto> allEmployees = facade.getAllEmployees();
    return !allEmployees.isEmpty() ?
        ResponseEntity.ok().body(allEmployees) :
        ResponseEntity.notFound().build();
  }

  @GetMapping("/assessment")
  @ApiOperation("returns list of all employees.")
  public ResponseEntity<List<EmployeeInfoDto>> getNeedAssessmentEmployees() {
    List<EmployeeInfoDto> allEmployees = facade.geTAssessmentEmployees();
    return !allEmployees.isEmpty() ?
        ResponseEntity.ok().body(allEmployees) :
        ResponseEntity.notFound().build();
  }

  @GetMapping("/page")
  @ApiOperation("returns pagination list of all employees.")
  public ResponseEntity<Map<String, Object>> getEmployeesPage(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "5") int size)
      {
    Map<String, Object> allEmployees = facade.getAllEmployeesPage(page,size);
    return nonNull(allEmployees) ?
        ResponseEntity.ok().body(allEmployees) :
        ResponseEntity.notFound().build();
  }

  @PostMapping("/")
  @ApiOperation("creates new employee.")
  public ResponseEntity<EmployeeInfoDto> saveEmployee(
      @ApiParam("Employee info for creation.") @RequestBody EmployeeInfoCreationDto dto) {
    EmployeeInfoDto employeeById = facade.saveEmployeeInfo(dto);
    return nonNull(employeeById) ?
        ResponseEntity.ok().body(employeeById) :
        ResponseEntity.notFound().build();
  }

  @PatchMapping("/{id}")
  @ApiOperation("change assessment need flag and add folder url.")
  public ResponseEntity<EmployeeInfoDto> changeAssessmentFlagAndAddFolderUrl(
      @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
      @ApiParam("Employee id.") @PathVariable("id") Long id)
      throws URISyntaxException, IOException {
    EmployeeInfoDto employeeInfoDto = facade.changeAssessmentFlagAndAddFolderUrl(id, token);
    return nonNull(employeeInfoDto) ?
        ResponseEntity.ok().body(employeeInfoDto) :
        ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{firstName}/{lastName}")
  @ApiOperation("deletes employee by first name and last name.")
  public ResponseEntity<Void> deleteEmployeeByFirstAndLastName(
      @ApiParam("Employee first name,it can't be blank.") @PathVariable String firstName,
      @ApiParam("Employee last name,it can't be blank.") @PathVariable String lastName) {
    facade.deleteEmployeeByFirstAndLastName(firstName, lastName);
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  @ApiOperation("deletes employee by id.")
  public ResponseEntity<Void> deleteEmployeeById(
      @ApiParam("Employee id,it can't be blank.") @PathVariable Long id) {
    facade.deleteEmployeeById(id);
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/")
  @ApiOperation("deletes employee.")
  public ResponseEntity<Void> deleteAllEmployees() {
    facade.deleteAllEmployees();
    return ResponseEntity.notFound().build();
  }

}
