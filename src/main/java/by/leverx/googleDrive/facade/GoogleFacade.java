package by.leverx.googleDrive.facade;

import by.leverx.googleDrive.clientRest.GoogleRestClient;
import by.leverx.googleDrive.service.EmployeeInfoService;
import by.leverx.googleDrive.service.GoogleService;
import com.google.api.services.drive.model.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GoogleFacade {

  private GoogleService service;

  private EmployeeInfoService employeeService;

  private GoogleRestClient restClient;

  @Autowired
  public GoogleFacade(GoogleService service, EmployeeInfoService employeeService,
      GoogleRestClient restClient) {
    this.service = service;
    this.employeeService = employeeService;
    this.restClient = restClient;
  }

  public List<File> getAllFolders() throws GeneralSecurityException, IOException {
    return service.getPaginationFolderList();
  }

  public String getFiles(String token) {
    String result = restClient.performRequest(token);
    return result;
  }

  public String createFolderByName(String folderName, String toke){
    return restClient.createFolder(folderName, toke);
  }

  public String searchFolderByName(String folderName, String token){
    return restClient.searchFolderByName(folderName, token);
  }

  public List<String> uploadDocks(String folderId, String token)
      throws URISyntaxException, IOException {
    return restClient.uploadDocksToFolder(folderId, token);
  }
//  public String processEmployeeInfo(EmployeeInfoCreationDto dto) throws Exception {
//    String folderName = dto.getFirstName() + "_" + dto.getLastName();
//    String folderId = createFolderByFolderName(folderName);
//    EmployeeInfoDto savedUser = saveEmployeeInfo(dto, folderId);
//    if (Objects.nonNull(folderId) && Objects.nonNull(savedUser)) {
//      return folderId;
//    } else {
//      throw new SomethingWentWrongException("SOMETHING WENT WRONG");
//    }
//  }

//  private String createFolderByFolderName(String folderName)
//      throws Exception {
//    if (util.searchFolderByFolderName(folderName) == null) {
//      return service.createFolderAndUploadFile(folderName);
//    } else {
//      throw new SuchFolderAlreadyExist("THIS: '" + folderName + "' ALREADY IN USE.");
//    }
//  }

//  private EmployeeInfoDto saveEmployeeInfo(EmployeeInfoCreationDto creationDto, String folderId) {
//    EmployeeInfo byFirstNameAndLastName = repository.findByFirstNameAndLastName(
//        creationDto.getFirstName(), creationDto.getLastName());
//    if (Objects.isNull(byFirstNameAndLastName)) {
//      return employeeService.saveEmployeeByInfoAndFolderId(creationDto, folderId);
//    }
//    throw new SuchEmployeeAlreadyExist("EMPLOYEE ALREADY CREATED");
//  }

//  public void deleteFolderAndEmployee(String firstName, String lastName) throws Exception {
//    String folderName = firstName + "_" + lastName;
//    String folderId = util.searchFolderByFolderName(folderName);
//    if(Objects.nonNull(folderId)){
//      employeeService.deleteEmployee(firstName, lastName);
//    }
//    else throw new FolderNotFoundException("FOLDER NOT FOUND");
//  }
//   public EmployeeInfoDto getEmployeeById(Long id){
//    return employeeService.getEmployee(id);
//   }
}
