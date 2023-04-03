package by.leverx.googleDrive.config;

import static by.leverx.googleDrive.util.ConstantMessage.SwaggerConstant.SWAGGER_CALENDAR_TAG;
import static by.leverx.googleDrive.util.ConstantMessage.SwaggerConstant.SWAGGER_CALENDAR_TAG_DESCRIPTION;
import static by.leverx.googleDrive.util.ConstantMessage.SwaggerConstant.SWAGGER_DRIVE_TAG;
import static by.leverx.googleDrive.util.ConstantMessage.SwaggerConstant.SWAGGER_DRIVE_TAG_DESCRIPTION;
import static by.leverx.googleDrive.util.ConstantMessage.SwaggerConstant.SWAGGER_EMPLOYEE_TAG;
import static by.leverx.googleDrive.util.ConstantMessage.SwaggerConstant.SWAGGER_EMPLOYEE_TAG_DESCRIPTION;
import static by.leverx.googleDrive.util.ConstantMessage.SwaggerConstant.SWAGGER_E_MAIL;
import static by.leverx.googleDrive.util.ConstantMessage.SwaggerConstant.SWAGGER_FILE_TAG;
import static by.leverx.googleDrive.util.ConstantMessage.SwaggerConstant.SWAGGER_FILE_TAG_DESCRIPTION;
import static by.leverx.googleDrive.util.ConstantMessage.SwaggerConstant.SWAGGER_FULL_NAME;
import static by.leverx.googleDrive.util.ConstantMessage.SwaggerConstant.SWAGGER_GMAIL_TAG;
import static by.leverx.googleDrive.util.ConstantMessage.SwaggerConstant.SWAGGER_GMAIL_TAG_DESCRIPTION;
import static by.leverx.googleDrive.util.ConstantMessage.SwaggerConstant.SWAGGER_LICENCE;
import static by.leverx.googleDrive.util.ConstantMessage.SwaggerConstant.SWAGGER_LICENCE_URL;
import static by.leverx.googleDrive.util.ConstantMessage.SwaggerConstant.SWAGGER_PROJECT_DESCRIPTION;
import static by.leverx.googleDrive.util.ConstantMessage.SwaggerConstant.SWAGGER_PROJECT_TITLE;
import static by.leverx.googleDrive.util.ConstantMessage.SwaggerConstant.SWAGGER_PROJECT_VERSION;
import static by.leverx.googleDrive.util.ConstantMessage.SwaggerConstant.SWAGGER_TERM_OF_SERVICE;
import static by.leverx.googleDrive.util.ConstantMessage.SwaggerConstant.SWAGGER_URL;

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



  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(Predicates.not(PathSelectors.regex("/error.*")))
        .build()
        .tags(new Tag(SWAGGER_EMPLOYEE_TAG, SWAGGER_EMPLOYEE_TAG_DESCRIPTION),
            new Tag(SWAGGER_DRIVE_TAG, SWAGGER_DRIVE_TAG_DESCRIPTION),
            new Tag(SWAGGER_GMAIL_TAG,SWAGGER_GMAIL_TAG_DESCRIPTION),
            new Tag(SWAGGER_CALENDAR_TAG,SWAGGER_CALENDAR_TAG_DESCRIPTION),
            new Tag(SWAGGER_FILE_TAG,SWAGGER_FILE_TAG_DESCRIPTION))
        .apiInfo(getApiInfo());
  }

  private ApiInfo getApiInfo() {
    return new ApiInfo(
        SWAGGER_PROJECT_TITLE,
        SWAGGER_PROJECT_DESCRIPTION,
        SWAGGER_PROJECT_VERSION,
        SWAGGER_TERM_OF_SERVICE,
        new Contact(SWAGGER_FULL_NAME, SWAGGER_URL, SWAGGER_E_MAIL),
        SWAGGER_LICENCE,
        SWAGGER_LICENCE_URL,
        Collections.emptyList());
  }
}
