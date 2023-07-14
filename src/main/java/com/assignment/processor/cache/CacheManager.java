package com.assignment.processor.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Component
public class CacheManager {

    private final static Logger logger = LoggerFactory.getLogger(CacheManager.class);

    private static Cache stagingCache = new Cache();

    private static Cache primaryCache = new Cache();

    public static void createStagingCacheEntry(String key, String val) {
        logger.info (String.format("Create or Overwrite staging cache with key : %s  value : %s", key, val));
        stagingCache.createCacheEntry(key, val);
    }

    public static void createPrimaryCacheEntry(String key, String val) {
        logger.info (String.format("Create or Overwrite primary cache with key : %s  value : %s", key, val));
        primaryCache.createCacheEntry(key, val);
    }

    public static Optional<String> getCacheEntry(String key) {
        return primaryCache.getEntry(key);
    }

    public static boolean markForDelete(String key) {
        return stagingCache.markCacheEntryForDelete(key);
    }

    public static boolean deleteCacheEntry(String key) {
        return primaryCache.deleteCacheEntry(key);
    }

    public static Set<Map.Entry<String, Optional<String>>> retrieveStagingCacheEntries() {
        return stagingCache.returnCacheEntries();
    }

    public static void clearStagingCache() {
        stagingCache.clear();
    }

    //TODO : Handle Bootup scenario for handling multiple requests
    // & map UUID with empty staging cache
    public static void initializeStagingCache() {

        Map.of(UUID.randomUUID(),new Cache());
//        stagingCache = new Cache();

    }
}
