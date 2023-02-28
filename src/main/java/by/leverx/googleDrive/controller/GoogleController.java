package by.leverx.googleDrive.controller;

import by.leverx.googleDrive.config.SwaggerConfig;
import by.leverx.googleDrive.facade.GoogleFacade;
import com.google.api.services.drive.model.File;
import com.google.auth.oauth2.AccessToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@Api(tags = {SwaggerConfig.GOOGLE_TAG})
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
  @ApiOperation("returns all folder names from google drive.")
  public ResponseEntity<List<File>> getAllFolders(
      @RequestHeader(HttpHeaders.AUTHORIZATION) AccessToken token)
      throws GeneralSecurityException, IOException {
    List<File> allFolders = facade.getAllFolders();
    return !allFolders.isEmpty() ?
        ResponseEntity.ok().body(allFolders) :
        ResponseEntity.notFound().build();
  }
  @GetMapping("/files")
  @ApiOperation("returns all folder names from google drive.")
  public ResponseEntity<String> getFiles(
      @RequestHeader(HttpHeaders.AUTHORIZATION) String token)
      throws GeneralSecurityException, IOException {
    String allFolders = facade.getFiles(token);
    return !allFolders.isEmpty() ?
        ResponseEntity.ok().body(allFolders) :
        ResponseEntity.notFound().build();
  }
}
