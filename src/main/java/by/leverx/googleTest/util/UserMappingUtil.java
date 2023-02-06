package by.leverx.googleTest.util;

import by.leverx.googleTest.user.UserInfo;
import by.leverx.googleTest.user.dto.UserInfoCreationDto;
import by.leverx.googleTest.user.dto.UserInfoDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMappingUtil {

  private ModelMapper mapper;

  private UserUtil util;

  @Autowired
  public UserMappingUtil(ModelMapper mapper, UserUtil util) {
    this.mapper = mapper;
    this.util = util;
  }

  public UserInfo mapToUser(UserInfoCreationDto creationDto) {
    return this.mapper.map(creationDto, UserInfo.class);
  }

  public UserInfoDto mapToDto (UserInfo info){
    return this.mapper.map(info,UserInfoDto.class);
  }
}
