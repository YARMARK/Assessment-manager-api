package by.leverx.googleTest.facade;

import by.leverx.googleTest.exception.FolderNotFoundException;
import by.leverx.googleTest.exception.SomethingWentWrongException;
import by.leverx.googleTest.exception.SuchFolderAlreadyExist;
import by.leverx.googleTest.exception.SuchEmployeeAlreadyExist;
import by.leverx.googleTest.repository.EmployeeInfoRepository;
import by.leverx.googleTest.service.GoogleService;
import by.leverx.googleTest.service.EmployeeInfoServiceImpl;
import by.leverx.googleTest.employee.EmployeeInfo;
import by.leverx.googleTest.employee.dto.EmployeeInfoCreationDto;
import by.leverx.googleTest.employee.dto.EmployeeInfoDto;
import by.leverx.googleTest.util.GoogleUtil;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GoogleFacade {

  private GoogleService service;

  private GoogleUtil util;

  private EmployeeInfoServiceImpl employeeService;

  private EmployeeInfoRepository repository;

  @Autowired
  public GoogleFacade(GoogleService service, GoogleUtil util, EmployeeInfoRepository repository,
      EmployeeInfoServiceImpl employeeService) {

    this.service = service;
    this.util = util;
    this.repository = repository;
    this.employeeService = employeeService;
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
