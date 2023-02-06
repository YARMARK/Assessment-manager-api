package by.leverx.googleTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class GoogleTestApplication {

  public static void main(String[] args) {
    SpringApplication.run(GoogleTestApplication.class, args);
  }

}
