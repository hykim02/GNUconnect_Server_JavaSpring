package com.example.Jinus.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
public class RedisCacheManager {
    @Bean
    public CacheManager contentCacheManager(RedisConnectionFactory cf) {
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();

        // 1. 식당 리스트 캐시 (불변 데이터, 30일 유지)
        cacheConfigurations.put("cafeteriaList",
                RedisCacheConfiguration.defaultCacheConfig()
                        .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())) // Key Serializer
                        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())) // Value Serializer
                        .entryTtl(Duration.ofDays(30))
                        .disableCachingNullValues()); // null 캐싱 방지


        // 2. 식단 데이터 캐시 (하루 단위로 갱신)
//        cacheConfigurations.put("dietList",
//                RedisCacheConfiguration.defaultCacheConfig()
//                        .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())) // Key Serializer
//                        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())) // Value Serializer
//                        .entryTtl(Duration.ofHours(12))
//                        .disableCachingNullValues());

        // 3. 캠퍼스 이름 캐시 (불변 데이터, 30일 유지)
        cacheConfigurations.put("campusName",
                RedisCacheConfiguration.defaultCacheConfig()
                        .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())) // Key Serializer
                        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())) // Value Serializer
                        .entryTtl(Duration.ofDays(30))
                        .disableCachingNullValues()); // null 캐싱 방지

        // 3. 식당 id 캐시 (불변 데이터, 30일 유지)
        cacheConfigurations.put("cafeteriaId",
                RedisCacheConfiguration.defaultCacheConfig()
                        .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())) // Key Serializer
                        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())) // Value Serializer
                        .entryTtl(Duration.ofDays(30))
                        .disableCachingNullValues()); // null 캐싱 방지

        // 4. 캠퍼스 id 캐시 (불변 데이터, 30일 유지)
        cacheConfigurations.put("campusId",
                RedisCacheConfiguration.defaultCacheConfig()
                        .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())) // Key Serializer
                        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())) // Value Serializer
                        .entryTtl(Duration.ofDays(30))
                        .disableCachingNullValues()); // null 캐싱 방지

        // 5. 식당 url 캐시 (불변 데이터, 30일 유지)
        cacheConfigurations.put("cafeteriaUrl",
                RedisCacheConfiguration.defaultCacheConfig()
                        .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())) // Key Serializer
                        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())) // Value Serializer
                        .entryTtl(Duration.ofDays(30))
                        .disableCachingNullValues()); // null 캐싱 방지


        return org.springframework.data.redis.cache.RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(cf)
                .withInitialCacheConfigurations(cacheConfigurations) // 캐시별 설정 적용
                .build();
    }

}
