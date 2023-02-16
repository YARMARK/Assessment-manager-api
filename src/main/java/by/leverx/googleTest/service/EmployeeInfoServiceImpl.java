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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    for (EmployeeInfo info : allEmployees) {
      returnList.add(mappingUtil.mapToDto(info));
    }
    return returnList;
  }

  @Override
  public List<EmployeeInfoDto> getAllEmployeesPage(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    List<EmployeeInfoDto> returnList = new ArrayList<>();
    Page<EmployeeInfo> employeePage = repository.findAll(pageable);
    for (EmployeeInfo info : employeePage) {
      returnList.add(mappingUtil.mapToDto(info));
    }
    return returnList;
  }

  @Override
  public EmployeeInfoDto saveEmployeeByInfoAndFolderId(
      EmployeeInfoCreationDto creationDto) { // Delete folder ID
    EmployeeInfo employeeInfoWithoutDates = mappingUtil.mapToEmployee(creationDto);
    EmployeeInfo employeeInfoWithDates = util.setIncomeAndAssessmentDates(employeeInfoWithoutDates);
//    employeeInfoWithDates = util.setFolderUrl(employeeInfoWithDates, folderId);
    EmployeeInfo save = repository.save(employeeInfoWithDates);
    return mappingUtil.mapToDto(save);
  }


  @Override
  public List<EmployeeInfoDto> saveAllEmployeesInfo(List<EmployeeInfoCreationDto> input) {
    List<EmployeeInfoDto> employeeInfoDtoList = new ArrayList<>();
    for (EmployeeInfoCreationDto employeeInfo : input) {
      EmployeeInfoDto employeeInfoDto = saveEmployeeByInfoAndFolderId(employeeInfo);
      employeeInfoDtoList.add(employeeInfoDto);
    }
    return employeeInfoDtoList;
  }

  @Override
  public void deleteEmployeeByFirstAndLastName(String firstName, String lastName) {
    EmployeeInfo byFirstNameAndLastName = repository.findByFirstNameAndLastName(firstName,
        lastName);
    if (Objects.nonNull(byFirstNameAndLastName)) {
      repository.deleteById(byFirstNameAndLastName.getEmployeeId());
    } else {
      throw new EmployeeNotFoundException("EMPLOYEE NOT FOUND");
    }
  }

  @Override
  public void deleteEmployeeById(Long id) {
    Optional<EmployeeInfo> byId = repository.findById(id);
    if (byId.isPresent()) {
      repository.deleteById(id);
    } else {
      throw new EmployeeNotFoundException("EMPLOYEE NOT FOUND");
    }
  }

  @Override
  public void deleteAllEmployees(){
    repository.deleteAll();
  }

}
