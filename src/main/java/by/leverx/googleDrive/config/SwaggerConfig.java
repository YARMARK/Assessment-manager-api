package by.leverx.googleDrive.config;

import static by.leverx.googleDrive.util.ConstantMessage.*;

import by.leverx.googleDrive.util.ConstantMessage;
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

  public static final String SWAGGER_EMPLOYEE_TAG = "Employee Controller";

  public static final String SWAGGER_GOOGLE_TAG = "Google Controller";

  public static String SWAGGER_EMPLOYEE_TAG_DESCRIPTION =
      "set of endpoints for Creating, Retrieving, and Deleting employees.";

  public static String SWAGGER_GOOGLE_TAG_DESCRIPTION = "set endpoints to manage file in google drive.";

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(Predicates.not(PathSelectors.regex("/error.*")))
        .build()
        .tags(new Tag(SWAGGER_EMPLOYEE_TAG, SWAGGER_EMPLOYEE_TAG_DESCRIPTION),
            new Tag(SWAGGER_GOOGLE_TAG, SWAGGER_GOOGLE_TAG_DESCRIPTION))
        .apiInfo(getApiInfo());
  }

  private ApiInfo getApiInfo() {
    return new ApiInfo(
        getSwaggerProjectTitle(),
        getSwaggerProjectDescription(),
        getSwaggerProjectVersion(),
        getSwaggerTermOfService(),
        new Contact(getSwaggerFullName(), getSwaggerUrl(), getSwaggerEMail()),
        getSwaggerLicence(),
        getSwaggerLicenceUrl(),
        Collections.emptyList());
  }
}
