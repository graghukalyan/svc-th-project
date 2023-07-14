package com.assignment.processor.cache;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Cache {

    private final ConcurrentHashMap<String,String> map;

    public Cache() {
        map = new ConcurrentHashMap<>();
    }

    public String getEntry(String key) {
        return map.get(key);
    }

    private String createCacheEntry(String key) {
        String result = map.get(key);
        if (result == null) {
            String putResult = map.putIfAbsent(key, "");
            if (putResult != null) {
                result = putResult;
            }
        }
        return result;
    }

    public void createCacheEntry(String key, String val) {
        map.putIfAbsent(key, val);
    }

    @SneakyThrows(NoSuchElementException.class)
    public boolean deleteCacheEntry(String key) {
        String val = getEntry(key);
        if (val != null) {
            return map.remove(key,val);
        } else {
            throw new NoSuchElementException("Requested Key doesn't exist");
        }
    }

    public Set<Map.Entry<String, String>> returnCacheEntries() {
        return map.entrySet();
    }

    public void clear() {
        map.clear();
    }

}
