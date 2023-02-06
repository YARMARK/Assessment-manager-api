package by.leverx.googleTest.service;

import by.leverx.googleTest.exception.UserNotFoundException;
import by.leverx.googleTest.repository.UserInfoRepository;
import by.leverx.googleTest.user.UserInfo;
import by.leverx.googleTest.user.dto.UserInfoCreationDto;
import by.leverx.googleTest.user.dto.UserInfoDto;
import by.leverx.googleTest.util.UserMappingUtil;
import by.leverx.googleTest.util.UserUtil;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private UserInfoRepository repository;

  private UserMappingUtil mappingUtil;

  private UserUtil util;

  @Autowired
  public UserServiceImpl(UserInfoRepository repository, UserMappingUtil mappingUtil,
      UserUtil util) {
    this.repository = repository;
    this.mappingUtil = mappingUtil;
    this.util = util;
  }

  @Override
  public UserInfoDto getUser(Long id) {

    Optional<UserInfo> byId = repository.findById(id);
    if (byId.isPresent()) {
      return mappingUtil.mapToDto(byId.get());
    }
    throw new UserNotFoundException("USER NOT FOUND");
  }

  @Override
  public UserInfoDto saveUser(UserInfoCreationDto creationDto, String folderId) {
    UserInfo userInfoWithoutDates = mappingUtil.mapToUser(creationDto);
    UserInfo userInfoWithDates =util.setIncomeAndAssessmentDates(userInfoWithoutDates);
    userInfoWithDates = util.setFolderUrl(userInfoWithDates, folderId);
    UserInfo save = repository.save(userInfoWithDates);
    return mappingUtil.mapToDto(save);
  }

  @Override
  public void deleteUser(String firstName, String lastName) {
    UserInfo byFirstNameAndLastName = repository.findByFirstNameAndLastName(firstName,
        lastName);
    if (Objects.nonNull(byFirstNameAndLastName)) {
      repository.deleteById(byFirstNameAndLastName.getUserId());
    } else {
      throw new UserNotFoundException("USER DOESN'T EXIST");
    }
  }
}
