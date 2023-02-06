package by.leverx.googleTest.facade;

import by.leverx.googleTest.exception.FolderNotFoundException;
import by.leverx.googleTest.exception.SomethingWentWrongException;
import by.leverx.googleTest.exception.SuchFolderAlreadyExist;
import by.leverx.googleTest.exception.SuchUserAlreadyExist;
import by.leverx.googleTest.repository.UserInfoRepository;
import by.leverx.googleTest.service.GoogleService;
import by.leverx.googleTest.service.UserServiceImpl;
import by.leverx.googleTest.user.UserInfo;
import by.leverx.googleTest.user.dto.UserInfoCreationDto;
import by.leverx.googleTest.user.dto.UserInfoDto;
import by.leverx.googleTest.util.GoogleUtil;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GoogleFacade {

  private GoogleService service;

  private GoogleUtil util;

  private UserServiceImpl userService;

  private UserInfoRepository repository;

  @Autowired
  public GoogleFacade(GoogleService service, GoogleUtil util, UserInfoRepository repository,
      UserServiceImpl userService) {

    this.service = service;
    this.util = util;
    this.repository = repository;
    this.userService = userService;
  }

  public String createFolderAndSaveUser(UserInfoCreationDto dto) throws Exception {
    String folderName = dto.getFirstName() + "_" + dto.getLastName();
    String folderId = createFolderByFolderName(folderName);
    UserInfoDto savedUser = saveUserInfo(dto, folderId);
    if (Objects.nonNull(folderId) && Objects.nonNull(savedUser)) {
      return folderId;
    } else {
      throw new SomethingWentWrongException("SOMETHING WENT WRONG");
    }
  }

  private String createFolderByFolderName(String folderName)
      throws Exception {
    if (util.searchFolderByFolderName(folderName) == null) {
      return service.createFolderAndUploadFile(folderName);
    } else {
      throw new SuchFolderAlreadyExist("THIS: '" + folderName + "' ALREADY IN USE.");
    }
  }

  private UserInfoDto saveUserInfo(UserInfoCreationDto creationDto, String folderId) {
    UserInfo byFirstNameAndLastName = repository.findByFirstNameAndLastName(
        creationDto.getFirstName(), creationDto.getLastName());
    if (Objects.isNull(byFirstNameAndLastName)) {
      return userService.saveUser(creationDto, folderId);
    }
    throw new SuchUserAlreadyExist("USER ALREADY CREATED");
  }

  public void deleteFolderAndUser(String firstName, String lastName) throws Exception {
    String folderName = firstName + "_" + lastName;
    String folderId = util.searchFolderByFolderName(folderName);
    if(Objects.nonNull(folderId)){
      userService.deleteUser(firstName, lastName);
    }
    else throw new FolderNotFoundException("FOLDER NOT FOUND");
  }
   public UserInfoDto getUserById(Long id){
    return userService.getUser(id);
   }
}
