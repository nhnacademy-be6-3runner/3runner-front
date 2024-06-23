package com.nhnacademy.bookstore.global.config;

import java.time.Duration;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Object> sessionRedisTemplate = new RedisTemplate<>();
		sessionRedisTemplate.setConnectionFactory(redisConnectionFactory);


		sessionRedisTemplate.setKeySerializer(new StringRedisSerializer());
		sessionRedisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

		sessionRedisTemplate.setHashKeySerializer(new StringRedisSerializer());
		sessionRedisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
		sessionRedisTemplate.afterPropertiesSet();
		return sessionRedisTemplate;
	}

	@Bean
	public CacheManager cacheManager() {
		RedisCacheManager.RedisCacheManagerBuilder builder =
			RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory());

		RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
			.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
			.disableCachingNullValues()
			.entryTtl(Duration.ofHours(1L));

		builder.cacheDefaults(configuration);

		return builder.build();
	}

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory();
	}
}
