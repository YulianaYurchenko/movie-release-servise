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
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.time.Duration;

@EnableCaching
@Configuration
public class CacheConfig {
  @Autowired
  private RedisProperties properties;

  @Bean
  public RedisConnectionFactory redisConnectionFactory() {
    RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration(properties.getHost(), properties.getPort());
    return new LettuceConnectionFactory(redisConfig);
  }

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
  @ConfigurationProperties("spring.redis")
  public static class RedisProperties {
    private String host = "localhost";
    private int port = 6379;
    private Duration expiration = Duration.parse("PT2M");
  }
}
