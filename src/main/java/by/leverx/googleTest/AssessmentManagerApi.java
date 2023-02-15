package by.leverx.googleTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AssessmentManagerApi {

  public static void main(String[] args) {
    SpringApplication.run(AssessmentManagerApi.class, args);
  }

}
