package by.leverx.googleTest.repository;

import by.leverx.googleTest.employee.EmployeeInfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeInfoRepository extends JpaRepository<EmployeeInfo, Long> {

  EmployeeInfo findByFirstNameAndLastName(String firstName, String lastName);

  List<EmployeeInfo> findByNeedAssessment(Boolean variable);
}
