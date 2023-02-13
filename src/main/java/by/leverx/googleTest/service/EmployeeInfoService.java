package by.leverx.googleTest.service;

import by.leverx.googleTest.user.UserInfo;
import by.leverx.googleTest.user.dto.UserInfoCreationDto;
import by.leverx.googleTest.user.dto.UserInfoDto;
import java.util.List;

public interface UserInfoService {

  UserInfoDto getUser(Long id);

  UserInfoDto saveUser (UserInfoCreationDto creationDto, String folderId);

  List<UserInfoDto> saveAllUserInfo (List<UserInfo> userInfoListFromJira);

  void deleteUser (String firstName, String lastName);
}
