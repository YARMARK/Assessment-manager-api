package by.leverx.googleTest.repository;

import by.leverx.googleTest.employee.EmployeeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<EmployeeInfo, Long> {

  EmployeeInfo findByFirstNameAndLastName (String firstName, String lastName);
}
