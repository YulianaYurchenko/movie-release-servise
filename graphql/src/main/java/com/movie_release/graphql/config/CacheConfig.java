package com.movie_release.graphql.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;

@EnableCaching
@Configuration
public class CacheConfig {
  @Autowired
  private CacheProperties properties;

  @Bean
  public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
    return RedisCacheManager.builder(redisConnectionFactory)
      .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(properties.getExpiration()))
      .build();
  }

  @Getter
  @Setter
  @Configuration
  @ConfigurationProperties("spring.redis.cache.expiration")
  public static class CacheProperties {
    private Duration expiration = Duration.parse("PT30M");
  }
}
