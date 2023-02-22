package by.leverx.googleTest.util;

import by.leverx.googleTest.employee.EmployeeInfo;
import by.leverx.googleTest.employee.EmployeeInfoMapping;
import by.leverx.googleTest.employee.dto.EmployeeInfoCreationDto;
import by.leverx.googleTest.employee.dto.EmployeeInfoDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.checkerframework.checker.units.qual.A;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMappingUtil {

  private ModelMapper mapper;

  private EmployeeUtil util;

  private ObjectMapper objectMapper;

  @Autowired
  public EmployeeMappingUtil(ModelMapper mapper, EmployeeUtil util, ObjectMapper objectMapper) {
    this.mapper = mapper;
    this.util = util;
    this.objectMapper = objectMapper;
  }

  public EmployeeInfo mapToEmployee(EmployeeInfoCreationDto creationDto) {
    return this.mapper.map(creationDto, EmployeeInfo.class);
  }

  public EmployeeInfoDto mapToDto (EmployeeInfo info){
    return this.mapper.map(info, EmployeeInfoDto.class);
  }

  public List<EmployeeInfoDto> mapListToDto(List<EmployeeInfo> employeeInfos){
    List<EmployeeInfoDto> returnList = new ArrayList<>();
    for (EmployeeInfo info : employeeInfos){
      returnList.add(mapToDto(info));
    }
    return returnList;
  }

  public List<EmployeeInfoCreationDto> mapToInfo(String input) throws JsonProcessingException {
    List<EmployeeInfoMapping> employeeInfoMappings = objectMapper.readValue(input,
        new TypeReference<List<EmployeeInfoMapping>>() {
        });
    List<EmployeeInfoCreationDto> mappedEmployee = new ArrayList<>();
    for(EmployeeInfoMapping infoMapping : employeeInfoMappings){
      mappedEmployee.add(mapToCreationDto(infoMapping));
    }
    return mappedEmployee;
  }

  public EmployeeInfoCreationDto mapToCreationDto (EmployeeInfoMapping infoMapping){
    EmployeeInfoCreationDto info = new EmployeeInfoCreationDto();
    String[] firstAndLastName = infoMapping.getDisplayName().split("\\s", 2);
    if (firstAndLastName.length == 2){
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
