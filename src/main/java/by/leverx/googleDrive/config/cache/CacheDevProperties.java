package by.leverx.googleDrive.config.cache;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Data
@Component
@Profile(value = "dev")
public class CacheDevProperties implements CachePropertiesProvider{

  private String host;

  private String password;

  private int port;

  @Value("${spring.cache.cache-names}")
  private String cacheName;
}
