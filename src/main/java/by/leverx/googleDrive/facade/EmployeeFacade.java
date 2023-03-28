package by.leverx.googleDrive.facade;

import static java.util.Objects.isNull;

import by.leverx.googleDrive.dto.EmployeeInfoCreationDto;
import by.leverx.googleDrive.dto.EmployeeInfoDto;
import by.leverx.googleDrive.service.EmployeeInfoService;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeFacade {

  private EmployeeInfoService employeeInfoService;

  private GoogleFacade googleFacade;


  @Autowired
  public EmployeeFacade(EmployeeInfoService employeeInfoService, GoogleFacade googleFacade) {
    this.employeeInfoService = employeeInfoService;
    this.googleFacade = googleFacade;
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

  public Map<String, Object> getAllEmployeesPage(int page, int size) {
    return employeeInfoService.getAllEmployeesPage(page,size);
  }

  public EmployeeInfoDto saveEmployeeInfo(EmployeeInfoCreationDto creationDto) {
    return employeeInfoService.saveEmployeeByInfoAndFolderId(creationDto);
  }

  public EmployeeInfoDto changeAssessmentFlagAndAddFolderUrl(Long id, String token)
      throws URISyntaxException, IOException {
    String folderName = employeeInfoService.checkIsEmployeeExistAndGetFolderName(id);
    String folderId = googleFacade.searchFolderByName(folderName, token);
    if (isNull(folderId)) {
      folderId = googleFacade.createFolderByName(folderName, token);
    }
    googleFacade.uploadDocks(folderId, token);
    return employeeInfoService.changeAssessmentFlagAndAddFolderUrl(id, folderId);
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
