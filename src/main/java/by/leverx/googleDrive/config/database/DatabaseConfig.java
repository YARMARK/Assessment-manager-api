package by.leverx.googleDrive.config.database;

import static java.lang.String.format;

import javax.sql.DataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DatabaseConfig {

  private static final String DEV_URL = "jdbc:h2:mem:%s;DB_CLOSE_DELAY=-1";

  private static final String TEST_PROD_URL = "jdbc:postgresql://%s:%s/%s";

  private static final String CHANGELOG_PATH = "classpath:changelog/db.changelog-master.xml";

  private final PropertiesProvider properties;

  public DatabaseConfig(PropertiesProvider credentials) {
    this.properties = credentials;
  }

  @Profile(value = "dev")
  @Bean
  public DataSource getDataSource() {
    var dataSource = new DriverManagerDataSource();

    dataSource.setUrl(format(DEV_URL, properties.getDatabaseName()));
    dataSource.setUsername(properties.getUserName());
    dataSource.setPassword(properties.getPassword());
    dataSource.setDriverClassName(properties.getDriverClassName());

    return dataSource;
  }

  @Profile(value = "test")
  @Bean
  public DataSource getTestDataSource() {
    var dataSource = new DriverManagerDataSource();

    dataSource.setUrl(format(TEST_PROD_URL, properties.getHostName(), properties.getPort(),
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

    dataSource.setUrl(format(TEST_PROD_URL, properties.getHostName(), properties.getPort(),
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
    liquibase.setChangeLog(CHANGELOG_PATH);
    return liquibase;
  }
}
