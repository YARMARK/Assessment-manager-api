package by.leverx.googleTest.facade;

import by.leverx.googleTest.repository.EmployeeInfoRepository;
import by.leverx.googleTest.service.GoogleServiceImpl;
import by.leverx.googleTest.service.EmployeeInfoServiceImpl;
import by.leverx.googleTest.util.GoogleUtil;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.model.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class GoogleFacade {

  private GoogleServiceImpl service;

  private GoogleUtil util;

  private EmployeeInfoServiceImpl employeeService;

  private EmployeeInfoRepository repository;

  @Autowired
  public GoogleFacade(GoogleServiceImpl service, GoogleUtil util, EmployeeInfoRepository repository,
      EmployeeInfoServiceImpl employeeService) {

    this.service = service;
    this.util = util;
    this.repository = repository;
    this.employeeService = employeeService;
  }

  public List<File> getAllFolders() throws GeneralSecurityException, IOException {
    return service.getPaginationFolderList();
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
