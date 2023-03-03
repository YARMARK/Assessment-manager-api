package by.leverx.googleDrive.service.serviceImpl;

import static by.leverx.googleDrive.util.EmployeeUtil.setIncomeAndAssessmentDates;
import static by.leverx.googleDrive.util.EmployeeUtil.sortByAssessmentDate;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import by.leverx.googleDrive.dto.EmployeeInfoCreationDto;
import by.leverx.googleDrive.dto.EmployeeInfoDto;
import by.leverx.googleDrive.dto.mapper.EmployeeInfoMapper;
import by.leverx.googleDrive.employee.EmployeeInfo;
import by.leverx.googleDrive.exception.EmployeeNotFoundException;
import by.leverx.googleDrive.exception.SuchEmployeeAlreadyExist;
import by.leverx.googleDrive.repository.EmployeeInfoRepository;
import by.leverx.googleDrive.service.EmployeeInfoService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeInfoServiceImpl implements EmployeeInfoService {

  private EmployeeInfoRepository repository;

  private EmployeeInfoMapper mappingUtil;

  @Autowired
  public EmployeeInfoServiceImpl(EmployeeInfoRepository repository,
      EmployeeInfoMapper mappingUtil) {
    this.repository = repository;
    this.mappingUtil = mappingUtil;
  }

  @Override
  public EmployeeInfoDto getEmployeeById(Long id) {

    Optional<EmployeeInfo> byId = repository.findById(id);
    if (byId.isPresent()) {
      return mappingUtil.mapToDto(byId.get());
    }
    throw new EmployeeNotFoundException(id.toString());
  }

  @Override
  public List<EmployeeInfoDto> getAllEmployees() {
    List<EmployeeInfo> allEmployees = repository.findAll();
    return mappingUtil.mapListToDto(allEmployees);
  }

  @Override
  public Map<String, Object> getAllEmployeesPage(Pageable pageable) {
    List<EmployeeInfoDto> targetData = new ArrayList<>();
    Map<String, Object> result = new HashMap<>();
    Page<EmployeeInfo> employeePage = repository.findAll(pageable);
    for (EmployeeInfo info : employeePage.getContent()) {
      targetData.add(mappingUtil.mapToDto(info));
    }
    result.put("employee", targetData);
    result.put("currentPage", employeePage.getNumber());
    result.put("totalEmployees", employeePage.getTotalElements());
    result.put("totalPages", employeePage.getTotalPages());
    return result;
  }

  @Override
  public EmployeeInfoDto saveEmployeeByInfoAndFolderId(
      EmployeeInfoCreationDto creationDto) {
    if (!checkIsEmployeeExist(creationDto)) {
      EmployeeInfo employeeInfoWithoutDates = mappingUtil.mapToEmployee(creationDto);
      EmployeeInfo employeeInfoWithDates = setIncomeAndAssessmentDates(
          employeeInfoWithoutDates);
      EmployeeInfo save = repository.save(employeeInfoWithDates);
      return mappingUtil.mapToDto(save);
    } else {
      throw new SuchEmployeeAlreadyExist(creationDto.getFirstName(), creationDto.getLastName());
    }
  }


  @Override
  public List<EmployeeInfoDto> saveAllEmployeesInfo(List<EmployeeInfoCreationDto> input) {
    List<EmployeeInfoDto> employeeInfoDtoList = new ArrayList<>();
    for (EmployeeInfoCreationDto employeeInfo : input) {
      Boolean isEmployeeExist = checkIsEmployeeExist(employeeInfo);
      if (!isEmployeeExist) {
        EmployeeInfoDto employeeInfoDto = saveEmployeeByInfoAndFolderId(employeeInfo);
        employeeInfoDtoList.add(employeeInfoDto);
      }
    }
    return employeeInfoDtoList;
  }

  @Override
  public List<EmployeeInfoDto> updateAssessmentDate(List<EmployeeInfoDto> employeesList) {
    List<EmployeeInfoDto> returnList = new ArrayList<>();
    List<EmployeeInfo> updatedList = updateAssessmentMarker(employeesList);
    for (EmployeeInfo infoDto : updatedList) {
      repository.save(infoDto);
      returnList.add(mappingUtil.mapToDto(infoDto));
    }
    return returnList;
  }

  @Override
  public List<EmployeeInfoDto> needAssessmentList() {
    List<EmployeeInfoDto> needAssessment = mappingUtil.mapListToDto(
        repository.findByNeedAssessment(true));
    return sortByAssessmentDate(needAssessment);
  }

  @Override
  public void deleteEmployeeByFirstAndLastName(String firstName, String lastName) {
    EmployeeInfo byFirstNameAndLastName = repository.findByFirstNameAndLastName(firstName,
        lastName);
    if (nonNull(byFirstNameAndLastName)) {
      repository.deleteById(byFirstNameAndLastName.getEmployeeId());
    } else {
      throw new EmployeeNotFoundException(firstName, lastName);
    }
  }

  @Override
  public void deleteEmployeeById(Long id) {
    Optional<EmployeeInfo> byId = repository.findById(id);
    if (byId.isPresent()) {
      repository.deleteById(id);
    } else {
      throw new EmployeeNotFoundException(id.toString());
    }
  }

  @Override
  public void deleteAllEmployees() {
    repository.deleteAll();
  }

  @Override
  public Boolean checkIsEmployeeExist(EmployeeInfoCreationDto creationDto) {
    EmployeeInfo byFirstNameAndLastName = repository.findByFirstNameAndLastName(
        creationDto.getFirstName(), creationDto.getLastName());
    Boolean validMarker = true;
    if (isNull(byFirstNameAndLastName)) {
      validMarker = false;
    } else {
      validMarker = true;
    }
    return validMarker;
  }

  @Override
  public List<EmployeeInfo> updateAssessmentMarker(List<EmployeeInfoDto> employeesList) {
    List<EmployeeInfo> returnList = new ArrayList<>();
    for (EmployeeInfoDto infoDto : employeesList) {
      EmployeeInfo employeeInfo = repository.findByFirstNameAndLastName(
          infoDto.getFirstName(), infoDto.getLastName());
      employeeInfo.setNeedAssessment(true);
      returnList.add(employeeInfo);
    }
    return returnList;
  }
}
