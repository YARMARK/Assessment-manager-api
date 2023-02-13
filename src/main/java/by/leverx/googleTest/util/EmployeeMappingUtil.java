package by.leverx.googleTest.util;

import by.leverx.googleTest.employee.EmployeeInfo;
import by.leverx.googleTest.employee.dto.EmployeeInfoCreationDto;
import by.leverx.googleTest.employee.dto.EmployeeInfoDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMappingUtil {

  private ModelMapper mapper;

  private EmployeeUtil util;

  @Autowired
  public EmployeeMappingUtil(ModelMapper mapper, EmployeeUtil util) {
    this.mapper = mapper;
    this.util = util;
  }

  public EmployeeInfo mapToEmployee(EmployeeInfoCreationDto creationDto) {
    return this.mapper.map(creationDto, EmployeeInfo.class);
  }

  public EmployeeInfoDto mapToDto (EmployeeInfo info){
    return this.mapper.map(info, EmployeeInfoDto.class);
  }
}
