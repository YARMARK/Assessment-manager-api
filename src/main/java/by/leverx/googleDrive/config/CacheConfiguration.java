package by.leverx.googleDrive.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

@Configuration
public class CacheConfiguration {

  @Value("${spring.cache.cache-names}")
  private String cacheName;

  @Bean
  public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
    return (builder) -> builder
        .withCacheConfiguration(cacheName,
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
