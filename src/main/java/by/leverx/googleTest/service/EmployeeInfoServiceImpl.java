package by.leverx.googleTest.service;

import by.leverx.googleTest.exception.EmployeeNotFoundException;
import by.leverx.googleTest.repository.EmployeeInfoRepository;
import by.leverx.googleTest.employee.EmployeeInfo;
import by.leverx.googleTest.employee.dto.EmployeeInfoCreationDto;
import by.leverx.googleTest.employee.dto.EmployeeInfoDto;
import by.leverx.googleTest.util.EmployeeMappingUtil;
import by.leverx.googleTest.util.EmployeeUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeInfoServiceImpl implements EmployeeInfoService {

  private EmployeeInfoRepository repository;

  private EmployeeMappingUtil mappingUtil;

  private EmployeeUtil util;

  @Autowired
  public EmployeeInfoServiceImpl(EmployeeInfoRepository repository, EmployeeMappingUtil mappingUtil,
      EmployeeUtil util) {
    this.repository = repository;
    this.mappingUtil = mappingUtil;
    this.util = util;
  }

  @Override
  public EmployeeInfoDto getEmployeeById(Long id) {

    Optional<EmployeeInfo> byId = repository.findById(id);
    if (byId.isPresent()) {
      return mappingUtil.mapToDto(byId.get());
    }
    throw new EmployeeNotFoundException("EMPLOYEE NOT FOUND");
  }

  @Override
  public List<EmployeeInfoDto> getAllEmployees() {
    List<EmployeeInfo> allEmployees = repository.findAll();
    List<EmployeeInfoDto> returnList = new ArrayList<>();
    for (EmployeeInfo info : allEmployees){
      returnList.add(mappingUtil.mapToDto(info));
    }
    return returnList;
  }


  @Override
  public EmployeeInfoDto saveEmployee(EmployeeInfo employeeInfo) {
    util.setIncomeAndAssessmentDates(employeeInfo);
    return null;
  }

  @Override
  public EmployeeInfoDto saveEmployeeByInfoAndFolderId(EmployeeInfoCreationDto creationDto) { // Delete folder ID
    EmployeeInfo employeeInfoWithoutDates = mappingUtil.mapToEmployee(creationDto);
    EmployeeInfo employeeInfoWithDates =util.setIncomeAndAssessmentDates(employeeInfoWithoutDates);
//    employeeInfoWithDates = util.setFolderUrl(employeeInfoWithDates, folderId);
    EmployeeInfo save = repository.save(employeeInfoWithDates);
    return mappingUtil.mapToDto(save);
  }


  @Override
  public List<EmployeeInfoDto> saveAllEmployeesInfo(List<EmployeeInfo> employeeInfoListFromJira) {
    List <EmployeeInfoDto> employeeInfoDtoList = new ArrayList<>();
    for (EmployeeInfo employeeInfo : employeeInfoListFromJira){
//      saveEmployeeByInfoAndFolderId(employeeInfo);
//      employeeInfoDtoList.add(mappingUtil.mapToDto(savedUser));
    }
    return employeeInfoDtoList;
  }

  @Override
  public void deleteEmployee(String firstName, String lastName) {
    EmployeeInfo byFirstNameAndLastName = repository.findByFirstNameAndLastName(firstName,
        lastName);
    if (Objects.nonNull(byFirstNameAndLastName)) {
      repository.deleteById(byFirstNameAndLastName.getUserId());
    } else {
      throw new EmployeeNotFoundException("USER DOESN'T EXIST");
    }

  }
}
