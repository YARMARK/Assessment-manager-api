package by.leverx.googleTest.config;

import com.google.common.base.Predicates;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  public static final String EMPLOYEE_TAG = "Employee Controller";

  public static final String GOOGLE_TAG = "Google Controller";

  public static final String PROJECT_TITLE = "Assessment manager api";

  public static final String PROJECT_DESCRIPTION =
      "Application built on Spring Boot as API-provider to help admin with creating folders and"
          + " putting predefined template files for the next assessment date and sending notifications"
          + " for both sides of the assessment. This application has integration with Google Drive "
          + "(folders and files), JIRA (Employees info like first and last name).";

  public static final String PROJECT_VERSION = "1.0.0";

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(Predicates.not(PathSelectors.regex("/error.*")))
        .build()
        .tags(new Tag(EMPLOYEE_TAG, "set of endpoints for Creating, Retrieving," +
            " and Deleting employees."),
            new Tag(GOOGLE_TAG,"set endpoints to manage file in google drive."))
        .apiInfo(getApiInfo());
  }

  private ApiInfo getApiInfo() {
    return new ApiInfo(
        PROJECT_TITLE,
        PROJECT_DESCRIPTION,
        PROJECT_VERSION,
        "Term of services",
        new Contact("Yaraslau Markau", "https://mail.google.com/",
            "yaraslau.markau@leverx.com"),
        "Apache 2.0",
        "https://www.apache.org/licenses/LICENSE-2.0.html",
        Collections.emptyList());
  }
}
