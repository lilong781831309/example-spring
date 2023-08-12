package org.xinhua.example.spring.cache.redis.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.cache")
public class CacheProperties {

    @Value("${prefix:cache}")
    private String prefix;

    @Value("${default-ttl:300}")
    private Long defaultTtl;

    private Map<String, Long> customTtls;

}