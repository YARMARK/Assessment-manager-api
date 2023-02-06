package by.leverx.googleTest.controller;

import by.leverx.googleTest.facade.GoogleFacade;
import by.leverx.googleTest.user.dto.UserInfoCreationDto;
import by.leverx.googleTest.user.dto.UserInfoDto;
import java.util.Objects;
import javax.validation.Valid;
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
public class GoogleController {

  private GoogleFacade facade;

  @Autowired
  public GoogleController(GoogleFacade facade) {
    this.facade = facade;
  }

  @GetMapping("/user/{id}")
  public ResponseEntity<UserInfoDto> getUser(@PathVariable("id") Long id) {
    UserInfoDto userById = facade.getUserById(id);
    return Objects.nonNull(userById) ? ResponseEntity.ok().body(userById)
        : ResponseEntity.notFound().build();
  }

  @PostMapping("/creatFolder")
  public ResponseEntity<String> createFolderByFirstNameAndLastName(
      @Valid @RequestBody UserInfoCreationDto infoDto) throws Exception {
    String folderByName = facade.createFolderAndSaveUser(infoDto);
    return ResponseEntity.ok().body(folderByName);
  }

  @DeleteMapping("/deleteFolder/{firstName}/{lastName}")
  public ResponseEntity<Void> deleteByUserName(@PathVariable("firstName") String firstName,
      @PathVariable("lastName") String lastName) throws Exception {
    facade.deleteFolderAndUser(firstName, lastName);
    return ResponseEntity.notFound().build();
  }
}
