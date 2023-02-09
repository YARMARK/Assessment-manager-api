package by.leverx.googleTest.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
public class MapperConfig {

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

//  @Bean
//  public Resource getResource(){
//    return new ClassPathResource("templates/");
//  }

}
