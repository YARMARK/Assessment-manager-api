package by.leverx.googleDrive.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class ApplicationConfig {

//  @Value("${spring.mail.username}")
//  private String username;
//
//  @Value("${spring.mail.password}")
//  private String password;

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Bean
  public ObjectMapper objectMapper() {
    JavaTimeModule module = new JavaTimeModule();
    return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .registerModule(module);
  }

  @Bean
  public Jackson2ObjectMapperBuilderCustomizer addCustomDateSerializer() {
    return builder -> builder.serializers(
        new LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME));
  }

//  @Bean
//  public JavaMailSender getMailSender (){
//    JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
//    mailSenderImpl.setHost("smtp.gmail.com");
//    mailSenderImpl.setPort(587);
//    mailSenderImpl.setUsername(username);
//    mailSenderImpl.setPassword(password);
//
//    Properties props = mailSenderImpl.getJavaMailProperties();
//    props.put("mail.transport.protocol","smtp");
//    props.put("mail.smtp.auth", "true");
//    props.put("mail.smtp.starttls.enable", "true");
//    props.put("mail.debug", "true");
//
//    return mailSenderImpl;
//   }
}
