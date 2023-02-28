package by.leverx.googleDrive.dto.mapper;

import by.leverx.googleDrive.dto.EmployeeInfoCreationDto;
import by.leverx.googleDrive.dto.EmployeeInfoDto;
import by.leverx.googleDrive.dto.EmployeeInfoJiraDto;
import by.leverx.googleDrive.employee.EmployeeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeInfoMapper {

  private ModelMapper mapper;

  private ObjectMapper objectMapper;

  @Autowired
  public EmployeeInfoMapper(ModelMapper mapper, ObjectMapper objectMapper) {
    this.mapper = mapper;
    this.objectMapper = objectMapper;
  }

  public EmployeeInfo mapToEmployee(EmployeeInfoCreationDto creationDto) {
    return this.mapper.map(creationDto, EmployeeInfo.class);
  }

  public EmployeeInfoDto mapToDto(EmployeeInfo info) {
    return this.mapper.map(info, EmployeeInfoDto.class);
  }

  public List<EmployeeInfoDto> mapListToDto(List<EmployeeInfo> employeeInfos) {
    List<EmployeeInfoDto> returnList = new ArrayList<>();
    for (EmployeeInfo info : employeeInfos) {
      returnList.add(mapToDto(info));
    }
    return returnList;
  }

  public List<EmployeeInfoCreationDto> mapToInfo(String input) throws JsonProcessingException {
    List<EmployeeInfoJiraDto> employeeInfoJiraDto = objectMapper.readValue(input,
        new TypeReference<List<EmployeeInfoJiraDto>>() {
        });
    List<EmployeeInfoCreationDto> mappedEmployee = new ArrayList<>();
    for (EmployeeInfoJiraDto infoMapping : employeeInfoJiraDto) {
      mappedEmployee.add(mapToCreationDto(infoMapping));
    }
    return mappedEmployee;
  }

  public EmployeeInfoCreationDto mapToCreationDto(EmployeeInfoJiraDto infoMapping) {
    EmployeeInfoCreationDto info = new EmployeeInfoCreationDto();
    String[] firstAndLastName = infoMapping.getDisplayName().split("\\s", 2);
    if (firstAndLastName.length == 2) {
      info.setFirstName(firstAndLastName[0]);
      info.setLastName(firstAndLastName[1]);
    } else {
      info.setFirstName(firstAndLastName[0]);
    }
    info.setEmail(infoMapping.getSelf());
    info.setTitle(infoMapping.getLocale());
    return info;
  }
}
