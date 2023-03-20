package by.leverx.googleDrive.controller;

import static java.util.Objects.nonNull;

import by.leverx.googleDrive.config.SwaggerConfig;
import by.leverx.googleDrive.facade.GoogleFacade;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.auth.oauth2.AccessToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@Api(tags = {SwaggerConfig.SWAGGER_GOOGLE_TAG})
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
  public ResponseEntity<List<String>> getAllFolders(
      @RequestHeader(HttpHeaders.AUTHORIZATION) String token)
      throws GeneralSecurityException, IOException {
    List<String> allFolders = facade.getAllFolders(token);
    return !allFolders.isEmpty() ?
        ResponseEntity.ok().body(allFolders) :
        ResponseEntity.notFound().build();
  }

  @GetMapping("/manager/getFolders")
  @ApiOperation("returns all folder names from google drive.")
  public ResponseEntity<FileList> getAllFoldersManager()
      throws GeneralSecurityException, IOException {
    FileList allFolders = facade.getAllFoldersManager();
    return !allFolders.isEmpty() ?
        ResponseEntity.ok().body(allFolders) :
        ResponseEntity.notFound().build();
  }

  @GetMapping("/files")
  @ApiOperation("returns all folder names from google drive.")
  public ResponseEntity<String> getFiles(
      @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
    String allFolders = facade.getFiles(token);
    return nonNull(allFolders) ?
        ResponseEntity.ok().body(allFolders) :
        ResponseEntity.notFound().build();
  }

  @PostMapping("/folder/{folderName}")
  @ApiOperation("create folder by folder name in google drive.")
  public ResponseEntity<String> createFolder(
      @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
      @ApiParam("Folder name") @PathVariable("folderName") String folderName) {
    String folderId = facade.createFolderByName(folderName, token);
    return nonNull(folderId) ?
        ResponseEntity.ok().body(folderId) :
        ResponseEntity.notFound().build();
  }
}
