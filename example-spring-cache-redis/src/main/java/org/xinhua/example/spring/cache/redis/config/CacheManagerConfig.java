package org.xinhua.example.spring.cache.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@AutoConfigureAfter({RedisConfig.class})
public class CacheManagerConfig {

    @Autowired
    private CacheProperties cacheProperties;

    @Autowired
    private RedisSerializer redisSerializer;

    @Bean
    RedisSerializationContext.SerializationPair<Object> serializationPair() {
        return RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer);
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheManager redisCacheManager = new RedisCacheManager(
                RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory),
                getDefaultCacheConfigure(),
                getCacheConfigureMap());
        redisCacheManager.setTransactionAware(true);
        return redisCacheManager;
    }

    private RedisCacheConfiguration getDefaultCacheConfigure() {
        return getCacheConfigure(cacheProperties.getDefaultTtl());
    }

    private Map<String, RedisCacheConfiguration> getCacheConfigureMap() {
        Map<String, RedisCacheConfiguration> cacheConfigurationMap = new HashMap<>();
        Map<String, Long> customTtls = cacheProperties.getCustomTtls();
        if (customTtls != null) {
            customTtls.forEach((cacheName, ttl) -> cacheConfigurationMap.put(cacheName, getCacheConfigure(ttl == null ? -1 : ttl)));
        }
        return cacheConfigurationMap;
    }

    private RedisCacheConfiguration getCacheConfigure(long ttl) {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(ttl))
                .serializeValuesWith(serializationPair())
                //.disableCachingNullValues()
                .computePrefixWith(cacheKeyPrefix());
    }

    private CacheKeyPrefix cacheKeyPrefix() {
        return cacheName -> cacheProperties.getPrefix() + ":" + cacheName + ":";
    }

}