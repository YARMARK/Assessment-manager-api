package by.leverx.googleDrive.repository;

import by.leverx.googleDrive.employee.EmployeeInfo;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeInfoRepository extends JpaRepository<EmployeeInfo, Long> {

  @Override
  Page<EmployeeInfo> findAll(Pageable pageable);

  EmployeeInfo findByFirstNameAndLastName(String firstName, String lastName);

  List<EmployeeInfo> findByNeedAssessment(Boolean variable);
}
