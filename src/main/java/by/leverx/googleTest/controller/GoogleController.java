package by.leverx.googleTest.controller;

import by.leverx.googleTest.facade.GoogleFacade;
import com.google.api.services.drive.model.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class GoogleController {

  private GoogleFacade facade;

  @Autowired
  public GoogleController(GoogleFacade facade) {
    this.facade = facade;
  }

//  @GetMapping("/user/{id}")
//  public ResponseEntity<EmployeeInfoDto> getUser(@PathVariable("id") Long id) {
//    EmployeeInfoDto userById = facade.getEmployeeById(id);
//    return Objects.nonNull(userById) ? ResponseEntity.ok().body(userById)
//        : ResponseEntity.notFound().build();
//  }

//  @PostMapping("/creatFolder")
//  public ResponseEntity<String> createFolderByFirstNameAndLastName(
//      @Valid @RequestBody EmployeeInfoCreationDto infoDto) throws Exception {
//    String folderByName = facade.processEmployeeInfo(infoDto);
//    return ResponseEntity.ok().body(folderByName);
//  }

//  @DeleteMapping("/deleteFolder/{firstName}/{lastName}")
//  public ResponseEntity<Void> deleteByUserName(@PathVariable("firstName") String firstName,
//      @PathVariable("lastName") String lastName) throws Exception {
//    facade.deleteFolderAndEmployee(firstName, lastName);
//    return ResponseEntity.notFound().build();
//  }

  @GetMapping("/getFolders")
  public ResponseEntity<List<File>> getAllFolders()
      throws GeneralSecurityException, IOException {
    List<File> allFolders = facade.getAllFolders();
    return !allFolders.isEmpty() ? ResponseEntity.ok().body(allFolders)
        : ResponseEntity.notFound().build();
  }
}
