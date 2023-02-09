package by.leverx.googleTest.config.dbConfig;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
@Data
public class DevCredentials implements DbCredentials {

  @Value("${spring.datasource.username}")
  String userName;

  @Value("${spring.datasource.password}")
  String password;

  @Value("${spring.datasource.dbName}")
  String dbName;

  @Value("${spring.datasource.driver-class-name}")
  String driverClassName;

  String port;

  String hostName;
}
