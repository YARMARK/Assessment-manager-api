package by.leverx.googleDrive.config;

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

  private static final String PROJECT_TITLE = "Assessment manager api";

  public static final String EMPLOYEE_TAG_DESCRIPTION =
      "set of endpoints for Creating, Retrieving, and Deleting employees.";

  public static final String GOOGLE_TAG_DESCRIPTION = "set endpoints to manage file in google drive.";

  private static final String TERM_OF_SERVICE = "Term of services";

  private static final String FULL_NAME = "Yaraslau Markau";

  private static final String E_MAIL = "yaraslau.markau@leverx.com";

  private static final String URL = "https://mail.google.com/";

  private static final String LICENCE = "Apache 2.0";

  private static final String LICENCE_URL = "https://www.apache.org/licenses/LICENSE-2.0.html";

  public static final String PROJECT_DESCRIPTION =
      "Application built on Spring Boot as API-provide to help admin with creating folders and putting"
          + " predefined template files for the next assessment date and sending notifications for"
          + " both sides of the assessment. This application has integration with Google Drive (folders"
          + " and files), JIRA (Employees info like first and last name).";

  public static final String PROJECT_VERSION = "1.0.0";

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(Predicates.not(PathSelectors.regex("/error.*")))
        .build()
        .tags(new Tag(EMPLOYEE_TAG, EMPLOYEE_TAG_DESCRIPTION),
            new Tag(GOOGLE_TAG, GOOGLE_TAG_DESCRIPTION))
        .apiInfo(getApiInfo());
  }

  private ApiInfo getApiInfo() {
    return new ApiInfo(
        PROJECT_TITLE,
        PROJECT_DESCRIPTION,
        PROJECT_VERSION,
        TERM_OF_SERVICE,
        new Contact(FULL_NAME, URL, E_MAIL),
        LICENCE,
        LICENCE_URL,
        Collections.emptyList());
  }
}
