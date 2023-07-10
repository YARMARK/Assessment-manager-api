package by.leverx.googleDrive.config.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

@Configuration
public class CacheConfiguration {

  private CachePropertiesProvider cachePropertiesProvider;

  @Autowired
  public CacheConfiguration(CachePropertiesProvider cachePropertiesProvider) {
    this.cachePropertiesProvider = cachePropertiesProvider;
  }

  @Profile(value = "dev")
  @Bean
  public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizerDev() {
    return (builder) -> builder
        .withCacheConfiguration(cachePropertiesProvider.getCacheName(),
            RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(5))
                .serializeValuesWith(SerializationPair.fromSerializer(
                    new GenericJackson2JsonRedisSerializer(redisObjectMapper()))));
  }

  @Bean
  public ObjectMapper redisObjectMapper() {
    return new ObjectMapper().registerModule(new JavaTimeModule());
  }
}
