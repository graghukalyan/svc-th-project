package com.assignment.processor.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Component
public class CacheManager {
    private final static Logger logger = LoggerFactory.getLogger(CacheManager.class);
    private static HashMap<String, Cache> stagingCacheContainer = new HashMap<>();

    private static Cache primaryCache = new Cache();

    public void createStagingCacheEntry(String key, String val) {
        Cache stagingCache = stagingCacheContainer.get(fetchThreadUniqueKey());
        logger.info (String.format("Create or Overwrite staging cache with key : %s  value : %s", key, val));
        stagingCache.createCacheEntry(key, val);
        stagingCacheContainer.put(fetchThreadUniqueKey(),stagingCache);
    }

    public static void createPrimaryCacheEntry(String key, String val) {
        logger.info (String.format("Create or Overwrite primary cache with key : %s  value : %s", key, val));
        primaryCache.createCacheEntry(key, val);
    }

    public static Optional<String> getCacheEntry(String key) {
        return primaryCache.getEntry(key);
    }

    public boolean markForDelete(String key) {
        Cache stagingCache = stagingCacheContainer.get(fetchThreadUniqueKey());
        if(stagingCache.markCacheEntryForDelete(key)) {
            stagingCacheContainer.put(fetchThreadUniqueKey(),stagingCache);
            return true;
        }
        return false;
    }

    public static boolean deleteCacheEntry(String key) {
        return primaryCache.deleteCacheEntry(key);
    }

    public Set<Map.Entry<String, Optional<String>>> retrieveStagingCacheEntries() {

        return stagingCacheContainer.get(fetchThreadUniqueKey()).returnCacheEntries();
    }

    public void clearStagingCache() {
        stagingCacheContainer.get(fetchThreadUniqueKey()).clear();
        stagingCacheContainer.remove(fetchThreadUniqueKey());
    }

    //TODO : Handle Bootup scenario for handling multiple requests
    // & map UUID with empty staging cache
    public void initializeStagingCache() {

        String key = fetchThreadUniqueKey();
        logger.info (String.format("Creating instance of staging cache with key : %s ", key));
        stagingCacheContainer.put(key, new Cache());
    }

    private static String fetchThreadUniqueKey() {

        return Thread.currentThread().getName() + "-" + Thread.currentThread().getId();
    }
}
