package by.leverx.googleTest.service;

import by.leverx.googleTest.user.dto.UserInfoCreationDto;
import by.leverx.googleTest.user.dto.UserInfoDto;

public interface UserService {

  UserInfoDto getUser(Long id);

  UserInfoDto saveUser (UserInfoCreationDto creationDto, String folderId);

  void deleteUser (String firstName, String lastName);
}
