package by.leverx.googleTest.repository;

import by.leverx.googleTest.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

  UserInfo findByFirstNameAndLastName (String firstName, String lastName);
}
