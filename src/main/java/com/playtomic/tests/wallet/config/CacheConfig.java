package com.playtomic.tests.wallet.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {


        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(buildCache("REQUEST_INFO_CACHE", 24, TimeUnit.HOURS, 3));
        return caffeineCacheManager;


    }

    private static Caffeine buildCache(String name, long ttl, TimeUnit ttlUnit, long size) {
        return Caffeine.newBuilder()
                .expireAfterWrite(ttl, ttlUnit)
                //.expireAfterAccess(ttl, ttlUnit)
                .maximumSize(size);

    }

}
