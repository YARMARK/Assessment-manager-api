package by.leverx.googleDrive.config.cache;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Data
@Component
@Profile(value = "prod")
public class CacheProdProperties implements CachePropertiesProvider {

  @Value("${vcap.services.cache_db.credentials.hostname}")
  private String host;

  @Value("${vcap.services.cache_db.credentials.password}")
  private String password;

  @Value("${vcap.services.cache_db.credentials.port}")
  private int port;

  @Value("${spring.cache.cache-names}")
  private String cacheName;
}
