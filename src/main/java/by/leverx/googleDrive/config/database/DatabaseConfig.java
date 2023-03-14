package by.leverx.googleDrive.config.database;

import static by.leverx.googleDrive.util.ConstantMessage.*;
import static java.lang.String.format;

import by.leverx.googleDrive.util.ConstantMessage;
import javax.sql.DataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DatabaseConfig {

  private final PropertiesProvider properties;

  public DatabaseConfig(PropertiesProvider credentials) {
    this.properties = credentials;
  }

  @Profile(value = "dev")
  @Bean
  public DataSource getDataSource() {
    var dataSource = new DriverManagerDataSource();

    dataSource.setUrl(format(getDbDevUrl(), properties.getDatabaseName()));
    dataSource.setUsername(properties.getUserName());
    dataSource.setPassword(properties.getPassword());
    dataSource.setDriverClassName(properties.getDriverClassName());

    return dataSource;
  }

  @Profile(value = "test")
  @Bean
  public DataSource getTestDataSource() {
    var dataSource = new DriverManagerDataSource();

    dataSource.setUrl(format(getDbTestProdUrl(), properties.getHostName(), properties.getPort(),
        properties.getDatabaseName()));
    dataSource.setUsername(properties.getUserName());
    dataSource.setPassword(properties.getPassword());
    dataSource.setDriverClassName(properties.getDriverClassName());

    return dataSource;
  }

  @Profile(value = "prod")
  @Bean
  public DataSource getProdDataSource() {
    var dataSource = new DriverManagerDataSource();

    dataSource.setUrl(format(getDbTestProdUrl(), properties.getHostName(), properties.getPort(),
        properties.getDatabaseName()));
    dataSource.setUsername(properties.getUserName());
    dataSource.setPassword(properties.getPassword());
    dataSource.setDriverClassName(properties.getDriverClassName());

    return dataSource;
  }

  @Profile(value = "prod")
  @Bean
  public SpringLiquibase springLiquibase() {
    SpringLiquibase liquibase = new SpringLiquibase();
    liquibase.setDataSource(getProdDataSource());
    liquibase.setChangeLog(getDbChangelogPath());
    return liquibase;
  }
}
