package com.assignment.processor.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class CacheTest {

    private Cache cache;

    @BeforeEach
    void setUp() {
        cache = new Cache();
    }

    @Test
    void testGetEntry_ExistingKey() {
        String key = "key";
        String value = "value";
        cache.createCacheEntry(key, value);
        Optional<String> result = cache.getEntry(key);
        assertTrue(result.isPresent());
        assertEquals(value, result.get());
    }

    @Test
    void testGetEntry_NonexistentKey() {
        String key = "nonexistent";
        Optional<String> result = cache.getEntry(key);
        assertFalse(result.isPresent());
    }

    @Test
    void testCreateCacheEntry() {
        String key = "key";
        String value = "value";
        cache.createCacheEntry(key, value);
        Optional<String> result = cache.getEntry(key);
        assertTrue(result.isPresent());
        assertEquals(value, result.get());
    }

    @Test
    void testDeleteCacheEntry_ExistingKey() {
        String key = "key";
        String value = "value";
        cache.createCacheEntry(key, value);
        assertTrue(cache.deleteCacheEntry(key));
        Optional<String> result = cache.getEntry(key);
        assertFalse(result.isPresent());
    }

    @Test
    void testDeleteCacheEntry_NonexistentKey() {
        String key = "nonexistent";
        assertThrows(NoSuchElementException.class, () -> cache.deleteCacheEntry(key));
    }

    @Test
    void testReturnCacheEntries() {
        String key1 = "key1";
        String value1 = "value1";
        String key2 = "key2";
        String value2 = "value2";
        cache.createCacheEntry(key1, value1);
        cache.createCacheEntry(key2, value2);
        Set<Map.Entry<String, Optional<String>>> entries = cache.returnCacheEntries();
        assertNotNull(entries);
        assertEquals(2, entries.size());
        assertTrue(entries.contains(new AbstractMap.SimpleEntry<>(key1, Optional.of(value1))));
        assertTrue(entries.contains(new AbstractMap.SimpleEntry<>(key2, Optional.of(value2))));
    }

    @Test
    void testClearCacheEntry() {
        String key = "key";
        String value = "value";
        cache.createCacheEntry(key, value);
        cache.clear();
        Optional<String> result = cache.getEntry(key);
        assertFalse(result.isPresent());
    }

    @Test
    void testMarkCacheEntryForDelete() {
        String key = "key";
        assertTrue(cache.markCacheEntryForDelete(key));
        Optional<String> result = cache.getEntry(key);
        assertFalse(result.isPresent());
    }
}