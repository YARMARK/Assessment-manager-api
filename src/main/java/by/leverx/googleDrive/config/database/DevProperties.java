package by.leverx.googleDrive.config.database;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
@Data
public class DevProperties implements PropertiesProvider {

  @Value("${spring.datasource.username}")
  String userName;

  @Value("${spring.datasource.password}")
  String password;

  @Value("${spring.datasource.database-name}")
  String databaseName;

  @Value("${spring.datasource.driver-class-name}")
  String driverClassName;

  String port;

  String hostName;
}
