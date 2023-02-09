package by.leverx.googleTest.config.dbConfig;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DbConfiguration {

  private final DbCredentials credentials;

  public DbConfiguration(DbCredentials credentials) {
    this.credentials = credentials;
  }

  @Profile(value = "dev")
  @Bean
  public DataSource getDataSource() {

    var dataSource = new DriverManagerDataSource();
    dataSource.setUrl("jdbc:h2:mem:" + credentials.getDbName() + ";DB_CLOSE_DELAY=-1");
    dataSource.setUsername(credentials.getUserName());
    dataSource.setPassword(credentials.getPassword());
    dataSource.setDriverClassName(credentials.getDriverClassName());

    return dataSource;
  }

  @Bean
  public DataSource getTestDataSource() {
    var dataSource = new DriverManagerDataSource();

    dataSource.setUrl("jdbc:postgresql://" + credentials.getHostName() + ":" + credentials.getPort() +
        "/" + credentials.getDbName());
    dataSource.setUsername(credentials.getUserName());
    dataSource.setPassword(credentials.getPassword());
    dataSource.setDriverClassName(credentials.getDriverClassName());

    return dataSource;
  }
}
