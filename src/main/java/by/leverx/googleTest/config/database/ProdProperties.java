package by.leverx.googleTest.config.database;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile(value = "prod")
@Component
@Data
public class ProdProperties implements PropertiesProvider{

  @Value("${vcap.services.my_db.credentials.username}")
  private String userName;

  @Value("${vcap.services.my_db.credentials.password}")
  private String password;

  @Value(value = "${spring.datasource.driver-class-name}")
  private String driverClassName;

  @Value("${vcap.services.my_db.credentials.dbname}")
  private String databaseName;

  @Value("${vcap.services.my_db.credentials.hostname}")
  private String hostName;

  @Value("${vcap.services.my_db.credentials.port}")
  private String port;
}
