package com.assignment.processor.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CacheManagerTest {

    private CacheManager cacheManager;

    @BeforeEach
    public void setUp() {
        cacheManager = new CacheManager();
    }

    @Test
    public void testInitializeStagingCache() {
        cacheManager.initializeStagingCache();

        // Assert that the staging cache is initialized
        assertNotNull(cacheManager.getCacheEntry(cacheManager.fetchThreadUniqueKey()));
    }

    @Test
    public void testCreateStagingCacheEntry() {
        String key = "key";
        String value = "value";

        cacheManager.initializeStagingCache();
        cacheManager.createStagingCacheEntry(key, value);

        // Assert that the cache entry was created
        Set<Map.Entry<String, Optional<String>>> cacheEntry = cacheManager.retrieveStagingCacheEntries();
        assertTrue(cacheEntry.size() > 0);
        assertEquals(key, cacheEntry.stream().findFirst().get().getKey());
    }

    @Test
    public void testCreatePrimaryCacheEntry() {
        String key = "key";
        String value = "value";

        CacheManager.createPrimaryCacheEntry(key, value);

        // Assert that the cache entry was created
        Optional<String> cacheEntry = cacheManager.getCacheEntry(key);
        assertTrue(cacheEntry.isPresent());
        assertEquals(value, cacheEntry.get());
    }

    @Test
    public void testGetCacheEntry() {
        String key = "key";
        String value = "value";
        CacheManager.createPrimaryCacheEntry(key, value);

        Optional<String> cacheEntry = cacheManager.getCacheEntry(key);

        // Assert that the cache entry is retrieved correctly
        assertTrue(cacheEntry.isPresent());
        assertEquals(value, cacheEntry.get());
    }

    @Test
    public void testMarkForDelete() {
        String key = "key";
        String value = "value";
        cacheManager.initializeStagingCache();
        boolean result = cacheManager.markForDelete(key);
        // Assert that the cache entry is marked for delete
        assertTrue(result);
    }

    @Test
    public void testDeleteCacheEntry() {
        String key = "key";
        String value = "value";
        CacheManager.createPrimaryCacheEntry(key, value);

        boolean result = CacheManager.deleteCacheEntry(key);

        // Assert that the cache entry is deleted
        assertTrue(result);
        assertFalse(cacheManager.getCacheEntry(key).isPresent());
    }

    @Test
    public void testRetrieveStagingCacheEntries() {
        String key1 = "key1";
        String value1 = "value1";
        String key2 = "key2";
        String value2 = "value2";
        cacheManager.initializeStagingCache();
        cacheManager.createStagingCacheEntry(key1, value1);
        cacheManager.createStagingCacheEntry(key2, value2);

        Set<Map.Entry<String, Optional<String>>> entries = cacheManager.retrieveStagingCacheEntries();

        // Assert that the staging cache entries are retrieved correctly
        assertEquals(2, entries.size());
        assertTrue(entries.stream().anyMatch(e -> e.getKey().equals(key1) && e.getValue().equals(Optional.of(value1))));
        assertTrue(entries.stream().anyMatch(e -> e.getKey().equals(key2) && e.getValue().equals(Optional.of(value2))));
    }

    @Test
    public void testClearStagingCache() {
        String key = "key";
        String value = "value";

        cacheManager.initializeStagingCache();
        cacheManager.createStagingCacheEntry(key, value);
        cacheManager.clearStagingCache();

        Set<Map.Entry<String, Optional<String>>> cacheEntry = cacheManager.retrieveStagingCacheEntries();

        // Assert that the staging cache is cleared
        assertTrue(cacheEntry.isEmpty());
    }

    @Test
    public void testFetchThreadUniqueKey() {
        // Assert that the unique key is generated correctly
        String uniqueKey = cacheManager.fetchThreadUniqueKey();
        assertEquals(Thread.currentThread().getName() + "-" + Thread.currentThread().getId(), uniqueKey);
    }
}