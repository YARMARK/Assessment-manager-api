package by.leverx.googleDrive.config.database;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile(value = "test")
@Component
@Data
public class TestProperties implements PropertiesProvider {

  @Value("${spring.datasource.username}")
  private String userName;

  @Value("${spring.datasource.password}")
  private String password;

  @Value("${spring.datasource.driver-class-name}")
  private String driverClassName;

  @Value("${spring.datasource.database-name}")
  private String databaseName;

  @Value("${spring.datasource.hostname}")
  private String hostName;

  @Value("${spring.datasource.port}")
  private String port;
}
