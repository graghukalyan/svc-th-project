package com.assignment.processor.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public class CacheManager {

    static Logger logger = LoggerFactory.getLogger(CacheManager.class);

    public static Cache stagingCache = new Cache();

    public static Cache primaryCache = new Cache();

    public static void createStagingCacheEntry(String key, String val) {
        logger.info (String.format("Create or Overwrite staging cache with key : %s  value : %s", key, val));
        stagingCache.createCacheEntry(key, val);
    }

    public static void createPrimaryCacheEntry(String key, String val) {
        logger.info (String.format("Create or Overwrite primary cache with key : %s  value : %s", key, val));
        primaryCache.createCacheEntry(key, val);
    }

    public static String getCacheEntry(String key) {
        return primaryCache.getEntry(key);
    }

    public static boolean deleteCacheEntry(String key) {
        return primaryCache.deleteCacheEntry(key);
    }

    public static Set<Map.Entry<String, String>> retrieveStagingCacheEntries() {
        return stagingCache.returnCacheEntries();
    }

    public static void clearStagingCache() {
        stagingCache.clear();
    }
}
